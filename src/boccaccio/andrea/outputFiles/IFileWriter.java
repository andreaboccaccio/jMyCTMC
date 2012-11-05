package boccaccio.andrea.outputFiles;

import boccaccio.andrea.ctmc.ICTCM;

public interface IFileWriter {

	public void write(ICTCM ctcm, String pathname) throws Exception;
}
