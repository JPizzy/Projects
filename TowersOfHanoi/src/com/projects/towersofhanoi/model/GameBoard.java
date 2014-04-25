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
		for(int i = 0; i < numOfDisks; i++) {
			int diskSize = numOfDisks - i;
			pegs[0].placeDisk(new Disk(diskSize));
		}

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
	}
	
	public void removeDiskFromPeg(int pegNumber) {
		if(heldDisk == null) {
			if(pegs[pegNumber - 1].getNumOfDisks() != 0) {
				heldDisk = pegs[pegNumber - 1].removeDisk();
				notifyListeners();
			}
		}
	}
	
	public int getNumOfPegs() {
		return numOfPegs;
	}
	
	public int getNumOfDisks() {
		return numOfDisks;
	}
	
	public void addListener(ChangeListener listener) {
		listeners.add(listener);
	}
	
	private void notifyListeners() {
		ChangeEvent event = new ChangeEvent(this);
		for(ChangeListener listener : listeners) {
			listener.stateChanged(event);
		}
	}

}
