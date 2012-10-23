package com.sqli.liferay.imex.core.service;

import com.sqli.liferay.imex.core.group.GroupExportOptions;

import java.util.Properties;

public class ExportOptions extends Options<GroupExportOptions> {

	@Override
	protected GroupExportOptions newGroupOptions(String prefix, Properties props) {
		return new GroupExportOptions(prefix, props);
	}

}
