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

package com.sqli.liferay.imex.portal.util;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.GetterUtil;

public class PrefsPropsUtil {

	public static boolean getBoolean(String name, boolean defaultValue) throws SystemException {
		return GetterUtil.getBoolean(getString(name), defaultValue);
	}

	public static String getString(String name) throws SystemException {
		try {
			return com.liferay.portal.kernel.util.PrefsPropsUtil.getString(0, name);
		} catch (Exception e) {
			throw new SystemException(e);
		}
	}
	
	public static String getString(String name, String defaultValue) throws SystemException {
		return GetterUtil.getString(getString(name), defaultValue);
	}

	public static long getLong(String name, int defaultValue) throws SystemException {
		return GetterUtil.getLong(getString(name), defaultValue);
	}

	public static int getInteger(String name,
			int defaultValue) throws SystemException {
		return GetterUtil.getInteger(getString(name), defaultValue);
	}

}
