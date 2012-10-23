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

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;

public class PortletPermissions {
	
	@Element(required=true)
	private Resource portletResource;
	
	@ElementList(entry="model-resource", inline=true, required=false)
	private LinkedList<Resource> modelResourceList = new LinkedList<Resource>();
	
	public Resource getPortletResource() {
		return portletResource;
	}
	public void setPortletResource(Resource portletResource) {
		this.portletResource = portletResource;
	}
	
	public LinkedList<Resource> getModelResourceList() {
		return modelResourceList;
	}
	
	public void setModelResourceList(LinkedList<Resource> modelResources) {
		this.modelResourceList = modelResources;
	}	
}
