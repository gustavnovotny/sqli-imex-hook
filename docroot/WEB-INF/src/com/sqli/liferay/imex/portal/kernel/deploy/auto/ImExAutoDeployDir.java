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
