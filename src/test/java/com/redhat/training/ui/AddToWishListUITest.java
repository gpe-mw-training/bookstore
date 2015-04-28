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

import org.jboss.arquillian.graphene.page.InitialPage;
import org.jboss.arquillian.graphene.page.Page;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.redhat.training.BaseUITestTemplate;
import com.redhat.training.ui.pages.BuyPage;
import com.redhat.training.ui.pages.CategoryPage;
import com.redhat.training.ui.pages.DetailsPage;
import com.redhat.training.ui.pages.HomePage;
import com.redhat.training.ui.pages.OrderPage;
import com.redhat.training.ui.pages.PaymentPage;
import com.redhat.training.ui.pages.RegistrationPage;
import com.redhat.training.ui.pages.ReviewPage;

@RunWith(Arquillian.class)
public class AddToWishListUITest extends BaseUITestTemplate {

	@Page
	private HomePage homePage;
	@Page
	private CategoryPage categoryPage;

	@Page
	private DetailsPage detailsPage;

	@Page
	private BuyPage buyPage;

	@Page
	private RegistrationPage registrationPage;
	
	@Page
	private PaymentPage paymentPage;
	
	@Page
	private ReviewPage reviewPage;
	
	@Page
	private OrderPage orderPage;

	@Test
	@InSequence(1)
	public void testPurchase(@InitialPage RegistrationPage registrationPage) {
		registrationPage.signIn("admin", "redhat");
		homePage.hoverOverChildCategoryPage();
		categoryPage.selectFirstBook();
		detailsPage.addToWishList();

	}

}
