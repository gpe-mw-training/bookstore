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

import java.util.Calendar;
import java.util.Date;

import org.jboss.arquillian.graphene.Graphene;
import org.jboss.arquillian.graphene.page.Location;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

@Location("faces/jbtFSearch.xhtml")
public class FlightSearchPage {

	@FindBy(id = "searchFlight_header")
	private WebElement tableHeader;

	@FindBy(id = "searchFlightForm:originField")
	private WebElement origin;

	@FindBy(id = "searchFlightForm:destField")
	private WebElement destination;

	@FindBy(id = "searchFlightForm:flightSearchButton")
	private WebElement searchButton;

	@FindBy(id = "searchFlightForm:departureDateFieldPopupButton")
	private WebElement calendar;
	@FindBy(id = "searchFlightForm:departureDateFieldContent")
	private WebElement popup;
	@FindBy(id = "searchFlightForm:departureDateFieldEditor")
	private WebElement monthpicker;
	
	@FindBy
	private WebElement status;

	public boolean isOnPage() {
		try {
			return tableHeader.getText().equals("Search for a Flight");
		} catch (NoSuchElementException e) {
			return false;
		}

	}

	public void search(Date depart, String origin, String destination) {
		Assert.assertTrue(isOnPage());
		Assert.assertEquals("User is logged in", "Welcome: ", status
				.getText().trim().substring(0, 9));
		if (depart != null) {
			setDate(depart);
		}
		Select originSelect = new Select(this.origin);
		originSelect.selectByValue(origin);
		Select destSelect = new Select(this.destination);
		destSelect.selectByValue(destination);
		Graphene.guardHttp(searchButton).click();
	}

	private void setDate(Date date) {
		// Break date into elements
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		String day = String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
		int month = cal.get(Calendar.MONTH);
		String year = String.valueOf(cal.get(Calendar.YEAR));
		// open popup calendar
		calendar.click();
		WebElement monthButton = popup.findElement(
				By.className("rf-cal-hdr-month")).findElement(By.xpath("div"));

		monthButton.click();

		WebElement m = monthpicker.findElement(By
				.id("searchFlightForm:departureDateFieldDateEditorLayoutM"
						+ month));
		m.click(); // select month
		for (int i = 0; i < 10; i++) {
			WebElement y = monthpicker.findElement(By
					.id("searchFlightForm:departureDateFieldDateEditorLayoutY"
							+ i));
			if (y.getText().equals(year)) {
				y.click(); // select year
				break;
			}
		}
		monthpicker.findElement(By.className("rf-cal-monthpicker-ok")).click();
		// find day
		for (int i = 0; i < 42; i++) {
			WebElement d = popup.findElement(By
					.id("searchFlightForm:departureDateFieldDayCell" + i));
			if (d.getText().equals(day)) {
				d.click(); // select day
				break;
			}
		}
	}
}
