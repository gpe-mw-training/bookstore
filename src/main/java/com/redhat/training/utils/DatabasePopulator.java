package com.redhat.training.utils;

import java.math.BigDecimal;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.search.MassIndexer;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;

import com.redhat.training.domain.CatalogItem;
import com.redhat.training.domain.Customer;

@Singleton
@Startup
public class DatabasePopulator {

	@PersistenceContext
	private EntityManager mgr;
	
	@Inject
	private PropertyManager props;

	@PostConstruct
	public void setup() {
		if (props.getPreLoadData()) {
			FullTextEntityManager ftem = Search.getFullTextEntityManager(mgr);
			MassIndexer indexer = ftem.createIndexer(CatalogItem.class);
			try {
				indexer.startAndWait();
			} catch (InterruptedException e) {
				System.out.println("Re-indexing failed while populating database");
				e.printStackTrace();
			}
			loadCatalog();
			loadCustomers();
		}
	}

	private void loadCatalog() {
		CatalogItem item = new CatalogItem("ABC123",
				"The Boy Scouts at the Panama-Pacific Exposition",
				new BigDecimal("45.00"), "description 1", "Lt. Howard Payson",
				"/images/books/BoyScouts.jpg", "military", true);
		mgr.persist(item);
		item = new CatalogItem("DEF456",
				"The 3 Little Kittens",
				new BigDecimal("15.00"), "description 2", "Unknown",
				"/images/books/3LittleKittens.jpg", "children", false);
		mgr.persist(item);
		item = new CatalogItem("GHI789",
				"The Tale of Jemima Puddle Duck",
				new BigDecimal("25.00"), "description 3", "Beatrix Potter",
				"/images/books/JemimaPuddleDuck.jpg", "children", true);
		mgr.persist(item);
		item = new CatalogItem("123132",
				"The Adventures of Maya the Bee",
				new BigDecimal("23.00"), "description 4", "Waldemar Bonsels",
				"/images/books/AdventuresOfMayaTheBee.jpg", "children", false);
		mgr.persist(item);
		item = new CatalogItem("45677",
				"Camera Comics",
				new BigDecimal("195.00"), "expensive sucker", "Unknown",
				"/images/books/CameraComics.jpg", "comics", false);
		mgr.persist(item);
		System.out.println("Loaded catalog");
	}
	
	private void loadCustomers() {
		Customer customer = new Customer("John","Doe", "jdoe", "redhat", "jdoe@doe.com");
		mgr.persist(customer);
	}
}
