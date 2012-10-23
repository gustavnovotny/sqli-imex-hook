package com.sqli.liferay.imex.core.group;

import com.liferay.portal.DuplicateGroupException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.PrincipalThreadLocal;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.sqli.liferay.imex.core.FilesNames;
import com.sqli.liferay.imex.core.group.model.ImExGroup;
import com.sqli.liferay.imex.core.group.model.enums.OnDuplicateMethodEnum;
import com.sqli.liferay.imex.core.lar.LarImporter;
import com.sqli.liferay.imex.util.xml.SimpleXmlProcessor;

import java.io.File;
import java.util.List;
import java.util.Map;

public class GroupImporter {
	
	private static Log _log = LogFactoryUtil.getLog(GroupImporter.class);
	
	private LarImporter larImporter;
	
	public GroupImporter() {
		this.larImporter = new LarImporter();
	}

	public void doImport(long companyId, File groupDir, GroupImportOptions options) throws Exception {
		long classPK = 0;
		doImport(companyId, groupDir, options, classPK );
	}
	
	public void doImport(long companyId, File groupDir, GroupImportOptions options,  long classPK) throws Exception {
		
		SimpleXmlProcessor<ImExGroup> xmlProcessor = new SimpleXmlProcessor<ImExGroup>(ImExGroup.class, groupDir, FilesNames.GROUP);	
		ImExGroup g = xmlProcessor.read();

		boolean site = g.isSite();
		long userId = getDefaultAdmin(companyId).getUserId();
		String className = g.getClassName();
		String name = g.getName();
		String description = g.getDescription();
		int type = g.getType().getIntValue();
		String friendlyURL = g.getFriendlyURL();
		boolean active = g.isActive();
		ServiceContext serviceContext = new ServiceContext();

		UnicodeProperties unicodeProperties = new UnicodeProperties();
		unicodeProperties.putAll(g.getTypeSettings());
		String typeSettings = unicodeProperties.toString();
		
		//Tentative de création
		Group group = null;
		try {
			
			group = GroupLocalServiceUtil.addGroup(userId, className, classPK, name, description, type, friendlyURL, site, active, serviceContext);
			doImportLars(groupDir, options, userId, group);
			_log.info("[" + group.getFriendlyURL() + "] => [CREATE] ...");
			
		} catch(DuplicateGroupException e) {
			
			//Chargement du group
			group = GroupLocalServiceUtil.getFriendlyURLGroup(companyId, friendlyURL);
			
			OnDuplicateMethodEnum method = options.getOnduplicateBehavior(group);
			
			if (!method.getValue().equals(OnDuplicateMethodEnum.SKIP.getValue())) {

				long groupId = group.getGroupId();
				
				//Si le group existe
				if (method.getValue().equals(OnDuplicateMethodEnum.UPDATE.getValue())) {
					
					GroupLocalServiceUtil.updateGroup(groupId, name, description, type, friendlyURL, active, serviceContext);
					
				} else if (method.getValue().equals(OnDuplicateMethodEnum.REPLACE.getValue())) {
					
					//Suppression du Group
					GroupLocalServiceUtil.deleteGroup(group);
										
					//Création du group
					group = GroupLocalServiceUtil.addGroup(userId, className, classPK, name, description, type, friendlyURL, site, active, serviceContext);
					
				}
				
				doImportLars(groupDir, options, userId, group);
				
				_log.info("[" + group.getFriendlyURL() + "] => ["  + method.getValue() + "] ...");
				
			} 
			
		}
	
		
	}

	private void doImportLars(File groupDir, GroupImportOptions options, long userId, Group group) throws Exception {
		
		//Import des lars	
		boolean privateLayout;
		Map<String, String[]> parameterMap;
		
		//Eviter les principal exceptions à l'import des LARS
		PrincipalThreadLocal.setName(userId);
		
		if (options.isPrivatePagesEnabled()) {
			privateLayout = true;
			parameterMap = options.getPrivatePagesParameterMap();
			larImporter.doImport(userId, group, groupDir, privateLayout, parameterMap);
		}
		
		if (options.isPublicPagesEnabled()) {
			privateLayout = false;
			parameterMap = options.getPublicPagesParameterMap();
			larImporter.doImport(userId, group, groupDir, privateLayout, parameterMap);
		}	
		
	}
	
	private User getDefaultAdmin(long companyId) throws PortalException, SystemException {
		Role r = RoleLocalServiceUtil.getRole(companyId, RoleConstants.ADMINISTRATOR);
		List<User> users = UserLocalServiceUtil.getRoleUsers(r.getRoleId());
		return users.get(0);
	}
	
}
