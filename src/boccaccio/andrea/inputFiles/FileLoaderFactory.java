package boccaccio.andrea.inputFiles;

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
		return new MyCTMC();
	}
}
