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

@Stateless
public class CustomerService {

	@PersistenceContext
	private EntityManager mgr;

	public Customer findByCredentials(String username, String password) {

		CriteriaBuilder builder = mgr.getCriteriaBuilder();
		CriteriaQuery<Customer> criteria = builder.createQuery(Customer.class);
		Root<Customer> root = criteria.from(Customer.class);

		TypedQuery<Customer> query = mgr.createQuery(criteria.select(root)
				.where(builder.equal(root.get("username"), username)));
		try {
			return query.getSingleResult();	
		} catch(NoResultException e) {
			return null;
		}

	}
	
	public boolean createUser(String firstName, String lastName, String email, String username, String password) {
		Customer check = findByCredentials(username, password);
		if (check != null) {
			return false;
		}
		Customer customer = new Customer(firstName, lastName, username, password, email);
		mgr.persist(customer);
		
		return true;
	}

}
