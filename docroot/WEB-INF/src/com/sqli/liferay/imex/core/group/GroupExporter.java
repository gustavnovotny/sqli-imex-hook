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

package com.sqli.liferay.imex.core.group;

import com.liferay.portal.model.Group;
import com.liferay.util.Normalizer;
import com.sqli.liferay.imex.core.FilesNames;
import com.sqli.liferay.imex.core.group.model.ImExGroup;
import com.sqli.liferay.imex.core.lar.LarExporter;
import com.sqli.liferay.imex.util.xml.SimpleXmlProcessor;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class GroupExporter {

	private LarExporter larExporter;

	public GroupExporter() {
		larExporter = new LarExporter();
	}

	public void doExport(Group group, File parentDir, GroupOptions options)
			throws Exception {
		String groupDirName = Normalizer.normalizeToAscii(group.getName());
		File groupDir = new File(parentDir, groupDirName);
		boolean success = groupDir.mkdirs();
		if (!success) {
			throw new IOException("Failed to create directory " + groupDir);
		}

		SimpleXmlProcessor<ImExGroup> processor = new SimpleXmlProcessor<ImExGroup>(
				ImExGroup.class, groupDir, FilesNames.GROUP);
		processor.write(new ImExGroup(group));

		boolean privateLayout;
		Map<String, String[]> parameterMap;

		if (options.isPrivatePagesEnabled()) {
			privateLayout = true;
			parameterMap = options.getPrivatePagesParameterMap();
			larExporter.doExport(group, groupDir, privateLayout, parameterMap);
		}

		if (options.isPublicPagesEnabled()) {
			privateLayout = false;
			parameterMap = options.getPublicPagesParameterMap();
			larExporter.doExport(group, groupDir, privateLayout, parameterMap);
		}

	}

}
