package com.redhat.training.service;

import javax.inject.Inject;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.redhat.training.BaseWebTestTemplate;
import com.redhat.training.domain.Customer;
import com.redhat.training.domain.UserToken;

@RunWith(Arquillian.class)
public class RememberMeServiceTest extends BaseWebTestTemplate {

	@Inject
	private RememberMeService rememberMeService;
	@Inject 
	private CustomerService customerService;

	@Test
	public void testCreateTokenForUser() {
		Customer customer = customerService.findByCredentials("jdoe", "redhat123");
		String token = rememberMeService.recordToken(customer);
		Assert.assertNotNull(token);
	}
	@Test
	public void testFindTokenForUser() {
		Customer customer = customerService.findByCredentials("jdoe", "redhat123");
		String token = rememberMeService.recordToken(customer);
		UserToken userToken = rememberMeService.findToken(token);
		Assert.assertEquals(customer, userToken.getCustomer());
	}
	
}
