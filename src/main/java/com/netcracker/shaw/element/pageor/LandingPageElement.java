package com.netcracker.shaw.element.pageor;

import static com.netcracker.shaw.factory.Locator.*;

import org.openqa.selenium.By;

import com.netcracker.shaw.at_shaw_sd.util.Constants;
import com.netcracker.shaw.at_shaw_sd.util.Utility;
import com.netcracker.shaw.element.PageElement;
import com.netcracker.shaw.factory.ByType;
import com.netcracker.shaw.factory.Locator;

/**
 * @author Ramesh Reddy K (raka0617)
 *
 * Aug 10, 2018
 */

public enum LandingPageElement implements PageElement {
	
	USER_NAME(XPATH, "//input[@id='user']"),
	USER_NAME1(ID, "user"),
	PASSWORD(XPATH, "//input[@id='pass']"),
	LOGIN(XPATH, "//input[@value='Log In']"),
	CUSTOMER(XPATH, getPropValue("customer")),
	LOGIN_BTN(XPATH,getPropValue("loginBtn")),
	LOGOFF_BTN(XPATH, "//a[text()='Log Off']");
	//LOGOFF_BTN(XPATH, "//a[@id='9135631596313479955']");

	private Locator locator;
	private String expression;
	
	public static String getPropValue(String value){
        value = Utility.getValueFromPropertyFile( value, Constants.LANDING_PAGE_PROP_PATH);
        return value;
    }

	LandingPageElement(Locator locator, String expression){
		this.locator = locator;
		this.expression = expression;
	}

	public Locator getLocator() {
		return locator;
	}

	public String getValue() {
		return expression;
	}

	public By getBy(String...placeholder) {
		return ByType.getLocator(locator, expression, placeholder);
	}	
}
