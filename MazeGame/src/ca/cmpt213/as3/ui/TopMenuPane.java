package ca.cmpt213.as3.ui;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import ca.cmpt213.as3.gamelogic.GameController;

/**
 * This pane contains all the buttons for the game. It also contains the Controller
 * that handles keystrokes. The buttons show appropriate dialogs for each situation.
 * In case of game over the keyboard is disabled. Starting a new game enables the
 * keyboard again.
 * 
 * @author Jaspal
 */
public class TopMenuPane extends JPanel{
	private static final long serialVersionUID = 1L;
	private static final String ICON_NEW_GAME = "images/important.png";
	private static final String ICON_HELP = "images/easymoblog.png";
	private static final String ICON_ABOUT = "images/cookie.png";
	
	private GameController game;

	public TopMenuPane(GameController game) {
		this.game = game;
		JPanel keyboardController = new PlayerController(game);
		
		setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		add(makeNewGameButton(keyboardController));
		add(makeHelpButton());
		add(keyboardController);
		add(Box.createHorizontalGlue());
		add(makeAboutButton());
		registerForKeyboardUpdates(keyboardController);
	}
	
	// Observer that disables keyboard input in the case of the game ending
	private void registerForKeyboardUpdates(final JPanel keyboardController) {
		game.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent arg0) {
				if(game.hasWon() || game.hasLost()) {
					keyboardController.setEnabled(false);
				}
			}
		});
	}
	
	private Component makeNewGameButton(final Component keyboardController) {
		JButton btn = new JButton("New Game");
		btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				showConformationDialog(keyboardController);
			}
		});
		btn.setFocusable(false);
		return btn;
	}
	
	private Component makeHelpButton() {
		JButton btn = new JButton("Help...");
		btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				showHelpDialog();
			}
		});
		btn.setFocusable(false);
		return btn;
	}
	
	private Component makeAboutButton() {
		JButton btn = new JButton("About...");
		btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				showAboutDialog();
			}
		});
		btn.setFocusable(false);
		return btn;
	}
	
	private void showConformationDialog(Component keyboardController) {
		String message;
		message = "Do you really want to start a new game?";
		
		int userChoice = JOptionPane.showConfirmDialog(
				null,
				message,
				"New Game?",
				JOptionPane.YES_NO_OPTION,
				JOptionPane.WARNING_MESSAGE,
				new ImageIcon(ICON_NEW_GAME));
		
		if(userChoice == JOptionPane.YES_OPTION) {
			game.resetGame();
			keyboardController.setEnabled(true);
		}
	}
	
	private void showHelpDialog() {
		String message;
		message =  "You are in a world that makes no sense. Follow the directions to escape!\n\n" +
				"DIRECTIONS:\n" +
				"\tFind " + game.getCheeseToCollect() + " cookies before a ladybug turns you into dust!\n\n" +
				
				"HOW TO MOVE:\n" +
				"\tUse the arrow keys to move.\n";
		JOptionPane.showMessageDialog(
				null, 
				message,
				"Game Help",
				JOptionPane.INFORMATION_MESSAGE,
				new ImageIcon(ICON_HELP));
	}

	private void showAboutDialog() {
		String message;
		message = "Game written by Jaspal Sandhu, 2014.\n" +
				"To satisfy the requirements of Assignment 3 for CMPT 213.\n\n" +
				
				"Images by Everaldo Coelho http://www.everaldo.com\n" +
				"(In the Crystal Project Application set).\n";
		JOptionPane.showMessageDialog(
				null, 
				message,
				"About Game",
				JOptionPane.INFORMATION_MESSAGE,
				new ImageIcon(ICON_ABOUT));
	}
}
