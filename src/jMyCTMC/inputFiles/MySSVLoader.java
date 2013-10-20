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

class MySSVLoader extends FileLoaderAbstract {

	public MySSVLoader(FileLoaderAbstract fa) {
		this.successor = fa;
	}
	
	@Override
	protected boolean check(String pathname) {
		return this.checkExtension(pathname, "SSV");
	}

	@Override
	public ICTMC load(String pathname) throws Exception {
		ICTMC ret = null;
		BufferedReader inputStrm;
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
		
		if(this.check(pathname))
		{
			i = 0;
			inputStrm = new BufferedReader(new FileReader(pathname));			
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
