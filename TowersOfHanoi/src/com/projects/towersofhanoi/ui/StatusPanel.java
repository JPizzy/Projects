package com.projects.towersofhanoi.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

public class StatusPanel extends JPanel{
	private static final long serialVersionUID = 1L;
	
	JLabel statusLabel;

	public StatusPanel() {
		setLayout(new FlowLayout(FlowLayout.LEFT));
		add(makeStatusLabel());
		setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
	}
	
	private Component makeStatusLabel() {
		statusLabel = new JLabel();
		String text = "Place all disks on middle peg.";
		statusLabel.setText(text);
		return statusLabel;
	}
	
	private void updateStatus() {
		
	}
}
