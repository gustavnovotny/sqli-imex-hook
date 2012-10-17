package com.sqli.liferay.imex.core.role;

import java.io.File;

import com.liferay.portal.model.Role;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.sqli.liferay.imex.core.role.model.PortletPermissions;
import com.sqli.liferay.imex.core.role.model.RolePermissions;
import com.sqli.liferay.imex.core.role.service.RolePermissionsService;
import com.sqli.liferay.imex.core.role.service.impl.RolePermissionsServiceImpl;
import com.sqli.liferay.imex.util.xml.SimpleXmlProcessor;

public class RolePermissionsImporter {
	
	private RolePermissionsService rolePermissionsService;

	public RolePermissionsImporter() {
		super();
		this.rolePermissionsService = new RolePermissionsServiceImpl();
	}

	public void doImport(long companyId, long roleId, File roleDirectory) throws Exception {
		Role role = RoleLocalServiceUtil.getRole(roleId);
		
		if(rolePermissionsService.isUpdateableRole(role.getName())) { 
		
			SimpleXmlProcessor<RolePermissions> xmlProcessor = new SimpleXmlProcessor<RolePermissions>(RolePermissions.class, roleDirectory, "role-permissions.xml");	
			RolePermissions rolePermissions = xmlProcessor.read();
			
			rolePermissionsService.updateRolePermissions(
						companyId, 
						roleId, 
						rolePermissions);
		}
	}
}
