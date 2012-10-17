package com.sqli.liferay.imex.core.service;

import com.sqli.liferay.imex.core.group.GroupImportOptions;

public class ImportOptions extends Options<GroupImportOptions> {

	@Override
	protected GroupImportOptions newGroupOptions() {
		return new GroupImportOptions();
	}

}
