//Copyright (C)2012-2013
//Andrea Boccaccio contact email: 4ndr34.b0cc4cc10@gmail.com

//This file is part of jMyCTMC.

//jMyCTMC is free software: you can redistribute it and/or modify
//it under the terms of the GNU General Public License as published by
//the Free Software Foundation, either version 3 of the License, or
//(at your option) any later version.

//jMyCTMC is distributed in the hope that it will be useful,
//but WITHOUT ANY WARRANTY; without even the implied warranty of
//MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//GNU General Public License for more details.

//You should have received a copy of the GNU General Public License
//along with jMyCTMC.  If not, see <http://www.gnu.org/licenses/>.

package jMyCTMC.properties;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.Enumeration;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;
import java.util.Set;

class MyROPropertiesSingleton implements IMyProperties {
	
	private static MyROPropertiesSingleton instance=null;
	
	private MyROPropertiesSingleton() throws InvalidPropertiesFormatException, IOException {
		Properties def = new Properties();
		FileInputStream in = new FileInputStream("defaultConfiguration.xml");
		
		def.loadFromXML(in);
		in.close();
		this.prop = new Properties(def);
		in = new FileInputStream(
				this.prop.getProperty(
						"appConfigurationFileName",
						"AppConfiguration.xml"
						)
				);
		this.prop.loadFromXML(in);
		in.close();
	}
	
	public static MyROPropertiesSingleton getInstance() throws InvalidPropertiesFormatException, IOException {
		if(instance==null) {
			instance = new MyROPropertiesSingleton();
		}
		return instance;
	}
	
	@Override
	public String getProperty(String key) {
		return this.prop.getProperty(key);
	}
	
	@Override
	public String getProperty(String key, String defaultValue) {
		return this.prop.getProperty(key, defaultValue);
	}
	
	@Override
	public void list(PrintStream out) {
		this.prop.list(out);
	}
	
	@Override
	public void list(PrintWriter out) {
		this.prop.list(out);
	}
	
	@Override
	public Enumeration<?> propertyNames() {
		return this.prop.propertyNames();
	}
	
	@Override
	public Set<String> stringPropertyNames() {
		return this.prop.stringPropertyNames();
	}
	
	@Override
	public void load(Reader reader) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void load(InputStream inStream) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void store(Writer writer, String comments) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void store(OutputStream out, String comments) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void loadFromXML(InputStream in) throws IOException,
			InvalidPropertiesFormatException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void storeToXML(OutputStream os, String comment) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void storeToXML(OutputStream os, String comment, String encoding)
			throws IOException {
		// TODO Auto-generated method stub
		
	}
	
	private Properties prop;
	
}
