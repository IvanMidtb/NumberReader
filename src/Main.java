import java.io.IOException;

/**
 * @author Ivan Midtbust Heger
 * Main to show the features of this program
 */
public class Main {

	public static void main(String[] args) {
		try {
			FileAid fa = new FileAid();
			// create training set
			DataSet trainingSet = fa.CreateImageDataSet("data", true);
			DataSet testingSet = new DataSet();
			// takes 10000 data points out of the training set and adds them to a testing set
			for (int i = 0; i < 10000; i++) {
				testingSet.addPoint(trainingSet.popRandomPoint());
			}
			int[] layers = {28*28, 20, 20, 10};
			Network n = new Network(layers);
			Matrix data = new Matrix(10, 1);
			// outputs the data for an image before training
			data.setMatrix(n.run(fa.getIntensities("trainingData/data9_01.png")));
			System.out.println("Results for image data9_01.png:\n"+data);
			// trains the network
			n.SGD(trainingSet, 10, 5000, 5, testingSet);
			// outputs the data for an image after training
			data.setMatrix(n.run(fa.getIntensities("trainingData/data9_01.png")));
			System.out.println("Results for image data9_01.png:\n"+data);
			System.out.println("done");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
