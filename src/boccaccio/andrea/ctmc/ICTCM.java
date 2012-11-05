package boccaccio.andrea.ctmc;

import java.util.List;

import Jama.Matrix;

public interface ICTCM {
	
	public int getK();

	public List<Matrix> getTransitorio();
	
	public boolean isIrreducible();
	
	public Matrix getStazionario() throws Exception;
}
