package boccaccio.andrea.filesystem;

import java.io.FileFilter;
import java.util.List;

public class FileFilterFactory {

	private static FileFilterFactory instance=null;
	
	private FileFilterFactory() {
		
	}
	
	public static FileFilterFactory getInstance() {
		if(instance==null) {
			instance = new FileFilterFactory();
		}
		return instance;
	}
	
	public FileFilter getFileFilter(List<String> params) {
		
		return new MyFileFilterMultipleExtension(params);
	}
}
