package com.netcracker.shaw.factory;

import org.openqa.selenium.By;

/**
 * @author Ramesh Reddy K (raka0617)
 *
 * Aug 10, 2018
 */

public class ByType {
	
	public static By getLocator(Locator locator, String expression, String... placeholders) {
		expression = String.format(expression, (Object[]) placeholders);

		switch (locator) {
		case ID:
			return By.id(expression);
		case XPATH:
			return By.xpath(expression);
		case LINK_TEXT:
			return By.linkText(expression);
		case CSS_SELECTOR:
			return By.cssSelector(expression);
		case PARTIAL_LINK_TEXT:
			return By.partialLinkText(expression);
		case CLASS_NAME:
			return By.className(expression);
		case TAG_NAME:
			return By.tagName(expression);
		case NAME:
			return By.name(expression);
		}
		return null;
	}
}
