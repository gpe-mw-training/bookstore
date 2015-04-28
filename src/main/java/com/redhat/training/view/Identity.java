package com.redhat.training.view;

import javax.enterprise.inject.Model;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import com.redhat.training.domain.Customer;
import com.redhat.training.service.CustomerService;
import com.redhat.training.service.RememberMeService;

@Model
public class Identity {

	@Inject
	private CustomerService service;
	@Inject
	private RememberMeService rememberMeService;
	@Inject
	private ShoppingCart cart;

	private String username;
	private String password;
	private String retype;
	private String firstName;
	private String lastName;
	private String email;
	private boolean rememberMe = false;
	private boolean agreement = false;
	private String location = "index";

	public String login() {

		Customer customer = service.findByCredentials(username, password);

		if (customer == null) {
			password = "";
			FacesContext
					.getCurrentInstance()
					.addMessage(
							null,
							new FacesMessage(
									"Invalid account information was entered.  Please try again."));
			return "registration?faces-redirect=true";
		}

		if(rememberMe==true){
			
			String token = rememberMeService.recordToken(customer);
			HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
			Cookie cookie = createCookie(token);
			response.addCookie(cookie);
			
		}
		cart.setCustomer(customer);

		return location + "?faces-redirect=true";
	}

	private Cookie createCookie(String uniqueId) {
		Cookie cookie = new Cookie("auth_key", uniqueId);
		cookie.setMaxAge(-1);
		return cookie;
	}

	public void createAccount() {
		
		if (!agreement) {
			FacesContext.getCurrentInstance().addMessage(
					"registration_form:agreement",
					new FacesMessage(null, "Please agree to the terms of service"));
			return;
		}

		if (!password.equals(retype)) {
			FacesContext.getCurrentInstance().addMessage(
					"registration_form:password",
					new FacesMessage(null, "Passwords do not match"));
			return;
		}
		if (!service.createUser(firstName, lastName, email, username, password)) {
			FacesContext.getCurrentInstance().addMessage(
					"registration_form:username",
					new FacesMessage(null, "Username already exists"));
			return;
		}
		
		login();

	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRetype() {
		return retype;
	}

	public void setRetype(String retype) {
		this.retype = retype;
	}

	public boolean isAgreement() {
		return agreement;
	}

	public void setAgreement(boolean agreement) {
		this.agreement = agreement;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public boolean isRememberMe() {
		return rememberMe;
	}

	public void setRememberMe(boolean rememberMe) {
		this.rememberMe = rememberMe;
	}

}
