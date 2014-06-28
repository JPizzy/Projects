package com.projects.lol.statcalculator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RunePageGenerator {

	private static final int MARK_SLOTS = 9;
	private static final double AD_MARK = 0.95;
	private static final double AS_MARK = 0.017;
	
	private static final int QUINT_SLOTS = 3;
	private static final double AD_QUINT = 2.25;
	private static final double AS_QUINT = 0.045;
	
	List<Rune> marks = new ArrayList<Rune>();
	List<Rune> seals = new ArrayList<Rune>();
	List<Rune> glyphs = new ArrayList<Rune>();
	List<Rune> quints = new ArrayList<Rune>();
	
	Rune markAD = new Rune("AD Mark", "Mark", "AD", AD_MARK);
	Rune markAS = new Rune("AS Mark", "Mark", "AS", AS_MARK);
	Rune quintAD = new Rune("AD Quint", "Quint", "AD", AD_QUINT);
	Rune quintAS = new Rune("AS Quint", "Quint", "AS", AS_QUINT);
	
	Champion champ;
	
	public RunePageGenerator(Champion champ) {
		this.champ = champ;
	}
	
	public RunePage getDPSPage() {
		//Add DPS runes
		ArrayList<Rune> m = new ArrayList<Rune>();
		ArrayList<Rune> q = new ArrayList<Rune>();
		m.add(markAD);
		m.add(markAS);
		q.add(quintAD);
		q.add(quintAS);
		ArrayList<RunePage> pages = generateAllPages(m, q);
		double maxDPS = 0;
		double currentDPS = 0;
		RunePage bestDPSPage = new RunePage();
		for(RunePage page : pages) {
			currentDPS = calcD(page);
			if(currentDPS > maxDPS) {
				maxDPS = currentDPS;
				bestDPSPage = page;
			}
		}
		return bestDPSPage;
	}
	
	private double calcD(RunePage page) {
		HashMap<String, Double> stats = page.getStats();
		double bonusAtkSpd = 0;
		double bonusAtkDmg = 0;
		if(stats.containsKey("AD")) {
			bonusAtkDmg += stats.get("AD");
		}
		if(stats.containsKey("AS")) {
			bonusAtkSpd += stats.get("AS");
		}
		return (champ.getBaseAS()) * (bonusAtkSpd + 1) * (champ.getBaseAD() + bonusAtkDmg);
	}
	
//	public double calculateDPS(Champion champ) {
//		double bonusAtkSpd = 0;
//		double bonusAtkDmg = 0;
//		for(Rune rune : page.get(0)) {
//			if(rune.getStatType().equals("AS")) {
//				bonusAtkSpd += rune.getValue();				
//			}
//			else if(rune.getStatType().equals("AD")){
//				bonusAtkDmg += rune.getValue();				
//			}
//		}
//		return (champ.getBaseAS()) * (bonusAtkSpd + 1) * (champ.getBaseAD() + bonusAtkDmg);
//	}
	
	public ArrayList<RunePage> generateAllPages(ArrayList<Rune> marks, ArrayList<Rune> quints) {
		ArrayList<RunePage> pages = new ArrayList<RunePage>();
		
		RunePage tempPage = new RunePage();
		tempPage.fillPage(marks.get(0), quints.get(0));
		pages.add(new RunePage(tempPage));
		List<Rune> thisMarks = tempPage.getMarks();
		List<Rune> thisQuints = tempPage.getQuints();
		
		//Loop through marks
		for(int markToSet = 1; markToSet < marks.size(); markToSet++) {
			//Loop through and set mark
			for(int markSlotsFilled = 0; markSlotsFilled < MARK_SLOTS; markSlotsFilled++) {
				thisMarks.set(markSlotsFilled, marks.get(markToSet));
				pages.add(new RunePage(tempPage));
				//Loop through quints
				for(int quintToSet = 1; quintToSet < quints.size(); quintToSet++) {
					//Loop through and set quint
					List<Rune> tempQuints = new ArrayList<Rune>();
					tempQuints.addAll(thisQuints);
					for(int quintSlotsFilled = 0; quintSlotsFilled < QUINT_SLOTS; quintSlotsFilled++) {
						thisQuints.set(quintSlotsFilled, quints.get(quintToSet));
						pages.add(new RunePage(tempPage));
					}
					//Reset Quints
					thisQuints.removeAll(thisQuints);
					thisQuints.addAll(tempQuints);
				}
			}
		}
		//TEST PRINT
//		System.out.println("NumOfPages: " + pages.size());
//		for(int i = 0; i < pages.size(); i++) {
//			System.out.print("P" + i + " ");
//			pages.get(i).printPage();
//		}
		return pages;
	}
}
