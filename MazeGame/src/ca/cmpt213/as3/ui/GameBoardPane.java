package ca.cmpt213.as3.ui;

import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import ca.cmpt213.as3.gamelogic.Coordinate;
import ca.cmpt213.as3.gamelogic.GameController;
import ca.cmpt213.as3.gamelogic.MazeNode;

/**
 * GameBoardPane contains and displays the maze. Displays the maze as a GridLayout.
 * Each cell is a JLabel. Resizes all images to display nicely.
 * Observes the game model and redraws the UI if the game state is changed.
 * Plays sounds when game won or lost and also reveals the entire maze.
 * 
 * @author Jaspal
 */
public class GameBoardPane extends JPanel {
	private static final long serialVersionUID = 1L;
	private static final int IMAGE_HEIGHT = 45;
	private static final int IMAGE_WIDTH = 45;	
	private static final ImageIcon ICON_WALL = getScaleImageIcon(new ImageIcon("images/firewall.png"), IMAGE_WIDTH, IMAGE_HEIGHT);
	private static final ImageIcon ICON_FOG = getScaleImageIcon(new ImageIcon("images/katomic.png"), IMAGE_WIDTH, IMAGE_HEIGHT);
	private static final ImageIcon ICON_PATH = getScaleImageIcon(new ImageIcon("images/atlantikdesigner.png"), IMAGE_WIDTH, IMAGE_HEIGHT);
	private static final ImageIcon ICON_DEAD = getScaleImageIcon(new ImageIcon("images/kasteroids.png"), IMAGE_WIDTH, IMAGE_HEIGHT);
	private static final ImageIcon ICON_MOUSE = getScaleImageIcon(new ImageIcon("images/katuberling.png"), IMAGE_WIDTH, IMAGE_HEIGHT);
	private static final ImageIcon ICON_CAT = getScaleImageIcon(new ImageIcon("images/bug.png"), IMAGE_WIDTH, IMAGE_HEIGHT);
	private static final ImageIcon ICON_CHEESE = getScaleImageIcon(new ImageIcon("images/cookie.png"), IMAGE_WIDTH, IMAGE_HEIGHT);

	private GameController game;
	JLabel[][] gridLabels;
	
	public GameBoardPane(GameController game) {
		this.game = game;
		gridLabels = new JLabel[game.getMazeRows()][game.getMazeColumns()];
		setLayout(new GridLayout(game.getMazeRows(), game.getMazeColumns()));
		setIntialImages();
		registerForGameUpdates();
	}

	// Observer redraws UI if state has changed
	// Plays sound if game won or lost
	private void registerForGameUpdates() {
		game.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent arg0) {
				if(game.hasWon()) {
					game.playSound(game.SOUND_GAME_WON);
					game.removeAllFOG();
				}
				else if (game.hasLost()) {
					game.playSound(game.SOUND_GAME_LOST);
					game.removeAllFOG();
				}
				redrawUI();
			}
		});
	}
	
	private void redrawUI() {
		Coordinate current;
		for(int row = 1; row < game.getMazeRows() - 1; row++){
			for(int column = 1; column < game.getMazeColumns() - 1; column++){
				current = new Coordinate(column, row);
//				MazeNode node = game.getNode(current);
//				if(node.hasFOG()
//						&& !game.isCatAtLocation(current)
//						&& !game.isCheeseAtLocation(current)) {
//					continue;
//				}				
				ImageIcon image = getImageOfElement(current);
				gridLabels[row][column].setIcon(image);
				gridLabels[row][column].repaint();
			}
		}
	}
	
	private void setIntialImages() {
		Coordinate current;
		for(int row = 0; row < game.getMazeRows(); row++){
			for(int column = 0; column < game.getMazeColumns(); column++){
				current = new Coordinate(column, row);
				JLabel nodeLabel = new JLabel();
				ImageIcon image = getImageOfElement(current);
				nodeLabel.setIcon(image);
				gridLabels[row][column] = nodeLabel;
				add(gridLabels[row][column]);
			}
		}
	}
	
	private ImageIcon getImageOfElement(Coordinate current) {
		MazeNode currentNode = game.getNode(current);
		
		if (game.isMouseAtLocation(current) && game.isCatAtLocation(current)) {
			return ICON_DEAD;
		}
		else if (game.isMouseAtLocation(current)) {
			return ICON_MOUSE;
		}
		else if (game.isCatAtLocation(current)) {
			return ICON_CAT;
		}
		else if (game.isCheeseAtLocation(current)) {
			return ICON_CHEESE;
		}
		else if (currentNode.hasFOG()) {
			return ICON_FOG;
		}
		else if (currentNode.isWall()) {
			return ICON_WALL;
		}
		else {
			return ICON_PATH;
		}
	}
	
	static public ImageIcon getScaleImageIcon(ImageIcon icon, int width, int height) {
		return new ImageIcon(getScaledImage(icon.getImage(), width, height));
	}

	static private Image getScaledImage(Image srcImg, int width, int height){
		BufferedImage resizedImg = 
				new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = resizedImg.createGraphics();
		g2.setRenderingHint(
				RenderingHints.KEY_INTERPOLATION, 
				RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2.drawImage(srcImg, 0, 0, width, height, null);
		g2.dispose();
		return resizedImg;
	}

}
