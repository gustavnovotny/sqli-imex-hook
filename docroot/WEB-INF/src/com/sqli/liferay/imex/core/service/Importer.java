package com.sqli.liferay.imex.core.service;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.model.Company;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.sqli.liferay.imex.core.group.GroupImportOptions;
import com.sqli.liferay.imex.core.group.GroupImporter;
import com.sqli.liferay.imex.core.role.RoleImporter;

import java.io.File;
import java.util.Properties;

public class Importer {
	
	private final static Log LOG = LogFactoryUtil.getLog(Importer.class);
	
	private RoleImporter roleImporter;
	private GroupImporter groupImporter;
	
	public Importer() {
		this.roleImporter = new RoleImporter() ;
		this.groupImporter = new GroupImporter() ;
	}
	
	public void doImport(File exportDir, Properties config) throws Exception {
		
		Properties props = new Properties();
		props.load(this.getClass().getResourceAsStream("/default.import.properties"));
		props.putAll(config);
		
		ImportOptions options = new ImportOptions();
		options.load(props, "import.");
		
		if(LOG.isInfoEnabled()) {
			LOG.info("Import options: " + props);
		}
		
		doImport(exportDir, options);
	}
	
	public void doImport(File importDir, ImportOptions options) throws Exception {
		 File[] companiesDirs = importDir.listFiles();
		 for (File companyDir : companiesDirs) {
			
			String webId = companyDir.getName();
			Company company = CompanyLocalServiceUtil.getCompanyByWebId(webId);
			long companyId = company.getCompanyId();
			
			// Roles
			
			File rolesDir = new File(companyDir, Directories.ROLES);
			File[] rolesDirs = rolesDir.listFiles();
			for (File roleDir : rolesDirs) {
				this.roleImporter.doImport(companyId, roleDir, options);
			}
			
			// Sites
			
			if(options.isSitesEnabled()) {
				File sitesDir = new File(companyDir, Directories.SITES);
				
				GroupImportOptions groupImportOptions = options.getSitesOptions();
				File[] sitesDirs = sitesDir.listFiles();
				for (File siteDir : sitesDirs) {
					this.groupImporter.doImport(companyId, siteDir, groupImportOptions);
				}
			
			}
		 }
	}
}
