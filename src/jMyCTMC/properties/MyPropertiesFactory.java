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
import java.util.InvalidPropertiesFormatException;

public class MyPropertiesFactory {
	
private static MyPropertiesFactory instance=null;
	
	private MyPropertiesFactory() throws InvalidPropertiesFormatException, IOException {
		this.prop = MyROPropertiesSingleton.getInstance();
	}
	
	public static MyPropertiesFactory getInstance() throws InvalidPropertiesFormatException, IOException {
		if(instance==null) {
			instance = new MyPropertiesFactory();
		}
		return instance;
	}
	
	public IMyProperties getProp() {
		return prop;
	}
	
	private IMyProperties prop;	
}
