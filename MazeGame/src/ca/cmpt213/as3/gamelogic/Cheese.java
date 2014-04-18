package ca.cmpt213.as3.gamelogic;

/**
 * Represents a piece of cheese that the user will try to collect. Keeps track of 
 * it's location and it's previous location.
 */
public class Cheese {

	private Coordinate location;
	private Coordinate lastLocation;

	public Cheese(Coordinate startingLocation){
		location = startingLocation;
		lastLocation = startingLocation;
	}
	
	public void move(Coordinate newLocation) {
		lastLocation = location;
		location = newLocation;
	}

	public Coordinate getLocation(){
		return location;
	}

	public Coordinate getLastLocation() {
		return lastLocation;
	}
}
