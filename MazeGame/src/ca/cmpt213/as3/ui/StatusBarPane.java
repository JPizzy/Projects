package ca.cmpt213.as3.ui;

import java.awt.Component;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import ca.cmpt213.as3.gamelogic.GameController;

/**
 * This pane should be displayed at the bottom. It will display the game status.
 * Keeps track of the amount of objects the player has collected.
 * Displays win and loss when they occur.
 * 
 * @author Jaspal
 */
public class StatusBarPane extends JPanel {
	private static final long serialVersionUID = 1L;

	private GameController game;

	public StatusBarPane(GameController game) {
		this.game = game;
		setLayout(new FlowLayout());
		add(makeLabel());
	}
	
	private Component makeLabel() {
		final JLabel statusLabel = new JLabel("Collect " + game.getCheeseToCollect() + " to win!");
		game.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				updateStatusLabel(statusLabel);
			}
		});
		updateStatusLabel(statusLabel);
		return statusLabel;
	}
	
	private void updateStatusLabel(JLabel label) {
		String text = null;
		if(game.hasWon()) {
			text = "Congratulations! You have won the game!";
		}
		else if(game.hasLost()) {
			text = "Oh no! You lost!";
		}
		else {
			text = "Cookies collected: " + game.getCheeseCount() + " of " + game.getCheeseToCollect();			
		}
		
		label.setText(text);
	}

}
