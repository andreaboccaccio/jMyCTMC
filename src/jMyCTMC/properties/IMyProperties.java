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

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.Enumeration;
import java.util.InvalidPropertiesFormatException;
import java.util.Set;

public interface IMyProperties {
	
	public String getProperty(String key);
	
	public String getProperty(String key, String defaultValue);
	
	public void list(PrintStream out);
	
	public void list(PrintWriter out);
	
	public void load(Reader reader) throws IOException;
	
	public void load(InputStream inStream) throws IOException;
	
	public void store(Writer writer, String comments) throws IOException;
	
	public void store(OutputStream out, String comments) throws IOException;
	
	public void loadFromXML(InputStream in) throws IOException, InvalidPropertiesFormatException;
	
	public void storeToXML(OutputStream os, String comment) throws IOException;
	
	public void storeToXML(OutputStream os, String comment, String encoding) throws IOException;
	
	public Enumeration<?> propertyNames();
	
	public Set<String> stringPropertyNames();
}
