package com.projects.towersofhanoi.model;

import java.awt.Color;
import java.util.Random;

public class Disk {
	
	private int size;
	private Color diskColor;

	public Disk(int size) {
		this.size = size;
		
		Random rand = new Random();
		float red = (float) (rand.nextFloat() / 2 + 0.5);
		float green = (float) (rand.nextFloat() / 2 + 0.5);
		float blue = (float) (rand.nextFloat() / 2 + 0.5);
		Color randomColor = new Color(red, green, blue);
		diskColor = randomColor;
	}
	
	public int getSize() {
		return size;
	}
	
	public Color getColor() {
		return diskColor;
	}

}
