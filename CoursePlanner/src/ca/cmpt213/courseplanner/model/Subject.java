package ca.cmpt213.courseplanner.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

/**
 * author jaspal, jason
 * This class contains all the classes for a specific subject
 */
public class Subject {

	private String name;
	private HashMap<String, Course> courses;
	
	public Subject(String name) {
		this.name = name;
		courses = new HashMap<String, Course>();
	}
	
	public void addCourse(Course course) {
		courses.put(course.getCourseName(), course);
	}
	
	public boolean hasCourse(Course course) {
		return courses.containsValue(course);
	}
	
	public String getName() {
		return name;
	}
	
	public Course getCourse(Course course) {
		return courses.get(course.getCourseName());
	}
	
	public Course getCourse(String courseName) {
		return courses.get(courseName);
	}
	
	public Course[] getAllCourses() {
		Course[] newArray = new Course[courses.size()];
		courses.values().toArray(newArray);
		return newArray;
	}
	
	public String[] getAllCourseNames() {
		String[] newArray = new String[courses.size()];
		courses.keySet().toArray(newArray);
		Arrays.sort(newArray);
		return newArray;
		//return sortArray(courses.values());
	}
	
//	private String[] sortArray(Collection<Course> c) {
//		List<Course> courses = (List<Course>) c;
//		Collections.sort(courses, new CatalogNumberComparator());
//		String[] newArray = new String[courses.size()];
//		for(int i = 0; i < newArray.length; i++) {
//			newArray[i] = courses.get(i).getCourseName();
//		}
//		return newArray;
//	}

	public String[] getFilteredCourses(boolean undergrad, boolean grad) {
		ArrayList<String> filteredList = new ArrayList<String>();
		for(Course course : courses.values()) {
			String numWithNoX = course.getCatalogNumber().replaceAll("X", "0");
			int courseNumber = getStringValue(numWithNoX);
			if(undergrad && courseNumber <= 499) {
				filteredList.add(course.getCourseName());
			}
			else if(grad && courseNumber >= 500) {
				filteredList.add(course.getCourseName());
			}
		}

		Collections.sort(filteredList, new CatalogNumberComparator());
		String[] newArray = new String[filteredList.size()];
		filteredList.toArray(newArray);
		return newArray;
	}
	
	public int getNumOfCourses() {
		return courses.size();
	}
	
	private int getStringValue(String str) {
		return Integer.valueOf("0" + str.replaceAll("(\\d*).*", "$1"));
	}

	/**
	 * Used to compare two course name strings, in the form "CMPT 320W", for sorting.
	 * Removes the subject name and any letters in the catalog number.
	 * Then compares the left over numbers.
	 */
	class CatalogNumberComparator implements Comparator<String> {
		@Override
		public int compare(String courseName1, String courseName2) {
			//Remove everything before the space.
			String catalogNum1 = courseName1.split(" ")[1];
			String catalogNum2 = courseName2.split(" ")[1];
			//Remove letters and convert to int
			catalogNum1 = catalogNum1.replaceAll("X", "0");
			catalogNum2 = catalogNum2.replaceAll("X", "0");
			int num1 = getStringValue(catalogNum1);
			int num2 = getStringValue(catalogNum2);
			if(num1 > num2) {
				return 1;
			}
			else if(num1 < num2) {
				return -1;
			}
			else {
				return 0;
			}
		}
		
	}

}
