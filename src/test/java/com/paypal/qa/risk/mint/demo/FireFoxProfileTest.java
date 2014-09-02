package com.paypal.qa.risk.mint.demo;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.paypal.qa.risk.mint.util.Helper;
import com.paypal.qa.risk.mint.util.SauceTest;

public class FireFoxProfileTest extends SauceTest {

	/**
	 * -DSELENIUM_BROWSER=firefox
	 * -DFF_PROFILE_FILE="/Users/shiyao/Library/Application Support/Firefox/Profiles/b6fsc6nc.default"
	 */
    @Test
	public void validateAdminPageTitle() {
		Helper.driver().get("https://admin.stage2p2516.qa.paypal.com/cgi-bin/admin");
		assertEquals("Login - PayPal Administrative Tools", Helper.driver().getTitle());
	}
}
