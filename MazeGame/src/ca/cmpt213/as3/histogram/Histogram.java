package ca.cmpt213.as3.histogram;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;

/**
 * 
 * @author Jaspal
 *
 */
public class Histogram implements Iterable<Histogram.Bar>{

	private int [] data;
	private int numOfBars;
	private ArrayList<Bar> bars;
	
	public Histogram(int[] data, int numOfBars){
		//deep copy of data needed in case interval size changes
		this.data = Arrays.copyOf(data, data.length);
		this.numOfBars = numOfBars;
		this.bars = createBars();
	}
	
	public void setData(int[] newData){
		for(int num : data){
			if(num < 0) {
				throw new IllegalArgumentException("Data can only contain non-negative integers.");
			}
		}
		this.data = newData;
		this.bars = createBars();
	}
	
	public void setNumberBars(int numOfBars){
		if (numOfBars < 0){
			throw new IllegalArgumentException("The number of bars must be greater than 0.");
		}
		this.numOfBars = numOfBars;
		this.bars = createBars();
	}
	
	public int getNumberBars(){
		return numOfBars;
	}
	
	public Iterator<Bar> iterator(){
		return Collections.unmodifiableList(bars).iterator();
	}
	
	public int getMaxBarCount(){
		int max = 0;
		for (Bar bar : bars){
			if (bar.getCount() > max) {
				max = bar.getCount();
			}
		}
		return max;
	}
	
	private ArrayList<Bar> createBars(){
		//arrange data lowest to highest
		Arrays.sort(data);
		int intervalSize = 0;
		if(numOfBars > 0) {
			intervalSize = (getMaxNumber(data) / numOfBars);
		}
		int lowerLimit = 0;
		int upperLimit = lowerLimit + intervalSize;
		int count;
		
		ArrayList<Bar> bars = new ArrayList<Bar>(numOfBars);
		for(int i = 0; i < numOfBars; i++) {
			count = 0;
			upperLimit = lowerLimit + intervalSize;
			for(int num : data){
				if (num >= lowerLimit && num <= upperLimit){
					count++;
				}
			}
			bars.add(new Bar(lowerLimit, upperLimit, count));
			lowerLimit += intervalSize + 1; //add 1 so no overlap between intervals
		}
		return bars;
		
	}	
	
	private int getMaxNumber(int[] data){
		int max = 0;
		for(int num : data){
			if (num > max)
				max = num;
		}
		return max;
	}

	/**
	 * 
	 * @author Jaspal
	 *
	 */
	public class Bar {
		
		private int minimum;
		private int maximum;
		private int height;

		public Bar(int minimum, int maximum, int height){
			this.minimum = minimum;
			this.maximum = maximum;
			this.height = height;
		}
		
		public int getRangeMin(){
			return minimum;
		}
		
		public int getRangeMax(){
			return maximum;
		}
		
		public int getCount(){
			return height;
		}
		
		@Override
		public String toString(){
			return String.format("Bar [%d, %d] = %d", minimum, maximum, height);
		}

	}
	
}
