package com.projects.towersofhanoi.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class GameBoard {

	private int numOfDisks;
	private int numOfPegs;
	private Peg[] pegs;
	private Disk heldDisk;
	private List<ChangeListener> listeners = new ArrayList<ChangeListener>();
	private List<ChangeListener> statusListeners = new ArrayList<ChangeListener>();
	
	public GameBoard(int numOfDisks, int numOfPegs) {
		this.numOfDisks = numOfDisks;
		this.numOfPegs = numOfPegs;
		heldDisk = null;
		createBoard();
	}

	private void createBoard() {
		pegs = new Peg[numOfPegs];
		for(int i = 0; i < numOfPegs; i++) {
			pegs[i] = new Peg(numOfDisks + 1);			
		}
		setInitialDisks();
	}
	
	private void setInitialDisks() {
		for(int i = 0; i < numOfDisks; i++) {
			int diskSize = numOfDisks - i;
			pegs[0].placeDisk(new Disk(diskSize));
		}
	}
	
	public void resetBoard() {
		for(Peg peg : pegs) {
			while(peg.getNumOfDisks() > 0) {
				peg.removeDisk();				
			}
		}
		setInitialDisks();
		notifyListeners();
	}
	
	public Iterator<Peg> iterator() {
		return Arrays.asList(pegs).iterator();
	}
	
	public Peg[] getPegs() {
		return pegs;
	}
	
	public void placeDiskOnPeg(int pegNumber) {
		Peg peg = pegs[pegNumber - 1];
		if(heldDisk != null) {
			if(peg.isEmpty() || heldDisk.getSize() < peg.getSizeOfTopDisk()) {
				peg.placeDisk(heldDisk);
				heldDisk = null;
				notifyListeners();	
			}
		}
		notifyStatusListeners();
	}
	
	public void removeDiskFromPeg(int pegNumber) {
		if(heldDisk == null) {
			if(pegs[pegNumber - 1].getNumOfDisks() != 0) {
				heldDisk = pegs[pegNumber - 1].removeDisk();
				notifyListeners();
			}
		}
		notifyStatusListeners();
	}
	
	public int getNumOfPegs() {
		return numOfPegs;
	}
	
	public int getNumOfDisks() {
		return numOfDisks;
	}
	
	public int getHeldDiskSize() {
		if(heldDisk == null) {
			return -1;
		}
		return heldDisk.getSize();
	}
	
	public boolean wonGame() {
		int middlePeg = pegs.length / 2;
		return (pegs[middlePeg].getNumOfDisks() == numOfDisks);
	}
	
	public void addListener(ChangeListener listener) {
		listeners.add(listener);
	}
	
	public void addStatusListener(ChangeListener listener) {
		statusListeners.add(listener);
	}
	
	private void notifyListeners() {
		ChangeEvent event = new ChangeEvent(this);
		for(ChangeListener listener : listeners) {
			listener.stateChanged(event);
		}
	}
	
	private void notifyStatusListeners() {
		ChangeEvent event = new ChangeEvent(this);
		for(ChangeListener listener : statusListeners) {
			listener.stateChanged(event);
		}
	}

}
