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
