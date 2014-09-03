package com.paypal.qa.risk.mint.demo;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.paypal.qa.risk.mint.util.Helper;
import com.paypal.qa.risk.mint.util.SauceTest;

public class SauceOnDemandTest extends SauceTest {

	@Test
	public void validateJenkinsSitePageTitle() {
		Helper.driver().get("http://jenkins-ci.org/");
		assertEquals("Welcome to Jenkins CI! | Jenkins CI", Helper.driver().getTitle());
	}
	
	@Test
	public void validateAdminPageTitle() {
		Helper.driver().get("https://admin.stage2p2516.qa.paypal.com/cgi-bin/admin");
		assertEquals("Login - PayPal Administrative Tools", Helper.driver().getTitle());
	}
}
