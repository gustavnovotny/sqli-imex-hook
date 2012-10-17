package com.sqli.liferay.imex.core.role.model;

import java.util.HashMap;
import java.util.Map;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;

@Root(name="role")
public class ImExRole {
	
	public enum Type {
		COMMUNITY(RoleConstants.TYPE_SITE),
		ORGANIZATION(RoleConstants.TYPE_ORGANIZATION),
		REGULAR(RoleConstants.TYPE_REGULAR);
		
		private int intValue;
		
		Type(int intValue) {
			this.intValue = intValue;
		}
		
		public int getIntValue() {
			return intValue;
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
	private String name;
	@Element
	private Type type;
	@Element(required=false)
	private String description;

	public ImExRole() {
		
	}
	
	public ImExRole(Role role) {
		this.name = role.getName();
		this.type = Type.getFromIntValue(role.getType());
		this.description = role.getDescription();
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	

}
