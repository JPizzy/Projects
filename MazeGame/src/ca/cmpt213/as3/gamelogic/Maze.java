package ca.cmpt213.as3.gamelogic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a maze. Each cell of the maze is represented by a MazeNode.
 * Randomly generates a path using depth-first search with backtracking.
 */
public class Maze {

	private int columns;
	private int rows;
	public List<List<MazeNode>> mapOfMaze;
	
	
	public Maze(int rows, int columns){
		this.rows = rows;
		this.columns = columns;
		generateMaze();
	}
	
	/**
	 * Generate a grid that is set to all walls
	 * @return a grid of all walls
	 */
	private List<List<MazeNode>> generateBlankMaze(){
		List<List<MazeNode>> maze = new ArrayList<List<MazeNode>>(columns);
		for(int i = 0; i < rows; i++){
			maze.add(new ArrayList<MazeNode>());
		}

		//add a node to each element
		for(int row = 0; row < rows; row++){
			for(int column = 0; column < columns; column++){
				maze.get(row).add(column, new MazeNode(column, row));
				if(row == 0 || row == rows - 1 || column == 0 || column == columns - 1){
					maze.get(row).get(column).setFOG(false);
				}
			}
		}
		
		return maze;
	}
	
	/**
	 * Randomly generate a maze
	 * @return a finished maze
	 */
	private void generateMaze(){
		mapOfMaze = generateBlankMaze();
		MazeNode start = getNode(1 ,1);
		createPath(start);
		removeCorners();
	}
	
	/**
	 * Creates a path through a grid (represented by a list of lists).
	 * Implemented using depth-first search and backtracking.
	 * @param currentNode - start node for the path
	 * @param maze - blank grid to create the path in
	 */
	private void createPath(MazeNode currentNode){
		visit(currentNode);
		ArrayList<Coordinate> neighbours = currentNode.getNeighbours();
		removeEdgeNodes(neighbours);
		removeVisitedNodes(neighbours);
		
		if(neighbours.size() != 0){
			// shuffle list so next node to select is random
			Collections.shuffle(neighbours);
			
			// choose a neighbouring node from current spot
			// get the neighbours of new chosen node
			// if it has 2 or more neighbours go in that direction
			for(Coordinate neighbour : neighbours){
				MazeNode randomNode = getNode(neighbour.getX(), neighbour.getY());
				ArrayList<Coordinate> nextNeighbours = randomNode.getNeighbours();
				removeEdgeNodes(nextNeighbours);
				removeVisitedNodes(nextNeighbours);
				
				if(nextNeighbours.size() > 1 && numOfVisitedNeighbours(randomNode) < 2){
					createPath(randomNode);
					neighbours.remove(randomNode);
				}
			}
		}
	}
	
	/**
	 * A list of lists is organized by (y, x) so this method lets you access
	 * the elements by (x, y).
	 * @param x
	 * @param y
	 * @return MazeNode at (x, y)
	 */
	private MazeNode getNode(int x, int y){
		return mapOfMaze.get(y).get(x);
	}
	
	/**
	 * Marks the MazeNode as visited and removes the wall.
	 * @param node - MazeNode to visit and turn into a path.
	 */
	private void visit(MazeNode node){
		if(!node.isVisited()){
			node.setWall(false);
			node.visit(true);
		}
	}
	
	/**
	 * Removes the walls in the four corners of the maze
	 */
	private void removeCorners(){
		visit(getNode(1, 1));
		visit(getNode(1, rows - 2));
		visit(getNode(columns - 2, 1));
		visit(getNode(columns - 2, rows - 2));
	}
	
	/**
	 * Remove all MazeNodes that fall out of range or might be a part of the outer wall.
	 * @param neighbours - A list of MazeNodes to check
	 */
	private void removeEdgeNodes(ArrayList<Coordinate> neighbours){
		for(int i = 0; i < neighbours.size(); i++){
			if(neighbours.get(i).getX() < 1
			|| neighbours.get(i).getY() < 1
			|| neighbours.get(i).getX() >= this.columns - 1
			|| neighbours.get(i).getY() >= this.rows - 1){
				neighbours.remove(i);
				i--;
			}
		}
	}
	
	/**
	 * Remove all nodes that have been visited.
	 * @param neighbours - A list of MazeNodes to check
	 * @param maze - Maze to check on
	 */
	private void removeVisitedNodes(ArrayList<Coordinate> neighbours){
		for(int i = 0; i < neighbours.size(); i++){
			MazeNode currentNode = getNode(neighbours.get(i).getX(), neighbours.get(i).getY());
			if(currentNode.isVisited()){
				neighbours.remove(i);
				i--;
			}
		}
	}
	
	/**
	 * Get the number of node's neighbours that are visited
	 * @param node - the MazeNode to get neighbours from
	 * @param maze - the maze to check
	 * @return the number of neighbours that have been visited
	 */
	private int numOfVisitedNeighbours(MazeNode node){
		int visited = 0;
		ArrayList<Coordinate> neighbours = node.getNeighbours();
		removeEdgeNodes(neighbours);
		for(Coordinate c : neighbours){
			if(getNode(c.getX(), c.getY()).isVisited()){
				visited++;
			}
		}
		return visited;
	}
	
	public MazeNode getNode(Coordinate point){
		return mapOfMaze.get(point.getY()).get(point.getX());
	}
	
	public void removeWalls(int wallsToRemove){
		ArrayList<MazeNode> walls = new ArrayList<MazeNode>();
		for(int row = 1; row < this.rows - 1; row++){
			for(int column = 1; column < this.columns - 1; column++){
				if(getNode(column, row).isWall()){
					walls.add(getNode(column, row));
				}
			}
		}
		Collections.shuffle(walls);
		for(int i = 0; i < wallsToRemove; i++){
			walls.get(i).setWall(false);
		}
	}
	
	public void removeFOG(){
		for(int row = 0; row < this.rows; row++){
			for(int column = 0; column < this.columns; column++){
				getNode(column, row).setFOG(false);
			}
		}
	}
	
	public int getColumns() {
		return columns;
	}

	public int getRows() {
		return rows;
	}
}
