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

package jMyCTMC.inputFiles;

import jMyCTMC.ctmc.ICTMC;
import jMyCTMC.properties.IMyProperties;
import jMyCTMC.properties.MyPropertiesFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class FileLoaderCor extends FileLoaderAbstract {
	
private static FileLoaderCor instance=null;
	
	private FileLoaderCor() {
		IMyProperties mp;
		FileLoaderAbstract lastOne = null;
		boolean ok = false;
		try {
			mp = MyPropertiesFactory.getInstance().getProp();
			String[] classes = mp.getProperty("FileLoaderClasses").split(",");
			
			for(int i = (classes.length-1); i > -1; --i) {
				String curCl = classes[i].trim();
				try {
					Class<?> cl = Class.forName(curCl);
					Constructor<?> ci = cl.getDeclaredConstructor(new Class<?>[]{ Class.forName("jMyCTMC.inputFiles.FileLoaderAbstract") });
					FileLoaderAbstract fa = (FileLoaderAbstract) ci.newInstance(new Object[] { lastOne });
					lastOne = fa;
					ok = true;
				} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | NoSuchMethodException | SecurityException | IllegalArgumentException | InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			lastOne = new NullFileLoader(lastOne);
		}
		if(!ok) {
			lastOne = new NullFileLoader(lastOne);
		}
		this.successor = lastOne;
	}
	
	public static FileLoaderCor getInstance() {
		if(instance==null) {
			instance = new FileLoaderCor();
		}
		return instance;
	}

	@Override
	public ICTMC load(String pathname) throws FileNotFoundException,
			IOException, Exception {
		return this.toNext(pathname);
	}

}
