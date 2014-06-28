package com.projects.lol.statcalculator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class ChampionDataParser {
	
	private File championData;
	private final static int INDEX_NAME = 0;
	private final static int INDEX_BASE_AD = 1;
	private final static int INDEX_BASE_AS = 2;
	
	public ChampionDataParser(File championData) {
		this.championData = championData;
	}
	
	public ArrayList<Champion> createChampionData() {
		ArrayList<String> data = readFile();
		ArrayList<Champion> championData = parseData(data);
		return championData;
	}
	
	public ArrayList<String> readFile() {
		ArrayList<String> data = new ArrayList<String>();
		try {
			Scanner scanner = new Scanner(championData);
			String lineBuffer;
			scanner.nextLine();
			while(scanner.hasNextLine()) {
				lineBuffer = scanner.nextLine();
				data.add(lineBuffer);
			}
			scanner.close();
		}
		catch(FileNotFoundException e) {
			System.out.println("ERROR: Source File Not Found!");
		}
		return data;
	}
	
	public ArrayList<Champion> parseData(ArrayList<String> data) {
		ArrayList<Champion> championData = new ArrayList<Champion>();
		for(String line : data) {
			String[] parts = line.split(",");
			String name = parts[INDEX_NAME];
			double baseAD = Double.parseDouble(parts[INDEX_BASE_AD]);
			double baseAS = Double.parseDouble(parts[INDEX_BASE_AS]);
			
			championData.add(new Champion(name, baseAD, baseAS));
		}
		return championData;
	}
}
