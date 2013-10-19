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

package jMyCTMC.ctmc;

import java.util.ArrayList;
import java.util.List;

import org.jgrapht.DirectedGraph;
import org.jgrapht.alg.StrongConnectivityInspector;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;

import Jama.Matrix;

class MyCTMC implements ICTMC {
	
	private Matrix Q;

	private DirectedGraph<Integer, DefaultEdge> internalGraph;
	
	private double T;
	
	private double h;
	
	private int k;
	
	private double epsilon;
	
	private Matrix stazionario;
	
	private Matrix passoIntegrazione;
	
	private Matrix statoIniziale;
	
	private List<Matrix> transitorio;
	
	protected void setQ(Matrix q) {
		Q = q;
	}

	protected void setT(double t) {
		T = t;
	}

	protected void setH(double h) {
		this.h = h;
	}

	protected void setK(int k) {
		this.k = k;
	}

	protected void setEpsilon(double epsilon) {
		this.epsilon = epsilon;
	}

	protected void setStazionario(Matrix stazionario) {
		this.stazionario = stazionario;
	}

	protected MyCTMC(Matrix q, Matrix statoIniziale, double t, double h, double epsilon, int k) throws Exception {
		super();
		this.init(q, statoIniziale, t, h, epsilon, k);
	}

	protected void init(Matrix Q, Matrix Si, double t, double h, double epsilon, int k) throws Exception {
		int n = -1;
		int i = -1;
		int j = -1;
		double siCheck = 0;
		double lambdaMin = -1;
		double lambdaMax = -1;
		
		if(Q.getRowDimension() != Q.getColumnDimension()) {
			throw new Exception("Transition Rate Matrix Q has to be a square matrix");
		}
		this.Q = Q.copy();
		this.internalGraph = new SimpleDirectedGraph<>(DefaultEdge.class);
		n = this.getQ().getRowDimension();
		for (i = 0; i < n; i++) {
			this.internalGraph.addVertex(new Integer((i+1)));
		}
		lambdaMin = this.getQ().get(0, 1);
		lambdaMax = this.getQ().get(0, 1);
		for (i = 0; i < n; i++) {
			for(j = 0; j < n; j++) {
				if(j!=i) {
					if(this.getQ().get(i, j) > lambdaMax) {
						lambdaMax = this.getQ().get(i, j);
					}
					if((this.getQ().get(i, j) < lambdaMin)&&(this.getQ().get(i, j) > 0)) {
						lambdaMin = this.getQ().get(i, j);
					}
					if(this.getQ().get(i, j) > 0) {
						this.internalGraph.addEdge(new Integer((i+1)), new Integer((j+1)));
					}
				}
			}
		}
		if((Si.getRowDimension() != 1) || (Si.getColumnDimension() != n)) {
			throw new Exception("Intial State has to be 1xN");
		}
		for(i = 0; i < Si.getColumnDimension(); ++i) {
			siCheck += Si.get(0, i);
		}
		if(siCheck != 1) {
			throw new Exception("Starting vector components sum != 1");
		}
		this.statoIniziale = Si.copy();
		if(t <= (1/lambdaMin)) {
			throw new TTooLow("T <= 1/lambdaMin, T=" +T + " lambdaMin=" + lambdaMin + " 1/lambdaMin=" +(1/lambdaMin));
		}
		this.T = t;
		if(h >= (1/lambdaMax)) {
			throw new HTooHigh("h >= 1/lambdaMax, h=" +h + " lambdaMax=" + lambdaMax + " 1/lambdaMax=" +(1/lambdaMax));
		}
		this.h = h;
		this.epsilon = epsilon;
		this.k = k;
		try {
			this.computeStazionario();
		} catch (Exception e) {
			this.stazionario = null;
		}
		
		this.calcolaPassoIntegrazione();
		this.computeTransitorio();
	}
	
	
	
	protected void computeStazionario() throws Exception {
		
		Matrix W = this.getQ().copy();
		Matrix b;
		int i = -1;
		int n = W.getColumnDimension();
		int c = (int)Math.floor((Math.random()*n));
		double[][] ba = new double[1][n];
		
		if(!this.isIrreducible()) {
			throw new Exception("Trying to compute steady state in a reducible CTMC.");
		}
		b = new Matrix(ba);
		b.set(0,c,1);
		for (i = 0; i<n; ++i) {
			W.set(i, c, 1);
			if(i!=c) {
				b.set(0,i,0);
			}
		}
		this.stazionario = b.times(W.inverse());
		
	}
	
	protected boolean testMatrixMajor(Matrix A, Matrix B) {
		boolean ret = false;
		Matrix tmpC;
		int i = -1;
		int j = -1;
		int r = A.getRowDimension();
		int c = A.getColumnDimension();
		
		if((r == B.getRowDimension())&&(c == B.getColumnDimension())) {
			tmpC = A.minus(B);
			ret = false;
			for(i = 0; (i<r)&&(!ret); ++i) {
				for(j = 0; (j<c)&&(!ret);++j) {
					if(tmpC.get(i, j)>0) {
						ret = ret || true;
					}
				}
			}
		}
		
		return ret;
	}
	
	protected void calcolaPassoIntegrazione() {
		
		int i = -1;
		Matrix tmpOld = new Matrix(this.getQ().getRowDimension(), this.getQ().getColumnDimension());
		Matrix Diff;
		Matrix Acc;
		Matrix E = new Matrix(this.getQ().getRowDimension(), this.getQ().getColumnDimension(), this.epsilon);
		Matrix tmpNew = this.getQ().copy();
		
		for(i = 0; i < this.getQ().getRowDimension(); ++i) {
			tmpOld.set(i, i, 1);
		}
		tmpNew = tmpNew.times(this.h);
		Acc = tmpNew.plus(tmpOld);
		Diff = tmpNew.minus(tmpOld);
		i=2;
		tmpOld = tmpNew.copy();
		while(this.testMatrixMajor(Diff, E)) {
			tmpNew = tmpNew.times((this.h/i)).times(this.getQ());
			Diff = tmpNew.minus(tmpOld);
			Acc = tmpNew.plus(Acc);
			tmpOld = tmpNew.copy();
			++i;
		}
		this.passoIntegrazione = Acc.copy();
	}
	
	protected Matrix getPassoIntegrazione() {
		return this.passoIntegrazione;
	}
	
	protected void computeTransitorio() {
		int i = -1;
		int j = -1;
		int max = -1;
		double c = -1;
		double tmp = -1;
		Matrix tmpP = this.statoIniziale.copy();
		
		this.transitorio = new ArrayList<>();
		this.transitorio.add(tmpP);
		for (i=1;(i*this.h)<=this.T;++i) {
			tmpP = tmpP.times(this.getPassoIntegrazione());
			max = tmpP.getColumnDimension();
			c = 0;
			for (j = 0; j < max; ++j) {
				c += tmpP.get(0, j);
			}
			for (j = 0; j < max; ++j) {
				tmp = tmpP.get(0, j);
				tmpP.set(0, j, (tmp/c));
			}
			this.transitorio.add(tmpP);
		}
	}
	
	@Override
	public int getK() {
		return k;
	}
	
	@Override
	public Matrix getQ() {
		return this.Q;
	}

	@Override
	public boolean isIrreducible() {
		
		StrongConnectivityInspector<Integer, DefaultEdge> tmpSCI = new StrongConnectivityInspector<>(this.internalGraph);
		
		return tmpSCI.isStronglyConnected();
	}

	@Override
	public List<Matrix> getTransitorio() {
		return this.transitorio;
	}

	@Override
	public Matrix getStazionario() {
		return this.stazionario;
	}

	@Override
	public double getH() {
		// TODO Auto-generated method stub
		return this.h;
	}

	@Override
	public double getT() {
		// TODO Auto-generated method stub
		return this.T;
	}

	@Override
	public double getEpsilon() {
		// TODO Auto-generated method stub
		return this.epsilon;
	}

	@Override
	public Matrix getSi() {
		// TODO Auto-generated method stub
		return this.statoIniziale;
	}

}