package ca.cmpt213.courseplanner.model;

import java.util.ArrayList;

/**
 * author jaspal, jason 
 * Represents an offering for a course. An offering is distinguished
 * from another based on semester and location.
 * Each offering also contains one or more sections.
 */
public class CourseOffering {
	private final static int INDEX_SEMESTER = 0;
	private final static int INDEX_LOCATION = 3;
	private final static int INDEX_ENROLLMENT_CAP = 4;
	private final static int INDEX_SECTION = 5;
	private final static int INDEX_ENROLLMENT_TOTAL = 6;
	private final static int INDEX_INSTRUCTORS = 7;
	private final static int SEMESTER_CODE_SPRING = 1;
	private final static int SEMESTER_CODE_SUMMER = 4;
	private final static int SEMESTER_CODE_FALL = 7;
	private final static int CENTURY = 100;
	private final static int DECADE = 10;
	private final static int STARTING_CENTURY = 1900;
	
	private String semester;
	private String location;
	private String instructors;
	private ArrayList<Section> sections;
	
	public CourseOffering(String line) {
		String [] parts = line.split(",");
		semester = parts[INDEX_SEMESTER];
		location = parts[INDEX_LOCATION];
		instructors = "";
		
		int enrollmentCap = Integer.parseInt(parts[INDEX_ENROLLMENT_CAP]);
		String section = parts[INDEX_SECTION];
		int enrollmentTotal = Integer.parseInt(parts[INDEX_ENROLLMENT_TOTAL]);
		if(parts.length > INDEX_INSTRUCTORS) {
			for(int i = INDEX_INSTRUCTORS; i < parts.length; i++){
				instructors += parts[i];					
			}
		}
		
		sections = new ArrayList<Section>();
		sections.add(new Section(section, enrollmentTotal, enrollmentCap));
	}
	
	public CourseOffering(String semester, String location, String instructors, ArrayList<Section> sections) {
		this.semester = semester;
		this.location = location;
		this.instructors = instructors;
		this.sections = sections;
	}
	
	public String getSemesterMonth(){
		int month = Integer.parseInt(""+semester.charAt(semester.length() - 1));
		if (month == SEMESTER_CODE_SPRING) {
			return "Spring";
		}
		else if (month == SEMESTER_CODE_SUMMER) {
			return "Summer";
		}
		else if (month == SEMESTER_CODE_FALL) {
			return "Fall";
		}
		else {
			return "Undefined";
		}
	}
	
	public int getMonthAsColumnIndex(){
		int month = Integer.parseInt(""+semester.charAt(semester.length() - 1));
		if (month == SEMESTER_CODE_SPRING) {
			return 1;
		}
		else if (month == SEMESTER_CODE_SUMMER) {
			return 2;
		}
		else if (month == SEMESTER_CODE_FALL) {
			return 3;
		}
		else {
			return -1;
		}
	}
	
	public int getYear() {
		int centuries = Integer.parseInt(""+semester.charAt(0));
		int decades = Integer.parseInt(""+semester.charAt(1));
		int years = Integer.parseInt(""+semester.charAt(2));
		return STARTING_CENTURY + (centuries * CENTURY) + (decades * DECADE) + years;
	}

	public String getSemester() {
		return semester;
	}

	public String getLocation() {
		return location;
	}

	public String getInstructors() {
		return instructors;
	}

	public ArrayList<Section> getSections() {
		return sections;
	}
	
	public boolean equals(CourseOffering offering) {
		return this.location.equals(offering.location);
	}
	
	public void addSection(Section sectionToAdd) {
		boolean added = false;
		for(Section existingSection : sections) {
			if(existingSection.equals(sectionToAdd)) {
				existingSection.addToExistingSection(sectionToAdd);
				added = true;
			}
		}
		if(!added) {
			sections.add(sectionToAdd);
			added = true;
		}
	}
	
}