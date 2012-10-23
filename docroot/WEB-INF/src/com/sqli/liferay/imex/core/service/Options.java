package com.sqli.liferay.imex.core.service;

import com.liferay.portal.kernel.util.GetterUtil;
import com.sqli.liferay.imex.core.group.GroupOptions;

import java.util.Properties;

public abstract class Options<GROUP_OPTIONS extends GroupOptions> {

	private boolean sitesEnabled;
	private GROUP_OPTIONS sitesOptions;

	public void load(Properties props, String prefix) {
		sitesEnabled = GetterUtil.getBoolean(props.getProperty(prefix + "sites.enabled"));
		sitesOptions = newGroupOptions(prefix + "sites.", props);
		sitesOptions.load();
	}
	
	abstract protected GROUP_OPTIONS newGroupOptions(String prefix, Properties props);

	public boolean isSitesEnabled() {
		return sitesEnabled;
	}

	public void setSitesEnabled(boolean sitesEnabled) {
		this.sitesEnabled = sitesEnabled;
	}

	public GROUP_OPTIONS getSitesOptions() {
		return sitesOptions;
	}

	public void setSitesOptions(GROUP_OPTIONS sitesOptions) {
		this.sitesOptions = sitesOptions;
	}

}
