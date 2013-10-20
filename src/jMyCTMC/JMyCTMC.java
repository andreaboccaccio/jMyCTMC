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

package jMyCTMC;

import jMyCTMC.ctmc.ICTMC;
import jMyCTMC.filesystem.DirectoryFactory;
import jMyCTMC.filesystem.FileFilterFactory;
import jMyCTMC.inputFiles.FileLoaderCor;
import jMyCTMC.outputFiles.FileWriterFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class JMyCTMC {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		List<String> ff = new ArrayList<>();
		List<File> lf;
		ICTMC tmpCTCM;
		String inputFilename;
		String outputFilename;
		int i = -1;
		int max = -1;
		ff.add("ssv");
		ff.add("ctmc");
		lf = DirectoryFactory.getInstance().getDirectory(".").getFiles(FileFilterFactory.getInstance().getFileFilter(ff));
		
		max = lf.size();
		
		for(i=0;i<max;++i) {
			try {
				inputFilename = lf.get(i).getAbsolutePath();
				outputFilename = inputFilename + ".output.txt";
				tmpCTCM = FileLoaderCor.getInstance().load(inputFilename);
				FileWriterFactory.getInstance().getFileWriter().write(tmpCTCM, outputFilename);
				System.out.println(inputFilename + " computed, results in " + outputFilename);
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
