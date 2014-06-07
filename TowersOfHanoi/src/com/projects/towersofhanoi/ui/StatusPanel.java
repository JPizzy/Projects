package com.projects.towersofhanoi.ui;

import java.awt.Component;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.projects.towersofhanoi.model.GameBoard;

public class StatusPanel extends JPanel{
	private static final long serialVersionUID = 1L;
	
	JLabel statusLabel;
	GameBoard model;

	public StatusPanel(GameBoard model) {
		this.model = model;
		setLayout(new FlowLayout(FlowLayout.LEFT));
		add(makeStatusLabel());
		setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
		registerForUpdates();
	}
	
	private void registerForUpdates() {
		model.addStatusListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent arg0) {
				updateStatus();
			}
		});
	}
	
	private Component makeStatusLabel() {
		statusLabel = new JLabel();
		String text = "Place all disks on middle peg.";
		statusLabel.setText(text);
		return statusLabel;
	}
	
	private void updateStatus() {
		int heldDiskSize = model.getHeldDiskSize();
		if (model.wonGame()) {
			statusLabel.setText("You have solved the puzzle!");
		}
		else if (heldDiskSize <= 0) {
			statusLabel.setText("You are not holding a disk.");
		}
		else {
			statusLabel.setText("Disk Size: " + model.getHeldDiskSize());
		}
	}
}
