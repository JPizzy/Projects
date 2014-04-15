package ca.cmpt213.courseplanner.model;

import java.util.ArrayList;

/**
 * author jaspal, jason
 * Stores all information about a class
 */
public class Course {
	private final static int INDEX_SEMESTER = 0;
	private final static int INDEX_SUBJECT = 1;
	private final static int INDEX_CATALOG_NUMBER = 2;
	private final static int INDEX_LOCATION = 3;
	private final static int INDEX_ENROLLMENT_CAP = 4;
	private final static int INDEX_SECTION = 5;
	private final static int INDEX_ENROLLMENT_TOTAL = 6;
	private final static int INDEX_INSTRUCTORS = 7;
	
	private String subject;
	private String catalogNumber;
	private ArrayList<CourseOffering> offerings;
	
	public Course(String subject, String catalogNumber) {
		this.subject = subject;
		this.catalogNumber = catalogNumber;
	}
	
	public Course(String line) {
		String [] parts = line.split(",");
		subject = parts[INDEX_SUBJECT];
		catalogNumber = parts[INDEX_CATALOG_NUMBER];
		offerings = new ArrayList<CourseOffering>();
		offerings.add(new CourseOffering(line));
	}
	
	public String makeStringRepresentation() {
		String courseString = String.format("%s %s\n", subject, catalogNumber);
		for(CourseOffering offering : offerings) {
		courseString += String.format("\t%s in %s by %s\n", offering.getSemester(), offering.getLocation(), offering.getInstructors());
			for(Section section : offering.getSections()) {				
				courseString += String.format("\t\tType=%s Enrollment=%d/%d\n", section.getSectionType(), section.getEnrollmentTotal(), section.getEnrollmentCap());
			}
		}
		return courseString;
	}
	
	public String getSubject() {
		return subject;
	}

	public String getCatalogNumber() {
		return catalogNumber;
	}
	
	public String getCourseName() {
		return subject + " " + catalogNumber;
	}
	
	public int getFirstYearOfOffering() {
		int firstYear = Integer.MAX_VALUE;
		for(CourseOffering offering : offerings) {
			int yearOfOffering = offering.getYear();
			if(yearOfOffering < firstYear) {
				firstYear = yearOfOffering;
			}
		}
		return firstYear;
	}
	
	public int getLatestYearOfOffering() {
		int latestYear = -1;
		for(CourseOffering offering : offerings) {
			int yearOfOffering = offering.getYear();
			if(yearOfOffering > latestYear) {
				latestYear = yearOfOffering;
			}
		}
		return latestYear;
	}
	
	public CourseOffering[] getOfferings() {
		CourseOffering[] newArray = new CourseOffering[offerings.size()];
		offerings.toArray(newArray);
		return newArray;
	}
	
	public void addSectionToCourse(String line) {
		String [] parts = line.split(",");
		String semester = parts[INDEX_SEMESTER];
		String location = parts[INDEX_LOCATION];
		int enrollmentCap = Integer.parseInt(parts[INDEX_ENROLLMENT_CAP]);
		String section = parts[INDEX_SECTION];
		int enrollmentTotal = Integer.parseInt(parts[INDEX_ENROLLMENT_TOTAL]);
		
		for(CourseOffering offering : offerings) {
			boolean sameCampus = location.equals(offering.getLocation());
			boolean sameSemester = semester.equals(offering.getSemester());
			if(sameCampus && sameSemester) {
				offering.addSection(new Section(section, enrollmentTotal, enrollmentCap));
				break;
			}
		}
	}

	public void addNewOffering(String line) {
		String [] parts = line.split(",");
		String semester = parts[INDEX_SEMESTER];
		String location = parts[INDEX_LOCATION];
		int enrollmentCap = Integer.parseInt(parts[INDEX_ENROLLMENT_CAP]);
		String section = parts[INDEX_SECTION];
		int enrollmentTotal = Integer.parseInt(parts[INDEX_ENROLLMENT_TOTAL]);
		String instructors = "";
		if(parts.length > INDEX_INSTRUCTORS) {
			for(int i = INDEX_INSTRUCTORS; i < parts.length; i++){
				instructors += parts[i];					
			}
		}
		CourseOffering newCourseOffering = new CourseOffering(semester, location, instructors, new ArrayList<Section>());
		newCourseOffering.addSection(new Section(section, enrollmentTotal, enrollmentCap));
		offerings.add(newCourseOffering);
	}
	
	public boolean hasSameOffering(String line) {
		for(CourseOffering offering : offerings) {
			if(line.contains(offering.getSemester()) && line.contains(offering.getLocation())) {
				return true;
			}
		}
		return false;
	}

	// Used for hashtable
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((catalogNumber == null) ? 0 : catalogNumber.hashCode());
		result = prime * result + ((subject == null) ? 0 : subject.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Course other = (Course) obj;
		if (catalogNumber == null) {
			if (other.catalogNumber != null)
				return false;
		} else if (!catalogNumber.equals(other.catalogNumber))
			return false;
		if (subject == null) {
			if (other.subject != null)
				return false;
		} else if (!subject.equals(other.subject))
			return false;
		return true;
	}	

}
