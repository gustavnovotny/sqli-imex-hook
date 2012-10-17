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

	public void doExport(Group group, File parentDir, GroupOptions options) throws Exception {
		String groupDirName = Normalizer.normalizeToAscii(group.getName());
		File groupDir = new File(parentDir, groupDirName);
		boolean success = groupDir.mkdirs();
		if (!success) {
			throw new IOException("Failed to create directory " + groupDir);			
		}
		
		SimpleXmlProcessor<ImExGroup> processor = new SimpleXmlProcessor<ImExGroup>(ImExGroup.class, groupDir, FilesNames.GROUP);
		processor.write(new ImExGroup(group));

		boolean privateLayout;
		Map<String, String[]> parameterMap;
		
		if(options.isPrivatePagesEnabled()) {
			privateLayout = true;
			parameterMap = options.getPrivatePagesParameterMap();
			larExporter.doExport(group, groupDir, privateLayout, parameterMap);
		}
		
		if(options.isPublicPagesEnabled()) {
			privateLayout = false;
			parameterMap = options.getPublicPagesParameterMap();
			larExporter.doExport(group, groupDir, privateLayout, parameterMap);
		}
			
	}

}
