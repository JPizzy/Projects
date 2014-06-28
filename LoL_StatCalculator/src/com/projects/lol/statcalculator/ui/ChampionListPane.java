package com.projects.lol.statcalculator.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.projects.lol.statcalculator.LoLCalculator;

public class ChampionListPane extends JPanel{
	private static final long serialVersionUID = 1L;
	
	final static int SCROLLBAR_WIDTH = 100;
	final static int SCROLLBAR_HEIGHT = 300;
	final static int CELL_WIDTH = 100;
	
	private LoLCalculator model;

	public ChampionListPane(LoLCalculator calculator) {
		model = calculator;
		setLayout(new BorderLayout());
		add(createChampionList());
	}
	
	private Component createChampionList() {
		JPanel scrollablePane = new JPanel();
		scrollablePane.setLayout(new BorderLayout());
		final JList displayList = new JList();
		displayList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		displayList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		displayList.setVisibleRowCount(-1);
		displayList.setFixedCellWidth(CELL_WIDTH);
		displayList.setListData(model.getChampionNames());
		displayList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				if(displayList.getSelectedIndex() != -1) {
					model.setSelectedChampion(displayList.getSelectedValue().toString());
				}
			} 
		});
		
		JScrollPane scrollableList = new JScrollPane(displayList,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollableList.setPreferredSize(new Dimension(SCROLLBAR_WIDTH, SCROLLBAR_HEIGHT));
		scrollablePane.add(scrollableList, BorderLayout.WEST);
		return scrollablePane;
	}
}
