package boccaccio.andrea.ctmc;

import Jama.Matrix;

public class CTCMFactory {

	private static CTCMFactory instance=null;
	
	private CTCMFactory() {
		
	}
	
	public static CTCMFactory getInstance() {
		if(instance==null) {
			instance = new CTCMFactory();
		}
		return instance;
	}
	
	public ICTCM getCTCM(Matrix Q, Matrix Si, double t, double h, double epsilon, int k) throws Exception {
		return new MyCTCM(Q, Si, t, h, epsilon, k);
	}
}
