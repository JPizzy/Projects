package ca.cmpt213.as3.ui;

import ca.cmpt213.as3.gamelogic.GameController;

/**
 * Used to run the game. Instantiates the game and the UI.
 * 
 * @author Jaspal
 */
public class GameRunner {

	public static void main(String[] args) {
		GameController model = new GameController();
		
		// Text UI
//		TextUI textUI = new TextUI(model);
//		textUI.playGame();
		
		// Graphical UI
		new GraphicalUI(model);
	}

}
