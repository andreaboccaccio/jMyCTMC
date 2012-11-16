package boccaccio.andrea.ctmc;

import java.util.List;

import Jama.Matrix;

public interface ICTCM {
	
	public int getK();
	
	public Matrix getQ();
	
	public double getH();
	
	public double getT();
	
	public double getEpsilon();
	
	public Matrix getSi();

	public List<Matrix> getTransitorio();
	
	public boolean isIrreducible();
	
	public Matrix getStazionario() throws Exception;
}
