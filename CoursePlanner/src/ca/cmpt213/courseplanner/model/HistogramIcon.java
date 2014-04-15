package ca.cmpt213.courseplanner.model;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.util.Iterator;

import javax.swing.Icon;

/**
 * 
 * @author Jaspal, Jason
 * Displays an icon representation of an histogram object.
 */
public class HistogramIcon implements Icon{
	private Histogram model;
	private final int WIDTH;
	private final int HEIGHT;
	private final int MARGIN = 15;
	private final int TEXT_HEIGHT = 12;
	private final int BAR_TEXT_PADDING = 5;
	private final int TOP_MARGIN = MARGIN + TEXT_HEIGHT;
	private final int BOTTOM_MARGIN = MARGIN + (2 * TEXT_HEIGHT);
	private final int BAR_SPACING = 8;

	public HistogramIcon(Histogram hist, int width, int height) {
		this.model = hist;
		this.WIDTH = width;
		this.HEIGHT = height;
	}

	@Override
	public int getIconHeight() {
		return HEIGHT;
	}

	@Override
	public int getIconWidth() {
		return WIDTH;
	}

	@Override
	public void paintIcon(Component c, Graphics g, int x, int y) {
		Graphics2D g2d = (Graphics2D) g;

		//Set background
		g2d.setColor(Color.WHITE);
		g2d.fillRect(0, 0, WIDTH, HEIGHT);

		//Draw Axis
		g2d.setColor(Color.BLACK);
		g2d.drawLine(x + MARGIN, y + TOP_MARGIN, x + MARGIN, HEIGHT - BOTTOM_MARGIN);
		g2d.drawLine(x + MARGIN, HEIGHT - BOTTOM_MARGIN, WIDTH - MARGIN, HEIGHT - BOTTOM_MARGIN);

		//Draw Bars
		int maximumHeight = model.getMaxBarCount();
		int numOfBars = model.getNumberBars();

		if(numOfBars > 0) {
			Iterator<Histogram.Bar> itr = model.iterator();
			Histogram.Bar currentBar;
			int startX = x + MARGIN + BAR_SPACING;
			int bottomMargin = HEIGHT - BOTTOM_MARGIN;
			int barWidth = (WIDTH - MARGIN - MARGIN - (BAR_SPACING * numOfBars)) / numOfBars;
			int barIndex = 1;

			while(itr.hasNext()){
				currentBar = itr.next();

				double barHeightPercentage = ((double)currentBar.getCount() / maximumHeight);
				int barHeight = (int) ((bottomMargin - TOP_MARGIN) * barHeightPercentage);
				int startY = bottomMargin - barHeight;

				g2d.setColor(Color.BLUE);
				g2d.fillRect(startX, startY, barWidth, barHeight);

				// Get text offsets for centering of text
				int centerOfBar = startX + (barWidth / 2);
				String barCount = String.valueOf(currentBar.getCount());
				String barMinInterval = String.valueOf(currentBar.getRangeMin());
				Font font = g2d.getFont();
				FontRenderContext fontContext = g2d.getFontRenderContext();

				Rectangle2D bounds = font.getStringBounds(barCount, fontContext);
				double textWidth = bounds.getWidth();
				int barCountOffset = (int) (centerOfBar - (textWidth / 2));
				bounds = font.getStringBounds(barMinInterval, fontContext);
				textWidth = bounds.getWidth();
				int barMinIntervalOffset = (int) (centerOfBar - (textWidth / 2));

				// Draw Text
				g2d.setColor(Color.BLACK);
				g2d.drawString(barCount, barCountOffset, startY - BAR_TEXT_PADDING);
				if(barIndex % 2 == 0) {
					g2d.drawString(barMinInterval, barMinIntervalOffset, barHeight + startY + BAR_TEXT_PADDING + (2 * TEXT_HEIGHT));
				} else {
					g2d.drawString(barMinInterval, barMinIntervalOffset, barHeight + startY + BAR_TEXT_PADDING + TEXT_HEIGHT);
				}

				startX += BAR_SPACING + barWidth;
				barIndex++;
			}
		}
	}
}
