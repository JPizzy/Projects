package com.projects.towersofhanoi.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JPanel;

import com.projects.towersofhanoi.model.GameBoard;

public class ControlPanel extends JPanel{
	private static final long serialVersionUID = 1L;
	GameBoard game;
	
	public ControlPanel(GameBoard game) {
		this.game = game;
		setLayout(new GridLayout(0, game.getNumOfPegs()));
		for(int i = 0; i < game.getNumOfPegs(); i++){			
			add(makeButtonPanel(i + 1));
		}
	}
	
	public Component makeButtonPanel(final int pegNumber) {
		JButton addButton = new JButton("Place Disk");
		JButton removeButton = new JButton("Remove Disk");
		addButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				game.placeDiskOnPeg(pegNumber);
			}
		});
		removeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				game.removeDiskFromPeg(pegNumber);
			}
		});
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BorderLayout());
		buttonPanel.add(addButton, BorderLayout.NORTH);
		buttonPanel.add(Box.createVerticalGlue());
		buttonPanel.add(removeButton, BorderLayout.SOUTH);
		return buttonPanel;
	}
}
