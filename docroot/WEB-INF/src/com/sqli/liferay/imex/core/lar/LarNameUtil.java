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
