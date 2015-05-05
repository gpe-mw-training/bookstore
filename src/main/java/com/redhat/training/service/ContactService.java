package com.redhat.training.service;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.redhat.training.domain.Contact;

@Stateless
public class ContactService {

	@PersistenceContext
	private EntityManager entityManager;
	
	
	public void createMessageContact(Contact contact){
		entityManager.persist(contact);
	}
}
