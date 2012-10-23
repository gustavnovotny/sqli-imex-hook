package com.sqli.liferay.imex.core.role;

import com.liferay.portal.NoSuchRoleException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.model.Role;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.sqli.liferay.imex.core.FilesNames;
import com.sqli.liferay.imex.core.role.model.ImExRole;
import com.sqli.liferay.imex.core.service.ImportOptions;
import com.sqli.liferay.imex.util.xml.SimpleXmlProcessor;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class RoleImporter {
	
	private RolePermissionsImporter rolePermissionsImporter;
	
	public RoleImporter() {
		this.rolePermissionsImporter = new RolePermissionsImporter();
	}

	public void doImport(long companyId, File roleDir, ImportOptions options) throws Exception {
		
		SimpleXmlProcessor<ImExRole> xmlProcessor = new SimpleXmlProcessor<ImExRole>(ImExRole.class, roleDir, FilesNames.ROLE);	
		ImExRole r = xmlProcessor.read();
		Role role;
		try {
			role = RoleLocalServiceUtil.getRole(companyId, r.getName());
		} catch (NoSuchRoleException e) {
			
			//Initialisation des titres
			Map<java.util.Locale, String> titleMap = new HashMap<Locale, String>();
			initializeLocale(r.getName(), titleMap);
			
			Map<java.util.Locale, String> descriptionMap= new HashMap<Locale, String>();
			initializeLocale(r.getDescription(), descriptionMap);
			
			role = RoleLocalServiceUtil.addRole(new Long(0).longValue(), companyId, r.getName(), titleMap, descriptionMap, r.getType().getIntValue());
		
		}
	
		this.rolePermissionsImporter.doImport(companyId, role.getRoleId(), roleDir, options);
	}
	
	private void initializeLocale(String value, Map<java.util.Locale, String> map) {
		
		List<Locale> availableLocales = Arrays.asList(LanguageUtil.getAvailableLocales());
		
		for (Locale locale : availableLocales) {
			map.put(locale, value);
		}
		
	}
	
}
