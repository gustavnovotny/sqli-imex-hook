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

public class PropsValues {
	
	public static int PERMISSIONS_USER_CHECK_ALGORITHM = GetterUtil.getInteger(PropsUtil.get(PropsKeys.PERMISSIONS_USER_CHECK_ALGORITHM));

	public static final boolean IMEX_PERMISSIONS_UPDATE_REINIT =  GetterUtil.getBoolean((PropsKeys.IMEX_PERMISSIONS_UPDATE_REINIT));
	
}
