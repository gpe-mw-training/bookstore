package com.redhat.training.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class WishList implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@OneToOne
	@JoinColumn(name = "cust_id")
	private Customer customer;
	@OneToMany
	@JoinColumn(name="wishitem_id")
	private Set<WishListItem> items;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Set<WishListItem> getItems() {
		return items;
	}

	public void setItems(Set<WishListItem> items) {
		this.items = items;
	}
	public void addItem(WishListItem item){
		if(items==null){
			items = new HashSet<WishListItem>();
		}
		items.add(item);
	}


	public List<CatalogItem> getAsCatalogItems() {
		List<CatalogItem> catalogItems = new ArrayList<CatalogItem>();
		if(items!=null){
			for (WishListItem wishListItem : items) {
				catalogItems.add(wishListItem.getCatalogItem());
			}
		}
		return catalogItems;
	}

}
