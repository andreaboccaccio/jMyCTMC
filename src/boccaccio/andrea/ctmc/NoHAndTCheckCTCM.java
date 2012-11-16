package boccaccio.andrea.ctmc;

import Jama.Matrix;

class NoHAndTCheckCTCM extends MyCTCM {

	protected NoHAndTCheckCTCM(Matrix q, Matrix statoIniziale, double t,
			double h, double epsilon, int k) throws Exception {
		super(q, statoIniziale, t, h, epsilon, k);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void init(Matrix Q, Matrix Si, double t, double h,
			double epsilon, int k) throws Exception {
		// TODO Auto-generated method stub
		try {
			super.init(Q, Si, t, h, epsilon, k);
		} catch (HAndTException e) {
			this.setT(t);
			this.setH(h);
			this.setEpsilon(epsilon);
			this.setK(k);
			try {
				this.computeStazionario();
			} catch (Exception e2) {
				this.setStazionario(null);
			}
			this.calcolaPassoIntegrazione();
			this.computeTransitorio();
		}
		
	}
	
	

}
