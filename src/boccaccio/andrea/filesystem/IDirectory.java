package boccaccio.andrea.filesystem;

import java.io.File;
import java.io.FileFilter;
import java.util.List;

public interface IDirectory {
	
	public List<File> getFiles(FileFilter ff);

}