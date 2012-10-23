package com.sqli.liferay.imex.core.group;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropertiesUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class GroupOptions {

	private String prefix;
	private Properties props;
	
	private boolean privatePagesEnabled;
	private Map<String, String[]> privatePagesParameterMap;
	private boolean publicPagesEnabled;
	private Map<String, String[]> publicPagesParameterMap;
	
	public GroupOptions(String prefix, Properties props) {
		super();
		this.prefix = prefix;
		this.props = props;
	}

	public void load() {
		
		privatePagesEnabled = GetterUtil.getBoolean(props.get(prefix + "private.pages.enabled"));
		privatePagesParameterMap = buildParameterMap(props, prefix + "private.pages.parameter.");
		
		publicPagesEnabled = GetterUtil.getBoolean(props.get(prefix + "public.pages.enabled"));
		publicPagesParameterMap = buildParameterMap(props, prefix + "public.pages.parameter.");
		
	}
	
	protected Map<String, String[]> buildParameterMap(Properties props, String prefix) {
		
		boolean removePrefix = true;
		Properties parameterProperties = PropertiesUtil.getProperties(props, prefix, removePrefix);
		
		Map<String, String[]> parameterMap = new HashMap<String, String[]>();
		for (Map.Entry entry : parameterProperties.entrySet()) {
			String key = (String)entry.getKey();
			String[] value = ((String)entry.getValue()).split(",");
			parameterMap.put(key, value);
		}
		
		return parameterMap;
	}

	public boolean isPrivatePagesEnabled() {
		return privatePagesEnabled;
	}

	public void setPrivatePagesEnabled(boolean privatePagesEnabled) {
		this.privatePagesEnabled = privatePagesEnabled;
	}

	public Map<String, String[]> getPrivatePagesParameterMap() {
		return privatePagesParameterMap;
	}

	public void setPrivatePagesParameterMap(Map<String, String[]> privatePagesParameterMap) {
		this.privatePagesParameterMap = privatePagesParameterMap;
	}

	public boolean isPublicPagesEnabled() {
		return publicPagesEnabled;
	}

	public void setPublicPagesEnabled(boolean publicPagesEnabled) {
		this.publicPagesEnabled = publicPagesEnabled;
	}

	public Map<String, String[]> getPublicPagesParameterMap() {
		return publicPagesParameterMap;
	}

	public void setPublicPagesParameterMap(Map<String, String[]> publicPagesParameterMap) {
		this.publicPagesParameterMap = publicPagesParameterMap;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public Properties getProps() {
		return props;
	}

	public void setProps(Properties props) {
		this.props = props;
	}


}
