package com.netcracker.shaw.at_shaw_sd.xml;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Ramesh Reddy K (raka0617)
 *
 * Aug 10, 2018
 */

public class XMLElement {
	
	private Map<String, Object> map = new LinkedHashMap<String, Object>();
	
	public XMLElement add(String key, Object value) {
		while (map.containsKey(key)) {
			key += ".";
		}
		if (value instanceof String) {
			map.put(key, value);
		} else if (value instanceof XMLElement) {
			map.put(key, ((XMLElement) value).getMap());
		}
		return this;
	}
	
	public Map<String, Object> getMap() {
		return map;
	}
}