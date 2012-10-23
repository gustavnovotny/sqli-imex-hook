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

import java.util.LinkedList;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root
public class RolePermissions {
	
	@ElementList(entry="portlet-permissions", inline=true, required=false)
	private LinkedList<PortletPermissions> portletPermissionsList = new LinkedList<PortletPermissions>();

	public LinkedList<PortletPermissions> getPortletPermissionsList() {
		return portletPermissionsList;
	}

	public void setPortletPermissionsList(
			LinkedList<PortletPermissions> portletPermissionsList) {
		this.portletPermissionsList = portletPermissionsList;
	}

	
	
}
