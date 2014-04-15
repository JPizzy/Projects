package ca.cmpt213.courseplanner.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
/*
 * author jaspal, jason
 * This class represents a list of courses, it has the corresponding methods 
 * and fields required for filtering out certain courses in the list
 */
public class CourseList {

	private static final File FILEPATH = new File("data/course_data_2014.csv");
	private static final File OUTPUT = new File("data/output_dump.txt");
	private List<ChangeListener> selectedCourseListeners = new ArrayList<ChangeListener>();;
	private List<ChangeListener> selectedOfferingListeners = new ArrayList<ChangeListener>();;
	private List<ChangeListener> selectedFilterListeners = new ArrayList<ChangeListener>();
	private List<ChangeListener> refreshListeners = new ArrayList<ChangeListener>();
	
	private HashMap<String, Subject> subjects;
	private boolean showUndergrad;
	private boolean showGrad;
	private String selectedSubjectName;
	private String selectedCourseName;
	private CourseOffering selectedCourseOffering;
	
	public CourseList() {
		CourseListParser parser = new CourseListParser(FILEPATH);
		subjects = parser.createSubjects();
	}
	
	public void dumpModel() {
		try {
			PrintWriter writer = new PrintWriter(OUTPUT);
			for(Subject subject : subjects.values()) {
				Course[] courses = subject.getAllCourses();
				for(Course course : courses) {
					writer.println(course.makeStringRepresentation());
				}
			}
			writer.close();
			System.out.println("Course info written to output file: " + OUTPUT.getAbsolutePath());
		} catch (FileNotFoundException e) {
			System.out.println("ERROR: Output File Not Found!");
		}
	}
	
	// Getters
	public String[] getSubjectNames() {
		String[] newArray = new String [subjects.size()];
		subjects.keySet().toArray(newArray);
		Arrays.sort(newArray);
		return newArray;
	}
	
	public String[] getCourseNames(String subjectName) {
		return subjects.get(subjectName).getAllCourseNames();
	}

	public Course[] getCourses(String subjectName) {
		return subjects.get(subjectName).getAllCourses();
	}
	
	public String[] getFilteredCourses() {
		return subjects.get(selectedSubjectName).getFilteredCourses(showUndergrad, showGrad);
	}
	
	public String getSelectedCourseName() {
		return selectedCourseName;
	}
	
	public Course getSelectedCourse() {
		return subjects.get(selectedSubjectName).getCourse(selectedCourseName);
	}
	
	public CourseOffering[] getSelectedCoursesOfferings() {
		return getSelectedCourse().getOfferings();
	}
	
	public CourseOffering getSelectedOffering() {
		return selectedCourseOffering;
	}
	
	// Setters
	public void setFilter(String subject, boolean undergradSelected, boolean gradSelected) {
		selectedSubjectName = subject;
		showUndergrad = undergradSelected;
		showGrad = gradSelected;
		notifySelectedFilterListeners();
		notifyRefreshListeners();
	}
	
	public void setSelectedCourse(String courseName) {
		selectedCourseName = courseName;
		notifySelectedCourseListeners();
	}
	
	public void setSelectedOffering(CourseOffering offering) {
		selectedCourseOffering = offering;
		notifySelectedOfferingListeners();
	}
	
	// Add listeners
	public void addSelectedCourseListener(ChangeListener listener) {
		selectedCourseListeners.add(listener);
	}
	
	public void addSelectedOfferingListener(ChangeListener listener) {
		selectedOfferingListeners.add(listener);
	}
	
	public void addRefreshListener(ChangeListener listener) {
		refreshListeners.add(listener);
	}
	
	public void addSelectedFilterListener(ChangeListener listener) {
		selectedFilterListeners.add(listener);
	}
	
	// Notify listeners
	private void notifySelectedCourseListeners() {
		ChangeEvent event = new ChangeEvent(this);
		for(ChangeListener listener : selectedCourseListeners) {
			listener.stateChanged(event);
		}
	}
	
	private void notifySelectedOfferingListeners() {
		ChangeEvent event = new ChangeEvent(this);
		for(ChangeListener listener : selectedOfferingListeners) {
			listener.stateChanged(event);
		}
	}
	
	private void notifyRefreshListeners() {
		ChangeEvent event = new ChangeEvent(this);
		for(ChangeListener listener : refreshListeners) {
			listener.stateChanged(event);
		}
	}
	
	private void notifySelectedFilterListeners() {
		ChangeEvent event = new ChangeEvent(this);
		for(ChangeListener listener : selectedFilterListeners) {
			listener.stateChanged(event);
		}
	}

}
