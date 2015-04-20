package com.redhat.training.ui.pages;

import org.jboss.arquillian.graphene.page.Location;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@Location("faces/registration.xhtml")
public class RegistrationPage {
	
	@FindBy(id="login_form:username")
	private WebElement loginField;

	@FindBy(id="login_form:password")
	private WebElement passwordField;
	
	@FindBy(id="login_form:signin")
	private WebElement signInButton;
	
	public void signIn(String username, String password){
		loginField.sendKeys(username);
		passwordField.sendKeys(password);
		signInButton.click();
		
	}
	

}
