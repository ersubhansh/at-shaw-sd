package com.netcracker.shaw.element;

import org.openqa.selenium.By;

import com.netcracker.shaw.factory.Locator;

/**
 * @author Ramesh Reddy K (raka0617)
 *
 * Aug 10, 2018
 */

public interface PageElement {
	public Locator getLocator();
	public String getValue();
	public By getBy(String...placeholder);
}
