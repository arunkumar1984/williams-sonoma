/**
 *   File Name: BasicPageObject.java<br>
 *
 *   Venkatraman, Arunkumar<br>
 *   Java Boot Camp Exercise<br>
 *   Instructor: Jean-francois Nepton<br>
 *   Created: Jun 27, 2016
 *   
 */

package com.sqa.av.core;

import org.openqa.selenium.*;
import org.openqa.selenium.support.*;

/**
 * BasicPageObject //ADDD (description of class)
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
public class BasicPageObject {
	public BasicPageObject(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}

}
