package ca.cmpt213.courseplanner.ui;

import java.awt.BorderLayout;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import ca.cmpt213.courseplanner.model.CourseList;
/*
 * author jaspal, jason
 * This class assemble all the panel together and provides services to users.
 */
public class ProgramFrame {

	public static void main(String[] args) {
		CourseList courseList = new CourseList();
		//courseList.dumpModel();
		final JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel leftPanel = new JPanel();
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.PAGE_AXIS));
		leftPanel.add(new CourseFilterPanel(courseList));
		leftPanel.add(new CourseListPanel(courseList));
		
		JPanel rightPanel = new JPanel();
		rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.PAGE_AXIS));
		rightPanel.add(new StatisticsPanel(courseList));
		rightPanel.add(new DetailsPanel(courseList));
		
		frame.setLayout(new BorderLayout());
		frame.add(leftPanel, BorderLayout.WEST);
		frame.add(new CourseOfferingsPanel(courseList), BorderLayout.CENTER);
		frame.add(rightPanel, BorderLayout.EAST);
		frame.pack();
		frame.setVisible(true);	
	}

}
