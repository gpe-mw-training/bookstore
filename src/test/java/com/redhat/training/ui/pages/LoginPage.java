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
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage {

	@FindBy(id = "body")
	private WebElement fieldsetWithLoginAndPassword;
	@FindBy(id="loginBox")
	private WebElement loginBox;
	
	@FindBy(id="loginForm:username")
	private WebElement usernameField;
	
	@FindBy(id="loginForm:password")
	private WebElement passwordField;
	
	@FindBy(id = "loginForm:login")
	private WebElement loginButton;

	@FindBy(id = "logoutButton")
	private WebElement logoutButton;
	
	@FindBy(id = "loginForm:login_header")
	private WebElement tableHeader;
	
	
	public boolean isUsernameLabel(){
		return fieldsetWithLoginAndPassword.findElement(By.id("usernameLabel")).getText().equals("Username");
	}

	
	public boolean login(String username, String password) {
		if(!loginBox.isDisplayed()){
			loginBox.click();
		}
		usernameField.sendKeys(username);
		passwordField.sendKeys(password);
		Graphene.guardHttp(loginButton).click();
		return logoutButton.isDisplayed();
	}
}
