package ca.cmpt213.courseplanner.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;

import ca.cmpt213.courseplanner.model.CourseList;
/*
 * author jaspal,jason
 * This class provides the basic functionality of all panels used in this project
 */
public abstract class MyPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private final int PADDING = 3;
	
	private String title;
	private CourseList model;

	public MyPanel(CourseList model, String title) {
		this.model = model;
		this.title = title;
		JPanel content = makeContent();
		content.setBorder(BorderFactory.createBevelBorder(
				BevelBorder.LOWERED,
				Color.BLACK,
				Color.GRAY));
		
		setLayout(new BorderLayout());
		add(makeTitle(), BorderLayout.NORTH);
		add(content, BorderLayout.CENTER);
		setBorder(new EmptyBorder(PADDING, PADDING, PADDING, PADDING));
	}
	
	private Component makeTitle() {
		JLabel titleLabel = new JLabel(title);
		titleLabel.setForeground(Color.BLUE);
		return titleLabel;
	}
	
	public abstract JPanel makeContent();
	
	protected CourseList getModel() {
		return model;
	}
	
	protected void restrictVerticalResizing() {
		Dimension prefSize = this.getPreferredSize();
		Dimension newSize = new Dimension(Integer.MAX_VALUE, (int)prefSize.getHeight());
		this.setMaximumSize(newSize);
	}

}
