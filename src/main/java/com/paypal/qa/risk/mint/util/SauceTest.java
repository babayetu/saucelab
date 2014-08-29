package com.paypal.qa.risk.mint.util;

import java.net.URL;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TestName;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.saucelabs.common.SauceOnDemandAuthentication;
import com.saucelabs.common.SauceOnDemandSessionIdProvider;
import com.saucelabs.junit.SauceOnDemandTestWatcher;

public class SauceTest implements SauceOnDemandSessionIdProvider{

	private WebDriver driver;
    private boolean runOnSauce = System.getProperty("sauce") != null;
    private String sessionId;
    private static Date date = new Date();
    
    /** Authenticate to Sauce with environment variables SAUCE_USER_NAME and SAUCE_API_KEY **/
    private SauceOnDemandAuthentication authentication = new SauceOnDemandAuthentication();

    /** Report pass/fail to Sauce Labs **/
    // false to silence Sauce connect messages.
    public @Rule
    SauceOnDemandTestWatcher reportToSauce = new SauceOnDemandTestWatcher(this, authentication, false);

    @Rule
    public TestRule printTests = new TestWatcher() {
        protected void starting(Description description) {
            System.out.print("  Test name: " + description.getMethodName());
        }

        protected void finished(Description description) {
            final String session = getSessionId();

            if (session != null) {
                System.out.println(" " + "https://saucelabs.com/tests/" + session);
            } else {
                System.out.println();
            }
        }
    };
   
    public @Rule TestName testName = new TestName();

    /**
      * Creates a new {@link RemoteWebDriver} instance used to run WebDriver tests using
      * Sauce.
      *
      * @throws Exception thrown if an error occurs constructing the WebDriver
      */
    @Before
    public void setUp() throws Exception {

        DesiredCapabilities capabilities = new DesiredCapabilities();
        String version = Helper.readPropertyOrEnv("SELENIUM_VERSION", "");
        if (!version.equals("")) {
            capabilities.setCapability("version", version);
        }
        capabilities.setCapability("platform", Helper.readPropertyOrEnv("SELENIUM_PLATFORM", "XP"));
        capabilities.setCapability("browserName", Helper.readPropertyOrEnv("SELENIUM_BROWSER", "firefox"));
        String username = Helper.readPropertyOrEnv("SAUCE_USER_NAME", "shipuyao");
        String accessKey = Helper.readPropertyOrEnv("SAUCE_API_KEY", "30279c86-dac5-4617-b430-f8afe1e7dc89");
        
        if (runOnSauce) {
            capabilities.setCapability("name", testName.getMethodName() + " " + date);
        	URL serverAddress = new URL("http://" + username + ":" + accessKey + "@ondemand.saucelabs.com:80/wd/hub");
        	this.driver = new RemoteWebDriver(serverAddress, capabilities);
        	this.sessionId = ((RemoteWebDriver)driver).getSessionId().toString();
        } else {
        	// TODO: local driver
        	this.driver = new FirefoxDriver();
        }
        Helper.init(driver);
    }

	@After
	public void tearDown() throws Exception {
		if (driver != null) {
			driver.quit();
		}
	}

	public String getSessionId() {
		return runOnSauce ? this.sessionId : null;
	}
}
