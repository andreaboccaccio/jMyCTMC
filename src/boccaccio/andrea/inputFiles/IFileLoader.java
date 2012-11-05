package boccaccio.andrea.inputFiles;

import java.io.FileNotFoundException;
import java.io.IOException;

import boccaccio.andrea.ctmc.ICTCM;

public interface IFileLoader {

	public ICTCM load(String pathname) throws FileNotFoundException, IOException, Exception;
	
}