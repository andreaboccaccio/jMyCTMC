package boccaccio.andrea.filesystem;

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
