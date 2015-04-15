package com.redhat.training.service;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.redhat.training.BaseWebTestTemplate;
import com.redhat.training.domain.Customer;
import com.redhat.training.view.ShoppingCart;

@RunWith(Arquillian.class)
public class OrderServiceTest extends BaseWebTestTemplate {

	@Inject
	private OrderService orderService;
	@Inject
	private ShoppingCart cart;
	@Inject 
	private CustomerService customerService;

	@PersistenceContext
	private EntityManager entityManager;
	
	@Test
	public void testBuyItemsFromShoppingCart() {
		Customer customer = customerService.findByCredentials("jdoe", "redhat123");
		cart.addItem(1);
		cart.setCustomer(customer);
		try{
			orderService.placeOrder(cart);
			Assert.assertTrue(true);
		}catch(PersistenceException e){
			Assert.assertFalse(true);
		}
		
	}

}
