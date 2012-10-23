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

package com.sqli.liferay.imex.portal.util;

import com.liferay.portal.kernel.util.GetterUtil;

public class ImExPropsValues {

	public static final int BLACKLIST_THRESHOLD =
		GetterUtil.getInteger(
			PropsUtil.get(ImExPropsKeys.BLACKLIST_THRESHOLD), 10);

	public static final String DEPLOY_DIR =
		GetterUtil.getString(
		PropsUtil.get(ImExPropsKeys.DEPLOY_DIR), PropsUtil.get("auto.deploy.deploy.dir") + "/imex");

	public static final boolean ENABLED =
		GetterUtil.getBoolean(PropsUtil.get(ImExPropsKeys.ENABLED), true);

	public static final int INTERVAL = GetterUtil.getInteger(
		PropsUtil.get(ImExPropsKeys.INTERVAL), 10000);
}
