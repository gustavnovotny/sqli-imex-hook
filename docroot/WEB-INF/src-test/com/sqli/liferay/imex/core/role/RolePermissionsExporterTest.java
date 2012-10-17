package com.sqli.liferay.imex.core.role;

import java.io.File;
import java.util.HashSet;
import java.util.LinkedList;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import com.liferay.portal.model.impl.RoleImpl;
import com.sqli.liferay.imex.core.role.model.Action;
import com.sqli.liferay.imex.core.role.model.PortletPermissions;
import com.sqli.liferay.imex.core.role.model.Resource;
import com.sqli.liferay.imex.core.role.model.RolePermissions;
import com.sqli.liferay.imex.core.role.model.Scope;
import com.sqli.liferay.imex.core.role.service.RolePermissionsService;

public class RolePermissionsExporterTest {
	
	@Rule
	TemporaryFolder folder = new TemporaryFolder();
	
	@Test
	public void testDoExport() throws Exception {
		RolePermissionsExporter exporter = new RolePermissionsExporter(new RolePermissionsService() {
			
	
			
			@Override
			public RolePermissions getRolePermissions(long companyId, long roleId)
					throws Exception {
				RolePermissions perms = new RolePermissions();
				LinkedList<PortletPermissions> portletPermissionsList = new LinkedList<PortletPermissions>();
				perms.setPortletPermissionsList(portletPermissionsList);
				PortletPermissions pp1 = new PortletPermissions();
				portletPermissionsList.add(pp1);
				portletPermissionsList.add(pp1);
				Resource r = new Resource();
				pp1.setPortletResource(r);
				r.setResourceName("rn");
				LinkedList<Action> al = new LinkedList<Action>();
				r.setActionList(al);
				Action a = new Action();
				a.setActionId("ACTION_ID");
				a.setScope(Scope.COMPANY);
				HashSet<String> groupNames = new HashSet<String>();
				groupNames.add("gn1");
				groupNames.add("gn2");
				a.setCommunitiesNames(groupNames);
				al.add(a);
				al.add(a);
				LinkedList<Resource> mr = new LinkedList<Resource>();
				pp1.setModelResourceList(mr);
				mr.add(r);
				mr.add(r);				
				return perms;
			}
			
			@Override
			public void deletePermission(long companyId, long roleId,
					long permissionId, String name, int scope, String primKey,
					String actionId) throws Exception {
				// NOTHING TO DO
			}

			@Override
			public boolean isUpdateableRole(String roleName) {
				// NOTHING TO DO
				return false;
			}

			@Override
			public void updateRolePermissions(long companyId, long roleId,
					RolePermissions rolePermissions) throws Exception {
				// NOTHING TO DO
			}
		});
		File roleDirectory = this.folder.newFolder("role");
		exporter.doExport(new RoleImpl(), roleDirectory);
		System.out.println(roleDirectory);
	}

	public boolean isUpdateableRole(String roleName) {
		return false;
	}

	public void updateRolePermissions(long companyId, long roleId, RolePermissions rolePermissions)
			throws Exception {
	}

}
