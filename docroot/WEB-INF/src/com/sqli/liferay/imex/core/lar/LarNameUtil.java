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

package com.sqli.liferay.imex.core.lar;

import com.liferay.portal.model.Group;

import java.io.File;

class LarNameUtil {
	
	static File getLarFile(Group group, File groupDir, boolean privateLayout) {
		//File result = new File(groupDir, groupDir.getName() + "_" + (privateLayout?"Private":"Public") + "_Pages.lar" );
		File result = new File(groupDir,(privateLayout?"Private":"Public") + "_Pages.lar" );
		return result;
	}

}
