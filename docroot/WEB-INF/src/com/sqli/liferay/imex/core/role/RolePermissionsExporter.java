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
