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
import org.jboss.arquillian.graphene.page.Page;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@Location("faces/index.xhtml")
public class HomePage {

	@FindBy(id = "bookshopLogoForm:logoImageLink")
	private WebElement bookshopLogoLink;
	
	@FindBy(id = "bookshopLogoForm:logoImage")
	private WebElement bookshopLogo;	
	
	@Page
	private LoginPage loginPage;
	
	@Page
	private MenuPage menuPage;
	
	public boolean isHomePageLinkAvailable() {
		try {
			return bookshopLogoLink.getAttribute("href").contains("index.xhtml");
		} catch (NoSuchElementException e) {
			return false;
		}
	}
	
	public boolean isHomePageLinkWithImage(){
		try {
			return bookshopLogo.getAttribute("src").contains("shoppe-logo.png");
		} catch (NoSuchElementException e) {
			return false;
		}
	}
	
	public boolean isUsernameLabelCorrect(){
		return loginPage.isUsernameLabelAvailable();
	}
	
	public boolean login(String username, String password){
		return loginPage.login(username, password);
	}
	
	public boolean hoverOverChildCategoryPage(){
		return menuPage.selectChildrenCategory();
	}

}
