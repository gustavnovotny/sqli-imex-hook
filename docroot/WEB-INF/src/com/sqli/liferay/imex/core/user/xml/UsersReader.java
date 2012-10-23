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

package com.sqli.liferay.imex.core.user.xml;

import com.sqli.liferay.imex.util.xml.XmlStreamUtil;

import java.io.File;
import java.io.FileNotFoundException;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;


public class UsersReader {
	
	private XMLStreamReader reader;
	private boolean started;

	UsersReader(File roleDirectory) throws FileNotFoundException, XMLStreamException {
		File xmlFile = new File(roleDirectory, "role-permissions.xml");
		reader = XmlStreamUtil.createXMLStreamReader(xmlFile);
		started = false;
	}
		
	
	private void start() {
		
	}
	
	boolean readNextPortletPermissions() throws Exception {
		boolean hasNext = false;
		
		if (!started) {
			reader.next(); // <role-permissions>
		}
		
		reader.next(); // 
		
		if (!hasNext) {
			reader.close();
		}
		
		return hasNext;
	}

}
