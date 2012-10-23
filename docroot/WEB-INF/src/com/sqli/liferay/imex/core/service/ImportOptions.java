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

package com.sqli.liferay.imex.core.service;

import com.liferay.portal.kernel.util.GetterUtil;
import com.sqli.liferay.imex.core.group.GroupImportOptions;
import com.sqli.liferay.imex.portal.util.PropsKeys;

import java.util.Properties;

public class ImportOptions extends Options<GroupImportOptions> {
	
	public boolean reinitAllPermissionsOnImport;
	
	@Override
	protected GroupImportOptions newGroupOptions(String prefix, Properties props) {
		
		return new GroupImportOptions(prefix, props);
	}
	
	public boolean isReinitAllPermissionsOnImport() {
		return reinitAllPermissionsOnImport;
	}
	public void setReinitAllPermissionsOnImport(boolean reinitAllPermissionsOnImport) {
		this.reinitAllPermissionsOnImport = reinitAllPermissionsOnImport;
	}

	@Override
	public void load(Properties props, String prefix) {
		
		reinitAllPermissionsOnImport = GetterUtil.getBoolean(props.get(PropsKeys.IMEX_PERMISSIONS_UPDATE_REINIT));
		super.load(props, prefix);
		
	}
	
}
