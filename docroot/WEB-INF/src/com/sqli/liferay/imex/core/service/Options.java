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
