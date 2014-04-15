package ca.cmpt213.courseplanner.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
// author jaspal, jason 
// This class does the function desbribes below
// Store course list
// Sum up all data into one course
// Sort data into collections by subject
// Filter courses
public class CourseListParser {

	ArrayList<String> courses;
	
	public CourseListParser(File filePath) {
		courses = readFile(filePath);
	}
	
	public ArrayList<String> readFile(File dataFile) {
		ArrayList<String> data = new ArrayList<String>();
		try {
			Scanner scanner = new Scanner(dataFile);
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
	
	public HashMap<String, Subject> createSubjects() {
		HashMap<String, Subject> subjects = new HashMap<String, Subject>();
		for(String line : courses) {
			Course courseToAdd = new Course(line);
			String courseSubject = courseToAdd.getSubject();
			
			//Collection is empty
			if(subjects.size() == 0){
				//add very first subject
				Subject newSubject = new Subject(courseSubject);
				subjects.put(newSubject.getName(), newSubject);
				newSubject.addCourse(courseToAdd);
				continue;
			}
			
			//if subjects contains this subject already
			if(subjects.containsKey(courseSubject)) {
				//then add to existing subject
				Subject subject = subjects.get(courseSubject);
				//if course already exists
				if(subject.hasCourse(courseToAdd)) {
					Course sameCourse = subject.getCourse(courseToAdd);
					if(sameCourse.hasSameOffering(line)) {
						//add to previous section
						sameCourse.addSectionToCourse(line);
					}
					else {
						//add as a new offering
						sameCourse.addNewOffering(line);
					}
				}
				//if subject doesn't contain this course
				else {
					//then add as a new course
					subject.addCourse(courseToAdd);						
				}
			}
			//If course doesn't belong to any existing subject
			else {
				//then add new subject and add course to that
				Subject newSubject = new Subject(courseSubject);
				subjects.put(newSubject.getName(), newSubject);
				newSubject.addCourse(courseToAdd);
			}
		}
		return subjects;
	}
	
//	public ArrayList<Subject> createSubjects() {
//		ArrayList<Subject> subjects = new ArrayList<Subject>(1);
//		for(String line : courses) {
//			Course courseToAdd = new Course(line);
//			String courseSubject = courseToAdd.getSubject();
//			
//			if(subjects.size() == 0){
//				Subject newSubject = new Subject(courseSubject);
//				subjects.add(newSubject);
//				newSubject.addCourse(courseToAdd);
//				continue;
//			}
//			
//			int index = 0;
//			boolean added = false;
//			while(index < subjects.size() && !added) {
//				Subject subject = subjects.get(index);
//				//course belongs to this subject
//				if(courseSubject.equals(subject.getName())) {
//					//subject already contains this course
//					if(subject.hasCourse(courseToAdd)) {
//						Course sameCourse = subject.getCourse(courseToAdd);
//						if(sameCourse.hasSameOffering(line)) {
//							//add to previous section
//							sameCourse.addSectionToCourse(line);
//							added = true;
//						}
//						else {
//							//add as a new offering
//							sameCourse.addNewOffering(line);
//							added = true;
//						}
//					}
//					//subject doesn't contain this course
//					else {
//						//add new course
//						subject.addCourse(courseToAdd);						
//						added = true;
//					}
//				}
//				index++;
//			}
//			
//			//Course doesn't belong to any existing subject
//			if(!added) {
//				//add new subject
//				Subject newSubject = new Subject(courseSubject);
//				subjects.add(newSubject);
//				newSubject.addCourse(courseToAdd);
//			}
//		}
//		return subjects;
//	}
	
//	private String[] parseLine(String line) {
//		String [] parts = line.split(",");
//		return parts;
//	}

}
