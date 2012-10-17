package com.sqli.liferay.imex.core.group;

import com.liferay.portal.DuplicateGroupException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.sqli.liferay.imex.core.FilesNames;
import com.sqli.liferay.imex.core.group.model.ImExGroup;
import com.sqli.liferay.imex.core.lar.LarImporter;
import com.sqli.liferay.imex.util.xml.SimpleXmlProcessor;

import java.io.File;
import java.util.List;
import java.util.Map;

public class GroupImporter {
	
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
		
		Group group;
		//try {
			group = GroupLocalServiceUtil.addGroup(userId, className, classPK, name, description, type, friendlyURL, site, active, serviceContext);
			
			GroupLocalServiceUtil.updateGroup(group.getGroupId(), typeSettings);
			
		/*} catch(DuplicateGroupException e) {
			
			if() {
				GroupLocalServiceUtil.updateGroup(groupId, name, description, type, friendlyURL, active, serviceContext);
			} else () {
				
			}
		}*/
		
		
		
		boolean privateLayout;
		Map<String, String[]> parameterMap;
		
		if(options.isPrivatePagesEnabled()) {
			privateLayout = true;
			parameterMap = options.getPrivatePagesParameterMap();
			larImporter.doImport(group, groupDir, privateLayout, parameterMap);
		}
		
		if(options.isPublicPagesEnabled()) {
			privateLayout = false;
			parameterMap = options.getPublicPagesParameterMap();
			larImporter.doImport(group, groupDir, privateLayout, parameterMap);
		}
	}

	private User getDefaultAdmin(long companyId) throws PortalException, SystemException {
		Role r = RoleLocalServiceUtil.getRole(companyId, RoleConstants.ADMINISTRATOR);
		List<User> users = UserLocalServiceUtil.getRoleUsers(r.getRoleId());
		return users.get(0);
	}
	
	
	
}
