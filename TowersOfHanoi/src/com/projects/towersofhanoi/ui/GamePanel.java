package com.projects.towersofhanoi.ui;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.projects.towersofhanoi.model.GameBoard;

public class GamePanel extends JPanel{
	private static final long serialVersionUID = 1L;
	private GameBoard game;
	private TowersIcon icon;
	private JLabel towersLabel;
	
	public GamePanel(GameBoard game) {
		this.game = game;
		icon = new TowersIcon(400, 200, game);
		towersLabel = new JLabel(icon);
		add(towersLabel);
		setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		registerForUpdates();
	}
	
	private void registerForUpdates() {
		game.addListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				towersLabel.repaint();
			}
		});
	}
}
