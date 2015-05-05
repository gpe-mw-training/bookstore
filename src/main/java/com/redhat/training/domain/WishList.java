package com.redhat.training.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
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
	@OneToMany(cascade=CascadeType.ALL)
	@JoinColumn(name="wishitem_id")
	
	private Set<WishListItem> items;

	public WishList() {
	}
	
	public WishList(Customer customer) {
		this.customer = customer;
	}

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

	public void removeItem(CatalogItem item) {
		if(items!=null){
			WishListItem wishListItem = new WishListItem();
			wishListItem.setCatalogItem(item);
			if(items.contains(wishListItem)){
				items.remove(wishListItem);
			}
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((customer == null) ? 0 : customer.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		WishList other = (WishList) obj;
		if (customer == null) {
			if (other.customer != null)
				return false;
		} else if (!customer.getUsername().equals(other.customer.getUsername()))
			return false;
		return true;
	}

	public void addItem(CatalogItem item) {
		if(items==null){
			items = new HashSet<WishListItem>();
		}
		items.add(new WishListItem(item));
	}
	

}
