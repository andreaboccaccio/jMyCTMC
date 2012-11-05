package jMyCTCM;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import Jama.Matrix;
import boccaccio.andrea.ctmc.ICTCM;
import boccaccio.andrea.filesystem.DirectoryFactory;
import boccaccio.andrea.filesystem.FileFilterFactory;
import boccaccio.andrea.inputFiles.FileLoaderFactory;
import boccaccio.andrea.outputFiles.FileWriterFactory;

public class JMyCTCM {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		List<String> ff = new ArrayList<>();
		List<File> lf;
		Matrix tmpStazionario;
		List<Matrix> tmpTransitorio;
		ICTCM tmpCTCM;
		String inputFilename;
		String outputFilename;
		int i = -1;
		int j = -1;
		int max = -1;
		int k = -1;
		int t = -1;
		
		ff.add("input.ssv");
		lf = DirectoryFactory.getInstance().getDirectory(".").getFiles(FileFilterFactory.getInstance().getFileFilter(ff));
		
		max = lf.size();
		
		for(i=0;i<max;++i) {
			try {
				inputFilename = lf.get(i).getAbsolutePath();
				outputFilename = inputFilename.substring(0,inputFilename.lastIndexOf("input.ssv")) + "output.ssv";
				tmpCTCM = FileLoaderFactory.getInstance().getFileLoader().load(inputFilename);
				FileWriterFactory.getInstance().getFileWriter().write(tmpCTCM, outputFilename);
				if(tmpCTCM.isIrreducible()) {
					System.out.println("Soluzione stazionaria");
					tmpStazionario = tmpCTCM.getStazionario();
					tmpStazionario.print(20, 15);
				}
				System.out.println("Soluzione in transitorio");
				tmpTransitorio = tmpCTCM.getTransitorio();
				k = tmpCTCM.getK();
				for(j=0;j<tmpTransitorio.size();++j) {
					t = j % k;
					if(t==0) {
						System.out.println("j=" + j);
						tmpTransitorio.get(j).print(20, 15);
					}
				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
