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

package com.redhat.training.ui;

import java.util.Calendar;

import org.jboss.arquillian.graphene.page.InitialPage;
import org.jboss.arquillian.graphene.page.Page;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.redhat.training.BaseUITestTemplate;
import com.redhat.training.ui.pages.ConfirmationPage;
import com.redhat.training.ui.pages.FlightReservationPage;
import com.redhat.training.ui.pages.FlightSearchPage;
import com.redhat.training.ui.pages.FlightSearchResultsPage;
import com.redhat.training.ui.pages.HomePage;

@RunWith(Arquillian.class)
public class ReservationUITest extends BaseUITestTemplate {
	
	@Page
	private FlightSearchResultsPage resultsPage;
	@Page
	private FlightReservationPage reservationPage;
	@Page
	private ConfirmationPage confirmationPage;
	
	private static int res = 0;
	
	@Test
	@InSequence(1)
	public void loginAsAdministrator(@InitialPage HomePage homePage) {

		homePage.login("admin", "admin");
	}
	
	@Test
	@InSequence(2)
	public void makeReservation(@InitialPage FlightSearchPage searchPage) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.MONTH, Calendar.FEBRUARY);
		cal.set(Calendar.DAY_OF_MONTH, 10);
		cal.set(Calendar.YEAR, 2015);
		searchPage.search(cal.getTime(), "SFO", "JFK");
		//System.out.println(browser.getPageSource());
		Assert.assertTrue(resultsPage.isOnPage());
		resultsPage.selectFlight(1);
		Assert.assertTrue(reservationPage.isOnPage());
		reservationPage.selectMeal("Chicken");
		reservationPage.checkBags(1);
		reservationPage.upgradeFirstClass();
		reservationPage.buyInsurance();
		reservationPage.confirm();
		Assert.assertTrue(confirmationPage.isOnPage());
		int resId = confirmationPage.getConfirmationNumber();
		System.out.println("Made reservation number: " + resId);
		res = resId;
	}
	
	@Test
	@InSequence(3)
	public void selectSeat() {
		if (res == 0)
			Assert.fail("No reservation created.  Please check prior tests.");
		browser.get(deploymentURL.toExternalForm() + "/faces/jbtFSearchConfirm.xhtml?resId=" + res);
		Assert.assertTrue(confirmationPage.isOnPage());
		confirmationPage.selectSeat(0);
		Assert.assertTrue(confirmationPage.isOnPage());
		System.out.println("Seat reserved: " + confirmationPage.getSeat());
	}
}
