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
import com.redhat.training.domain.Promotion;

@Singleton
@Startup
public class DatabasePopulator {

	@PersistenceContext
	private EntityManager mgr;
	
	@Inject
	private PropertyManager props;

	@PostConstruct
	public void setup() {
		System.out.println("setup()");
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
			loadPromotions();
		}
	}

	private void loadPromotions() {
		Promotion promotion = new Promotion("kid10",10,true);
		mgr.persist(promotion);
		promotion = new Promotion("big61", 50, true);
		mgr.persist(promotion);
		
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
		item = new CatalogItem("45678",
				"The Art of War",
				new BigDecimal("20.00"), "description 5", "Sunzi",
				"/images/books/art-of-war-sunzi-lionel-giles.jpg", "military", false);
		mgr.persist(item);
		item = new CatalogItem("45679",
				"Captain Blood",
				new BigDecimal("22.95"), "description 6", "Rafael Sabatini",
				"/images/books/CaptainBlood.jpg", "children", false);
		mgr.persist(item);
		item = new CatalogItem("45680",
				"The Golden Book of Chemistry Experiments",
				new BigDecimal("32.95"), "description 7", "Robert Brent",
				"/images/books/ChemistryExperiments.png", "crafts", false);
		mgr.persist(item);
		item = new CatalogItem("45681",
				"The Thing from the Lake",
				new BigDecimal("25.95"), "description 8", "Eleanor M. Ingram",
				"/images/books/coverthingfromthelake.jpg", "children", false);
		mgr.persist(item);
		item = new CatalogItem("45682",
				"The Craft of Crochet Hook",
				new BigDecimal("35.95"), "description 9", "Flora Klickmann",
				"/images/books/CraftOfCrochet.jpg", "crafts", false);
		mgr.persist(item);
		item = new CatalogItem("45683",
				"Contes Mauves de ma Mere-Grand",
				new BigDecimal("45.95"), "description 10", "Charles Robert Dumas",
				"/images/books/Design-Book-cover-Juvenile-Contes-Mauves.jpg", "children", false);
		mgr.persist(item);
		item = new CatalogItem("45684",
				"English Fairy Tales",
				new BigDecimal("25.95"), "description 11", "Joseph Jacobs",
				"/images/books/EnglishFairyTales.jpg", "children", false);
		mgr.persist(item);
		item = new CatalogItem("45685",
				"The Home Art Book of Fancy Stitchery",
				new BigDecimal("45.95"), "description 12", "Flora Klickmann",
				"/images/books/FancyStitchery.jpg", "crafts", false);
		mgr.persist(item);
		item = new CatalogItem("45686",
				"The Farmer's Wife Canning and Preserving Cookbook",
				new BigDecimal("20.95"), "description 13", "Lela Nargi",
				"/images/books/FarmersWifeCanning.jpg", "crafts", false);
		mgr.persist(item);
		item = new CatalogItem("45687",
				"The Home Art of Crochet Book",
				new BigDecimal("15.95"), "description 14", "Flora Kickmann",
				"/images/books/HomeArtCrochet.jpg", "crafts", false);
		mgr.persist(item);
		item = new CatalogItem("45688",
				"Le Kybalion",
				new BigDecimal("30.95"), "description 15", "Three Initiates",
				"/images/books/LeKybalion.jpg", "crafts", false);
		mgr.persist(item);
		item = new CatalogItem("45689",
				"The Mary Frances Garden Book",
				new BigDecimal("40.95"), "description 16", "Jane Eayre Fryer",
				"/images/books/MaryFrancesGarden.jpg", "children", false);
		mgr.persist(item);
		item = new CatalogItem("45690",
				"The Mary Frances Housekeeper",
				new BigDecimal("55.95"), "description 17", "Jane Eayre Fryer",
				"/images/books/MaryFrancesHousekeeper.jpg", "children", false);
		mgr.persist(item);
		item = new CatalogItem("45691",
				"My New Alphabet Book",
				new BigDecimal("22.95"), "description 18", "Religious Tract Society",
				"/images/books/NewAlphabet.jpg", "children", false);
		mgr.persist(item);
		item = new CatalogItem("45691",
				"The Tale of Peter Rabbit",
				new BigDecimal("14.95"), "description 19", "Beatrix Potter",
				"/images/books/PeterRabbit.png", "children", false);
		mgr.persist(item);

		item = new CatalogItem("45677",
				"Camera Comics",
				new BigDecimal("195.00"), "expensive sucker", "Unknown",
				"/images/books/CameraComics.jpg", "comics", false);
		mgr.persist(item);
		item = new CatalogItem("45692",
				"Authentic Police Cases",
				new BigDecimal("200.00"), "expensive sucker", "Unknown",
				"/images/books/PoliceCases.jpg", "comics", false);
		mgr.persist(item);
		item = new CatalogItem("45693",
				"The Practical Book of Oriental Rugs",
				new BigDecimal("205.00"), "description 20", "G.Griffin Lewis",
				"/images/books/PracticalBookOfOrientalRugs.jpg", "crafts", false);
		mgr.persist(item);
		item = new CatalogItem("45694",
				"Priscilla Juniors' Crochet Book",
				new BigDecimal("40.00"), "description 21", "Priscilla Publishing Co.",
				"/images/books/PriscillaCrochetBook.jpg", "crafts", false);
		mgr.persist(item);
		item = new CatalogItem("45695",
				"The Public Domain Code Book",
				new BigDecimal("25.00"), "description 22", "Tony Laidig",
				"/images/books/PublicDomainCodeBook.jpg", "crafts", true);
		mgr.persist(item);

		item = new CatalogItem("45696",
				"The Adventures of Reddy Fox",
				new BigDecimal("10.95"), "description 23", "Thornton W. Burgess",
				"/images/books/ReddyFox.jpg", "children", true);
		mgr.persist(item);
		item = new CatalogItem("45697",
				"The Science of Getting Rich",
				new BigDecimal("5.95"), "description 24", "Wallace Wattles",
				"/images/books/ScienceOfGettingRich.jpg", "crafts", true);
		mgr.persist(item);
		item = new CatalogItem("45698",
				"The Casebook of Sherlock Holmes",
				new BigDecimal("20.95"), "description 25", "Arthur Conan Doyle",
				"/images/books/SherlockHolmes.jpg", "children", true);
		mgr.persist(item);

		item = new CatalogItem("45698",
				"The Story of a Stuffed Elephant",
				new BigDecimal("10.95"), "description 26", "Laura Lee Hope",
				"/images/books/StuffedElephant.jpg", "children", true);
		mgr.persist(item);

		item = new CatalogItem("45699",
				"Supergirl",
				new BigDecimal("4.95"), "description 27", "DC Comics",
				"/images/books/SuperGirl.jpg", "comics", true);
		mgr.persist(item);
		item = new CatalogItem("45600",
				"The Tale of Pigling Bland",
				new BigDecimal("14.95"), "description 28", "Beatrix Potter",
				"/images/books/TaleOfPiglingBland.jpg", "children", true);
		mgr.persist(item);
		item = new CatalogItem("45601",
				"The Offering",
				new BigDecimal("12.95"), "description 29", "Grace McCleen",
				"/images/books/The-Offering-lg.jpg", "crafts", true);
		mgr.persist(item);
		item = new CatalogItem("45602",
				"Time Detectives",
				new BigDecimal("22.95"), "description 30", "Brian Fagan",
				"/images/books/TimeDetectives.jpg", "crafts", true);
		mgr.persist(item);		
		item = new CatalogItem("45603",
				"Undercover Girl",
				new BigDecimal("220.95"), "description 31", "Starr Flagg",
				"/images/books/UndercoverGirl.jpg", "comics", false);
		mgr.persist(item);		
		item = new CatalogItem("45604",
				"The Yellow Fairy Book",
				new BigDecimal("22.95"), "description 32", "Andrew Lang",
				"/images/books/YellowFairy.jpg", "children", true);
		mgr.persist(item);		

		System.out.println("Loaded catalog");
	}
	
	private void loadCustomers() {
		Customer customer = new Customer("John","Doe", "jdoe", "redhat", "jdoe@doe.com");
		mgr.persist(customer);
		customer = new Customer("Guest","User", "guest", "user", "guest@doe.com");
		mgr.persist(customer);
		customer = new Customer("Admin","User", "admin", "redhat", "admin@bookshop.com",true);
		mgr.persist(customer);

		
	}
}
