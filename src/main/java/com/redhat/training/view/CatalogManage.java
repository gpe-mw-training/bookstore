package com.redhat.training.view;

import java.io.Serializable;

import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import com.redhat.training.domain.CatalogItem;
import com.redhat.training.service.CatalogService;

@Named
@ConversationScoped
public class CatalogManage implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private CatalogService service;

	private boolean editable=true;
	
	private CatalogItem catalogItem=new CatalogItem();

	public CatalogItem getCatalogItem() {
		return catalogItem;
	}

	public void setCatalogItem(CatalogItem catalogItem) {
		this.catalogItem = catalogItem;
	}

	public String addItem() {
		try {
			service.addItem(catalogItem);
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("Item sucessfully added"));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("Item was not added"));
		}

		return "index";
	}

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

}
