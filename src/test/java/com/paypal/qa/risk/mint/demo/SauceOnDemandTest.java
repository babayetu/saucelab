package com.paypal.qa.risk.mint.demo;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.paypal.qa.risk.mint.util.Helper;
import com.paypal.qa.risk.mint.util.SauceTest;

public class SauceOnDemandTest extends SauceTest {

	@Test
	public void validateTitle() {
		Helper.driver().get("https://www.stage2p2516.qa.paypal.com/us/cgi-bin/webscr?cmd=_login-run");
		assertEquals("Login United States - PayPal", Helper.driver().getTitle());
	}
}
