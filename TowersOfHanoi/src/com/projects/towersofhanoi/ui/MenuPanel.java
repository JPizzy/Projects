package com.projects.towersofhanoi.ui;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import com.projects.towersofhanoi.model.GameBoard;

public class MenuPanel extends JPanel{
	private static final long serialVersionUID = 1L;
	GameBoard model;
	
	public MenuPanel(GameBoard model) {
		this.model = model;
		setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		add(makeMenuPanel());
	}
	
	private Component makeMenuPanel() {
		JPanel menuPane = new JPanel();
		menuPane.add(makeResetButton());
		menuPane.add(Box.createHorizontalBox());
		menuPane.add(makeHelpButton());
		return menuPane;
	}
	
	private Component makeResetButton() {
		JButton resetButton = new JButton("Reset");
		resetButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				model.resetBoard();
			}
		});
		return resetButton;
	}
	
	private Component makeHelpButton() {
		JButton helpButton = new JButton("Help...");
		return helpButton;
	}
}
