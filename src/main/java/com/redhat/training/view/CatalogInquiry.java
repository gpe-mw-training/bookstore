package com.redhat.training.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.redhat.training.domain.CatalogItem;
import com.redhat.training.service.CatalogService;

@Named
@ConversationScoped
public class CatalogInquiry implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private CatalogService service;
	@Inject
	private Conversation conversation;
	@Inject
	private ShoppingCart cart;

	private boolean newItems = false;
	private String category;
	private CatalogItem item;
	private String searchCriteria;

	private List<CatalogItem> items = new ArrayList<CatalogItem>();

	public List<CatalogItem> getItems() {
		return items;
	}

	public String newArrivals() {

		items = service.lookupItems(true);
		newItems = true;
		category = "all";
		if (conversation.isTransient())
			conversation.begin();

		return "category?faces-redirect=true";

	}

	public String allItems() {
		items = service.lookupItems(false);
		newItems = false;
		category = "all";
		if (conversation.isTransient())
			conversation.begin();

		return "category?faces-redirect=true";
	}

	public String byCategory(String category, boolean newItems) {
		items = service.lookupItemsByCategory(category, newItems);
		this.newItems = newItems;
		this.category = category;
		if (conversation.isTransient())
			conversation.begin();

		return "category?faces-redirect=true";
	}

	public String search() {
		if (searchCriteria != null && !"".equals(searchCriteria)) {
			items = service.searchForItems(searchCriteria);
			newItems = false;
			category = "Search for: " + searchCriteria;
			if (conversation.isTransient())
				conversation.begin();

			return "category?faces-redirect=true";
		}
		
		return null;
	}

	public String pickItem(CatalogItem item) {
		this.item = item;
		if (conversation.isTransient())
			conversation.begin();
		if (!cart.getViewed().contains(item))
			cart.getViewed().add(item);
		return "details?faces-redirect=true";
	}
	
	public String recentlyViewed() {
		return "viewed?faces-redirect=true";
	}

	public boolean isNewItems() {
		return newItems;
	}

	public String getCategory() {
		return category;
	}

	public CatalogItem getItem() {
		return item;
	}

	public String getSearchCriteria() {
		return searchCriteria;
	}

	public void setSearchCriteria(String searchCriteria) {
		this.searchCriteria = searchCriteria;
	}
}
