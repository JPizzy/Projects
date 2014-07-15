package com.projects.lol.statcalculator.ui;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import com.projects.lol.statcalculator.LoLCalculator;
import com.projects.lol.statcalculator.RuneCalculator;

public class ProgramRunner {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		LoLCalculator calculator = new LoLCalculator();
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.add(new ChampionListPane(calculator), BorderLayout.WEST);
		frame.add(new RuneCalculatorPane(calculator), BorderLayout.CENTER);
		
		frame.pack();
		frame.setVisible(true);

	}

}
