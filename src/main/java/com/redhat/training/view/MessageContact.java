package com.redhat.training.view;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.redhat.training.domain.Contact;
import com.redhat.training.service.ContactService;

@Named
@RequestScoped
public class MessageContact implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private ContactService contactService;

	private Contact contact = new Contact();

	public Contact getContact() {
		return contact;
	}

	public void setContact(Contact contact) {
		this.contact = contact;
	}

	public String send(){
		contactService.createMessageContact(contact);
		return "index";
	}
	
}
