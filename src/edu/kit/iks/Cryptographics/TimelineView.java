/**
 * Copyright (c) 2014 Matthias Jaenicke <matthias.jaenicke@student.kit.edu>,
 * Matthias Plappert <undkc@student.kit.edu>,
 * Julien Duman <uncyc@student.kit.edu>, 
 * Christian Dreher <uaeef@student.kit.edu>,
 * Wasilij Beskorovajnov <uajkm@student.kit.edu> and 
 * Aydin Tekin <aydin.tekin@student.kit.edu>
 * 
 * Released under the MIT license (refer to LICENSE.md)
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package edu.kit.iks.Cryptographics;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.geom.Rectangle2D;
import java.util.Iterator;
import java.util.List;

import javax.swing.JPanel;

import org.xnap.commons.i18n.I18n;

import edu.kit.iks.CryptographicsLib.AbstractVisualizationInfo;
import edu.kit.iks.CryptographicsLib.Configuration;
import edu.kit.iks.CryptographicsLib.VisualizationButton;

/**
 * An instance of this class represents the view of a timeline 
 * 
 * @author Christian Dreher
 */
public class TimelineView extends JPanel implements ComponentListener {
	
	/**
	 * The stroke width used for painting the timeline.
	 */
	private static final float TIMELINE_STROKE_WIDTH = 3.0f;
	
	/**
	 * The stroke width used for painting the connection between marker and name label. 
	 */
	private static final float LABEL_STROKE_WIDTH = 2.0f;
	
	/**
	 * The radius of a marker.
	 */
	private static final int MARKER_RADIUS = 20;
	
	/**
	 * The padding between elements.
	 */
	private static final int PADDING = 10;
	
	/**
	 * The color of the timeline stroke.
	 */
	private static final Color TIMELINE_STROKE_COLOR = Color.BLACK;
	
	/**
	 * The color of the stroke between the label and the marker. 
	 */
	private static final Color LABEL_STROKE_COLOR = Color.GRAY;
	
	/**
	 * The color of the name label.
	 */
	private static final Color NAME_TEXT_COLOR = Color.BLACK;
	
	/**
	 * The color of the year label.
	 */
	private static final Color YEAR_TEXT_COLOR = Color.GRAY;
	
	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = -4974243564527826198L;
	
	/**
	 * Localization instance
	 */
	private static I18n i18n = Configuration.getInstance().getI18n(TimelineView.class);

	/**
	 * Buttons to open the popover of a specific procedure to eventually 
	 * start their visualization
	 */
	private VisualizationButton[] buttons;
	
	/**
	 * The list of all visualization infos.
	 */
	private List<AbstractVisualizationInfo> visualizationInfos;
	
	/**
	 * Gets the buttons symbolizing a procedure displayed on the timeline 
	 * 
	 * @return Array of all timeline-buttons
	 */
	public VisualizationButton[] getButtons() {
		return buttons;
	}
	
	/**
	 * Constructor initializing a new instance of {TimelineView} with 
	 * given {visualizationInfos}
	 * 
	 * @param visualizationInfos List of all {VisualizationInfo}-instances
	 */
	public TimelineView(List<AbstractVisualizationInfo> visualizationInfos) {
		super(null);
		
		this.addComponentListener(this);
		this.setPreferredSize(new Dimension(0, 200));
		
		this.visualizationInfos = visualizationInfos;
		
		// Initialize buttons. These buttons are not visible and positioned without a layout manager.
		// They simply fulfill the purpose of providing a clickable area.
		final Dimension size = new Dimension(MARKER_RADIUS * 2, MARKER_RADIUS * 2);
		this.buttons = new VisualizationButton[visualizationInfos.size()];
		int j = 0;
		for (Iterator<AbstractVisualizationInfo> i = visualizationInfos.iterator(); i.hasNext();) {
			AbstractVisualizationInfo visualizationInfo = i.next();
			VisualizationButton button = new VisualizationButton(visualizationInfo);
			button.setText("");
			button.setSize(size);
			button.setOpaque(false);
			button.setContentAreaFilled(false);
			button.setBorderPainted(false);
			this.add(button);
			this.buttons[j] = button;
			j++;
		}
		
		this.layoutButtons();
		this.validate();
	}
	
	/**
	 * Paints the timeline and all markers
	 */
	@Override
	protected void paintComponent(Graphics g) {
		// Configure graphics context.
		Graphics2D g2 = (Graphics2D)g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		
		this.paintTimeline(g2);
		
		// Draw markers for all visualization infos.
		for (Iterator<AbstractVisualizationInfo> i = this.visualizationInfos.iterator(); i.hasNext();) {
			AbstractVisualizationInfo visualizationInfo = i.next();
			this.paintMarker(g2, visualizationInfo);
		}
	}
	
	/**
	 * Paints the timeline.
	 * @param g2 The graphics context
	 */
	private void paintTimeline(Graphics2D g2) {
		final Stroke stroke = new BasicStroke(TIMELINE_STROKE_WIDTH);
		final int baseline = (int)(this.getSize().getHeight() / 2);
		
		// Paint.
		g2.setColor(TIMELINE_STROKE_COLOR);
		g2.setStroke(stroke);
		g2.drawLine(0, baseline, (int)this.getSize().getWidth(), baseline);
	}
	
	/**
	 * Paints the markers and the related labels.
	 * @param g2 The graphics context
	 * @param visualizationInfo The visualization info that is represented by the marker
	 */
	private void paintMarker(Graphics2D g2, AbstractVisualizationInfo visualizationInfo) {
		final int radius = MARKER_RADIUS;
		final int padding = PADDING;
		final int baseline = this.getSize().height / 2; 
		
		// Calculate marker position.
		int markerX = (int)(this.getSize().width * visualizationInfo.getTimelineOffset());
		int markerY = baseline;
		
		// Calculate name label position.
		FontMetrics nameMetrics = g2.getFontMetrics(g2.getFont());
		Rectangle2D nameRect = nameMetrics.getStringBounds(visualizationInfo.getName(), g2);
		int nameX = markerX - (int)(nameRect.getWidth() / 2);
		int nameY = -(int)nameRect.getY();
		
		// Calculate year label position.
		String year;
		if (visualizationInfo.getYear() >= 0) {
			year = new Integer(visualizationInfo.getYear()).toString();
		} else {
			year = String.format(i18n.tr("{0} B.C.", Math.abs(visualizationInfo.getYear())));
		}
		FontMetrics yearMetrics = g2.getFontMetrics(g2.getFont());
		Rectangle2D yearRect = yearMetrics.getStringBounds(year, g2);
		int yearX = markerX - (int)(yearRect.getWidth() / 2);
		int yearY = baseline + radius + padding - (int)yearRect.getY();
		
		// Draw name label.
		g2.setColor(LABEL_STROKE_COLOR);
		g2.setStroke(new BasicStroke(LABEL_STROKE_WIDTH));
		g2.drawLine(markerX, markerY - radius - padding, markerX, nameY + (int)nameRect.getHeight() + (int)nameRect.getY() + padding);
		g2.setColor(NAME_TEXT_COLOR);
		g2.drawString(visualizationInfo.getName(), nameX, nameY);
		
		// Draw year label.
		g2.setColor(YEAR_TEXT_COLOR);
		g2.drawString(year, yearX, yearY);
		
		g2.setColor(visualizationInfo.getDifficultyColor());
		g2.fillOval(markerX - radius, markerY - radius, radius * 2, radius * 2);
		g2.setColor(TIMELINE_STROKE_COLOR);
		g2.setStroke(new BasicStroke(TIMELINE_STROKE_WIDTH));
		g2.drawOval(markerX - radius, markerY - radius, radius * 2, radius * 2);
	}
	
	/**
	 * Manually layouts the buttons. We cannot use a layout manager for this since we need precise
	 * placement of the buttons.
	 */
	private void layoutButtons() {
		for (VisualizationButton button : this.buttons) {
			Point location = this.getLocation(button.getVisualizationInfo());
			Dimension size = button.getSize();
			location.x -= size.width / 2;
			location.y -= size.height / 2;
			button.setLocation(location);
		}
	}
	
	/**
	 * Calculates the correct location from a given visualization info.
	 * @param visualizationInfo the visualization info
	 * @return the location of that visualization info on the timeline
	 */
	private Point getLocation(AbstractVisualizationInfo visualizationInfo) {
		int x = (int)(this.getSize().getWidth() * visualizationInfo.getTimelineOffset());
		int y = (int)(this.getSize().getHeight() / 2);
		
		return new Point(x, y);
	}

	@Override
	public void componentResized(ComponentEvent e) {
		// If the view resizes, re-layout the buttons.
		this.layoutButtons();
	}

	@Override
	public void componentMoved(ComponentEvent e) {
		// Unused
	}

	@Override
	public void componentShown(ComponentEvent e) {
		// Unused
	}

	@Override
	public void componentHidden(ComponentEvent e) {
		// Unused
	}
}
