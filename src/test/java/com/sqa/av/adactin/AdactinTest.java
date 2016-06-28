/**
 *   File Name: AdactinTest.java<br>
 *
 *   Venkatraman, Arunkumar<br>
 *   Java Boot Camp Exercise<br>
 *   Instructor: Jean-francois Nepton<br>
 *   Created: Jun 27, 2016
 *   
 */

package com.sqa.av.adactin;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import org.testng.*;
import org.testng.annotations.*;

import com.sqa.av.helpers.*;

/**
 * AdactinTest //ADDD (description of class)
 * <p>
 * //ADDD (description of core fields)
 * <p>
 * //ADDD (description of core methods)
 * 
 * @author Venkatraman, Arunkumar
 * @version 1.0.0
 * @since 1.0
 *
 */
public class AdactinTest extends BasicTest {

	/**
	 * @param baseURL
	 */
	public AdactinTest() {
		super("http://adactin.com/HotelApp");
	}

	@Test
	public void adactinTest() throws InterruptedException {
		String expectedTitle = "AdactIn.com - Search Hotel";
		AdactinHomePage home = new AdactinHomePage(getDriver());
		home.loginToWebsite();
		WebElement myDynamicElement = (new WebDriverWait(getDriver(), 10))
				.until(ExpectedConditions.presenceOfElementLocated(By.id("myDynamicElement")));

		Assert.assertEquals(getDriver().getTitle(), expectedTitle, "Login Not Successful");
	}

}
