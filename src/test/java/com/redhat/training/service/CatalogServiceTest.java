package com.redhat.training.service;

import java.util.List;

import javax.inject.Inject;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.redhat.training.BaseWebTestTemplate;
import com.redhat.training.domain.CatalogItem;

@RunWith(Arquillian.class)
public class CatalogServiceTest extends BaseWebTestTemplate {

	@Inject
	private CatalogService service;

	@Test
	public void testNewArrivals() {
		List<CatalogItem> items = service.lookupItems(true);
		Assert.assertNotNull(items);
		Assert.assertEquals(12, items.size());

	}

	@Test
	public void testAllItems() {
		List<CatalogItem> items = service.lookupItems(false);
		Assert.assertNotNull(items);
		Assert.assertEquals(34, items.size());

	}

	@Test
	public void testByCategory() {
		List<CatalogItem> items = service.lookupItemsByCategory("children",
				false);
		Assert.assertNotNull(items);
		Assert.assertEquals(16, items.size());
	}

	@Test
	public void testByAllItemsUnavailableCategory() {
		List<CatalogItem> items = service.lookupItemsByCategory("sda", false);
		Assert.assertNotNull(items);
		Assert.assertEquals(0, items.size());
	}

	@Test
	public void testByCategoryNewItems() {
		List<CatalogItem> items = service.lookupItemsByCategory("children",
				true);
		Assert.assertNotNull(items);
		Assert.assertEquals(6, items.size());
		Assert.assertEquals("The Tale of Jemima Puddle Duck", items.get(0).getTitle());
	}

	@Test
	public void testGetCategories() {
		List<String> categories = service.getCategories();
		Assert.assertNotNull(categories);
		Assert.assertEquals(4, categories.size());
		Assert.assertTrue(categories.contains("military"));
		Assert.assertTrue(categories.contains("children"));
		Assert.assertTrue(categories.contains("comics"));
		Assert.assertTrue(categories.contains("crafts"));

	}

	@Test
	public void testSearchTitle() {
		List<CatalogItem> items = service.searchForItems("kittens");
		Assert.assertNotNull(items);
		Assert.assertEquals(1, items.size());
		Assert.assertEquals("The 3 Little Kittens", items.get(0).getTitle());
	}

	@Test
	public void testSearchCategory() {
		List<CatalogItem> items = service.searchForItems("military");
		Assert.assertNotNull(items);
		Assert.assertEquals(2, items.size());
		Assert.assertEquals("The Boy Scouts at the Panama-Pacific Exposition",
				items.get(0).getTitle());
	}

	@Test
	public void testSearchAuthor() {
		List<CatalogItem> items = service.searchForItems("howard");
		Assert.assertNotNull(items);
		Assert.assertEquals(1, items.size());
		Assert.assertEquals("The Boy Scouts at the Panama-Pacific Exposition",
				items.get(0).getTitle());
	}

	@Test
	public void testSearchDescription() {
		List<CatalogItem> items = service.searchForItems("description 4");
		Assert.assertNotNull(items);
		Assert.assertEquals(1, items.size());
		Assert.assertEquals("The Adventures of Maya the Bee", items.get(0)
				.getTitle());
	}
}
