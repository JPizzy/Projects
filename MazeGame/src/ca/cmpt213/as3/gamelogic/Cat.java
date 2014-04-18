package ca.cmpt213.as3.gamelogic;

import java.util.ArrayList;

/**
 * Represents a blind cat that randomly moves throughout the maze.
 * Keeps track of it's location and it's previous location.
 */
public class Cat {

	private Coordinate location;
	private Coordinate lastLocation;
	
	public Cat(Coordinate startingLocation){
		location = startingLocation;
		lastLocation = startingLocation;
	}

	public void move(ArrayList<Coordinate> validPaths) {
		boolean catMoved = false;
		int index = 0;
		Coordinate nextCoordinate;
		// shuffling the path
		java.util.Collections.shuffle(validPaths);
		
		nextCoordinate = validPaths.get(index);

		// if only one path is available then move to that coordinate
		if(validPaths.size() == 1) {
			lastLocation = location;
			location = nextCoordinate;
		} 
		else if(validPaths.size() > 1) {
			while(!catMoved && index < validPaths.size()) {
				// the cat should avoid moving to where it just came from
				if(nextCoordinate.equals(lastLocation)) {
					index++;
					nextCoordinate = validPaths.get(index);
				} 
				else {
					lastLocation = location;
					location = nextCoordinate;
					catMoved = true;
				}
			}
		}
	}
	
	public Coordinate getLocation() {
		return location;
	}
	
	public Coordinate getLastLocation() {
		return lastLocation;
	}
	
	public ArrayList<Coordinate> getNeighbours() {
		ArrayList<Coordinate> neighbours = new ArrayList<Coordinate>();
		neighbours.add(this.location.getEast());
		neighbours.add(this.location.getNorth());
		neighbours.add(this.location.getSouth());
		neighbours.add(this.location.getWest());
		return neighbours;
	}	
}
