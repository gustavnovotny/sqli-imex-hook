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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.model.Group;
import com.sqli.liferay.imex.core.group.model.enums.OnDuplicateMethodEnum;

import java.util.Properties;


public class GroupImportOptions extends GroupOptions{
	
	private static Log _log = LogFactoryUtil.getLog(GroupOptions.class);
	
	private OnDuplicateMethodEnum onDuplicateBehavior;
	
	public GroupImportOptions(String prefix, Properties props) {
		super(prefix, props);
	}
	
	@Override
	public void load() {
		
		super.load();
		
		String value = GetterUtil.getString(super.getProps().get(super.getPrefix() + "on.duplicate"));
		onDuplicateBehavior = OnDuplicateMethodEnum.fromValue(value);
		if (onDuplicateBehavior == null || onDuplicateBehavior.equals("")) {
			onDuplicateBehavior = OnDuplicateMethodEnum.SKIP;
			_log.warn("Setting default value [" + onDuplicateBehavior.getValue() + "]");
		}
	}
	
	public OnDuplicateMethodEnum getOnduplicateBehavior(Group group) {
		
		if (group != null) {
			
			String suffix = "." + group.getFriendlyURL().replaceAll("/", "");
			String value = GetterUtil.getString(super.getProps().get(super.getPrefix() + "on.duplicate" + suffix));
			
			if (value != null && !value.equals("")) {
				OnDuplicateMethodEnum converted = OnDuplicateMethodEnum.fromValue(value);
				
				if (converted != null) {
					return converted;
				}
			}
			
		}
		return this.onDuplicateBehavior;
		
	}

	public OnDuplicateMethodEnum getOnDuplicateBehavior() {
		return onDuplicateBehavior;
	}

	public void setOnDuplicateBehavior(OnDuplicateMethodEnum onDuplicateBehavior) {
		this.onDuplicateBehavior = onDuplicateBehavior;
	}


}
