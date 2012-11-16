package boccaccio.andrea.outputFiles;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.List;

import Jama.Matrix;
import boccaccio.andrea.ctmc.ICTCM;

public class MySSVWWithHeading implements IFileWriter {

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
			tmpM = ctcm.getQ();
			max = tmpM.getRowDimension();
			outputStrm.write("n=" + max);
			outputStrm.write(";");
			outputStrm.write(" h=" + String.format("%.16e", ctcm.getH()));
			outputStrm.write(";");
			outputStrm.write(" T=" + String.format("%.16e", ctcm.getT()));
			outputStrm.write(";");
			outputStrm.write(" epsilon=" + String.format("%.16e", ctcm.getEpsilon()));
			outputStrm.write(";");
			outputStrm.write(" k=" + ctcm.getK());
			outputStrm.newLine();
			outputStrm.write("Q");
			outputStrm.newLine();
			outputStrm.write(this.matrixToString(tmpM, "%.16e"));
			if(ctcm.isIrreducible()) {
				outputStrm.newLine();
				outputStrm.write("Catena irriducibile soluzione all'equilibrio");
				outputStrm.newLine();
				tmpM = ctcm.getStazionario();
				outputStrm.write(this.matrixToString(tmpM, "%.16e"));
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
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			outputStrm.close();
		}

	}
	
	protected String matrixToString(Matrix m, String format) {
		StringBuilder ret = new StringBuilder();
		int i = -1;
		int j = -1;
		int maxI = -1;
		int maxJ = -1;
		
		for(i=0,maxI=m.getRowDimension(),maxJ=m.getColumnDimension();i<maxI;++i) {
			if(i>0) {
				ret.append(System.getProperty("line.separator"));
			}
			for(j=0;j<maxJ;++j) {
				if(j>0) {
					ret.append(";");
				}
				ret.append(String.format(format, m.get(i,j)));
			}
		}
		return ret.toString();
	}

}
