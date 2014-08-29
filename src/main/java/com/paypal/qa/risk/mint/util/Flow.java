package com.paypal.qa.risk.mint.util;

import com.paypal.qa.risk.paypal.page.PayPalHomePage;

public final class Flow {

	public static void logIn(String userEmail, String password) {
		Helper.driver().get("https://www.stage2p2516.qa.paypal.com/us/cgi-bin/webscr?cmd=_login-run");
		PayPalHomePage.getEmailAddressTextField().sendKeys(userEmail);
		PayPalHomePage.getPasswordTextField().sendKeys(password);
		PayPalHomePage.getLoginButton().click();
	}
}
