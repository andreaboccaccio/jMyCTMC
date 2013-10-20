//Copyright (C)2012-2013
//Andrea Boccaccio contact email: 4ndr34.b0cc4cc10@gmail.com

//This file is part of jMyCTMC.

//jMyCTMC is free software: you can redistribute it and/or modify
//it under the terms of the GNU General Public License as published by
//the Free Software Foundation, either version 3 of the License, or
//(at your option) any later version.

//jMyCTMC is distributed in the hope that it will be useful,
//but WITHOUT ANY WARRANTY; without even the implied warranty of
//MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//GNU General Public License for more details.

//You should have received a copy of the GNU General Public License
//along with jMyCTMC.  If not, see <http://www.gnu.org/licenses/>.

package jMyCTMC.inputFiles;

import jMyCTMC.ctmc.CTMCFactory;
import jMyCTMC.ctmc.ICTMC;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import Jama.Matrix;

class MyCTMCLoader extends FileLoaderAbstract {
	
	MyCTMCLoader(FileLoaderAbstract fa) {
		this.successor = fa;
	}
	
	@Override
	protected boolean check(String pathname) {
		return this.checkExtension(pathname, "CTMC");
	}

	@Override
	public ICTMC load(String pathname) throws Exception {
		ICTMC ret = null;
		BufferedReader inputStrm;
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
		
		if(this.check(pathname))
		{
			i = 0;
			inputStrm = new BufferedReader(new FileReader(pathname));
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
				ret = CTMCFactory.getInstance().getCTCM(new Matrix(tmpQ), new Matrix(si), T, h, e, k);
			} catch (IOException ioe) {
				ioe.printStackTrace();
			} finally {
				inputStrm.close();
			}
		}
		else {
			ret = this.toNext(pathname);
		}
		
		return ret;
	}

}
