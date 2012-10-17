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
