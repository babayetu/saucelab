package com.paypal.qa.risk.paypal.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.paypal.qa.risk.mint.util.Helper;

public class PayPalHomePage {

	public static WebElement getEmailAddressTextField() {
		return Helper.element(By.id("login_email"));
	}

	public static WebElement getPasswordTextField() {
		return Helper.element(By.id("login_password"));
	}

	public static WebElement getLoginButton(){
		return Helper.element(By.xpath("//input[@name='submit.x']"));
	}
}
