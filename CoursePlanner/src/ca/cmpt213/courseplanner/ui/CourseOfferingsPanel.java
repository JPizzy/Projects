package ca.cmpt213.courseplanner.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import ca.cmpt213.courseplanner.model.Course;
import ca.cmpt213.courseplanner.model.CourseList;
import ca.cmpt213.courseplanner.model.CourseOffering;
/*
 * author jaspal,jason
 * This panel displays all passed offered courses with the course name and number selected
 * by user from the courseListPanel in their respective grids. 
 * Also this panel allows user to choose a course and see the status of the course.
 */
public class CourseOfferingsPanel extends MyPanel {
	private static final long serialVersionUID = 1L;
	private static final String TITLE = "Course Offering By Semester";
	private static final int NUM_OF_SEMESTERS = 3;
	private static final int HEIGHT = 600;
	private static final int WIDTH = 600;
	JPanel offeringsPanel;

	public CourseOfferingsPanel(CourseList model) {
		super(model, TITLE);
	}
	
	private void registerForUpdates(JPanel panel) {
		CourseList model = super.getModel();
		model.addSelectedCourseListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				updateGrid();
			}
		});
		model.addRefreshListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				clearPanel();
			}
		});
	}
	
	private void clearPanel() {
		String directions = "Use a filter to select a course";
		offeringsPanel.removeAll();
		offeringsPanel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		offeringsPanel.setBackground(Color.WHITE);
		offeringsPanel.setLayout(new BorderLayout());
		offeringsPanel.add(new JLabel(directions, SwingConstants.CENTER), BorderLayout.CENTER);
		offeringsPanel.revalidate();
		offeringsPanel.repaint();
	}
	
	private void updateGrid() {
		CourseList model = super.getModel();
		Course course = model.getSelectedCourse();
		int firstYear = course.getFirstYearOfOffering();
		int latestYear = course.getLatestYearOfOffering();
		int yearRange = latestYear - firstYear + 1;
		GridBagLayout gridBag = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();
		offeringsPanel.removeAll();
		offeringsPanel.setLayout(gridBag);
		for(int row = 0; row <= yearRange; row++) {
			for(int column = 0; column <= NUM_OF_SEMESTERS; column++) {
				gbc.fill = GridBagConstraints.BOTH;
				gbc.gridx = column;
				gbc.gridy = row;
				if(row == 0) {
					gbc.weightx = 1;
					gbc.weighty = 1;
					offeringsPanel.add(makeSemesterPanel(column));
				}
				else if(column == 0 && row > 0) {
					gbc.weightx = 0;
					gbc.weighty = 0;
					offeringsPanel.add(makeYearPanel(firstYear + row - 1), gbc);
				}
				else if(row >= 1 && column >= 1){
					gbc.weightx = 1;
					gbc.weighty = 1;
					offeringsPanel.add(makeOfferingButton(course, row, column), gbc);					
				}
			}
		}
		offeringsPanel.revalidate();
		offeringsPanel.repaint();
	}
	
	private Component makeSemesterPanel(int column) {
		JPanel semesterPanel = new JPanel();;
		String semesterString = "";
		if(column == 1) {
			semesterString = "Spring";
		}
		else if(column == 2) {
			semesterString = "Summer";
		}
		else if(column == 3) {
			semesterString = "Fall";
		}
		semesterPanel.add(new JLabel(semesterString));
		semesterPanel.setBackground(Color.WHITE);
		return semesterPanel;
	}
	
	private Component makeYearPanel(int year) {
		JPanel yearPanel = new JPanel();
		String yearString = String.valueOf(year);
		yearPanel.add(new JLabel(yearString));
		yearPanel.setBackground(Color.WHITE);
		return yearPanel;
	}
	
	private Component makeOfferingButton(Course course, int row, int column) {
		final CourseList model = super.getModel();
		CourseOffering[] offerings = course.getOfferings();
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.PAGE_AXIS));
		int firstYear = course.getFirstYearOfOffering();
		
		for(final CourseOffering offering : offerings) {
			boolean belongsToSameSemester = offering.getMonthAsColumnIndex() == column;
			boolean belongsToSameYear = offering.getYear() - firstYear == row - 1;
			if(belongsToSameSemester && belongsToSameYear) {
				JButton button = new JButton(course.getCourseName() + " - " + offering.getLocation());
				button.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						model.setSelectedOffering(offering);
					}
				});
				
				JPanel singleButtonPanel = new JPanel();
				singleButtonPanel.setLayout(new BorderLayout());
				singleButtonPanel.add(button, BorderLayout.NORTH);
				buttonsPanel.add(singleButtonPanel);
			}
		}
		buttonsPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		return buttonsPanel;
	}

	@Override
	public JPanel makeContent() {
		offeringsPanel = new JPanel();
		registerForUpdates(offeringsPanel);
		clearPanel();
		return offeringsPanel;
	}

}
