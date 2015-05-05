package com.redhat.training.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class WishListItem implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@OneToOne
	@JoinColumn(name = "item_id")
	private CatalogItem catalogItem;

	public WishListItem() {
	}

	public WishListItem(CatalogItem item) {
		this.catalogItem = item;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public CatalogItem getCatalogItem() {
		return catalogItem;
	}

	public void setCatalogItem(CatalogItem catalogItem) {
		this.catalogItem = catalogItem;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((catalogItem == null) ? 0 : catalogItem.getId());
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
		WishListItem other = (WishListItem) obj;
		if (catalogItem == null) {
			if (other.catalogItem != null)
				return false;
		} else if (!catalogItem.getId().equals(other.catalogItem.getId()))
			return false;
		return true;
	}

}
