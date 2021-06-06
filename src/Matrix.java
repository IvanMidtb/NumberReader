import java.util.Random;

public class Matrix{
	
	private double[][] matrix;
	
	public Matrix(int h, int l) {
		matrix = new double[h][l];
	}
	
	public int getHeight() {
		return matrix.length;
	}
	
	public int getLength() {
		return matrix[0].length;
	}
	
	public void genRandom() {
		for(int h = 0; h < getHeight(); h++) {
			for(int l = 0; l < getLength(); l++) {
				matrix[h][l] = new Random().nextGaussian();
			}
		}
	}
	
	public void setMatrix(double[][] d) {
		matrix = d;
	}
	
	public void setMatrix(double[] d) {
		for(int i = 0; i < d.length; i++) {
			matrix[i][0] = d[i];
		}
	}
	
	public void setPoint(int h, int l, double v) {
		matrix[h][l] = v;
	}
	
	public double get(int h, int l) {
		return matrix[h][l];
	}
	
	public Matrix addToThis(Matrix m) {
		if(this.getHeight() != m.getHeight() || this.getLength() != m.getLength()) System.out.println("Error");
		for(int h = 0; h < matrix.length; h++) {
			for(int l = 0; l < matrix[h].length; l++) {
				matrix[h][l] += m.get(h, l);
			}	
		}
		return this;
	}
	
	public Matrix multToThis(double s) {
		for(int h = 0; h < matrix.length; h++) {
			for(int l = 0; l < matrix[h].length; l++) {
				matrix[h][l] *= s;
			}
		}
		return this;
	}
	
	public Matrix mult(double i) {
		Matrix toReturn = new Matrix(this.getHeight(), this.getLength());
		for(int h = 0; h < this.getHeight(); h++) {
			for(int l = 0; l < this.getLength(); l++) {
				toReturn.setPoint(h, l, this.get(h, l) * i);
			}
		}
		return toReturn;
	}
	

	public Matrix mult(Matrix m) {
		Matrix toReturn = new Matrix(this.getHeight(), m.getLength());
		if(m.getHeight() != this.getLength()) System.out.println("opps");
		for(int h = 0; h < toReturn.getHeight(); h++) {
			for(int l = 0; l < toReturn.getLength(); l++) {
				toReturn.setPoint(h, l,  multAt(h, l, m));
			}
		}
		return toReturn;
	}
	
	private double multAt(int h, int l, Matrix m) {
		double toReturn = 0;
		for(int i = 0; i < m.getHeight(); i++) {
			toReturn += this.get(h, i) * m.get(i, l) ;
		}
		return toReturn;
	}

	/**
	 * @param m matrix to be added
	 * @return the addition of the two matixies without edditing either one
	 */
	public Matrix add(Matrix m) {
		Matrix toReturn = new Matrix(m.getHeight(), m.getLength());
		for(int h = 0; h < this.getHeight(); h++) {
			for(int l = 0; l < m.getLength(); l++) {
				toReturn.setPoint(h, l, this.get(h, l) + m.get(h, l));
			}
		}
		return toReturn;
	}
	
	/**
	 * @param m matrix to be subtracted
	 * @return the subtraction of the two matixies without edditing either one
	 */
	public Matrix subtract(Matrix m) {
		Matrix toReturn = new Matrix(m.getHeight(), m.getLength());
		for(int h = 0; h < this.getHeight(); h++) {
			for(int l = 0; l < m.getLength(); l++) {
				toReturn.setPoint(h, l, this.get(h, l) - m.get(h, l));
			}
		}
		return toReturn;
	}
	
	@Override
	public String toString() {
		String toReturn = "";
		for(int h = 0; h < this.getHeight(); h++) {
			for(int l = 0; l < this.getLength(); l++) {
				toReturn += " " + this.get(h, l);
			}
			if(h < this.getHeight() - 1) toReturn += "\n";
		}
		return toReturn;
	}

	public Matrix sigmoid() {
		Matrix toReturn = new Matrix(this.getHeight(), this.getLength());
		for(int h = 0; h < this.getHeight(); h++) {
			for(int l = 0; l < this.getLength(); l++) {
				toReturn.setPoint(h, l, sigmoidFunction(get(h, l)));
			}
		}
		return toReturn;
	}
	
	// Derivative of sigmoid fuction
	private double sigmoidFunction(double d) {
		return (1 / (1 + Math.exp(d)));
	}

	public Matrix sigmoidPrime() {
		Matrix toReturn = new Matrix(this.getHeight(), this.getLength());
		for(int h = 0; h < this.getHeight(); h++) {
			for(int l = 0; l < this.getLength(); l++) {
				toReturn.setPoint(h, l, sigmoidPrimeFunction(get(h, l)));
			}
		}
		return toReturn;
	}
	
	private double sigmoidPrimeFunction(double d) {
		return sigmoidFunction(d)*(1-sigmoidFunction(d));
	}
	
	/**
	 * Requires this matrix to be a vector
	 * @param m another vector
	 * @return the Hadamard product of the two vectors 
	 * S.HadProducs(T) -> (S * T)j = SjTj
	 */
	public Matrix HadProduc(Matrix m) {
		Matrix toReturn = new Matrix(m.getHeight(), 1);
		for(int i = 0; i < m.getHeight(); i++) {
			toReturn.setPoint(i, 0, (this.get(i, 0) * m.get(i, 0)));
		}
		return toReturn;
	}
	
	/**
	 * @return a transposed vesion of the matrix
	 */
	public Matrix Transpose() {
		Matrix toReturn = new Matrix(this.getLength(), this.getHeight()); 
		for (int l = 0; l < this.getLength(); l++) {
			for (int h = 0; h < this.getHeight(); h++) {
				toReturn.setPoint(l, h, this.get(h, l));
			}
		}
		return toReturn;
	}

	public double[] getVector() {
		double[] toReturn = new double[getHeight()];
		for (int i = 0; i < toReturn.length; i++) {
			toReturn[i] = this.get(i, 0);
		}
		return toReturn;
	}
}