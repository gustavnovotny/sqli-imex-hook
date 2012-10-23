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
