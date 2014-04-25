package com.projects.towersofhanoi.model;

import java.util.Iterator;
import java.util.Stack;

public class Peg {

	private Stack<Disk> disks;
	private int height;
	
	public Peg(int height) {
		disks = new Stack<Disk>();
		this.height = height;
	}
	
	public int getHeight() {
		return height;
	}
	
	public void placeDisk(Disk disk) {
		disks.push(disk);
	}
	
	public Disk removeDisk() {
		return disks.pop();
	}
	
	public int getNumOfDisks() {
		return disks.size();
	}
	
	public int getSizeOfTopDisk() {
		if(!disks.empty()) {
			return disks.peek().getSize();			
		}
		else {
			return -1;
		}
	}
	
	public boolean isEmpty() {
		return disks.empty();
	}
	
	public Iterator<Disk> iterator() {
		return disks.iterator();
	}

}
