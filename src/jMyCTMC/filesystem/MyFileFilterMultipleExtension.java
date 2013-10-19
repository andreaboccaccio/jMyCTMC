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

package jMyCTMC.filesystem;

import java.io.File;
import java.io.FileFilter;
import java.util.List;

class MyFileFilterMultipleExtension implements FileFilter {
	
	private List<String> strExt;

	/**
	 * @param strExt 
	 */
	protected MyFileFilterMultipleExtension(List<String> strExt) {
		super();
		this.strExt = strExt;
	}

	@Override
	public boolean accept(File pathname) {
		boolean found = false;
		int i = -1;
		int l = this.strExt.size();
		String tmpStr;
		
		for(i=0;(!found)&&(i<l);++i) {
			tmpStr = this.strExt.get(i);
			if((pathname.isFile())&&(pathname.getName().endsWith(tmpStr))) {
				found = true;
			}
		}
		return found;
	}

}
