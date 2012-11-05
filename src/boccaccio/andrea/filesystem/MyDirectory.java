/**
 * 
 */
package boccaccio.andrea.filesystem;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

class MyDirectory implements IDirectory {
	
	private File internalFile;
	private List<File> listFiles;

	protected MyDirectory(String path) {
		this.internalFile = new File(path);
		this.listFiles = new ArrayList<>();
	}

	@Override
	public List<File> getFiles(FileFilter ff) {
		File[] tmpFiles;
		int i = -1;
		
		if(!this.listFiles.isEmpty()) {
			this.listFiles.clear();
		}
		if(this.internalFile.isDirectory()) {
			tmpFiles = this.internalFile.listFiles(ff);
			for (i = 0; i < tmpFiles.length; i++) {
				this.listFiles.add(tmpFiles[i]);
			}
		}
		
		return this.listFiles;
	}
	
	
}
