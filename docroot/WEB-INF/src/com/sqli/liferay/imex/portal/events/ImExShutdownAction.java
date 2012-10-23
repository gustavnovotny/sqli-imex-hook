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

package com.sqli.liferay.imex.portal.events;

import com.liferay.portal.kernel.deploy.auto.AutoDeployUtil;
import com.liferay.portal.kernel.events.ActionException;
import com.liferay.portal.kernel.events.SimpleAction;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

public class ImExShutdownAction extends SimpleAction {
	
	private boolean executed = false;
	
	@Override
	public void run(String[] ids) throws ActionException {
		// Auto deploy
		
		if (this.executed) {
			return;
		}
		
		if (_log.isInfoEnabled()) {
			_log.info("Unregistering IMEX auto deploy directories");
		}
		
		AutoDeployUtil.unregisterDir(ImExStartupAction.IMEX_AUTO_DEPLOY_DIR);
		
		this.executed = true;
	}

	private static Log _log = LogFactoryUtil.getLog(ImExShutdownAction.class);
}
