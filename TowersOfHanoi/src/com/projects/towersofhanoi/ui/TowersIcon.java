package com.projects.towersofhanoi.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Iterator;
import java.util.Random;

import javax.swing.Icon;

import com.projects.towersofhanoi.model.Disk;
import com.projects.towersofhanoi.model.GameBoard;
import com.projects.towersofhanoi.model.Peg;

public class TowersIcon implements Icon{
	private final int WIDTH;
	private final int HEIGHT;
	private final int MARGIN = 10;
	private final int PADDING = 10;
	private final int PEG_WIDTH = 6;
	private final String PEG_COLOR = "#7A2900";
	
	GameBoard game;
	
	public TowersIcon(int width, int height, GameBoard model) {
		game = model;
		WIDTH = width;
		HEIGHT = height;
	}

	@Override
	public int getIconHeight() {
		return HEIGHT;
	}

	@Override
	public int getIconWidth() {
		return WIDTH;
	}

	@Override
	public void paintIcon(Component c, Graphics g, int x, int y) {
		Random rand = new Random();
		Graphics g2d = (Graphics2D) g;
		
		//Fill background
		g2d.setColor(Color.WHITE);
		g2d.fillRect(0, 0, WIDTH, HEIGHT);
		
		//Draw Base
		g2d.setColor(Color.BLACK);
		g2d.drawLine(MARGIN, HEIGHT - MARGIN, WIDTH - MARGIN, HEIGHT - MARGIN);
		
		
		//Draw Pegs
		int distanceBetweenPegs = (WIDTH - (MARGIN * 2)) / game.getNumOfPegs();
		Iterator<Peg> itr = game.iterator();
		int initialX = (distanceBetweenPegs / 2) - (PEG_WIDTH / 2);
		
		int diskHeight = (int) (((HEIGHT - (MARGIN * 2))*0.8) / game.getNumOfDisks());
		
		while(itr.hasNext()) {
			g2d.setColor(Color.decode(PEG_COLOR));
			Peg currentPeg = itr.next();
			g2d.fillRect(MARGIN + initialX, MARGIN, PEG_WIDTH, HEIGHT - MARGIN - MARGIN);
			
			// Draw Disks
			Iterator<Disk> itrD = currentPeg.iterator();
			int intialY = HEIGHT - MARGIN - diskHeight;
			while(itrD.hasNext()) {
				float red = (float) (rand.nextFloat() / 2 + 0.5);
				float green = (float) (rand.nextFloat() / 2 + 0.5);
				float blue = (float) (rand.nextFloat() / 2 + 0.5);
				Color randomColor = new Color(red, green, blue);
				g2d.setColor(randomColor);
				
				Disk currentDisk = itrD.next();
				int diskWidth = (distanceBetweenPegs- PADDING) / (game.getNumOfDisks() - currentDisk.getSize() + 1);
				g2d.fillRoundRect(MARGIN + initialX + (PEG_WIDTH / 2) - (diskWidth / 2), 
						intialY, 
						diskWidth, 
						diskHeight, 
						3, 
						3);
				intialY -= diskHeight;
			}
			initialX += distanceBetweenPegs;
		}
	}
}
