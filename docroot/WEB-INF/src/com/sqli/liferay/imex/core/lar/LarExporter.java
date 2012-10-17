package com.sqli.liferay.imex.core.lar;

import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.LayoutLocalServiceUtil;

import java.io.File;
import java.util.Date;
import java.util.Map;

public class LarExporter {

	public LarExporter() {
		
	}

	public void doExport(Group group, File groupDir, boolean privateLayout, Map<String, String[]> parameterMap) throws Exception {
		
		long groupId = group.getGroupId();
		long[] layoutIds = null;
		Date startDate = null;
		Date endDate = null;

		File larFile = LayoutLocalServiceUtil.exportLayoutsAsFile(groupId, privateLayout, layoutIds, parameterMap, startDate, endDate);
		
		FileUtil.move(larFile, LarNameUtil.getLarFile(group, groupDir, privateLayout));
	}
	
}
