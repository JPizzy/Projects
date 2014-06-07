package com.projects.towersofhanoi.ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.RoundRectangle2D;
import java.util.Iterator;

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
	private final int STROKE_WIDTH = 2;
	private final float PERCENT_OF_PEG_COVERED = 0.8f;
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
		Graphics2D g2d = (Graphics2D) g;
		
		//Fill background
		g2d.setColor(Color.WHITE);
		g2d.fillRect(0, 0, WIDTH, HEIGHT);
		
		//Draw Base
		g2d.setColor(Color.BLACK);
		g2d.drawLine(MARGIN, HEIGHT - MARGIN, WIDTH - MARGIN, HEIGHT - MARGIN);
		
		
		//Draw Pegs
		int distanceBetweenPegs = (WIDTH - (MARGIN * 2)) / game.getNumOfPegs();
		Iterator<Peg> itr = game.iterator();
		int currentX = (distanceBetweenPegs / 2) - (PEG_WIDTH / 2);
		
		int diskHeight = (int) (((HEIGHT - (MARGIN * 2))*PERCENT_OF_PEG_COVERED) / game.getNumOfDisks());
		
		while(itr.hasNext()) {
			g2d.setColor(Color.decode(PEG_COLOR));
			Peg currentPeg = itr.next();
			g2d.fillRect(MARGIN + currentX, MARGIN, PEG_WIDTH, HEIGHT - MARGIN - MARGIN);
			
			// Draw Disks
			Iterator<Disk> itrD = currentPeg.iterator();
			int currentY = HEIGHT - MARGIN - diskHeight;
			while(itrD.hasNext()) {
				Disk currentDisk = itrD.next();
				int diskWidth = (distanceBetweenPegs - PADDING) / game.getNumOfDisks() * currentDisk.getSize();
				int startingXofDisk = MARGIN + currentX + (PEG_WIDTH / 2) - (diskWidth / 2);
				RoundRectangle2D disk = new RoundRectangle2D.Double(startingXofDisk,
						currentY, 
						diskWidth, 
						diskHeight, 
						5, 
						5);
				
				Color diskColor = currentDisk.getColor();
				GradientPaint paint = new GradientPaint(startingXofDisk, currentY, diskColor,
						startingXofDisk + diskWidth, currentY + diskHeight, Color.WHITE);
				
				g2d.setPaint(paint);
				g2d.fill(disk);
				g2d.setColor(Color.BLACK);
				g2d.setStroke(new BasicStroke(STROKE_WIDTH));
				g2d.draw(disk);
				
				//Draw disk size
				g2d.drawString(Integer.toString(currentDisk.getSize()),
						MARGIN + currentX,
						currentY + (diskHeight / 2));
				currentY -= diskHeight;
			}
			currentX += distanceBetweenPegs;
		}
	}
}
