package com.redhat.training.service;

import java.util.UUID;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.redhat.training.domain.Customer;
import com.redhat.training.domain.UserToken;

@Stateless
public class RememberMeService {

	@PersistenceContext
	private EntityManager entityManager;

	public String recordToken(Customer customer) {
		String uniqueId = UUID.randomUUID().toString();
		UserToken token = new UserToken(customer, uniqueId);
		entityManager.persist(token);
		return uniqueId;
	}

	public UserToken findToken(String key) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<UserToken> criteria = builder
				.createQuery(UserToken.class);
		Root<UserToken> root = criteria.from(UserToken.class);
		TypedQuery<UserToken> query = entityManager.createQuery(criteria
				.select(root).where(builder.equal(root.get("token"), key)));
		try {
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}

	}

	public UserToken findToken(Customer customer) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<UserToken> criteria = builder
				.createQuery(UserToken.class);
		Root<UserToken> root = criteria.from(UserToken.class);
		TypedQuery<UserToken> query = entityManager.createQuery(criteria
				.select(root).where(
						builder.equal(root.get("customer"), customer)));
		try {
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}

	}

	public void logout(Customer customer) {
		UserToken token = findToken(customer);
		if(token!=null){
			entityManager.remove(token);	
		}
		
	}

}
