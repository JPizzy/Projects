package ca.cmpt213.courseplanner.ui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import ca.cmpt213.courseplanner.model.CourseList;
import ca.cmpt213.courseplanner.model.CourseOffering;
import ca.cmpt213.courseplanner.model.Histogram;
import ca.cmpt213.courseplanner.model.HistogramIcon;

/*
 * author jaspal,jason
 * This class provides the statistic of courses which have been selected by users.
 * It represents the frequency of campus and semester distribution.
 */
public class StatisticsPanel extends MyPanel {
	private static final long serialVersionUID = 1L;
	private static final int HISTOGRAM_HEIGHT = 150;
	private static final int HISTOGRAM_WIDE = 250;
	private static final int NUM_OF_SEMESTERS = 3;
	private static final int NUM_OF_CAMPUS = 4;
	private static final int PADDING = 12;
	
	JPanel statPanel;
	private JLabel courseLabel;
	private static String TITLE = "Statistics";

	private List<Integer> semesterDistribution;
	private List<Integer> campusDistribution;

	private static Histogram semesterHistogram = new Histogram(new int[] {}, 0);
	private static Histogram campusHistogram = new Histogram(new int[] {}, 0);

	public StatisticsPanel(CourseList model) {
		super(model, TITLE);
		registerForUpdates();
		restrictVerticalResizing();
		semesterDistribution = new ArrayList<Integer>();
		campusDistribution = new ArrayList<Integer>();
	}

	private void registerForUpdates() {
		CourseList model = super.getModel();
		model.addSelectedCourseListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				updateContentPanel();
			}
		});
		model.addRefreshListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				clearCourseInfoDisplay();
			}
		});
	}

	private void updateContentPanel() {
		CourseList model = super.getModel();
		String courseName = model.getSelectedCourseName();
		
		courseLabel.setText("Course: " + courseName);
		semesterHistogram.setNumberBars(NUM_OF_SEMESTERS);
		campusHistogram.setNumberBars(NUM_OF_CAMPUS);
		semesterHistogram.setData(getSemesterDistributionData());
		campusHistogram.setData(getCampusDistributionData());
	}

	private void clearCourseInfoDisplay() {
		semesterHistogram.setData(new int[] {});
		campusHistogram.setData(new int[] {});
	}

	private int[] getSemesterDistributionData() {
		CourseList model = super.getModel();
		semesterDistribution.clear();
		CourseOffering[] offerings = model.getSelectedCoursesOfferings();
		int semester = 0;
		for(int i = 0; i < offerings.length; i++) {
			semester = offerings[i].getMonthAsColumnIndex();
			if(semester == 1) {
				semesterDistribution.add(0);
			} else if(semester == 2) {
				semesterDistribution.add(1);
			} else if(semester == 3) {
				semesterDistribution.add(2);
			} else { } // do nothing
		}
		return convertData(semesterDistribution);
	}

	private int[] convertData(List<Integer> list) {
		int[] convertedData = new int[list.size()];
		for(int i = 0; i < list.size(); i++) {
			convertedData[i] = list.get(i);
		}
		return convertedData;
	}

	private int[] getCampusDistributionData() {
		CourseList model = super.getModel();
		campusDistribution.clear();
		CourseOffering[] offerings = model.getSelectedCoursesOfferings();
		String campus = null;
		for(int i = 0; i < offerings.length; i++) {
			campus = offerings[i].getLocation().toUpperCase();
			if(campus.equals("BURNABY")) {
				campusDistribution.add(0);
			} else if(campus.equals("SURREY")) {
				campusDistribution.add(1);
			} else if(campus.equals("HRBRCNTR")) {
				campusDistribution.add(2);
			} else {
				campusDistribution.add(3);
			}
		}
		return convertData(campusDistribution);
	}

	private JLabel makeHistogramLabel(Histogram histogram) {
		final JLabel label = new JLabel(new HistogramIcon(histogram, HISTOGRAM_WIDE, HISTOGRAM_HEIGHT));
		histogram.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent event) {
				//System.out.println("redrawing histogram");
				label.repaint();
			}
		});
		return label;
	}

	@Override
	public JPanel makeContent() {
		JPanel statPanel = new JPanel();
		statPanel.setLayout(new BoxLayout(statPanel, BoxLayout.PAGE_AXIS));
		courseLabel = new JLabel("Course:");
		statPanel.add(courseLabel);
		
		statPanel.add(Box.createVerticalStrut(PADDING));
		statPanel.add(new JLabel("Semester Offerings"));
		statPanel.add(makeHistogramLabel(semesterHistogram));
		statPanel.add(new JLabel("(0=Spring, 1=Summer, 2=Fall)"));
		
		statPanel.add(Box.createVerticalStrut(PADDING));
		statPanel.add(new JLabel("Campus Offerings:"));
		statPanel.add(makeHistogramLabel(campusHistogram));
		statPanel.add(new JLabel("(0=Bby, 1=Sry, 2=Van, 3=Other)"));
		return statPanel;
	}
}
