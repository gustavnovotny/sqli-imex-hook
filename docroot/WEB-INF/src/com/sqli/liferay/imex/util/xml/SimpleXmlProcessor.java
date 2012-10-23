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

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.simpleframework.xml.stream.Format;
import org.simpleframework.xml.stream.HyphenStyle;
import org.simpleframework.xml.stream.Style;

public class SimpleXmlProcessor<T> {

	private Class<T> clazz;
	private File file;
	private Serializer serializer;
	
	public SimpleXmlProcessor(Class<T> clazz, File directory, String fileName) {
		super();
		Style style = new HyphenStyle();
		Format format = new Format(3, "<?xml version=\"1.0\" encoding=\"UTF-8\"?>", style);
		this.serializer = new Persister(format); 
		this.clazz = clazz;
		this.file = new File(directory, fileName);
	}
	
	public T read() throws Exception {
		T result = serializer.read(clazz, file);
		return result;
	}
	
	public void write(T source) throws Exception {
		serializer.write(source, file);
	}

	
	
}
