package boccaccio.andrea.filesystem;

public class DirectoryFactory {
	
	private static DirectoryFactory instance=null;
	
	private DirectoryFactory() {
		
	}
	
	public static DirectoryFactory getInstance() {
		if(instance==null) {
			instance = new DirectoryFactory();
		}
		return instance;
	}
	
	public IDirectory getDirectory(String path) {
		
		return new MyDirectory(path);
	}
}
