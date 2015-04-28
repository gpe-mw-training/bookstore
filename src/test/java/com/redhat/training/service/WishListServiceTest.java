package com.redhat.training.service;

import javax.inject.Inject;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.redhat.training.BaseWebTestTemplate;
import com.redhat.training.domain.Customer;
import com.redhat.training.domain.WishList;

@RunWith(Arquillian.class)
public class WishListServiceTest extends BaseWebTestTemplate {

	@Inject
	private WishListService wishListService;
	@Inject
	private CustomerService customerService;

	@Test
	public void testUsernameWishList() {
		Customer customer = customerService.findByCredentials("admin", "redhat");
		WishList wishList = wishListService.findByCustomer(customer);
		Assert.assertNull(wishList);
	}
}
