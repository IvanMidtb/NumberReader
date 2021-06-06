/**
 * @author Ivan
 *
 *
 */
public class Network {
	
	private Matrix[] weights, biases;
	private int numLayers;
	int[] sizes;
	
	
	/**
	 * @param sizes array of the number of nodes at each layer, the first layer
	 * is the input and the last is the output.
	 */
	public Network(int[] sizes) {
		numLayers = sizes.length;
		this.sizes = sizes;
		weights = new Matrix[numLayers - 1];
		biases = new Matrix[numLayers - 1];
		generateRandWeights();
		generateRandBiasess();
	}

	// generate random weights using a normal distribution
	private void generateRandWeights() {
		for(int i = 0; i < weights.length; i++) {
			weights[i] = new Matrix(sizes[i + 1], sizes[i]);
			weights[i].genRandom();
		}
	}
	// generate random biases using a normal distribution
	private void generateRandBiasess() {
		for(int i = 0; i < biases.length; i++) {
			biases[i] = new Matrix(sizes[i + 1], 1);
			biases[i].genRandom();
		}
	}
	
	/**
	 * @return a Matrix[] of the weights
	 */
	public Matrix[] getWeights() {
		return weights;
	}
	
	
	/**
	 * @return a Matrix[] of the biases
	 */
	public Matrix[] getBiases() {
		return biases;
	}
	
	/**
	 * @param weights a Matrix[] of weights to set in the network
	 */
	public void setWeights(Matrix[] weights) {
		this.weights = weights;
	}
	
	/**
	 * @param biasesa Matrix[] of biases to set in the network
	 */
	public void setBiases(Matrix[] biases) {
		this.biases = biases;
	}
	
	
	/**
	 * Takes a input and runs it through the network, returning the output
	 * 
	 * @param input
	 * @return the output of the network
	 */
	public double[] run(double[] input) {
		Matrix values = new Matrix(input.length, 1);
		values.setMatrix(input);
		for(int i = 0; i < numLayers - 1; i++) {
			values = runSet(values, i).sigmoid();
		}
		return values.getVector();
	}
	
	
	/**
	 * @param values of the previous layer
	 * @param layerNum the layer number to compute
	 * @return the value of the layer to be computed before being run with a Sigmoid function
	 */
	private Matrix runSet(Matrix values, int layerNum) {
		// System.out.println(weights[layerNum] + "\n" + biases[layerNum]);
		return weights[layerNum].mult(values).add(biases[layerNum]);
	}

	public int[] getSizes() {
		return sizes;
	}
	
	/**
	 * @param trainingSet set of data to be use for learning
	 * @param epochs number of batches to run for training
	 * @param batchSize number of data points to use in each batch
	 * @param eta eta to use for gradient descent
	 * @param testingData testing data to use after each batch
	 */
	public void SGD(DataSet trainingSet, int epochs, int batchSize, double eta, DataSet testingData) {
		boolean test = (testingData != null);
		for (int i = 0; i < epochs; i++) {
			DataSet miniBatch = new DataSet();
			for(int j = 0; j < batchSize; j++) {
				if(!trainingSet.isEmpty())
					miniBatch.addPoint(trainingSet.popRandomPoint());
			}
			// trains the network with the mini batch
			trainMiniBatch(miniBatch, eta);
			// test the accuracy of the network
			if(test) {
				System.out.print("epoch " + i + ":");
				System.out.println(evaluate(testingData)*100 + "% success");
			}
		}
		System.out.println("all batches completed");
	}
	
	// trains network with a single minibatch of inputs and expected outputs
	private void trainMiniBatch(DataSet miniBatch, double eta) {
		double runs = miniBatch.size();
		Matrix[] weightChangeSum = new Matrix[numLayers - 1], biasChangeSum = new Matrix[numLayers -1];
		boolean first = true;
		
		while(!miniBatch.isEmpty()) {
			DataPoint point = miniBatch.popRandomPoint();
			// z is the output of layer n before sig function and a is after the sig function 
			Matrix[] z =  new Matrix[numLayers - 1], a = new Matrix[numLayers - 1];
			double[] in = point.getInput(), out = point.getOutput();
			
			// y represents the expected outputs
			Matrix y = new Matrix(out.length, 1);
			y.setMatrix(out);
			// values stores the values calculated at each layer
			Matrix values, inputMatrix = new Matrix(in.length, 1);
			inputMatrix.setMatrix(in);
			values = inputMatrix;
			// computes the z and a for each layer. n = 0 corresponds to layer after input i.e. layer 2
			for(int i = 0; i < numLayers - 1; i++) {
				z[i] = runSet(values, i);
				a[i] = z[i].sigmoid();
				values = a[i];
			}
			// delta stores the error of each layer
			Matrix[] delta = backPropogation(z, y);
			Matrix[] dWeight = gradientWeightDescent(delta, a, inputMatrix), dBias = gradienBiasDescent(delta);
			if(first) {
				weightChangeSum = dWeight;
				biasChangeSum = dBias;
				first = false;
			}else {
				for (int i = 0; i < numLayers - 1; i++) {
					weightChangeSum[i].addToThis(dWeight[i]);
					biasChangeSum[i].addToThis(dBias[i]);
				}
			}
		}
		for (int i = 0; i < numLayers - 1; i++) {
			weights[i] = weights[i].subtract(weightChangeSum[i].mult((eta/runs)));
			biases[i] = biases[i].subtract(biasChangeSum[i].mult((eta/runs)));
		}
	}
	
	
	// runs backpropogation to get the error of each layer
		private Matrix[] backPropogation(Matrix[] z, Matrix y) {
			Matrix[] delta = new Matrix[numLayers - 1];
			// start at last layer
			int i = numLayers - 2;
			// delta of last layer. (a(L) - y) hadproduct sig'(z(L))
			Matrix costDerivative  = z[i].sigmoid().subtract(y);
			delta[i] = costDerivative.HadProduc(z[i].sigmoidPrime());
			for(i = numLayers - 3; i>=0; i--) {
				Matrix tWeight = weights[i+1].Transpose();
				Matrix x = tWeight.mult(delta[i+1]);
				delta[i] = x.HadProduc(z[i].sigmoidPrime());
			}
			return delta;
		}
	
	/**
	 * @param delta
	 * @param a
	 * @return gradient descent for the weights of a single data point
	 */
	private Matrix[] gradientWeightDescent(Matrix[] delta, Matrix[] a, Matrix in) {
		Matrix[] toReturn = new Matrix[numLayers - 1];
		for(int i = weights.length - 1; i > 0; i--) {
			toReturn[i] = delta[i].mult(a[i-1].Transpose());
		}
		toReturn[0] = delta[0].mult(in.Transpose());
		return toReturn;
	}
	
	/**
	 * @param delta
	 * @param a
	 * @return gradient descent for the biases of a single data point
	 */
	private Matrix[] gradienBiasDescent(Matrix[] delta) {
		Matrix[] toReturn = new Matrix[numLayers - 1];
		for(int i = biases.length - 1; i >= 0; i--) {
			toReturn[i] = delta[i];
		}
		return toReturn;
	}

	/**
	 * @param trainingSet set of data to be use for learning
	 * @param epochs number of baches to run for training
	 * @param bachSize number of data points to use in each bach
	 * @param eta eta to use for gradient descent
	 */
	public void SGD(DataSet trainingSet, int epochs, int bach_size, double eta) {
		SGD(trainingSet, epochs, bach_size, eta, null);
	}
	
	/**
	 * @param testingData data set to be examined containing the input and expected output
	 * @return the successes of the network as a number from 0 to 1
	 */
	public double evaluate(DataSet testingData) {
		int total = testingData.size(), good = 0;
		// determine how many runs are success full
		for(DataPoint p: testingData.getData()) {
			if(evalRun(p)) {
				good++;
			}
		}
		// calculate success
		return good/(double)total;
	}
	
	// helper method for evaluate
	private boolean evalRun(DataPoint point) {
		double[] result = run(point.getInput());
		int high = 0;
		//finds the highest output to compare with the expected result
		for(int i = 0; i < result.length; i++) {
			if(result[i] > result[high])
				high = i;
		}
		if(point.getOutput()[high] == 1) {
			return true;
		}
		return false;
	}
}