package ca.cmpt213.as3.gamelogic;

import java.util.ArrayList;

/**
 * Represents a node in a maze. Keeps track of the several states of the node and
 * it's (x, y) location in the maze. Can retrieve neighbouring node's locations as well.
 */
public class MazeNode {

	private boolean hasFOG;
	private boolean isWall;
	private boolean visited;
	private Coordinate location;
	
	public MazeNode(int x, int y){
		hasFOG = true;
		isWall = true;
		visited = false;
		location = new Coordinate(x, y);
	}
	
	public boolean hasFOG() {
		return hasFOG;
	}
	
	public void setFOG(boolean hasFOG) {
		this.hasFOG = hasFOG;
	}
	
	public boolean isWall() {
		return isWall;
	}
	
	public void setWall(boolean isWall) {
		this.isWall = isWall;
	}
	
	public boolean isVisited() {
		return visited;
	}
	
	public void visit(boolean visted) {
		this.visited = visted;
	}
	
	public Coordinate getLocation() {
		return location;
	}
	
	/**
	 * Get all the neighbours of this MazeNode
	 * @return a ArrayList of all immediate neighbours
	 */
	public ArrayList<Coordinate> getNeighbours(){
		ArrayList<Coordinate> neighbours = new ArrayList<Coordinate>();
		neighbours.add(this.location.getEast());
		neighbours.add(this.location.getNorth());
		neighbours.add(this.location.getSouth());
		neighbours.add(this.location.getWest());
		return neighbours;
	}


}
