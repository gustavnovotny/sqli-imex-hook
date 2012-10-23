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

package com.sqli.liferay.imex.util.xml;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

public class XmlStreamUtil {
	
	public static XMLStreamReader createXMLStreamReader(File file) throws FileNotFoundException, XMLStreamException {
	
		XMLInputFactory factory = XMLInputFactory.newInstance();
		if(factory.isPropertySupported("javax.xml.stream.isValidating")) {
			factory.setProperty("javax.xml.stream.isValidating", Boolean.TRUE);
		}
	
		XMLStreamReader reader = factory.createXMLStreamReader(new FileReader(file));
	
		return reader;
	}

}
