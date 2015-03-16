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

import org.jboss.arquillian.graphene.Graphene;
import org.jboss.arquillian.graphene.page.Page;
import org.junit.Assert;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ConfirmationPage {

	@FindBy(id = "confirmation_header")
	private WebElement tableHeader;
	@FindBy(id = "reservation")
	private WebElement resId;
	@FindBy(id = "price")
	private WebElement price;
	@FindBy(id = "fees")
	private WebElement fees;
	@FindBy(xpath = "//a[text()='Select Seat']")
	private WebElement seatSelection;
	@FindBy(id = "seat")
	private WebElement seat;
	
	@Page
	private SeatPage seatPage;
	
	public boolean isOnPage() {
		try {
			return tableHeader.getText().equals("Reservation Confirmation");
		} catch(NoSuchElementException e) {
			return false;
		}
	}
	
	public int getConfirmationNumber() {
	
		return new Integer(resId.getText());
	}
	
	public String getPrice() {
		return price.getText();
	}
	
	public String getFees() {
		return fees.getText();
	}
	
	public String getSeat() {
		return seat.getText();
	}
	
	public void selectSeat(int seat) {
		Graphene.guardHttp(seatSelection).click();
		Assert.assertTrue(seatPage.isOnPage());
		seatPage.select(seat);
	}
}
