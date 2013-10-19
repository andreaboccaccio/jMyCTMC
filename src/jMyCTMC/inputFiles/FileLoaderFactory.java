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

public class FileLoaderFactory {

	private static FileLoaderFactory instance=null;
	
	private FileLoaderFactory() {
		
	}
	
	public static FileLoaderFactory getInstance() {
		if(instance==null) {
			instance = new FileLoaderFactory();
		}
		return instance;
	}
	
	public IFileLoader getFileLoader() {
		return new MyCTMCLoader();
	}
}
