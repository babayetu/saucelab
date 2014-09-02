package com.paypal.qa.risk.mint.util;

import java.io.File;
import java.net.URL;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TestName;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.saucelabs.common.SauceOnDemandAuthentication;
import com.saucelabs.common.SauceOnDemandSessionIdProvider;
import com.saucelabs.junit.SauceOnDemandTestWatcher;

public class SauceTest implements SauceOnDemandSessionIdProvider{

	private WebDriver driver = null;
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
        	Helper.init(driver);
        } else {
			// Set default driver to FireFox browser with no profile
        	String browser = Helper.readPropertyOrEnv("SELENIUM_BROWSER", "firefox");
        	String firefoxProfileFile = Helper.readPropertyOrEnv("FF_PROFILE_FILE","");
			if (browser.toLowerCase().equals("firefox")) {
				if (firefoxProfileFile.isEmpty()) {
					this.driver = new FirefoxDriver();
				} else {
					FirefoxProfile firefoxProfile = new FirefoxProfile(new File(firefoxProfileFile));
					this.driver = new FirefoxDriver(firefoxProfile);
				}
			} else if (browser.toLowerCase().equals("chrome")) {
				// TODO: Mac OS?
//				ChromeOptions options = new ChromeOptions();
//				options.addExtensions(new File("/path/to/extension.crx"));
//				options.setBinary(new File("/path/to/chrome"));
//				this.driver = new ChromeDriver(options);
			} else {
				this.driver = new InternetExplorerDriver();
			}
        }
        this.driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
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
