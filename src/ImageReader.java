import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;

import javax.imageio.ImageIO;

public class ImageReader {
	
	public ImageReader() {
		//Generic Constructor
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
	
	
	public void write(Network n, String fileName){
		try {
			FileWriter fw = new FileWriter(fileName);
			Matrix[] matrixSet = n.getWeights();
			for(Matrix m: matrixSet) {
				fw.write(m + "\n");
			}
			Matrix[] biasSet = n.getBiases();
			for(Matrix b: biasSet) {
				fw.write(b + "\n");
			}
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

