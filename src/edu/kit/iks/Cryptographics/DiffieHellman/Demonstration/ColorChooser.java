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
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * This JPanel allows us to choose a color
 * 
 * @author kai
 *
 */
public class ColorChooser extends JPanel {
	
	private static final long serialVersionUID = -7179916629639929417L;

	/* will use/display next color if clicked */
	private JButton next;
	
	/* will use/display previous color if clicked */
	private JButton prev;
	
	/* a JPanel where the color is drawn */
	private DrawPanel dp;
	
	/* the index of the current color */
	private int index;
	
	/* the ellipses with color
	 * that visualizes the current color
	 */
	private Ellipse2DwithColor current;
	
	/* all possible colors to choose from */
	private Color[] toChooseFrom;
	
	private boolean hide;
	
	/**
	 * Will setup the object, and layout correctly
	 * @param d the size of the colorchooser
	 * @param color the default color
	 * @param colors all possible colors to choose from
	 */
	public ColorChooser(Dimension d, Color color, Color[] colors) {
		this.toChooseFrom = colors;
		this.setLayout(new FlowLayout());
		this.next = new JButton("->");
		this.prev = new JButton("<-");
		this.current = new Ellipse2DwithColor(0, 0, d.getHeight(), d.getWidth(), color);
		this.dp = new DrawPanel(d);
		this.next.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				index = (index + 1) % toChooseFrom.length;
				current.setColor(toChooseFrom[index]);
				repaint();
			}
		});
		this.prev.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				index = index > 0 ? (index - 1) % toChooseFrom.length : (toChooseFrom.length -1);
				current.setColor(toChooseFrom[index]);
				repaint();
			}
		});
		this.add(prev);
		this.add(dp);
		this.add(next);
		
		validate();
	}

	/*
	 * Here we draw the colorcircle
	 */
	private class DrawPanel extends JPanel {
		
		private static final long serialVersionUID = -3771892570139056170L;

		public DrawPanel(Dimension d) {
			this.setSize(d);
			this.setPreferredSize(d);
		}
		
		
		@Override
		protected void paintComponent(Graphics g) {
			Graphics2D g2 = (Graphics2D) g;
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2.setPaint(current.getColor());
			g2.fill(current);
		}
		
	}
	
	/**
	 * return the currently displayed color
	 * @return the current color
	 */
	public Color getCurrentColor() {
		return this.current.getColor();
	}
	
	/** 
	 * change the possible colors
	 * @param colors the possible colors to choose from
	 */
	public void setToChooseFrom(Color[] colors) {
		this.index = 0;
		this.toChooseFrom = colors;
		this.current.setColor(colors[0]);
		repaint();
	}

	/** 
	 * get the possible colors
	 * @return the colors to choose from
	 */
	public Color[] getToChooseFrom() {
		return toChooseFrom;
	}
	
	@Override
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		if(hide) {
		} else {
			super.paint(g2);
		}
		return;
	}

	public void setHide(boolean hide) {
		this.hide = hide;
		this.repaint();
	}
	
	public boolean getHide() {
		return this.hide;
	}
}
