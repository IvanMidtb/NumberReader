import java.util.ArrayList;
import java.util.Random;


/**
 * @author Ivan Midtbust Heger
 * Set of DataPoints to train a network
 */
public class DataSet{
	private ArrayList<DataPoint> data;
	private Random r;
	public DataSet() {
		data = new ArrayList<>();
		r = new Random();
	}
	
	/**
	 * @return a random DataPoint and removes it from the list
	 * 
	 */
	public DataPoint popRandomPoint() {
		if(isEmpty()) return null;
		return data.remove(r.nextInt(data.size()));
	}
	
	/**
	 * @param d data point to be added
	 */
	public void addPoint(DataPoint d) {
		data.add(d);
	}
	
	public boolean isEmpty() {
		return data.isEmpty();
	}

	public int size() {
		return data.size();
	}

	public ArrayList<DataPoint> getData(){
		return data;
	}
	
	/**
	 * randomly shuffles data in the data set
	 */
	public void shuffleData(){
		ArrayList<DataPoint> shuffled = new ArrayList<>();
		while(!data.isEmpty())
			shuffled.add(data.remove(r.nextInt(data.size())));
		data = shuffled;
	}
}
