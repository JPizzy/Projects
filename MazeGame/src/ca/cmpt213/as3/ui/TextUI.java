package ca.cmpt213.as3.ui;

import java.util.Scanner;

import ca.cmpt213.as3.gamelogic.*;

public class TextUI {
	GameController game;
		
	private static final String VALID_KEYS = "WASDM";
	private static final char WALL = '#';
	private static final char FOG = '.';
	private static final char PATH = ' ';
	private static final char DEAD = 'X';
	private static final char MOUSE = '@';
	private static final char CAT = '!';
	private static final char CHEESE = '$';

	public TextUI(GameController game ){
		this.game = game;
	}
	
	public void playGame() {
		boolean isAlive = true;
		printWelcome();
		printHelp();
		while (!game.hasWon() && isAlive) {
			printScreen();
			System.out.println("Cheese collected: " + game.getCheeseCount() + " of 5");
			Coordinate move;
			do {
				move = resolveInput(getInput());
			} while (!game.validMove(move));
			game.moveMouse(move);
			if (game.hasLost()) {
				isAlive = false;
				break;
			}
			game.moveCats();
			if (game.hasLost()) {
				isAlive = false;
			}
		}
		
		printScreen();
		if (game.hasWon()) {
			printWin();
		}
		else {
			printDeath();
		}
	}
	
	private void printScreen(){
		Coordinate current;
		for(int row = 0; row < game.getMazeRows(); row++){
			for(int column = 0; column < game.getMazeColumns(); column++){
				current = new Coordinate(column, row);
				char symbol = getElement(current);
				System.out.printf("%c", symbol);
			}
			System.out.println();
		}
	}
	
	private void printDeath(){
		System.out.println("The cat caught you and ate you!");
		printScreen();
	}
	
	private void printWin(){
		System.out.println("Congratulations you have ate all the cheese and are now stuffed!");
		printScreen();
	}
	
	private char getElement(Coordinate current) {
		MazeNode currentNode = game.getNode(current);
		
		if (game.isMouseAtLocation(current) && game.isCatAtLocation(current)) {
			return DEAD;
		}
		else if (game.isMouseAtLocation(current)) {
			return MOUSE;
		}
		else if (game.isCatAtLocation(current)) {
			return CAT;
		}
		else if (game.isCheeseAtLocation(current)) {
			return CHEESE;
		}
		else if (currentNode.hasFOG()) {
			return FOG;
		}
		else if (currentNode.isWall()) {
			return WALL;
		}
		else {
			return PATH;
		}
	}
	
	private String getInput(){
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		String userInput;
		do{
			System.out.print("Enter your move [WASD?]: ");
			userInput = scanner.nextLine();
			userInput = userInput.trim().substring(0, 1);
			if(VALID_KEYS.contains(userInput.toUpperCase())){
				continue;
			}
			else if(userInput.equals("?")){
				printHelp();
			}
			else{
				System.out.println("Invalid move. Please enter just A (left), S (down), D (right), or W (up).");
			}
		}while(!VALID_KEYS.contains(userInput.toUpperCase()) || userInput.isEmpty());
		return userInput;
	}
	
	private Coordinate resolveInput(String input){
		Coordinate newLocation = null;
		if (input.toUpperCase().equals("W")){
			newLocation = game.getPlayerLocation().getNorth();
		}
		else if (input.toUpperCase().equals("A")){
			newLocation = game.getPlayerLocation().getWest();
		}
		else if (input.toUpperCase().equals("S")){
			newLocation = game.getPlayerLocation().getSouth();
		}
		else if (input.toUpperCase().equals("D")){
			newLocation = game.getPlayerLocation().getEast();
		}
		else if (input.toUpperCase().equals("M")){
			game.removeAllFOG();
			printScreen();
			newLocation = resolveInput(getInput());
		}
		return newLocation;
	}
	
	private void printWelcome(){
		System.out.println("Welcome to the maze game!\n");
		printHelp();
	}
	
	private void printHelp(){
		System.out.println("DIRECTIONS:");
		System.out.println("\tFind 5 cheese before a cat eats you!");
		System.out.println("LEGEND:");
		System.out.println("\t#: Wall");
		System.out.println("\t@: You (a mouse)");
		System.out.println("\t!: Cat");
		System.out.println("\t$: Cheese");
		System.out.println("\t.: Unexplored");
		System.out.println("MOVES:");
		System.out.println("\tUse W (up), A (left), S (down) and D (right) to move.");
		System.out.println("\t(You must press enter after each move).\n");
	}

}
