package com.redhat.training.service;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.redhat.training.domain.Promotion;

@Stateless
public class DiscountService {
	
	@PersistenceContext
	private EntityManager mgr;
	
	public Promotion getPromotionCode(String code){
		CriteriaBuilder builder = mgr.getCriteriaBuilder();
		CriteriaQuery<Promotion> criteria = builder.createQuery(Promotion.class);
		Root<Promotion> root = criteria.from(Promotion.class);

		CriteriaQuery<Promotion> select = criteria.select(root);
		Predicate codeValue = builder.equal(root.get("code"), code);
		Predicate valid = builder.equal(root.get("valid"), true);
		Predicate and = builder.and(codeValue,valid);
		TypedQuery<Promotion> query = mgr.createQuery(select
				.where(and));
		try {
			return query.getSingleResult();	
		} catch(NoResultException e) {
			return null;
		}
	}
}
