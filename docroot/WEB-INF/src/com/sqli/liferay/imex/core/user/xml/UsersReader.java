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
