package com.sqli.liferay.imex.core.lar;

import com.liferay.portal.model.Group;
import com.liferay.portal.service.LayoutLocalServiceUtil;

import java.io.File;
import java.util.Map;

public class LarImporter {

	public LarImporter() {
		
	}

	public void doImport(Group group, File groupDir, boolean privateLayout, Map<String, String[]> parameterMap) throws Exception {
		
		long userId = 0;
		long groupId = group.getGroupId();

		File file = LarNameUtil.getLarFile(group, groupDir, privateLayout);

		LayoutLocalServiceUtil.importLayouts(userId, groupId, privateLayout, parameterMap, file);

	}
	
}
