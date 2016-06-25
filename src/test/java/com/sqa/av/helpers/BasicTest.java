package com.sqa.av.helpers;

import java.util.concurrent.*;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.firefox.*;
import org.testng.annotations.*;

public class BasicTest {
	private static String baseURL = "http://mtv.com"; // Set to mtv to show the
														// Super sets the value
														// in Craigslist
	private static WebDriver driver;

	/**
	 * @return the baseURL
	 */
	public static String getBaseURL() {
		return baseURL;
	}

	/**
	 * @return the driver
	 */
	public static WebDriver getDriver() {
		return driver;
	}

	@BeforeClass(enabled = false)
	public static void setupChrome() {
		System.setProperty("webdriver.chrome.driver", "drivers/chromedriver");
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.get(baseURL);
	}

	@BeforeClass(enabled = true)
	public static void setupFirefox() {
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.get(baseURL);
	}

	@AfterClass
	public static void teardown() {
		driver.quit();
	}

	private boolean acceptNextAlert = true;

	/**
	 *
	 */
	public BasicTest(String baseURL) {
		super();
		BasicTest.baseURL = baseURL;
		// TODO Auto-generated constructor stub
	}

	@BeforeMethod
	public void setupTest() {
		// Delete all saved cookies
		getDriver().manage().deleteAllCookies();
		// Go to base URL
		getDriver().get(getBaseURL());
	}

	@SuppressWarnings("unused")
	private String closeAlertAndGetItsText() {
		try {
			Alert alert = this.driver.switchTo().alert();
			String alertText = alert.getText();
			if (this.acceptNextAlert) {
				alert.accept();
			} else {
				alert.dismiss();
			}
			return alertText;
		} finally {
			this.acceptNextAlert = true;
		}
	}

	@SuppressWarnings("unused")
	private boolean isAlertPresent() {
		try {
			this.driver.switchTo().alert();
			return true;
		} catch (NoAlertPresentException e) {
			return false;
		}
	}

	@SuppressWarnings("unused")
	private boolean isElementPresent(By by) {
		try {
			this.driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}
}
