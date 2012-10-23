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

package com.sqli.liferay.imex.core.service;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.GroupConstants;
import com.liferay.portal.model.Role;
import com.liferay.portal.service.ClassNameLocalServiceUtil;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.sqli.liferay.imex.core.group.GroupExportOptions;
import com.sqli.liferay.imex.core.group.GroupExporter;
import com.sqli.liferay.imex.core.role.RoleExporter;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Properties;

public class Exporter {
	
	private final static Log LOG = LogFactoryUtil.getLog(Exporter.class);
	
	private RoleExporter roleExporter;
	private GroupExporter groupExporter;
	
	public Exporter() {
		this.roleExporter = new RoleExporter();
		this.groupExporter = new GroupExporter();
	}
	
	public void doExport(File exportDir, Properties config) throws Exception {
		
		Properties props = new Properties();
		props.load(this.getClass().getResourceAsStream("/default.export.properties"));
		props.putAll(config);
		
		ExportOptions options = new ExportOptions();
		options.load(props, "export.");
		
		if(LOG.isInfoEnabled()) {
			LOG.info("Export options: " + props);
		}
		
		doExport(exportDir, options);
	}

	public void doExport(File exportDir, ExportOptions options) throws Exception {		
		List<Company> companies = CompanyLocalServiceUtil.getCompanies();
		for (Company company : companies) {
			
			long companyId = company.getCompanyId();
			
			String webId = company.getWebId();
			File companyDir = new File(exportDir, webId);
			boolean success = companyDir.mkdirs();
			if (!success) {
				throw new IOException("Failed to create directory " + companyDir);
			}
			
			// Roles
			
			File rolesDir = new File(companyDir, Directories.ROLES);
			List<Role> roles = RoleLocalServiceUtil.getRoles(companyId);
			for (Role role : roles) {
				this.roleExporter.doExport(role, rolesDir);
			}
			
			// Sites
			
			if(options.isSitesEnabled()) {
				File sitesDir = new File(companyDir, Directories.SITES);
				
				GroupExportOptions groupExportOptions = options.getSitesOptions();
				
				LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
				long[] classNameIds = new long[] {ClassNameLocalServiceUtil.getClassName(Group.class.toString()).getClassNameId()};
				// List<Group> groups = GroupLocalServiceUtil.search(companyId, classNameIds, null, null, params , QueryUtil.ALL_POS, QueryUtil.ALL_POS);
				List<Group> groups = GroupLocalServiceUtil.getGroups(QueryUtil.ALL_POS, QueryUtil.ALL_POS);

				for (Group group : groups) {
					if(group.isSite()  
							&& group.getType() != GroupConstants.TYPE_SITE_SYSTEM
							&& !group.getFriendlyURL().equals("/control_panel")) {
						this.groupExporter.doExport(group, sitesDir, groupExportOptions);
					}
				}
			}
			
		}
	}

}
