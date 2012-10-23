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

package com.sqli.liferay.imex.core.group.model.enums;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

public enum OnDuplicateMethodEnum {
	
	SKIP ("SKIP"),
	REPLACE ("REPLACE"),
	UPDATE ("UPDATE");
	
	private static Log _log = LogFactoryUtil.getLog(OnDuplicateMethodEnum.class);
	
	private String value;
	
	private OnDuplicateMethodEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
	
	public static OnDuplicateMethodEnum fromValue(String text) {
		
		if (text != null && !text.equals("")) {
			for (OnDuplicateMethodEnum b : OnDuplicateMethodEnum.values()) {
				if (text.equalsIgnoreCase(b.value)) {
					return b;
				}
			}
		}
		_log.error("Unable to convert [" + text + "]");
		return null;

	}

}
