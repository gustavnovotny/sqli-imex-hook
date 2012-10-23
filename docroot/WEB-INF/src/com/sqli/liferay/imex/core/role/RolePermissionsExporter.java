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

package com.sqli.liferay.imex.core.role;

import java.io.File;

import com.liferay.portal.model.Role;
import com.sqli.liferay.imex.core.role.model.RolePermissions;
import com.sqli.liferay.imex.core.role.service.RolePermissionsService;
import com.sqli.liferay.imex.core.role.service.impl.RolePermissionsServiceImpl;
import com.sqli.liferay.imex.util.xml.SimpleXmlProcessor;

public class RolePermissionsExporter {
	
	private RolePermissionsService rolePermissionsService;

	public RolePermissionsExporter() {
		super();
		this.rolePermissionsService = new RolePermissionsServiceImpl();
	}
	
	public RolePermissionsExporter(RolePermissionsService rolePermissionsService) {
		super();
		this.rolePermissionsService = rolePermissionsService;
	}

	/**
	 * 
	 * @param companyId
	 * @param roleId
	 * @param roleDirectory
	 * @throws Exception
	 */
	public void doExport(Role role, File roleDirectory) throws Exception {
		long companyId = role.getCompanyId();
		long roleId = role.getRoleId();
		RolePermissions source = this.rolePermissionsService.getRolePermissions(companyId, roleId);
		SimpleXmlProcessor<RolePermissions> xmlProcessor = new SimpleXmlProcessor<RolePermissions>(RolePermissions.class, roleDirectory, "role-permissions.xml");	
		xmlProcessor.write(source);
	}
}
