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

package com.sqli.liferay.imex.servlet;

import com.liferay.portal.kernel.deploy.auto.AutoDeployDir;
import com.liferay.portal.kernel.deploy.auto.AutoDeployListener;
import com.liferay.portal.kernel.deploy.auto.AutoDeployUtil;
import com.liferay.portal.kernel.util.BasePortalLifecycle;
import com.sqli.liferay.imex.portal.events.ImExStartupAction;
import com.sqli.liferay.imex.portal.kernel.deploy.auto.ImExAutoDeployDir;
import com.sqli.liferay.imex.portal.kernel.deploy.auto.ImExAutoDeployListener;
import com.sqli.liferay.imex.portal.util.ImExPropsKeys;
import com.sqli.liferay.imex.portal.util.ImExPropsValues;
import com.sqli.liferay.imex.portal.util.PrefsPropsUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ImExServletContextListener extends BasePortalLifecycle implements ServletContextListener {

	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		portalDestroy();
	}

	public void contextInitialized(ServletContextEvent servletContextEvent) {
		registerPortalLifecycle();
	}

	@Override
	protected void doPortalDestroy() throws Exception {
		
		if (_log.isInfoEnabled()) {
			_log.info("Unregistering IMEX auto deploy directories");
		}
		
		AutoDeployUtil.unregisterDir(ImExStartupAction.IMEX_AUTO_DEPLOY_DIR);
	}

	@Override
	protected void doPortalInit() throws Exception {
		
		if (_log.isInfoEnabled()) {
			_log.info("Unregistering IMEX auto deploy directories");
		}
		
		AutoDeployUtil.unregisterDir(IMEX_AUTO_DEPLOY_DIR);
		
		try {
			if (PrefsPropsUtil.getBoolean(
					ImExPropsKeys.ENABLED,
					ImExPropsValues.ENABLED)) {

				if (_log.isInfoEnabled()) {
					_log.info("Registering IMEX auto deploy directories");
				}
				
				

				File deployDir = new File(
					PrefsPropsUtil.getString(
						ImExPropsKeys.DEPLOY_DIR,
						ImExPropsValues.DEPLOY_DIR));
				long interval = PrefsPropsUtil.getLong(
					ImExPropsKeys.INTERVAL,
					ImExPropsValues.INTERVAL);
				int blacklistThreshold = PrefsPropsUtil.getInteger(
					ImExPropsKeys.BLACKLIST_THRESHOLD,
					ImExPropsValues.BLACKLIST_THRESHOLD);

				List<AutoDeployListener> autoDeployListeners =
					getAutoDeployListeners();

				AutoDeployDir autoDeployDir = new ImExAutoDeployDir(
					IMEX_AUTO_DEPLOY_DIR, deployDir, interval,
					blacklistThreshold, autoDeployListeners);

				AutoDeployUtil.registerDir(autoDeployDir);
			}
			else {
				if (_log.isInfoEnabled()) {
					_log.info("Not registering IMEX auto deploy directories");
				}
			}
		}
		catch (Exception e) {
			_log.error(e);
		}
		
	}
	
	private List<AutoDeployListener> getAutoDeployListeners() {
		List<AutoDeployListener> list = new ArrayList<AutoDeployListener>();
		list.add(new ImExAutoDeployListener());
		return list;
	}
	
	public static final String IMEX_AUTO_DEPLOY_DIR = "IMEXAutoDeployDir";
	
	private static Log _log = LogFactory.getLog(ImExAutoDeployListener.class);
}
