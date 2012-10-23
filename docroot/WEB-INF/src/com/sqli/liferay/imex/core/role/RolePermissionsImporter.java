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
