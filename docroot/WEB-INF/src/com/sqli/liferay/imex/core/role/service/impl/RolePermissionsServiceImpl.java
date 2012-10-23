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

package com.sqli.liferay.imex.core.role.service.impl;

import com.liferay.portal.RolePermissionsException;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.GroupConstants;
import com.liferay.portal.model.Permission;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.ResourcePermission;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.PermissionLocalServiceUtil;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.service.ResourceBlockLocalServiceUtil;
import com.liferay.portal.service.ResourcePermissionLocalServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.util.PortletKeys;
import com.sqli.liferay.imex.core.role.model.Action;
import com.sqli.liferay.imex.core.role.model.PortletPermissions;
import com.sqli.liferay.imex.core.role.model.Resource;
import com.sqli.liferay.imex.core.role.model.RolePermissions;
import com.sqli.liferay.imex.core.role.model.Scope;
import com.sqli.liferay.imex.core.role.service.RolePermissionsService;
import com.sqli.liferay.imex.portal.security.permission.ResourceActionsUtil;
import com.sqli.liferay.imex.portal.util.PropsValues;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * 
 * @author slemarchand
 * 
 * com.liferay.portlet.enterpriseadmin.action.EditRolePermissionsAction
 *
 */
public class RolePermissionsServiceImpl implements RolePermissionsService {
	
	private static Log _log = LogFactoryUtil.getLog(RolePermissionsServiceImpl.class);
	
	@Override
	public void updateRolePermissions(
			long companyId,
			long roleId,
			RolePermissions rolePermissions) throws Exception {
		for (PortletPermissions portletPermissions : rolePermissions.getPortletPermissionsList()) {
			updateActions(companyId, roleId, portletPermissions);
		}
	}
	
	public void reinitRolePermissions(long roleId, boolean reInit) throws PortalException, SystemException {
		
		if (reInit) {
			
			Role role = RoleLocalServiceUtil.getRole(roleId);
	
			if (!isUpdateableRole(role.getName())) {
				throw new RolePermissionsException(role.getName());
			}
			
			if (PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM == 6) {			
				reinitAction_6(role);
			} else {
				reinitAction_1to5(role);
			}
			
		} else {
			_log.debug("Permissions will not be reset for roleId=[" + roleId + "] after import.");
		}
		
	}
	
	private void reinitAction_6(Role role) throws SystemException {
		
		long roleId = role.getRoleId();			
	    List<ResourcePermission> liste = ResourcePermissionLocalServiceUtil.getRoleResourcePermissions(roleId);
	    
	    for (ResourcePermission resourcePermission : liste) {
	    	
	    	if (resourcePermission.getScope() != ResourceConstants.SCOPE_INDIVIDUAL) {
	    		ResourcePermissionLocalServiceUtil.deleteResourcePermission(resourcePermission);
	    	}
	    	
	    }
				
	}
	
	private void reinitAction_1to5(Role role) throws SystemException {

		long roleId = role.getRoleId();			
	    List<Permission> liste = PermissionLocalServiceUtil.getRolePermissions(roleId);
	    
	    for (Permission permission : liste) {
	    	
	    	if (permission.getScope() != ResourceConstants.SCOPE_INDIVIDUAL) {
	    		PermissionLocalServiceUtil.deletePermission(permission);
	    	}
	    	
	    }
		
	}
	
	private void updateActions(
			long companyId,
			long roleId,
			PortletPermissions portletPermissions)
		throws Exception {
			
		Role role = RoleLocalServiceUtil.getRole(roleId);

		if (!isUpdateableRole(role.getName())){

			throw new RolePermissionsException(role.getName());
		}

		List<Resource> resourceList = new LinkedList<Resource>();
		resourceList.add(portletPermissions.getPortletResource());
		resourceList.addAll(portletPermissions.getModelResourceList());

		for (Resource resource : resourceList) {
		
			List<String> actions = ResourceActionsUtil.getResourceActions(resource.getResourceName());
			actions = ListUtil.sort(
				actions);
	
			String selResource = resource.getResourceName();
			
			for (Action action : resource.getActionList()) {
				String actionId = action.getActionId();
				int scope = action.getScope().getIntValue();
				Set<String> groupNames = action.getSitesNames();
				String[] groupIds = new String[groupNames.size()];
				int i = 0;
				for (String groupName : groupNames) {
					Group group = GroupLocalServiceUtil.getGroup(companyId, groupName);
					groupIds[i] = Long.toString(group.getGroupId());
					i++;
				}
				
				updateAction(
						role,
						selResource, actionId, scope, groupIds);
			}
		}
	}
	
	private void updateAction(Role role, String selResource, String actionId,
			int scope, String[] groupIds) throws Exception {
		
			boolean selected = true;
		
			if (PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM == 6) {
				if (ResourceBlockLocalServiceUtil.isSupported(
						selResource)) {
	
					updateActions_6Blocks(
						role, selResource,
						actionId, selected, scope, groupIds);
				}
				else {
					updateAction_6(
						role, selResource,
						actionId, selected, scope, groupIds);
				}
			}
			else {
				updateAction_1to5(
					role, selResource,
					actionId, selected, scope, groupIds);
			}
	}

	@Override
	public RolePermissions getRolePermissions(long companyId, long roleId) throws Exception{
		return getRolePermissions(companyId, roleId, false);
	}
		
	private RolePermissions getRolePermissions(long companyId, long roleId, boolean allActions)
			throws Exception {
				
		RolePermissions result = new RolePermissions();
		
		List<Portlet> portlets = PortletLocalServiceUtil.getPortlets(companyId, true, false);
		
		List<String> portletNames = new ArrayList<String>(portlets.size() + 1); 
		for (Portlet portlet : portlets) {
			String portletName = portlet.getPortletId();
			portletNames.add(portletName);
		}
		portletNames.add(PortletKeys.PORTAL);
		
		for (String portletName : portletNames) {
			PortletPermissions portletPermissions = new PortletPermissions();
			
			List<String> modelResources = ResourceActionsUtil.getPortletModelResources(portletName);
			for (String modelResource : modelResources) {
				
				Resource r = getModelResource(companyId, roleId, modelResource, allActions);
				if (allActions || r.getActionList().size() > 0){
					portletPermissions.getModelResourceList().add(r);
				}
			}
			
			Resource pr = getPortletResource(companyId, roleId, portletName, allActions);
			portletPermissions.setPortletResource(pr);	
			
			if (allActions 
					|| portletPermissions.getModelResourceList().size() > 0
					|| pr.getActionList().size() > 0) {
				result.getPortletPermissionsList().add(portletPermissions);
			}
		}
		
		return result;
	}
	
	protected void updateAction_1to5(
			Role role, String selResource, String actionId,
			boolean selected, int scope, String[] groupIds)
		throws Exception {

		long roleId = role.getRoleId();
		long companyId = role.getCompanyId();

		if (selected) {
			if (scope == ResourceConstants.SCOPE_COMPANY) {
				PermissionLocalServiceUtil.setRolePermission(
					roleId,  companyId, selResource, scope,
					String.valueOf(role.getCompanyId()), actionId);
			}
			else if (scope == ResourceConstants.SCOPE_GROUP_TEMPLATE) {
				PermissionLocalServiceUtil.setRolePermission(
					roleId, companyId, selResource,
					ResourceConstants.SCOPE_GROUP_TEMPLATE,
					String.valueOf(GroupConstants.DEFAULT_PARENT_GROUP_ID),
					actionId);
			}
			else {
				PermissionLocalServiceUtil.unsetRolePermissions(
					roleId, companyId, selResource, ResourceConstants.SCOPE_GROUP,
					actionId);

				for (String curGroupId : groupIds) {
					PermissionLocalServiceUtil.setRolePermission(
						roleId, companyId, selResource,
						ResourceConstants.SCOPE_GROUP, curGroupId, actionId);
				}
			}
		}
		else {

			// Remove company, group template, and group permissions

			PermissionLocalServiceUtil.unsetRolePermissions(
				roleId, companyId, selResource, ResourceConstants.SCOPE_COMPANY,
				actionId);

			PermissionLocalServiceUtil.unsetRolePermissions(
				roleId, companyId, selResource,
				ResourceConstants.SCOPE_GROUP_TEMPLATE, actionId);

			PermissionLocalServiceUtil.unsetRolePermissions(
				roleId, companyId, selResource, ResourceConstants.SCOPE_GROUP,
				actionId);
		}
	}

	protected void updateAction_6(
			Role role, String selResource, String actionId,
			boolean selected, int scope, String[] groupIds)
		throws Exception {

		long companyId = role.getCompanyId();
		long roleId = role.getRoleId();

		if (selected) {
			if (scope == ResourceConstants.SCOPE_COMPANY) {
				ResourcePermissionLocalServiceUtil.addResourcePermission(
					companyId, selResource, scope,
					String.valueOf(role.getCompanyId()), roleId, actionId);
			}
			else if (scope == ResourceConstants.SCOPE_GROUP_TEMPLATE) {
				ResourcePermissionLocalServiceUtil.addResourcePermission(
					companyId, selResource,
					ResourceConstants.SCOPE_GROUP_TEMPLATE,
					String.valueOf(GroupConstants.DEFAULT_PARENT_GROUP_ID),
					roleId, actionId);
			}
			else if (scope == ResourceConstants.SCOPE_GROUP) {
				ResourcePermissionLocalServiceUtil.removeResourcePermissions(
					companyId, selResource,
					ResourceConstants.SCOPE_GROUP, roleId, actionId);

				for (String curGroupId : groupIds) {
					ResourcePermissionLocalServiceUtil.addResourcePermission(
						companyId, selResource,
						ResourceConstants.SCOPE_GROUP, curGroupId, roleId,
						actionId);
				}
			}
		}
		else {

			// Remove company, group template, and group permissions

			ResourcePermissionLocalServiceUtil.removeResourcePermissions(
				companyId, selResource,
				ResourceConstants.SCOPE_COMPANY, roleId, actionId);

			ResourcePermissionLocalServiceUtil.removeResourcePermissions(
				companyId, selResource,
				ResourceConstants.SCOPE_GROUP_TEMPLATE, roleId, actionId);

			ResourcePermissionLocalServiceUtil.removeResourcePermissions(
				companyId, selResource, ResourceConstants.SCOPE_GROUP,
				roleId, actionId);
		}
	}
	
	protected void updateActions_6Blocks(
			Role role, String selResource, String actionId,
			boolean selected, int scope, String[] groupIds)
		throws Exception {

		long companyId = role.getCompanyId();
		long roleId = role.getRoleId();

		if (selected) {
			if (scope == ResourceConstants.SCOPE_GROUP) {
				ResourceBlockLocalServiceUtil.removeAllGroupScopePermissions(
					 companyId, selResource, roleId, actionId);
				ResourceBlockLocalServiceUtil.removeCompanyScopePermission(
					 companyId, selResource, roleId, actionId);

				for (String groupId : groupIds) {
					ResourceBlockLocalServiceUtil.addGroupScopePermission(
						companyId, GetterUtil.getLong(groupId),
						selResource, roleId, actionId);
				}
			}
			else {
				ResourceBlockLocalServiceUtil.removeAllGroupScopePermissions(
					companyId, selResource, roleId, actionId);
				ResourceBlockLocalServiceUtil.addCompanyScopePermission(
					companyId, selResource, roleId, actionId);
			}
		}
		else {
			ResourceBlockLocalServiceUtil.removeAllGroupScopePermissions(
				companyId, selResource, roleId, actionId);
			ResourceBlockLocalServiceUtil.removeCompanyScopePermission(
				companyId, selResource, roleId, actionId);
		}
	}
	
	private Resource getPortletResource(long companyId, long roleId, String portletResource, boolean allActions) throws PortalException, SystemException {
		List<String> actions = ResourceActionsUtil.getPortletResourceActions(portletResource);
		return getResource(companyId, roleId, portletResource, actions, allActions);
	}
	
	private Resource getModelResource(long companyId, long roleId, String modelResource, boolean allActions) throws PortalException, SystemException {
		List<String> actions = ResourceActionsUtil.getModelResourceActions(modelResource);
		return getResource(companyId, roleId, modelResource, actions, allActions);
	}
	
	private Resource getResource(long companyId, long roleId, String resource, List<String> actions, boolean allActions) throws PortalException, SystemException {
		Resource r = new Resource();
		r.setResourceName(resource);
		Company company = CompanyLocalServiceUtil.getCompany(companyId);
		Role role = RoleLocalServiceUtil.getRole(roleId);
		for (String actionId : actions) {
			Action action = new Action();
			action.setActionId(actionId);
			Scope scope = getScope(company, role, resource, actionId);
			action.setScope(scope);
			if (allActions || !scope.equals(Scope.NONE)){
				r.getActionList().add(action);
				if (scope.equals(Scope.GROUP)) {
					LinkedHashMap<String, Object> groupParams = new LinkedHashMap<String, Object>();

					List<Comparable> rolePermissions = new ArrayList<Comparable>();
	
					rolePermissions.add(resource);
					rolePermissions.add(new Integer(ResourceConstants.SCOPE_GROUP));
					rolePermissions.add(actionId);
					rolePermissions.add(new Long(role.getRoleId()));
	
					groupParams.put("rolePermissions", rolePermissions);
						
					List<Group> groups = GroupLocalServiceUtil.search(company.getCompanyId(), null, null, groupParams, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
				
					for (Group group : groups) {
						if (group.isSite()) {
							action.getSitesNames().add(group.getName());
						} else {
							throw new IllegalStateException();
						}
					}
					
				}
			}
		}
		return r;
	}
	
	private Scope getScope(Company company, Role role, String curResource, String actionId) throws PortalException, SystemException {
		boolean hasCompanyScope = false;
		boolean hasGroupTemplateScope = false;
		boolean hasGroupScope = false;

		
		if (PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM == 6) {
			hasCompanyScope = (role.getType() == RoleConstants.TYPE_REGULAR) && ResourcePermissionLocalServiceUtil.hasScopeResourcePermission(company.getCompanyId(), curResource, ResourceConstants.SCOPE_COMPANY, role.getRoleId(), actionId);
			hasGroupTemplateScope = ((role.getType() == RoleConstants.TYPE_SITE) || (role.getType() == RoleConstants.TYPE_ORGANIZATION)) && ResourcePermissionLocalServiceUtil.hasScopeResourcePermission(company.getCompanyId(), curResource, ResourceConstants.SCOPE_GROUP_TEMPLATE, role.getRoleId(), actionId);
			hasGroupScope = (role.getType() == RoleConstants.TYPE_REGULAR) && ResourcePermissionLocalServiceUtil.hasScopeResourcePermission(company.getCompanyId(), curResource, ResourceConstants.SCOPE_GROUP, role.getRoleId(), actionId);
		} else {
			hasCompanyScope = (role.getType() == RoleConstants.TYPE_REGULAR) && PermissionLocalServiceUtil.hasRolePermission(role.getRoleId(), company.getCompanyId(), curResource, ResourceConstants.SCOPE_COMPANY, actionId);
			hasGroupTemplateScope = ((role.getType() == RoleConstants.TYPE_SITE) || (role.getType() == RoleConstants.TYPE_ORGANIZATION)) && PermissionLocalServiceUtil.hasRolePermission(role.getRoleId(), company.getCompanyId(), curResource, ResourceConstants.SCOPE_GROUP_TEMPLATE, actionId);
			hasGroupScope = (role.getType() == RoleConstants.TYPE_REGULAR) && PermissionLocalServiceUtil.hasRolePermission(role.getRoleId(), company.getCompanyId(), curResource, ResourceConstants.SCOPE_GROUP, actionId);
		}
		
		Scope result = null;
		if (hasCompanyScope) {
			result = Scope.COMPANY;
		} else if (hasGroupTemplateScope) {
			// TODO : revoir ça !!!
			result = Scope.GROUP;
		} else if (hasGroupScope) {
			result = Scope.GROUP;
		} else {
			result = Scope.NONE;
		}
		
		return result;
	}

	public boolean isUpdateableRole(String roleName) {
		return !IMMUTABLE_ROLES_NAMES.contains(roleName);
	}
	
	public final static Set<String> IMMUTABLE_ROLES_NAMES = new HashSet<String>();
	
	static {
		IMMUTABLE_ROLES_NAMES.add(RoleConstants.ADMINISTRATOR);
		IMMUTABLE_ROLES_NAMES.add(RoleConstants.OWNER);
		IMMUTABLE_ROLES_NAMES.add(RoleConstants.SITE_ADMINISTRATOR);
		IMMUTABLE_ROLES_NAMES.add(RoleConstants.SITE_OWNER);
		IMMUTABLE_ROLES_NAMES.add(RoleConstants.ORGANIZATION_ADMINISTRATOR);
		IMMUTABLE_ROLES_NAMES.add(RoleConstants.ORGANIZATION_OWNER);
	}
}
