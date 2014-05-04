package com.projects.towersofhanoi.ui;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.projects.towersofhanoi.model.GameBoard;

public class ProgramRunner {

	public ProgramRunner() {
		GameBoard game = new GameBoard(4, 3);
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel content = new JPanel();
		content.setLayout(new BoxLayout(content, BoxLayout.PAGE_AXIS));
		content.add(new GamePanel(game));
		content.add(new ControlPanel(game));
		content.add(new StatusPanel());
		frame.add(content);
		
		frame.pack();
		frame.setVisible(true);
	}
	
	public static void main(String[] args) {
		new ProgramRunner();
	}

}
