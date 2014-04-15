package ca.cmpt213.courseplanner.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ca.cmpt213.courseplanner.model.CourseList;
/*
 * author jaspal, jason
 * The course filter panel filters courses base on user selection.
 * Users can pick any course they want and they can also pick undergrade or 
 * grad courses.
 * The desired course will then be displayed in the list.
 */
public class CourseFilterPanel extends MyPanel {
	private static final long serialVersionUID = 1L;
	private static String TITLE = "Course Filter Box";
	
	private JComboBox subjectComboBox;
	private JCheckBox undergradBox;
	private JCheckBox gradBox;

	public CourseFilterPanel(CourseList model) {
		super(model, TITLE);
		restrictVerticalResizing();
	}
	
	private Component makeComboBox() {
		JPanel comboPanel = new JPanel();
		String[] subjectStrings = super.getModel().getSubjectNames();
		subjectComboBox = new JComboBox(subjectStrings);
		comboPanel.setLayout(new BoxLayout(comboPanel, BoxLayout.LINE_AXIS));
		comboPanel.add(new JLabel("Departments"));
		comboPanel.add(subjectComboBox);
		return comboPanel;
	}
	
	private Component makeCheckBoxes() {
		JPanel checkBoxes = new JPanel();
		checkBoxes.setLayout(new BoxLayout(checkBoxes, BoxLayout.PAGE_AXIS));
		undergradBox = new JCheckBox("Include undergrad courses");
		gradBox = new JCheckBox("Include grad courses");
		undergradBox.setSelected(true);
		checkBoxes.add(undergradBox);
		checkBoxes.add(gradBox);
		return checkBoxes;
	}
	
	private Component makeUpdateButton() {
		final CourseList model = super.getModel();
		JPanel buttonPanel = new JPanel();
		JButton updateButton = new JButton("Update Course List");
		updateButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String selectedSubject = (String) subjectComboBox.getSelectedItem();
				boolean undergrad = undergradBox.isSelected();
				boolean grad = gradBox.isSelected();
				model.setFilter(selectedSubject, undergrad, grad);
				}
		});
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));
		buttonPanel.add(Box.createHorizontalGlue());
		buttonPanel.add(updateButton);
		return buttonPanel;
	}

	@Override
	public JPanel makeContent() {
		JPanel filterPanel = new JPanel();
		filterPanel.setLayout(new BorderLayout());
		filterPanel.add(makeComboBox(), BorderLayout.NORTH);
		filterPanel.add(makeCheckBoxes(), BorderLayout.CENTER);
		filterPanel.add(makeUpdateButton(), BorderLayout.SOUTH);
		return filterPanel;
	}

}
