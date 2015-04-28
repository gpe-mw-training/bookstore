package com.redhat.training.service;

import javax.inject.Inject;

import junit.framework.Assert;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.redhat.training.BaseWebTestTemplate;
import com.redhat.training.domain.Customer;

@RunWith(Arquillian.class)
public class CustomerServiceTest extends BaseWebTestTemplate {

	@Inject
	private CustomerService customerService;

	@Test
	public void testCreateNewUser() {
		boolean success = customerService.createUser("Mary", "Doe", "mary@doe.com", "mdoe", "marydoe123");
		Assert.assertTrue(success);
	}

	@Test
	public void testCreateExistingUser() {
		boolean success = customerService.createUser("John", "Doe", "john.doe@redhat.com", "jdoe", "johndoe123");
		Assert.assertFalse(success);
	}
	
	@Test
	public void testFindExistingUser() {
		Customer customer = customerService.findByCredentials("jdoe", "johndoe123");
		Assert.assertNotNull(customer);
		Assert.assertEquals("John", customer.getFirstName());
		Assert.assertEquals("Doe", customer.getLastName());
	}
	@Test
	public void testFindUnexistentUser() {
		Customer customer = customerService.findByCredentials("amanda", "johndoe123");
		Assert.assertNull(customer);
	}
}
