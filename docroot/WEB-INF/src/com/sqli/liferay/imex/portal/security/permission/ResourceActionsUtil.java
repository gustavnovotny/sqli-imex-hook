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

package com.sqli.liferay.imex.portal.security.permission;

import java.util.List;

import com.liferay.portal.kernel.util.PortalClassInvoker;

/**
 * <a href="ResourceActionsUtil.java.html"><b><i>View Source</i></b></a>
 * 
 * @author slemarchand
 * 
 */
public class ResourceActionsUtil {

	public static List<String> getResourceActions(String name) {
		return invoke("getResourceActions", name);
	}

	public static List<String> getModelResourceActions(String name) {
		return invoke("getModelResourceActions", name);
	}

	public static List<String> getPortletModelResources(String portletName) {
		return invoke("getPortletModelResources", portletName);
	}

	public static List<String> getPortletNames() {
		return invoke("getPortletNames");
	}

	public static List<String> getPortletResourceActions(String name) {
		return invoke("getPortletResourceActions", name);
	}

	@SuppressWarnings("unchecked")
	private static <T> T invoke(String method, Object... args) {
		Object returnObj;
		try {
			returnObj = PortalClassInvoker.invoke(_CLASS, method,
					args, false);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		if (returnObj != null) {
			return (T) returnObj;
		} else {
			return null;
		}
	}
	private static final String _CLASS = "com.liferay.portal.security.permission.ResourceActionsUtil";

}