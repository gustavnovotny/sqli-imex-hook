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

import com.liferay.portal.model.Role;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.sqli.liferay.imex.core.role.model.RolePermissions;
import com.sqli.liferay.imex.core.role.service.RolePermissionsService;
import com.sqli.liferay.imex.core.role.service.impl.RolePermissionsServiceImpl;
import com.sqli.liferay.imex.core.service.ImportOptions;
import com.sqli.liferay.imex.util.xml.SimpleXmlProcessor;

import java.io.File;

public class RolePermissionsImporter {
	
	private RolePermissionsService rolePermissionsService;

	public RolePermissionsImporter() {
		super();
		this.rolePermissionsService = new RolePermissionsServiceImpl();
	}

	public void doImport(long companyId, long roleId, File roleDirectory, ImportOptions options) throws Exception {
		Role role = RoleLocalServiceUtil.getRole(roleId);
		
		if(rolePermissionsService.isUpdateableRole(role.getName())) { 
		
			SimpleXmlProcessor<RolePermissions> xmlProcessor = new SimpleXmlProcessor<RolePermissions>(RolePermissions.class, roleDirectory, "role-permissions.xml");	
			RolePermissions rolePermissions = xmlProcessor.read();
			
			rolePermissionsService.reinitRolePermissions(roleId, options.isReinitAllPermissionsOnImport());
			
			rolePermissionsService.updateRolePermissions(
						companyId, 
						roleId, 
						rolePermissions);
		}
	}
}
