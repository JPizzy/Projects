package ca.cmpt213.as3.ui;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import ca.cmpt213.as3.gamelogic.GameController;

/**
 * Contains all the JPanels that make up the GUI for the maze game.
 * <p>Contains a menu at the top for the game's buttons.
 * <br>Contains the game board in the center to display the maze.
 * <br>Contains a status bar at the bottom to notify the user of game status.
 * 
 * @author Jaspal
 */
public class GraphicalUI {
	
	public GraphicalUI(GameController game) {
		final JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(new TopMenuPane(game), BorderLayout.NORTH);
		frame.add(new GameBoardPane(game), BorderLayout.CENTER);
		frame.add(new StatusBarPane(game), BorderLayout.SOUTH);
		frame.pack();
		frame.setVisible(true);
	}

}
