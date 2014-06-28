package com.projects.lol.statcalculator;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class LoLCalculator {

	private final static String CHAMPION_DATA_PATH = "data/test_data.txt";
	private List<ChangeListener> refreshListeners = new ArrayList<ChangeListener>();
	RuneCalculator runeCalc;
	private ArrayList<Champion> championList;
	private Champion selectedChampion;
	private RunePage runePage;
	
	public LoLCalculator() {
		runeCalc = new RuneCalculator();
		ChampionDataParser parser = new ChampionDataParser(new File(CHAMPION_DATA_PATH));
		championList = parser.createChampionData();
	}
	
	public String[] getChampionNames() {
		String[] championNames = new String[championList.size()];
		for(int i = 0; i < championList.size(); i++) {
			championNames[i] = championList.get(i).getName();
		}
		return championNames;
	}
	
	public ArrayList<Champion> getChampionList() {
		return championList;
	}
	
	public Champion getSelectedChampion() {
		return selectedChampion;
	}
	
	public RunePage getRunePage() {
		return runePage;
	}
	
	public void setSelectedChampion(String name) {
		for(Champion champion : championList) {
			if(champion.getName() == name) {
				selectedChampion = champion;
				break;
			}
		}
		runePage = runeCalc.calculateDPSPage(selectedChampion);
		notifyRefreshListeners();
	}
	
	public void addRefreshListener(ChangeListener listener) {
		refreshListeners.add(listener);
	}
	
	public void notifyRefreshListeners() {
		ChangeEvent event = new ChangeEvent(this);
		for(ChangeListener listener : refreshListeners) {
			listener.stateChanged(event);
		}
	}
}
