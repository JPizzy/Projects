package ca.cmpt213.courseplanner.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import ca.cmpt213.courseplanner.model.CourseList;
/**
 * 
 * @author jaspal,jason
 * This panel displays courses which satisfy the condition the user condition(from course list filter ).
 */
public class CourseListPanel extends MyPanel {
	private static final long serialVersionUID = 1L;
	private static String TITLE = "Course List";
	private static final int SCROLLBAR_HEIGHT = 400;
	private static final int SCROLLBAR_WIDTH = 225;
	private static final int CELL_WIDTH = 100;

	public CourseListPanel(CourseList model) {
		super(model, TITLE);
	}
	
	private void registerForUpdates(final JList list) {
		final CourseList model = super.getModel();
		model.addSelectedFilterListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent arg0) {
				list.setListData(model.getFilteredCourses());
			}
		});
	}

	private Component createList() {
		final CourseList model = super.getModel();
		JPanel scrollablePanel = new JPanel();
		final JList displayList = new JList();
		displayList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		displayList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		displayList.setVisibleRowCount(-1);
		displayList.setFixedCellWidth(CELL_WIDTH);
		registerForUpdates(displayList);
		displayList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				if(displayList.getSelectedIndex() != -1) {
					model.setSelectedCourse(displayList.getSelectedValue().toString());
				}
			}
		});
		
		JScrollPane listScroller = new JScrollPane(displayList,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		listScroller.setPreferredSize(new Dimension(SCROLLBAR_WIDTH, SCROLLBAR_HEIGHT));
		scrollablePanel.setLayout(new BorderLayout());
		scrollablePanel.add(listScroller, BorderLayout.WEST);
		return scrollablePanel;
	}

	@Override
	public JPanel makeContent() {
		JPanel listPanel = new JPanel();
		listPanel.setLayout(new BorderLayout());
		listPanel.add(createList(), BorderLayout.WEST);
		return listPanel;
	}

}
