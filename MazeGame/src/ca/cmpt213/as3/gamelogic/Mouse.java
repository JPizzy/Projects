package ca.cmpt213.as3.gamelogic;

/**
 * Represents a mouse that the user will play as. Keeps track of it's location
 * and it's previous location.
 */
public class Mouse {

	private Coordinate location;
	private Coordinate lastLocation;
	
	public Mouse(Coordinate startingLocation){
		location = startingLocation;
		lastLocation = startingLocation;
	}
	
	public void move(Coordinate newLocation){
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
