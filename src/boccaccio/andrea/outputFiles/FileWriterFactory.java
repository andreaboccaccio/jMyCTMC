package boccaccio.andrea.outputFiles;

public class FileWriterFactory {
	private static FileWriterFactory instance=null;
	
	private FileWriterFactory() {
		
	}
	
	public static FileWriterFactory getInstance() {
		if(instance==null) {
			instance = new FileWriterFactory();
		}
		return instance;
	}
	
	public IFileWriter getFileWriter() {
		return new MySSVWWithHeading();
	}
}
