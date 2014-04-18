package ca.cmpt213.courseplanner.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import ca.cmpt213.courseplanner.model.CourseList;
import ca.cmpt213.courseplanner.model.CourseOffering;
import ca.cmpt213.courseplanner.model.Section;

public class DetailsPanel extends MyPanel {
	private static final long serialVersionUID = 1L;
	private static final int LABEL_HEIGHT = 12;
	private static final int LABEL_WIDTH = 125;
	private static final String LINE_BORDER_COLOR = "#1FBED6";
	private static final String TITLE = "Details of Course Offering";
	private JPanel gridPanel;
	private JPanel sectionsPanel;
	private JPanel detailsPanel;
	private JLabel courseLabel;
	private JLabel locationLabel;
	private JLabel semesterLabel;
	private JLabel instructorsLabel;

	public DetailsPanel(CourseList model) {
		super(model, TITLE);
		registerForUpdates();
	}
	
	private void registerForUpdates() {
		CourseList model = super.getModel();
		model.addSelectedOfferingListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				updateGridPanel();
				updateSectionsPanel();
			}
		});
		model.addRefreshListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				clearPanel();
			}
		});
	}
	
	private void initGridPanel() {
		courseLabel = new JLabel();
		locationLabel = new JLabel();
		semesterLabel = new JLabel();
		instructorsLabel = new JLabel();
		courseLabel.setBorder(BorderFactory.createLineBorder(Color.decode(LINE_BORDER_COLOR)));
		locationLabel.setBorder(BorderFactory.createLineBorder(Color.decode(LINE_BORDER_COLOR)));
		semesterLabel.setBorder(BorderFactory.createLineBorder(Color.decode(LINE_BORDER_COLOR)));
		instructorsLabel.setBorder(BorderFactory.createLineBorder(Color.decode(LINE_BORDER_COLOR)));
		instructorsLabel.setPreferredSize(new Dimension(LABEL_WIDTH, LABEL_HEIGHT));
		
		gridPanel.setLayout(new GridLayout(4,2));
		gridPanel.add(new JLabel("Course Name:"));
		gridPanel.add(courseLabel);
		gridPanel.add(new JLabel("Location:"));
		gridPanel.add(locationLabel);
		gridPanel.add(new JLabel("Semester:"));
		gridPanel.add(semesterLabel);
		gridPanel.add(new JLabel("Instructors:"));
		gridPanel.add(instructorsLabel);
	}
	
	private void clearPanel() {
		gridPanel.removeAll();
		sectionsPanel.removeAll();
		initGridPanel();
	}
	
	private void updateGridPanel() {
		CourseList model = super.getModel();
		CourseOffering offering = model.getSelectedOffering();
		
		gridPanel.removeAll();
		initGridPanel();
		
		courseLabel.setText(model.getSelectedCourseName());
		locationLabel.setText(offering.getLocation());
		semesterLabel.setText(offering.getSemesterMonth() + " " + offering.getYear());
		instructorsLabel.setText(offering.getInstructors());
		instructorsLabel.setToolTipText(offering.getInstructors());
	}
	
	private void updateSectionsPanel() {
		sectionsPanel.removeAll();
		
		CourseList model = super.getModel();
		CourseOffering offering = model.getSelectedOffering();
		ArrayList<Section> sections = offering.getSections();
		sectionsPanel.setLayout(new GridLayout(sections.size() + 1, 2));
		sectionsPanel.add(new JLabel("Section Type"));
		sectionsPanel.add(new JLabel("Enrollment(filled/cap)"));
		for(Section section : sections) {
			String sectionType = section.getSectionType();
			int enrollmentTotal = section.getEnrollmentTotal();
			int enrollmentCap = section.getEnrollmentCap();
			sectionsPanel.add(new JLabel(sectionType));
			String label = String.format("%d / %d", enrollmentTotal, enrollmentCap);
			sectionsPanel.add(new JLabel(label));
		}
	}

	@Override
	public JPanel makeContent() {
		gridPanel = new JPanel();
		detailsPanel = new JPanel();
		sectionsPanel = new JPanel();
		detailsPanel.setLayout(new BorderLayout());
		detailsPanel.add(gridPanel, BorderLayout.NORTH);
		detailsPanel.add(sectionsPanel, BorderLayout.SOUTH);
		clearPanel();
		return detailsPanel;
	}

}
