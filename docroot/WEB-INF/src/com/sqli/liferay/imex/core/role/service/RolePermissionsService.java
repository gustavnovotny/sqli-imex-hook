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

package com.sqli.liferay.imex.core.role.service;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.sqli.liferay.imex.core.role.model.RolePermissions;

/**
 * 
 * @author slemarchand
 * 
 * com.liferay.portlet.enterpriseadmin.action.EditRolePermissionsAction
 *
 */
public interface RolePermissionsService {
	
	/*
	public void deletePermission(
			long companyId,
			long roleId,
			long permissionId,
			String name,
			int scope,
			String primKey,
			String actionId)
		throws Exception;
		*/

	
	public RolePermissions getRolePermissions(
			long companyId, 
			long roleId)
		throws Exception;


	public void updateRolePermissions(long companyId, long roleId, RolePermissions rolePermissions)
			throws Exception;

	public boolean isUpdateableRole(String roleName);
	
	public void reinitRolePermissions(long roleId, boolean reInitOnImport) throws PortalException, SystemException;

}
