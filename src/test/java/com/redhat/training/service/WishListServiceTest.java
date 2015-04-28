package com.redhat.training.service;

import javax.inject.Inject;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.redhat.training.BaseWebTestTemplate;
import com.redhat.training.domain.CatalogItem;
import com.redhat.training.domain.Customer;
import com.redhat.training.domain.WishList;

@RunWith(Arquillian.class)
public class WishListServiceTest extends BaseWebTestTemplate {

	@Inject
	private WishListService wishListService;
	@Inject
	private CustomerService customerService;
	@Inject
	private CatalogService catalogService;

	@After
	public void cleanDatabase(){
		Customer customer = customerService.findByCredentials("admin", "redhat");
		wishListService.removeWishList(customer);
	}
		
	@Test
	public void testUsernameWishList() {
		Customer customer = customerService.findByCredentials("admin", "redhat");
		WishList wishList = wishListService.findByCustomer(customer);
		Assert.assertNotNull(wishList);
		Assert.assertNull(wishList.getItems());
	}
	@Test
	public void testAddingToWishList() {
		Customer customer = customerService.findByCredentials("admin", "redhat");
		WishList wishList = wishListService.findByCustomer(customer);
		CatalogItem item1 = catalogService.getItem(123132);
		CatalogItem item2 = catalogService.getItem(45678);
		wishListService.addItem(customer, item1);
		wishListService.addItem(customer, item2);
		wishList = wishListService.findByCustomer(customer);
		Assert.assertNotNull(wishList);
		Assert.assertEquals(2,wishList.getAsCatalogItems().size());
	}
}
