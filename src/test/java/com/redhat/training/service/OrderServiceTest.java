package com.redhat.training.service;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.PersistenceException;

import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.redhat.training.BaseWebTestTemplate;
import com.redhat.training.domain.Customer;
import com.redhat.training.domain.Order;
import com.redhat.training.view.ShoppingCart;

@RunWith(Arquillian.class)
public class OrderServiceTest extends BaseWebTestTemplate {

	@Inject
	private OrderService orderService;
	@Inject
	private ShoppingCart cart;
	@Inject 
	private CustomerService customerService;

	@Test
	@InSequence(1)
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
	@Test
	@InSequence(2)
	public void testListItemsBought() {
			List<Order> orders=orderService.getAllOpenOrders();
			Assert.assertEquals(1, orders.size());
		
	}
}
