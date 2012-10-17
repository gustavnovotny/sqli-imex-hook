package com.sqli.liferay.imex.portal.events;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.liferay.portal.kernel.deploy.auto.AutoDeployDir;
import com.liferay.portal.kernel.deploy.auto.AutoDeployListener;
import com.liferay.portal.kernel.deploy.auto.AutoDeployUtil;
import com.liferay.portal.kernel.events.ActionException;
import com.liferay.portal.kernel.events.SimpleAction;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.sqli.liferay.imex.portal.kernel.deploy.auto.ImExAutoDeployDir;
import com.sqli.liferay.imex.portal.kernel.deploy.auto.ImExAutoDeployListener;
import com.sqli.liferay.imex.portal.util.ImExPropsKeys;
import com.sqli.liferay.imex.portal.util.ImExPropsValues;
import com.sqli.liferay.imex.portal.util.PrefsPropsUtil;

public class ImExStartupAction extends SimpleAction {

	public static final String IMEX_AUTO_DEPLOY_DIR = "IMEXAutoDeployDir";
	
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
		
		this.executed = true;
	}

	private List<AutoDeployListener> getAutoDeployListeners() {
		List<AutoDeployListener> list = new ArrayList<AutoDeployListener>();
		list.add(new ImExAutoDeployListener());
		return list;
	}

	private static Log _log = LogFactoryUtil.getLog(ImExStartupAction.class);
}
