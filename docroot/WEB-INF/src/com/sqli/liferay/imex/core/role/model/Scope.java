/**
 * Copyright (c) 2012 SQLI. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.sqli.liferay.imex.core.role.model;

import java.util.HashMap;
import java.util.Map;

import com.liferay.portal.model.ResourceConstants;

public enum Scope {
	
	NONE(ResourceConstants.SCOPE_COMPANY-1),
	COMPANY(ResourceConstants.SCOPE_COMPANY),
	GROUP(ResourceConstants.SCOPE_GROUP),
	GROUP_TEMPLATE(ResourceConstants.SCOPE_GROUP_TEMPLATE);
	
	private int intValue;
	
	Scope(int intValue) {
		this.intValue = intValue;
	}
	
	public int getIntValue() {
		return intValue;
	}
	
	private static Map<Integer, Scope> scopesFromIntValue = new HashMap<Integer, Scope>();
	
	public static Scope getFromIntValue(int value) {
		if (scopesFromIntValue.size() == 0) {
			for (Scope scope : Scope.values()) {
				scopesFromIntValue.put(scope.getIntValue(), scope);
			}
		}
		
		return scopesFromIntValue.get(value);
	}
	
}
