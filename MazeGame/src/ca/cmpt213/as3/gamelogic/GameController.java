package ca.cmpt213.as3.gamelogic;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Manages all game elements. Contains main game logic.
 * Sets up maze and all maze elements. Updates positions of all elements.
 * Calls UI to draw all elements.
 */
public class GameController {
	
	public final File SOUND_GAME_WON = new File("sounds/IFEELGOOD.wav");
	public final File SOUND_GAME_LOST = new File("sounds/POP.WAV");
	public final File SOUND_WALL_BLOCK = new File("sounds/DING.WAV");
	
	private static final int CHEESE_TO_COLLECT = 5;
	private static final int MAZE_ROWS = 15;
	private static final int MAZE_COLUMNS = 20;
	
	private static final Coordinate TOP_LEFT = new Coordinate(1, 1);
	private static final Coordinate TOP_RIGHT = new Coordinate(MAZE_COLUMNS - 2, 1);
	private static final Coordinate BOTTOM_LEFT = new Coordinate(1, MAZE_ROWS - 2);
	private static final Coordinate BOTTOM_RIGHT = new Coordinate(MAZE_COLUMNS - 2, MAZE_ROWS - 2);
	
	private List<ChangeListener> listeners = new ArrayList<ChangeListener>();
	private Maze mazeMap;
	private ArrayList<Cat> cats;
	private Mouse mouse;
	private Cheese cheese;
	private int cheeseCount = 0;
	
	public GameController () {
		mazeMap = new Maze(MAZE_ROWS, MAZE_COLUMNS);
		mazeMap.removeWalls(30);
		placeElements();
		revealFOG();
	}
	
	private void revealFOG(){
		mazeMap.getNode(mouse.getLocation().getNorth()).setFOG(false);
		mazeMap.getNode(mouse.getLocation().getNorthEast()).setFOG(false);
		mazeMap.getNode(mouse.getLocation().getEast()).setFOG(false);
		mazeMap.getNode(mouse.getLocation().getSouthEast()).setFOG(false);
		mazeMap.getNode(mouse.getLocation().getSouth()).setFOG(false);
		mazeMap.getNode(mouse.getLocation().getSouthWest()).setFOG(false);
		mazeMap.getNode(mouse.getLocation().getWest()).setFOG(false);
		mazeMap.getNode(mouse.getLocation().getNorthWest()).setFOG(false);
	}
	
	private void placeElements(){
		mouse = new Mouse(TOP_LEFT);
		cheese = new Cheese(BOTTOM_RIGHT);
		randomGenerateCheese();
		
		cats = new ArrayList<Cat>();
		cats.add(new Cat(TOP_RIGHT));
		cats.add(new Cat(TOP_RIGHT));
		cats.add(new Cat(BOTTOM_LEFT));
		cats.add(new Cat(BOTTOM_LEFT));
		cats.add(new Cat(BOTTOM_RIGHT));
		cats.add(new Cat(BOTTOM_RIGHT));
	}
	
	private boolean gotCheese(){
		boolean ateCheese = false;
		if(cheese.getLocation().getX() == mouse.getLocation().getX() &&
				cheese.getLocation().getY() == mouse.getLocation().getY()){
			ateCheese = true;
		}
		return ateCheese;
	}
	
	public void resetGame() {
		mazeMap = new Maze(MAZE_ROWS, MAZE_COLUMNS);
		mazeMap.removeWalls(30);
		cheeseCount = 0;
		placeElements();
		revealFOG();
		notifyListeners();
	}
	
	//move the check to maze
	//getPaths()
	public boolean validMove(Coordinate newLocation){
		boolean valid = true;
		// remove the path if it is a wall or it is occupied or it is part of the outerwall
		if(mazeMap.getNode(newLocation).isWall() ||
				//mazeMap.getNode(newLocation).isOccupied() ||
				newLocation.getX() < 1 || newLocation.getY() < 1 ||
				newLocation.getX() >= mazeMap.getColumns() - 1 || 
				newLocation.getY() >= mazeMap.getRows() - 1) {
			valid = false;
		}
		return valid;
	}
	
	public void moveMouse (Coordinate newLocation) {
		mouse.move(newLocation);
		revealFOG();
		if(gotCheese()){
			cheeseCount++;
			randomGenerateCheese();
		}
		notifyListeners();
	}
	
	public void moveCats(){
		for(Cat cat: cats) {
			// get the all the neighbours around the cat
			ArrayList<Coordinate> neighbours = mazeMap.getNode(cat.getLocation()).getNeighbours();
			// remove the invalid path
			for(int i = 0;i < neighbours.size(); i++) {
				// remove the path if it is a wall
				if(mazeMap.getNode(neighbours.get(i)).isWall()) {
					neighbours.remove(i);
					i--;
				}
			}

			cat.move(neighbours);
		}
		notifyListeners();
	}
	
	// randomly move the cheese to a new(valid) coordinate
	// a valid coordinate should meet the the following criteria:
	// does not contain a wall, does not contain a mouse, does not contain a cheese.
	// the new coordinate will be different compare to the last coordinate
	public void randomGenerateCheese() {
		ArrayList<Coordinate> openSpaces = new ArrayList<Coordinate>();
		for(int row = 0; row < mazeMap.getRows(); row++){
			for(int column = 0; column < mazeMap.getColumns(); column++){
				Coordinate current = new Coordinate(column, row);
				MazeNode node = mazeMap.getNode(current);
				if(!node.isWall() && 
						!isMouseAtLocation(current) &&
						!isCheeseAtLocation(current)) {
					openSpaces.add(current);
				}
			}
		}
		java.util.Collections.shuffle(openSpaces);
		cheese.move(openSpaces.get(0));
	}
	
	public void removeAllFOG () {
		mazeMap.removeFOG();
	}
	
	public int getCheeseCount () {
		return cheeseCount;
	}
	
	public int getCheeseToCollect () {
		return CHEESE_TO_COLLECT;
	}
	
	public MazeNode getNode (Coordinate location) {
		return mazeMap.getNode(location);
	}
	
	public Coordinate getPlayerLocation () {
		return mouse.getLocation();
	}
	
	public boolean isMouseAtLocation(Coordinate location) {
		return mouse.getLocation().equals(location);
	}
	
	public boolean isCatAtLocation(Coordinate location) {
		for (Cat cat : cats) {
			if (cat.getLocation().equals(location)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isCheeseAtLocation(Coordinate location) {
		return cheese.getLocation().equals(location);
	}
	
	public boolean hasWon () {
		boolean allCheeseCollected = cheeseCount >= CHEESE_TO_COLLECT;
		return allCheeseCollected && !hasLost();
	}
	
	public boolean hasLost () {
		return isCatAtLocation(mouse.getLocation());
	}
	
	public int getMazeRows() {
		return MAZE_ROWS;
	}
	
	public int getMazeColumns() {
		return MAZE_COLUMNS;
	}
	
	// Observer Functions
	public void addChangeListener(ChangeListener listener) {
		listeners.add(listener);
	}
	private void notifyListeners() {
		ChangeEvent event = new ChangeEvent(this);
		for(ChangeListener listener : listeners) {
			listener.stateChanged(event);
		}
	}
	
	public void playSound(File sound) {
		try {
			AudioInputStream audioInputStream =
					AudioSystem.getAudioInputStream(sound);
			Clip clip = AudioSystem.getClip();
			clip.open(audioInputStream);
			clip.start();
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
	}

}
