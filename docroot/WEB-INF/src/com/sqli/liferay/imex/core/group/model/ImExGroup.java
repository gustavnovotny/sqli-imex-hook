package com.sqli.liferay.imex.core.group.model;

import com.liferay.portal.model.Group;
import com.liferay.portal.model.GroupConstants;

import java.util.HashMap;
import java.util.Map;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementMap;
import org.simpleframework.xml.Root;

@Root(name="group")
public class ImExGroup {
	
	public enum Type {
		OPEN(GroupConstants.TYPE_SITE_OPEN, GroupConstants.TYPE_SITE_OPEN_LABEL),
		PRIVATE(GroupConstants.TYPE_SITE_PRIVATE, GroupConstants.TYPE_SITE_PRIVATE_LABEL),
		RESTRICTED(GroupConstants.TYPE_SITE_RESTRICTED, GroupConstants.TYPE_SITE_RESTRICTED_LABEL),
		SYSTEM(GroupConstants.TYPE_SITE_SYSTEM, GroupConstants.TYPE_SITE_SYSTEM_LABEL);
		
		private int intValue;
		private String label;
		
		Type(int intValue, String label) {
			this.intValue = intValue;
			this.label = label;
		}
		
		public int getIntValue() {
			return intValue;
		}
		
		public String getLabel() {
			return label;
		}
		
		private static Map<Integer, Type> typesFromIntValue = new HashMap<Integer, Type>();
		
		public static Type getFromIntValue(int value) {
			if (typesFromIntValue.size() == 0) {
				for (Type type : Type.values()) {
					typesFromIntValue.put(type.getIntValue(), type);
				}
			}
			
			return typesFromIntValue.get(value);
		}
	}
	
	@Element
	private String className;
	
	@Element
	private String name;
	
	@Element(required=false)
	private String description;
	
	@Element
	private Type type;
	
	@ElementMap
	private Map<String,String> typeSettings;
	
	@Element
	private String friendlyURL;
	
	@Element
	private boolean site;
	
	@Element
	private boolean active;

	public ImExGroup() {
		
	}
	
	public ImExGroup(Group group) {
		this.className = group.getClassName();
		this.name = group.getName();
		this.description = group.getDescription();
		this.type = Type.getFromIntValue(group.getType());
		this.typeSettings = new HashMap<String, String>(group.getTypeSettingsProperties());
		this.friendlyURL = group.getFriendlyURL();
		this.site = group.isSite();
		this.active = group.isActive();
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public Map<String, String> getTypeSettings() {
		return typeSettings;
	}

	public void setTypeSettings(Map<String, String> typeSettings) {
		this.typeSettings = typeSettings;
	}

	public String getFriendlyURL() {
		return friendlyURL;
	}

	public void setFriendlyURL(String friendlyURL) {
		this.friendlyURL = friendlyURL;
	}

	public boolean isSite() {
		return site;
	}

	public void setSite(boolean site) {
		this.site = site;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	
	
	
}
