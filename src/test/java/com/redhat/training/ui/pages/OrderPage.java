/*
 Copyright (c) 2015 Red Hat, Inc.

 Redistribution and use in source and binary forms, with or without
 modification, are permitted provided that the following conditions are
 met:

   1. Redistributions of source code must retain the above copyright
   notice, this list of conditions and the following disclaimer.

   2. Redistributions in binary form must reproduce the above
   copyright notice, this list of conditions and the following
   disclaimer in the documentation and/or other materials provided
   with the distribution.

 THIS SOFTWARE IS PROVIDED BY RED HAT, INC. ``AS IS'' AND ANY EXPRESS
 OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 DISCLAIMED. IN NO EVENT SHALL RED HAT, INC. BE LIABLE FOR ANY DIRECT,
 INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING
 IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 POSSIBILITY OF SUCH DAMAGE.
 */

package com.redhat.training.ui.pages;

import org.jboss.arquillian.graphene.page.Location;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@Location("faces/order.xhtml")
public class OrderPage {

	@FindBy(id = "order:address1")
	private WebElement addressField;

	@FindBy(id = "order:city")
	private WebElement cityField;

	@FindBy(id = "order:state")
	private WebElement stateField;
	@FindBy(id = "order:zip")
	private WebElement zipCodeField;
	@FindBy(id = "order:country")
	private WebElement countryField;
	@FindBy(id = "order:shipsame")
	private WebElement shipSameAddressCheckBox;
	@FindBy(id = "order:nextButton")
	private WebElement nextButton;
	@FindBy(id="order:saddress1")
	private WebElement shippingAddressField;

	@FindBy(id="order:scity")
	private WebElement shippingCityField;
	@FindBy(id="order:sstate")
	private WebElement shippingStateField;
	
	@FindBy(id="order:szip")
	private WebElement shippingZipCode;
	
	@FindBy(id="order:scountry")
	private WebElement shippingCountry;
	

	public void updateBillingAddress(String address, String city,
			String state, String zipCode, String country) {
			addressField.sendKeys(address);
			cityField.sendKeys(city);
			stateField.sendKeys(state);
			zipCodeField.sendKeys(zipCode);
			countryField.sendKeys(country);
			shippingAddressField.sendKeys(address);
			shippingCityField.sendKeys(city);
			shippingStateField.sendKeys(state);
			shippingZipCode.sendKeys(zipCode);
			shippingCountry.sendKeys(country);
			nextButton.click();
	}

}
