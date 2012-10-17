package com.sqli.liferay.imex.core.service;

import com.sqli.liferay.imex.core.group.GroupExportOptions;

public class ExportOptions extends Options<GroupExportOptions> {

	@Override
	protected GroupExportOptions newGroupOptions() {
		return new GroupExportOptions();
	}

}
