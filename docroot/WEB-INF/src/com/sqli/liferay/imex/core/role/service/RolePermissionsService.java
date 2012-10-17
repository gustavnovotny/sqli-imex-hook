package com.sqli.liferay.imex.core.role.service;

import com.sqli.liferay.imex.core.role.model.PortletPermissions;
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

}
