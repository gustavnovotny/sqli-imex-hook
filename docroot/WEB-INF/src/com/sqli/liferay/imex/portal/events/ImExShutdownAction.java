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
