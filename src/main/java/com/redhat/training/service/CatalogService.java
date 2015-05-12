package com.redhat.training.service;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.Query;
import org.apache.lucene.util.Version;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;

import com.redhat.training.domain.CatalogItem;

@Stateless
public class CatalogService {

	@PersistenceContext
	private EntityManager mgr;
	
	public CatalogItem getItem(Integer id) {
		return mgr.find(CatalogItem.class, id);
	}

	public List<CatalogItem> lookupItems(boolean newItems) {

		CriteriaBuilder builder = mgr.getCriteriaBuilder();
		CriteriaQuery<CatalogItem> criteria = builder
				.createQuery(CatalogItem.class);
		Root<CatalogItem> root = criteria.from(CatalogItem.class);
		if (newItems) {
			TypedQuery<CatalogItem> query = mgr.createQuery(criteria.select(
					root).where(builder.equal(root.get("newItem"), true)));
			return query.getResultList();
		} else {
			TypedQuery<CatalogItem> query = mgr.createQuery(criteria
					.select(root));
			return query.getResultList();
		}

	}

	public List<CatalogItem> lookupItemsByCategory(String category,
			boolean newItems) {
		CriteriaBuilder builder = mgr.getCriteriaBuilder();
		CriteriaQuery<CatalogItem> criteria = builder
				.createQuery(CatalogItem.class);
		Root<CatalogItem> root = criteria.from(CatalogItem.class);
		if (newItems) {
			TypedQuery<CatalogItem> query = mgr.createQuery(criteria.select(
					root).where(
					builder.and(builder.equal(root.get("newItem"), true),
							builder.equal(root.get("category"), category))));
			return query.getResultList();
		} else {
			TypedQuery<CatalogItem> query = mgr.createQuery(criteria.select(
					root).where(builder.equal(root.get("category"), category)));
			return query.getResultList();
		}
	}

	public List<String> getCategories() {

		TypedQuery<String> query = mgr.createQuery(
				"select distinct i.category from CatalogItem i", String.class);
		return query.getResultList();
	}

	public List<CatalogItem> searchForItems(String criteria) {
		try {
			FullTextEntityManager fem = Search.getFullTextEntityManager(mgr);

			MultiFieldQueryParser mfqp = new MultiFieldQueryParser(
					Version.LUCENE_36, new String[] { "category",
							"description", "title", "author" },
					new StandardAnalyzer(Version.LUCENE_36));
			mfqp.setDefaultOperator(QueryParser.Operator.AND);
			Query query = mfqp.parse(criteria);
			javax.persistence.Query fquery = fem.createFullTextQuery(query,
					CatalogItem.class);
			@SuppressWarnings("unchecked")
			List<CatalogItem> items = (List<CatalogItem>) fquery
					.getResultList();
			return items;
		} catch (ParseException e) {
			System.out.println(e);
			return new ArrayList<CatalogItem>();
		}

	}

	public void addItem(CatalogItem catalogItem) {
		mgr.persist(catalogItem);
	}
}
