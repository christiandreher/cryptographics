package edu.kit.iks.CryptographicsLib;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.Area;
import java.awt.geom.GeneralPath;
import java.awt.geom.RoundRectangle2D;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.border.Border;

/**
 * An instance of this class represents the view of a popover
 * 
 * @author Christian Dreher
 */
public class PopoverView extends JPanel {
	/**
	 * Defines the possible locations of a popover arrow. 
	 */
	private enum ArrowLocation {
	    TOP, BOTTOM 
	};
	
	/**
	 * The width of the arrow.
	 */
	final private static int ARROW_WIDTH = 40;
	
	/**
	 * The height of the arrow.
	 */
	final private static int ARROW_HEIGHT = 20;
	
	/**
	 * The inner inset of the popover.
	 */
	final private static int INSET = 20;
	
	/**
	 * The corner radius of the popover.
	 */
	final private static int CORNER_RADIUS = 20;
	
	/**
	 * The minimum padding between the container and the popover.
	 */
	final private static int MINIMUM_CONTAINER_PADDING = 5;
	
	private Color borderColor;
	
	/**
	 * The arrow location of the popover.
	 */
	private ArrowLocation arrowLocation = ArrowLocation.TOP;
	
	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = -5949014143695648861L;

	/**
	 * Button to close the popover
	 */
	private JButton closeButton;
	
	/**
	 * Content area of the popover
	 */
	private JPanel contentView;
	
	private Point anchorPoint;
	
	/**
	 * The shared container view
	 */
	static private JPanel containerView;
	
	/**
	 * Constructor initializing a new instance of {PopoverView}
	 */
	public PopoverView() {
		super(new GridBagLayout());
		
		this.setOpaque(false);
		this.setMaximumSize(new Dimension(500, 500));
		this.setBackground(new Color(1.0f, 1.0f, 1.0f, 0.9f));
		this.setBorderColor(Color.LIGHT_GRAY);
		
		// Create close button.
		GridBagConstraints closeConstraints = new GridBagConstraints();
		closeConstraints.gridx = 0;
		closeConstraints.gridy = 0;
		this.closeButton = new JButton("Close");
		this.add(this.closeButton, closeConstraints);
		
		// Create content view.
		GridBagConstraints contentConstraints = new GridBagConstraints();
		contentConstraints.gridx = 0;
		contentConstraints.gridy = 1;
		contentConstraints.gridwidth = 3;
		this.contentView = new JPanel();
		this.contentView.setOpaque(false);
		this.add(this.contentView, contentConstraints);
		
		this.validate();
	}
	
	/**
	 * Returns the CloseButton.
	 * 
	 * @return the closeButton button to return.
	 */
	public JButton getCloseButton() {
		return this.closeButton;
	}
	
	/**
	 * The content view contains the popover's content. This makes it easier to
	 * layout the popover and the content properly.
	 * 
	 * @return the content view.
	 */
	public JPanel getContentView() {
		return this.contentView;
	}
	
	/**
	 * Configures all popovers to use a given container view. The container view
	 * should be visible above all other components and should have maximum height
	 * and width. You cannot use popover views before configuring them without a container!
	 * 
	 * @param containerView the container view
	 */
	public static void setContainerView(JPanel containerView) {
		PopoverView.containerView = containerView;
		
		// Configure container.
		containerView.setLayout(null);
		containerView.setVisible(true);
	}
	
	/**
	 * Presents a popover in the container
	 * 
	 * @param originComponent the origin of the popover is where the arrow of the popover should point to
	 */
	public void present(JComponent anchorComponent) {
		this.validate();
		
		// Calculate the anchorPoint. The origin is in the center of the component.
		double x = anchorComponent.getLocationOnScreen().getX() - PopoverView.containerView.getLocationOnScreen().getX();
		x += anchorComponent.getSize().getWidth() / 2;
		double y = anchorComponent.getLocationOnScreen().getY() - PopoverView.containerView.getLocationOnScreen().getY();
		y += anchorComponent.getSize().getHeight() / 2;
		this.anchorPoint = new Point((int)x, (int)y);
		
		// Position in container view.
		PopoverView.containerView.add(this);
		PopoverView.containerView.validate();
		
		this.prepareForPresentation();
	}
	
	/**
	 * Removes a popover from the container
	 */
	public void dismiss() {
		PopoverView.containerView.remove(this);
		PopoverView.containerView.revalidate();
		PopoverView.containerView.repaint();
	}
	
	protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        final Area shape = this.createShape();
        Graphics2D g2d = (Graphics2D)g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Fill.
        g2d.setPaint(this.getBackground());
        g2d.fill(shape);
        
        // Stroke.
        g2d.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2d.setPaint(this.getBorderColor());
        g2d.draw(shape);
    }

	private Area createShape() {
		// Create the basic rounded shape.
		Area shape = null;
		switch (this.arrowLocation) {
			case TOP:
				shape = new Area(new RoundRectangle2D.Double(0.0, ARROW_HEIGHT, this.getSize().getWidth(), this.getSize().getHeight() - ARROW_HEIGHT, CORNER_RADIUS, CORNER_RADIUS));
				break;
				
			case BOTTOM:
				shape = new Area(new RoundRectangle2D.Double(0.0, 0.0, this.getSize().getWidth(), this.getSize().getHeight() - ARROW_HEIGHT, CORNER_RADIUS, CORNER_RADIUS));
				break;
		}
		
		// Calculate the offset for the arrow.
		int centerLocationX = (int)(this.getLocation().getX() + this.getWidth() / 2); 
		int offsetX = (int)(this.anchorPoint.getX() - centerLocationX);
		
		// Create the arrow path.
	    GeneralPath path = new GeneralPath(GeneralPath.WIND_EVEN_ODD);
	    Point arrowLocation = null;
	    switch (this.arrowLocation) {
			case TOP:
				arrowLocation = new Point((int)(this.getSize().getWidth() / 2 + offsetX), ARROW_HEIGHT);
				path.moveTo(arrowLocation.getX() - ARROW_WIDTH / 2, arrowLocation.getY());
				path.lineTo(arrowLocation.getX(), arrowLocation.getY() - ARROW_HEIGHT);
			    path.lineTo(arrowLocation.getX() + ARROW_WIDTH / 2, arrowLocation.getY());
			    break;
				
			case BOTTOM:
				arrowLocation = new Point((int)(this.getSize().getWidth() / 2 + offsetX), (int)(this.getSize().getHeight() - ARROW_HEIGHT));
				path.moveTo(arrowLocation.getX() - ARROW_WIDTH / 2, arrowLocation.getY());
				path.lineTo(arrowLocation.getX(), arrowLocation.getY() + ARROW_HEIGHT);
			    path.lineTo(arrowLocation.getX() + ARROW_WIDTH / 2, arrowLocation.getY());
			    break;
	    }
	    path.closePath();
	    shape.add(new Area(path));

	    return shape;
	}
	
	/**
	 * Prepares a popover for presentation by calculation its bounds and the proper
	 * arrow location. 
	 */
	private void prepareForPresentation() {
		Dimension containerSize = PopoverView.containerView.getSize();
		Point center = new Point((int)(containerSize.getWidth() / 2), (int)(containerSize.getHeight() / 2));
		double offsetY = center.getY() - this.anchorPoint.getY();
		
		// Figure out arrow location.
		if (offsetY < 0.0) {
			this.arrowLocation = ArrowLocation.BOTTOM;
		} else {
			this.arrowLocation = ArrowLocation.TOP;
		}
		
		this.updateBorder();
		this.updateBounds();
		this.repaint();
	}
	
	private void updateBorder() {
		Border border;
		switch (this.arrowLocation) {
			case TOP: border = BorderFactory.createEmptyBorder(INSET + ARROW_HEIGHT, INSET, INSET, INSET); break;
			case BOTTOM: border = BorderFactory.createEmptyBorder(INSET, INSET, INSET + ARROW_HEIGHT, INSET); break;
			default: border = BorderFactory.createEmptyBorder(0, 0, 0, 0); break;
		}
		
		this.setBorder(border);
	}
	
	private void updateBounds() {
		Dimension size = this.getPreferredSize();
		
		// First, try to align the popover horizontally centered to the anchor.
		Point location;
		switch (this.arrowLocation) {
			case TOP:
				location = new Point((int)(this.anchorPoint.getX() - size.getWidth() / 2), (int)this.anchorPoint.getY());
				break;
				
			case BOTTOM:
				location = new Point((int)(this.anchorPoint.getX() - size.getWidth() / 2), (int)(this.anchorPoint.getY() - size.getHeight()));
				break;
				
			default:
				location = new Point(0, 0);
				break;
		}
		Rectangle bounds = new Rectangle(location, size);
		
		// Check if the bounds fit into the container.
		Rectangle containerBounds = PopoverView.containerView.getBounds();
		if (!containerBounds.contains(bounds)) {
			if (bounds.getX() < MINIMUM_CONTAINER_PADDING) {
				bounds.setLocation(MINIMUM_CONTAINER_PADDING, (int)bounds.getY());
			} else if (bounds.getX() + bounds.getWidth() > containerBounds.getWidth() - MINIMUM_CONTAINER_PADDING) {
				bounds.setLocation((int)(containerBounds.getWidth() - MINIMUM_CONTAINER_PADDING - bounds.getWidth()), (int)bounds.getY());
			}
		}
		
		this.setBounds(bounds);
	}
	
	public void setBorderColor(Color color) {
		this.borderColor = color;
		this.repaint();
	}
	
	public Color getBorderColor() {
		return this.borderColor;
	}
}
