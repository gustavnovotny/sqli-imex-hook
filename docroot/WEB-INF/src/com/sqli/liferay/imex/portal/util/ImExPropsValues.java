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
