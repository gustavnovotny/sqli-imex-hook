/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
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