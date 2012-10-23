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
