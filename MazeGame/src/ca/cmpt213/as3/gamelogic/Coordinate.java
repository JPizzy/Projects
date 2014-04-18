package ca.cmpt213.as3.gamelogic;

/**
 * Represents an (x, y) coordinate. 
 * Can return the coordinates of all cardinal directions.
 */
public class Coordinate {

	private int x;
	private int y;
	
	public Coordinate(int x, int y){
		this.x = x;
		this.y = y;
	}

	public void setX(int x) {
		this.x = x;
	}
	public void setY(int y) {
		this.y = y;
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public boolean equals(Coordinate otherLocation) {
		boolean sameX = this.x == otherLocation.x;
		boolean sameY = this.y == otherLocation.y;
		return sameX && sameY;
	}
	
	//directions based on the origin being at the top left of the grid
	public Coordinate getNorth(){
		return new Coordinate(x, y - 1);
	}
	
	public Coordinate getNorthEast(){
		return new Coordinate(x + 1, y - 1);
	}

	public Coordinate getEast(){
		return new Coordinate(x + 1, y);
	}
	
	public Coordinate getSouthEast(){
		return new Coordinate(x - 1, y + 1);
	}
	

	public Coordinate getSouth(){
		return new Coordinate(x, y + 1);
	}
	
	public Coordinate getSouthWest(){
		return new Coordinate(x + 1, y + 1);
	}
	
	public Coordinate getWest(){
		return new Coordinate(x - 1, y);
	}
	
	public Coordinate getNorthWest(){
		return new Coordinate(x - 1, y - 1);
	}	
}
