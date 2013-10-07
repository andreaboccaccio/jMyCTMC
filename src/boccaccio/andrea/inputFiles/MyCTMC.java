package boccaccio.andrea.inputFiles;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import Jama.Matrix;
import boccaccio.andrea.ctmc.CTCMFactory;
import boccaccio.andrea.ctmc.ICTCM;

public class MyCTMC implements IFileLoader {

	public ICTCM load(String pathname) throws Exception {
		ICTCM ret = null;
		BufferedReader inputStrm = new BufferedReader(new FileReader(pathname));
		String currentLine;
		String[] fields;
		int i = -1;
		int j = -1;
		int n = -1;
		double h = -1;
		double T = -1;
		double e = -1;
		int k = -1;
		double[][] si = null;
		double[][] tmpQ = null;
		i = 0;
		try {
			h = 0.1;
			T = 1000;
			e = 0.0001;
			k = 100;
			currentLine = inputStrm.readLine();
			while(currentLine != null) {
				if(currentLine.startsWith("# rows:")) {
					fields = currentLine.split(":");
					n = Integer.parseInt(fields[1].trim());
					si = new double[1][n];
					tmpQ = new double[n][n];
				}else if((!currentLine.startsWith("#"))&&(currentLine.trim().length() > 0)) {
					fields = currentLine.split(" ");
					for(j = 0; j < n; ++j) {
						tmpQ[i][j] = Double.parseDouble(fields[j]);
					}
					++i;
				}
				currentLine = inputStrm.readLine();
			}
			si[0][0] = 1;
			for(j = 1; j < n; ++j) {
				si[0][j] = 0;
			}
			ret = CTCMFactory.getInstance().getCTCM(new Matrix(tmpQ), new Matrix(si), T, h, e, k);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			inputStrm.close();
		}
		
		return ret;
	}

}
