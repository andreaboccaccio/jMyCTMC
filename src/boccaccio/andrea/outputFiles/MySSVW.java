package boccaccio.andrea.outputFiles;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.List;

import Jama.Matrix;
import boccaccio.andrea.ctmc.ICTCM;

class MySSVW implements IFileWriter {

	@Override
	public void write(ICTCM ctcm, String pathname) throws Exception {
		BufferedWriter outputStrm = new BufferedWriter(new FileWriter(pathname));
		Matrix tmpM;
		List<Matrix> tmpTra;
		int i = -1;
		int max = -1;
		int j = -1;
		int maxT = -1;
		
		try {
			if(ctcm.isIrreducible()) {
				tmpM = ctcm.getStazionario();
				max = tmpM.getColumnDimension();
				for (i = 0;i<max;++i) {
					if(i>0) {
						outputStrm.write(";");
					}
					outputStrm.write(String.format("%.16e", tmpM.get(0, i)));
				}
			}
			outputStrm.newLine();
			outputStrm.write("**********");
			outputStrm.newLine();
			tmpTra = ctcm.getTransitorio();
			maxT = tmpTra.size();
			for(j=0;j<maxT;++j) {
				if((j%ctcm.getK())==0) {
					if(j>0) {
						outputStrm.newLine();
					}
					outputStrm.write("t="+j+"*h");
					outputStrm.newLine();
					tmpM = tmpTra.get(j);
					max = tmpM.getColumnDimension();
					for (i = 0;i<max;++i) {
						if(i>0) {
							outputStrm.write(";");
						}
						outputStrm.write(String.format("%.16e", tmpM.get(0, i)));
					}
				}
			}
		} finally {
			outputStrm.close();
		}

	}

}
