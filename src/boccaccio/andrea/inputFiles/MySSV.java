package boccaccio.andrea.inputFiles;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import Jama.Matrix;
import boccaccio.andrea.ctmc.CTCMFactory;
import boccaccio.andrea.ctmc.ICTCM;

public class MySSV implements IFileLoader {

	@Override
	public ICTCM load(String pathname) throws Exception {
		ICTCM ret = null;
		BufferedReader inputStrm = new BufferedReader(new FileReader(pathname));
		String currentLine;
		String[] fields;
		int i = -1;
		int tmpI = -1;
		int j = -1;
		int tmpJ = -1;
		int n = -1;
		double h = -1;
		double T = -1;
		double e = -1;
		int k = -1;
		int tmpF = -1;
		double[][] si = null;
		double[][] tmpQ = null;
		double rowSum = -1;
		
		i = 0;
		try {
			currentLine = inputStrm.readLine();
			while(currentLine != null) {
				fields = currentLine.split(";");
				switch (i) {
				case 0:
					n = Integer.parseInt(fields[0]);
					h = Double.parseDouble(fields[1]);
					T = Double.parseDouble(fields[2]);
					e = Double.parseDouble(fields[3]);
					k = Integer.parseInt(fields[4]);
					si = new double[1][n];
					tmpQ = new double[n][n];
					break;
				case 1:
					for(j = 0; j < n; ++j) {
						si[0][j] = Double.parseDouble(fields[j]);
					}
					break;

				default:
					tmpI = (i-2);
					tmpF = 0;
					for(j = 0; j < n; ++j) {
						if(j==tmpI) {
							tmpQ[tmpI][j] = 0;
						} else {
							tmpQ[tmpI][j] = Double.parseDouble(fields[tmpF]);
							++tmpF;
						}
					}
					
					break;
				}
				++i;
				currentLine = inputStrm.readLine();
			}
			for(j = 0; j < n; ++j) {
				rowSum = 0;
				for(tmpJ = 0; tmpJ < n; ++tmpJ) {
					if(tmpJ != j) {
						rowSum += tmpQ[j][tmpJ];
					}
				}
				tmpQ[j][j] = -rowSum;
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
