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

package com.sqli.liferay.imex.portal.kernel.deploy.auto;

import com.liferay.portal.kernel.deploy.auto.AutoDeployDir;
import com.liferay.portal.kernel.deploy.auto.AutoDeployListener;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.io.File;
import java.util.List;

public class ImExAutoDeployDir extends AutoDeployDir {

	public ImExAutoDeployDir(String name, File deployDir,
			long interval, int blacklistThreshold,
			List<AutoDeployListener> listeners) {
		super(name, deployDir, null, interval, blacklistThreshold, listeners);
	}
	
	protected void scanDirectory() {
		File[] files = this.getDeployDir().listFiles();

		for (int i = 0; i < files.length; i++) {
			File file = files[i];
			String fileName = file.getName().toLowerCase();
			if (file.isFile() && fileName.endsWith(".properties")) {
				processFile(file);
			}
		}
	}

	private final static Log LOG = LogFactoryUtil.getLog(ImExAutoDeployDir.class);

}
