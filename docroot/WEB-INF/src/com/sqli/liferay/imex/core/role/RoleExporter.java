package com.sqli.liferay.imex.core.role;

import java.io.File;
import java.io.IOException;
import java.text.Normalizer.Form;

import com.liferay.portal.model.Role;
import com.liferay.util.Normalizer;
import com.sqli.liferay.imex.core.FilesNames;
import com.sqli.liferay.imex.core.role.model.ImExRole;
import com.sqli.liferay.imex.core.role.service.RolePermissionsService;
import com.sqli.liferay.imex.core.role.service.impl.RolePermissionsServiceImpl;
import com.sqli.liferay.imex.util.xml.SimpleXmlProcessor;

public class RoleExporter {
	
	private RolePermissionsService rolePermissionsService;
	private RolePermissionsExporter rolePermissionsExporter;
	
	public RoleExporter() {
		this.rolePermissionsService = new RolePermissionsServiceImpl();
		this.rolePermissionsExporter = new RolePermissionsExporter();
	}

	public void doExport(Role role, File rolesDir) throws Exception {
		String roleDirName = Normalizer.normalizeToAscii(role.getName());
		File roleDir = new File(rolesDir, roleDirName);
		boolean success = roleDir.mkdirs();
		if (!success) {
			throw new IOException("Failed to create directory " + roleDir);
		}
		
		SimpleXmlProcessor<ImExRole> processor = new SimpleXmlProcessor<ImExRole>(ImExRole.class, roleDir, FilesNames.ROLE);
		processor.write(new ImExRole(role));
		
		if(rolePermissionsService.isUpdateableRole(roleDirName)) {
			this.rolePermissionsExporter.doExport(role, roleDir);
		}	
	}
}
