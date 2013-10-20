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

package jMyCTMC.ctmc;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import jMyCTMC.inputFiles.NullFileLoader;
import jMyCTMC.properties.IMyProperties;
import jMyCTMC.properties.MyPropertiesFactory;
import Jama.Matrix;

public class CTMCFactory {

	private static CTMCFactory instance=null;
	
	private CTMCFactory() {
		
	}
	
	public static CTMCFactory getInstance() {
		if(instance==null) {
			instance = new CTMCFactory();
		}
		return instance;
	}
	
	public ICTMC getCTCM(Matrix Q, Matrix Si, double t, double h, double epsilon, int k) throws Exception {
		IMyProperties mp;
		ICTMC ret = null;
		
		try {
			Class<?> cm = Class.forName("Jama.Matrix");
			mp = MyPropertiesFactory.getInstance().getProp();
			Class<?> cl = Class.forName(mp.getProperty("ICTMCClass").trim());
			Constructor<?> ci = cl.getDeclaredConstructor(new Class<?>[]{ cm, cm, double.class, double.class, double.class, int.class });
			ret = (ICTMC) ci.newInstance(new Object[] { Q, Si, t, h, epsilon, k });
		}
		catch (ClassNotFoundException | InstantiationException | IllegalAccessException | NoSuchMethodException | SecurityException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
			ret = new NoHAndTCheckCTMC(Q, Si, t, h, epsilon, k);
		}
		
		return ret;
	}
}
