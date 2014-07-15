package com.projects.lol.statcalculator.ui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.HashMap;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.projects.lol.statcalculator.Champion;
import com.projects.lol.statcalculator.LoLCalculator;
import com.projects.lol.statcalculator.Rune;
import com.projects.lol.statcalculator.RunePage;

public class RuneCalculatorPane extends JPanel {
	private static final long serialVersionUID = 1L;
	
	final static int WIDTH = 150;
	final static int HEIGHT = 300;
	
	private LoLCalculator model;
	private JPanel runeCalcPanel;
	private JLabel selectedChampionName;
	private JLabel marks;
	private JLabel quints;
	private JLabel dps;

	public RuneCalculatorPane(LoLCalculator calculator) {
		model = calculator;
		selectedChampionName = new JLabel("Champion");
		marks = new JLabel();
		quints = new JLabel();
		dps = new JLabel();
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		initRuneCalculatorPanel();
		add(runeCalcPanel);
		registerForUpdates();
	}
	
	private void registerForUpdates() {
		model.addRefreshListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent arg0) {
				updatePanel();
			}
		});
	}
	
	public void initRuneCalculatorPanel() {
		runeCalcPanel = new JPanel();
		runeCalcPanel.setLayout(new GridLayout(7,1));
		runeCalcPanel.add(selectedChampionName);
		runeCalcPanel.add(new JLabel("Marks"));
		runeCalcPanel.add(marks);
		runeCalcPanel.add(new JLabel("Quintessences"));
		runeCalcPanel.add(quints);
		runeCalcPanel.add(new JLabel("DPS"));
		runeCalcPanel.add(dps);
	}
	
	public void updatePanel() {
		RunePage page = model.getRunePage();
		selectedChampionName.setText(model.getSelectedChampion().getName());
		marks.setText(createRuneString(page.getMarks()));
		quints.setText(createRuneString(page.getQuints()));
		dps.setText(createDPSString());
	}
	
	private String createRuneString(List<Rune> runes) {
		int adCount = 0;
		int asCount = 0;
		String runeType = runes.get(0).getType();
		String s = "";
		for(Rune rune : runes) {
			if(rune.getStatType().equals("AD")) {
				adCount++;
			}
			if(rune.getStatType().equals("AS")) {
				asCount++;
			}
		}
		s += "<html>" + adCount + " AD " + runeType + "<br>" + asCount + " AS " + runeType + "</html>";
		return s;
	}
	
	private String createDPSString() {
		//TODO move calculation of DPS to model
		RunePage page = model.getRunePage();
		Champion champ = model.getSelectedChampion();
		HashMap<String, Double> runePageStats = page.getStats();
		double bonusAtkDmg = 0;
		double bonusAtkSpd = 0;
		if(runePageStats.containsKey("AS") ) {
			bonusAtkSpd = runePageStats.get("AS");			
		}
		if(runePageStats.containsKey("AD")) {			
			bonusAtkDmg = runePageStats.get("AD");
		}
		double dps = (champ.getBaseAS()) * (bonusAtkSpd + 1) * (champ.getBaseAD() + bonusAtkDmg);
		return String.format("%.2f", dps);
	}
}
