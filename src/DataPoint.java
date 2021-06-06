
/**
 * @author Ivan
 * class that stores an input and an expected output for a data point to train a network
 */
public class DataPoint {
	private double[] input, output;
	
	public DataPoint(double[] input, double[] output) {
		this.input = input;
		this.output = output;
	}

	public double[] getInput() {
		return input;
	}

	public double[] getOutput() {
		return output;
	}
}
