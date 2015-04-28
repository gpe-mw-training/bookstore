package com.redhat.training.ui.pages;

import org.jboss.arquillian.graphene.page.Location;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@Location("faces/review.xhtml")
public class ReviewPage {

	@FindBy(id="order:sendButton")
	private WebElement sendButton;
	
	public void placeOrder(){
		sendButton.click();
	}

}
