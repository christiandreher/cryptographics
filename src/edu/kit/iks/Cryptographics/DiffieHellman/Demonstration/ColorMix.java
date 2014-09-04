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

package edu.kit.iks.Cryptographics.DiffieHellman.Demonstration;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Area;

import javax.swing.JPanel;
import javax.swing.Timer;

import edu.kit.iks.CryptographicsLib.Logger;

/**
 * This view let's us mix to colors to
 * get a mixture color
 * 
 * @author kai
 *
 */
public class ColorMix extends JPanel {

	private static final long serialVersionUID = 4056277049609956169L;
	
	/* our two circles to mix */
	private Ellipse2DwithColor ellip1, ellip2;
	
	/* the mix of colors of ellip1 and ellip2 */
	private Color mixedColor;

	/* coordinates of the circles */
	private int x1, y1, x2, y2;
	
	/* diameter of the circles */
	private int diameter;

	/* true if we should mix the colors */
	private boolean mixcolors;

	/*
	 * fires timer events to update coordinates
	 */
	private Timer timer;
	
	/*
	 * frequency of timerevent
	 */
	private int timerInterval = 20;
	
	/* the middle part where the two circles will meet */
	private int middle;

	/* the original coordinates, so that we can reset them later */
	private int originalx1, originaly1, originalx2, originaly2;

	/*
	 * use a special color mixing function for computing
	 * the shared secret as we mix basically three colors
	 * instead of two and we must therefore use a corrected
	 * formula for this case
	 */
	private boolean computeFinalMix;
	
	/**
	 * Computes stuff out of the size
	 * and of the diameter 
	 * 
	 * @param circleSize the diameter of the circles
	 * @param dimension the size of the JPanel
	 */
	public ColorMix(int circleSize, Dimension dimension) {
		this.setSize(dimension);
		this.setPreferredSize(dimension);
		this.diameter = circleSize;
		this.originalx1 = circleSize;
		this.originaly1 = dimension.height-circleSize;
		this.originalx2 = dimension.width-circleSize;
		this.originaly2 = dimension.height-circleSize;
		this.x1 = originalx1;
		this.y1= originaly1;
		this.x2 = originalx2;
		this.y2 = originaly2;
		this.middle = (x1+x2)/2;
		this.ellip1 = new Ellipse2DwithColor(x1, y1, circleSize, circleSize, null);
		this.ellip2 = new Ellipse2DwithColor(x2, y2, circleSize, circleSize, null);
	}
	
	/**
	 * will mix colors visually inside the JPanel
	 * @param mix if true mixed, if false, stops mixing
	 * @param repeat will periodically start all over again if true, to have continous animation
	 * @param cb our nextstep to call when finished, only used when repeat set to false
	 */
	public void mixColors(boolean mix, boolean repeat, final NextStepCallback cb) {
		assert(this.ellip1.getColor() != null);
		assert(this.ellip2.getColor() != null);
		this.mixcolors = mix;
		this.x1 = originalx1;
		this.y1 = originaly1;
		this.x2 = originalx2;
		this.y2 = originaly2;
		if(mixcolors) {
			if(repeat) {
				this.timer = new Timer(timerInterval, new ActionListener() {
				
					@Override
					public void actionPerformed(ActionEvent arg0) {
						if(x1 < middle) {
							x1 += 1;
						} else {
							x1 = originalx1;
						}
						if(x2 > middle) {
							x2 -= 1;
						} else {
							x2 = originalx2;
						}
						repaint();
					}
				});
				timer.start();
			} else {
				this.timer = new Timer(timerInterval, new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent arg0) {
						Logger.debug(this.getClass().getName(), "mixColors", "timer in colormix ");
						if(x1 < middle) {
							x1 += 1;
						}
						if(x2 > middle) {
							x2 -= 1;
						}
						if(x2 <= middle && x1 >= middle) {
							timer.stop();
							if (cb != null) {
								Logger.debug(this.getClass().getName(), "mixColors", "called callback in colormix");
								cb.callback();	
							}
						}
						repaint();
					}
				});
				timer.start();
			}
		} else {
			timer.stop();
		}
	}
	
	/**
	 * seperate colors instead of mixing them
	 * @param seperate start if true, else stop
	 * @param repeat repeat periodically if true, else don't
	 */
	public void seperateColors(boolean seperate, boolean repeat) {
		assert(this.ellip1.getColor() != null);
		assert(this.ellip2.getColor() != null);
		this.mixcolors = seperate;
		this.x1 = middle;
		this.y1 = originaly1;
		this.x2 = middle;
		this.y2 = originaly2;
		if(mixcolors) {
			if(repeat) {
				this.timer = new Timer(20, new ActionListener() {
				
					@Override
					public void actionPerformed(ActionEvent arg0) {
						if(x1 > originalx1) {
							x1 -= 1;
						} else {
							x1 = middle;
						}
						if(x2 < originalx2) {
							x2 += 1;
						} else {
							x2 = middle;
						}
						repaint();
					}
				});
				timer.start();
			} else {
				this.timer = new Timer(20, new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent arg0) {
						Logger.debug(this.getClass().getName(), "mixColors", "timer in colormix ");
						if(x1 > originalx1) {
							x1 -= 1;
						}
						if(x2 < originalx2) {
							x2 += 1;
						}
						if(x2 <= middle && x1 >= middle) {
							timer.stop();
							Logger.debug(this.getClass().getName(), "mixColors", "stopped timer in colormix");
						}
						repaint();
					}
				});
				timer.start();
			}
		} else {
			timer.stop();
		}
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
//		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		if (mixcolors && isComputeFinalMix()) {
			ellip1.setFrame(x1, y1, diameter, diameter);
			g2.setPaint(ellip1.getColor());
			g2.fill(ellip1);
			
			ellip2.setFrame(x2, y2, diameter, diameter);
			g2.setPaint(ellip2.getColor());
			g2.fill(ellip2);
			
			Area area1 = new Area(ellip1);
			Area area2 = new Area(ellip2);
			area1.intersect(area2);
			computeFinalMixedColor(ellip1.getColor(), ellip2.getColor());
			g2.setPaint(mixedColor);
			g2.fill(area1);
		} else if (mixcolors) {
			ellip1.setFrame(x1, y1, diameter, diameter);
			g2.setPaint(ellip1.getColor());
			g2.fill(ellip1);
			
			ellip2.setFrame(x2, y2, diameter, diameter);
			g2.setPaint(ellip2.getColor());
			g2.fill(ellip2);
			
			Area area1 = new Area(ellip1);
			Area area2 = new Area(ellip2);
			area1.intersect(area2);
			computeMixedColor(ellip1.getColor(), ellip2.getColor());
			g2.setPaint(mixedColor);
			g2.fill(area1);
		}
	}

	private void computeMixedColor(Color color, Color color2) {
		int r1 = color.getRed();
		int r2 = color2.getRed();
		int g1 = color.getGreen();
		int g2 = color2.getGreen();
		int b1 = color.getBlue();
		int b2 = color2.getBlue();
		this.mixedColor = new Color((r1+r2)/2, (g1+g2)/2, (b1+b2)/2);
	}
	
	/*
	 * we need this one for the final color,
	 * as we mix three colors basically
	 */
	private void computeFinalMixedColor(Color color, Color color2) {
		int r1 = color.getRed();
		int r2 = color2.getRed();
		int g1 = color.getGreen();
		int g2 = color2.getGreen();
		int b1 = color.getBlue();
		int b2 = color2.getBlue();
		this.mixedColor = new Color((int)(r1/1.5+r2/3)/2, (int)(g1/1.5+g2/3)/2, (int)(b1/1.5+b2/3)/2);
	}

	/**
	 * change the color of a circle
	 * @param which 0 means first color, 1 means second
	 * @param color the color to set it to
	 */
	public void setEllipColor(int which, Color color) {
		if(which == 0) {
			ellip1.setColor(color);
		} else if (which == 1) {
			ellip2.setColor(color);
		}
	}
	
	/**
	 * return the mixed color
	 * @return the mixed color
	 */
	public Color getMixedColor() {
		return mixedColor;
	}

	/**
	 * use other colormixing formula if true
	 * @return true if use other colormix formula
	 */
	public boolean isComputeFinalMix() {
		return computeFinalMix;
	}

	/**
	 * set the computeFinalMix value
	 * @param computeFinalMix true if other colormix formula, else false
	 */
	public void setComputeFinalMix(boolean computeFinalMix) {
		this.computeFinalMix = computeFinalMix;
	}
	
	public void stopTimer() {
		if(timer != null) {
			timer.stop();
		}
	}
	
	
}