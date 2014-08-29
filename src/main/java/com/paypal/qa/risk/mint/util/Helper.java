package com.paypal.qa.risk.mint.util;

import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class Helper {

	private static WebDriver driver;
	public static URL serverAddress;
	private static WebDriverWait driverWait;

	/**
	 * Initialize the webdriver. Must be called before using any helper methods.
	 * *
	 */
	public static void init(WebDriver webDriver) {
		driver = webDriver;
		int timeoutInSeconds = 60;
		// must wait at least 60 seconds for running on Sauce.
		// waiting for 30 seconds works locally however it fails on Sauce.
		driverWait = new WebDriverWait(webDriver, timeoutInSeconds);
	}

	/**
	 * Set implicit wait in seconds *
	 */
	public static void setWait(int seconds) {
		driver.manage().timeouts().implicitlyWait(seconds, TimeUnit.SECONDS);
	}

	/**
	 * Return an element by locator *
	 */
	public static WebElement element(By locator) {
		return driver.findElement(locator);
	}

	/**
	 * Return a list of elements by locator *
	 */
	public static List<WebElement> elements(By locator) {
		return driver.findElements(locator);
	}

	public static WebDriver driver() {
		return driver;
	}

	public static String readPropertyOrEnv(String key, String defaultValue) {
		String v = System.getProperty(key);
		if (v == null)
			v = System.getenv(key);
		if (v == null)
			v = defaultValue;
		return v;
	}
	
}
