package com.projects.lol.statcalculator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class RunePage {
	
	private static final int MARKS = 0;
	private static final int SEALS = 1;
	private static final int GLYPHS = 2;
	private static final int QUINTS = 3;
	
	private static final int MARK_SLOTS = 9;
	private static final double AD_MARK = 0.95;
	private static final double AS_MARK = 0.017;
	
	private static final int QUINT_SLOTS = 3;
	private static final double AD_QUINT = 2.25;
	private static final double AS_QUINT = 0.045;
	
	Rune markAD = new Rune("AD Mark", "Mark", "AD", AD_MARK);
	Rune markAS = new Rune("AS Mark", "Mark", "AS", AS_MARK);
	Rune quintAD = new Rune("AD Quint", "Quint", "AD", AD_QUINT);
	Rune quintAS = new Rune("AS Quint", "Quint", "AS", AS_QUINT);
	
	private List<List<Rune>> page;
	private HashMap<String, Double> stats;
	
	List<Rune> marks = new ArrayList<Rune>();
	List<Rune> seals = new ArrayList<Rune>();
	List<Rune> glyphs = new ArrayList<Rune>();
	List<Rune> quints = new ArrayList<Rune>();

	// 30 Slots
	// 9 marks
	// 9 seals
	// 9 glyphs
	// 3 quints
	public RunePage() {
		initPage();
		//generatePage();
		calculateStats();
		//printPage();
	}
	
	public RunePage(RunePage pageToCopyFrom) {
		initPage();
		this.copyPage(pageToCopyFrom);
		calculateStats();
	}
	
	private void initPage() {
		page = new ArrayList<List<Rune>>();
		page.add(marks);
		page.add(seals);
		page.add(glyphs);
		page.add(quints);
		stats = new HashMap<String, Double>();
	}
	
	private void copyPage(RunePage toCopy) {
		marks.addAll(toCopy.getMarks());
		seals.addAll(toCopy.getSeals());
		glyphs.addAll(toCopy.getGlyphs());
		quints.addAll(toCopy.getQuints());
	}
	
	public void fillPage(Rune marks, Rune quints) {
		for(int i = 0; i < MARK_SLOTS; i++) {
			page.get(MARKS).add(marks);
		}
		for(int i = 0; i <QUINT_SLOTS; i++) {
			page.get(QUINTS).add(quints);
		}
		calculateStats();
	}
	
//	public void generatePage() {
//		fillPage(markAD, quintAD);
//		calculateStats();
//	}
	
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
	
	private void calculateStats() {
		for(List<Rune> runeType : page) {			
			for(Rune rune : runeType) {
				if(stats.containsKey(rune.getStatType())) {
					Double temp = stats.get(rune.getStatType());
					temp += rune.getValue();
					stats.put(rune.getStatType(), temp);
				}
				else {
					stats.put(rune.getStatType(), rune.getValue());
				}
			}
		}
	}
	
	public List<Rune> getMarks() {
		return marks;
	}
	
	public List<Rune> getSeals() {
		return seals;
	}
	
	public List<Rune> getGlyphs() {
		return glyphs;
	}
	
	public List<Rune> getQuints() {
		return quints;
	}
	
	public HashMap<String, Double> getStats() {
		return stats;
	}
	
	public void printPage() {
		// Print each individual rune in page
		System.out.println("Marks: ");
		for(Rune rune : page.get(MARKS)) {
			System.out.println(rune.toString());
		}
		System.out.println("Quints: ");
		for(Rune rune : page.get(QUINTS)) {
			System.out.println(rune.toString());
		}
		
		// Print total stats from rune page
		Set<String> runeType = stats.keySet();
		System.out.println("Stats from rune page:");
		for(String str : runeType) {
			System.out.println(str + " - " + stats.get(str));
		}
	}
}
