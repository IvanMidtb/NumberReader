import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.imageio.ImageIO;

public class FileAid {
	public FileAid() {
		
	}

	/**
	 * @param fileName file containing set of images
	 * @param report set to true to display progress as images are processed, false otherwise
	 * @return a data set containing pixel data from images and the corresponding number from their name
	 * @throws IOException
	 */
	public DataSet CreateImageDataSet(String fileName, boolean report) throws IOException {
		DataSet toReturn = new DataSet();
		File imageFile = new File(fileName);
		// creates a set of expected outputs for each number 0-9
		double[][] nums = new double[10][10];
		for(int i = 0; i < 10; i++) {
			for(int j = 0; j < 10; j++) {
				if(i == j) nums[i][j] = 1;
				else nums[i][j] = 0;
			}
		}
		if(report) 
			System.out.println("Begining conversion of images to input data");
		int numImages = 0, reportCount = imageFile.listFiles().length/10, reports = 1;
		// converts each image into a pixel data in a data point with appropriate expected output and adds it to a data sey
		for(File image: imageFile.listFiles()) {
			String imagePathName = image.getName();
			double[] number = nums[Character.getNumericValue(imagePathName.charAt(0))];
			double[] intensities = getIntensities(fileName + "\\" +imagePathName);
			toReturn.addPoint(new DataPoint(intensities, number));
			//reports on progress
			if(report) {
				numImages++;
				if(numImages == reportCount * (reports)) {
					System.out.println(reports*10 + "% of images processed; " + numImages + " images processed");
					reports++;
				}
			}
		}
			if(report)
				System.out.println("done processing images");
			return toReturn;
		}
	
	/**
	 * @param imageName the name of the file to get intensity data on.
	 * @return in[] of intensities
	 * @throws IOException
	 */
	public double[] getIntensities(String imageName) throws IOException {
		double[] toReturn = new double[28*28];
		File file = new File(imageName);
		BufferedImage img = ImageIO.read(file);
		// pixel data to double
		for(int h = 0; h <28; h++) {
			for (int w = 0; w < 28; w++) {
				int p = img.getRGB(w, h);
				if(p == -1){
					toReturn[h*28 + w] = 0;
				}else {
					Color c = new Color(p);
					double intensity = calcInt(c);
					toReturn[h*28 + w] = intensity;
				}
			}
		}
		return toReturn;
	}
	
	private double calcInt(Color c) {
		return (255 - (c.getGreen() + c.getBlue() + c.getRed())/(double)3)/(double)255;
	}

}
