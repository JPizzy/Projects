package ca.cmpt213.as3.ui;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import ca.cmpt213.as3.gamelogic.Coordinate;
import ca.cmpt213.as3.gamelogic.GameController;

/**
 * Controller that handles keystrokes of the arrow keys.
 * Each arrow key moves the player in that respective direction.
 * 
 * @author Jaspal
 */
public class PlayerController extends JPanel{
	private static final long serialVersionUID = 1L;
	// Names of arrow key actions.
	private static final String[] KEYS = {"UP", "DOWN", "LEFT", "RIGHT"};
	
	private GameController game;
	
	public PlayerController(GameController game) {
		this.game = game;
		registerKeyPresses();
	}
	public void registerKeyPresses() {
		for (int i = 0; i < KEYS.length; i++) {
			String key = KEYS[i];
			this.getInputMap().put(KeyStroke.getKeyStroke(key), key);
			this.getActionMap().put(key, getKeyListener(key));
		}
	}
	
	public AbstractAction getKeyListener(final String move) {
		return new AbstractAction() {
			private static final long serialVersionUID = 1L;
			public void actionPerformed(ActionEvent evt) {
				Coordinate newLocation = null;
				if (move.toUpperCase().equals("UP")){
					newLocation = game.getPlayerLocation().getNorth();
				}
				else if (move.toUpperCase().equals("LEFT")){
					newLocation = game.getPlayerLocation().getWest();
				}
				else if (move.toUpperCase().equals("DOWN")){
					newLocation = game.getPlayerLocation().getSouth();
				}
				else if (move.toUpperCase().equals("RIGHT")){
					newLocation = game.getPlayerLocation().getEast();
				}
				if(game.validMove(newLocation)) {
					game.moveMouse(newLocation);
					game.moveCats();
				}
				else {
					game.playSound(game.SOUND_WALL_BLOCK);
				}
			}
		};
	}
}
