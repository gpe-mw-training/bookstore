package com.redhat.training.service;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.redhat.training.domain.Customer;
import com.redhat.training.domain.WishList;

@Stateless
public class WishListService {

	@PersistenceContext
	private EntityManager mgr;

	public WishList findByCustomer(Customer customer) {

		CriteriaBuilder builder = mgr.getCriteriaBuilder();
		CriteriaQuery<WishList> criteria = builder.createQuery(WishList.class);
		Root<WishList> root = criteria.from(WishList.class);

		TypedQuery<WishList> query = mgr.createQuery(criteria.select(root)
				.where(builder.equal(root.get("customer"), customer)));
		try {
			return query.getSingleResult();	
		} catch(NoResultException e) {
			return null;
		}

	}
	
	
}
