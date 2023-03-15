package com.netcracker.shaw.factory;

/**
 * @author Ramesh Reddy K (raka0617)
 *
 * Aug 10, 2018
 */

public enum Locator {
	
	XPATH("xpath"),
	ID("id"), 
	LINK_TEXT("linkText"), 
	CSS_SELECTOR("cssSelector"), 
	PARTIAL_LINK_TEXT("partialLinktText"), 
	CLASS_NAME("className"), 
	TAG_NAME("tagname"), 
	NAME("name");

	private String locator;

	Locator(String locator) {
		this.locator = locator;
	}

	public String getType() {
		return locator;
	}
	}
