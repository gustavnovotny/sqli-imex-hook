package com.sqli.liferay.imex.portal.util;

import com.liferay.portal.kernel.util.GetterUtil;

public class PropsValues {
	
	public static int PERMISSIONS_USER_CHECK_ALGORITHM = GetterUtil.getInteger(PropsUtil.get(PropsKeys.PERMISSIONS_USER_CHECK_ALGORITHM));

	public static final boolean IMEX_PERMISSIONS_UPDATE_REINIT =  GetterUtil.getBoolean((PropsKeys.IMEX_PERMISSIONS_UPDATE_REINIT));
	
}
