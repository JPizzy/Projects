package com.projects.towersofhanoi.ui;

import java.awt.Component;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class StatusPanel extends JPanel{
	private static final long serialVersionUID = 1L;

	public StatusPanel() {
		setLayout(new FlowLayout());
		add(makeStatusLabel());
	}
	
	private Component makeStatusLabel() {
		JLabel statusLabel = new JLabel();
		String text = "Place all disks on middle peg.";
		statusLabel.setText(text);
		return statusLabel;
	}
}
