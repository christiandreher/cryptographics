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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.Timer;

import org.xnap.commons.i18n.I18n;

import edu.kit.iks.Cryptographics.DiffieHellman.Model;
import edu.kit.iks.CryptographicsLib.Configuration;
import edu.kit.iks.CryptographicsLib.Logger;

/**
 * This is the visual Communication Channel for
 * the Diffie-Hellman Key-Exchange Analogy.
 * In this JPanel we exchange colored circles and compute
 * a shared secret similar like in real DH-KE
 * 
 *  @author kai
 */

public class ColorChannel extends JPanel {

	private static final long serialVersionUID = 4073013433018353584L;

	/**
	 * Localization instance
	 */
	private static I18n i18n = Configuration.getInstance().getI18n(ColorChannel.class);
	
	/**
	 * remembers the colors of alice,bob,eve
	 */
	private Model model;
	
	/**
	 * This views are used for mixing the shared secrets of alice and bob
	 */
	private ColorMix cm1, cm2;
	
	/**
	 * we use this as a container for cm1 and cm2
	 */
	private JPanel container;
	
	/** the coordinates of the circles */
	private int x1, y1, x2, y2;
	
	/** the x coordinates for the communications lines */
	private int leftEnd, rightEnd, middle;
	
	/** the y coordinates for the communications lines 
	 * middleCircle and rightCircle are basically
	 * the same like middle and rightEnd but corrected
	 * by the size of the circles */
	private int lowerEnd, upperEnd, middleCircle, rightCircle;
	
	/**
	 * the original coordinate values
	 */
	private int originalx1, originaly1, originalx2, originaly2;
	
	/** kept colors are drawn to the screen*/
	private ArrayList<Ellipse2DwithLabel> keptColors;
	
	/** those are used for sending over the channel */
	private Ellipse2DwithColor ellip, ellip2;
	
	/** the circles that are displayed next to alice, bob and eve,
	 * need to know how many each one has */
	private int[] numOfCircles;
	
	/** the color to send next */
	private Color colorNextToSend = Color.BLACK;
	
	/** the color of the channel, that means the lines */
	private Color channelColor = Color.BLACK;
	
	/** is this the firstCall of the sendMethod,
	 * first means the first timer event
	 */
	private boolean firstTimerEventAlice, firstTimerEventBob;

	/** repeat the sending of the color
	 * if true
	 */
	private boolean repeatPeriodically;

	/** diameter of the ellipses2d circles */
	private int circleSize = 50;
	
	/** while sending these values will be true */
	private boolean sendAlice, sendBob;
	
	/** when true, all received colors will still
	 * be drawn next to the receivers, so that we
	 * know which person  got which color */
	private boolean keepCircles;
	
	/**
	 * used for firing timer evers to update the coordinates etc.
	 */
	private Timer timer;
	
	/** how quick shall the timer event be fired */
	private int timerInterval = 40;

	/**
	 * Constructor takes the size of JPanel, and
	 * the size of the circles. From there
	 * it computes the position for the communication
	 * channel and plot everything correctly
	 * 
	 * @param d the size of JPanel
	 * @param circleSize the diameter of the circles
	 */
	public ColorChannel(Dimension d, int circleSize) {
		container = new JPanel();
		container.setSize(new Dimension((int)d.getWidth(), (int)d.getHeight()/3));
		container.setPreferredSize(new Dimension((int)d.getWidth(), (int)d.getHeight()/3));
		this.cm1 = new ColorMix(circleSize, new Dimension((int)d.getWidth()/2, (int)d.getHeight()/4));
		this.cm2 = new ColorMix(circleSize, new Dimension((int)d.getWidth()/2, (int)d.getHeight()/4));
		this.setLayout(new BorderLayout());
		container.add(cm1);
		container.add(cm2);
		this.add(container, BorderLayout.SOUTH);
		this.model = new Model();
		this.setSize(d);
		this.setPreferredSize(d);
		this.circleSize = circleSize;
		this.leftEnd = (int) (0.25*this.getWidth());
		this.rightEnd = (int) (0.75*this.getWidth());
		this.lowerEnd = (int) (0.75*this.getHeight());
		this.upperEnd = (int) (0.25*this.getHeight());
		this.rightCircle = rightEnd-circleSize;
		this.originalx1 = leftEnd;
		this.originaly1 = lowerEnd-circleSize/2;
		this.middle = (leftEnd+rightEnd)/2;
		this.middleCircle = this.middle-circleSize/2;
		this.originalx2 = this.middle-circleSize/2;
		this.originaly2 = lowerEnd-circleSize/2;
		this.x1 = originalx1;
		this.y1 = originaly1;
		this.x2 = originalx2;
		this.y2 = originaly2;
		this.firstTimerEventAlice = true;
		this.firstTimerEventBob = true;
		this.numOfCircles = new int[3];
		this.keptColors = new ArrayList<>();
		ellip = new Ellipse2DwithColor(x1, y1, circleSize, circleSize);
		ellip2 = new Ellipse2DwithColor(x2, y2, circleSize, circleSize);
	}
	
	/*
	 * here we'll draw the circles and the communication lines
	 */
	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		super.paintComponent(g);
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setPaint(channelColor);
		
		drawChannel(g2, this.leftEnd, this.rightEnd, this.lowerEnd, this.upperEnd);
		for(Ellipse2DwithColor circle : keptColors) {
			paintCircle(g2, circle);
		}
		if (sendAlice) {
			
			ellip.setFrame(x1, y1, circleSize, circleSize);
			paintCircle(g2, ellip);
			if(x1 < this.middleCircle) {
				ellip2.setFrame(x2, y2, circleSize, circleSize);
				paintCircle(g2, ellip2);
			}
		} else if (sendBob) {
			ellip.setFrame(x1, y1, circleSize, circleSize);
			paintCircle(g2, ellip);
			if(x1 > this.middleCircle) {
				ellip2.setFrame(x2, y2, circleSize, circleSize);
				paintCircle(g2, ellip2);
			}
			
		}
		
	}

	private void paintCircle(Graphics2D g, Ellipse2DwithColor circle) {
		if(circle instanceof Ellipse2DwithLabel) {
//			g.setStroke(new BasicStroke());
			g.setColor(Color.BLACK);
			g.drawString(((Ellipse2DwithLabel) circle).getLabel(), (int) circle.getX(), (int) circle.getY());
		}
		g.setColor(circle.getColor());
		g.fill(circle);
		return;
	}

	/* 
	 * draw the communication channels, that means the lines that
	 * represents those
	 */
	private void drawChannel(Graphics2D g2, int x1, int x2, int x3, int x4) {
		g2.drawLine(x1, x3, x2, x3);
		g2.drawLine((x1+x2)/2, x4, (x1+x2)/2, x3);
		g2.drawString(i18n.tr("Alice"), x1-50, x3);
		g2.drawString(i18n.tr("Bob"), x2+10, x3);
		g2.drawString(i18n.tr("Eve"), (x1+x2)/2 - 15, x4-10);
	}
	
	/**
	 * send a color previously set to Bob
	 * @param cb the step that should be called when this method is finished
	 * @param keepFirst do we want to show the sent color after this method is finished
	 */
	public void sendToBob(final NextStepCallback cb, final boolean keepFirst, final String label) {
		if(sendAlice) {
			/* don't want to send colors
			 * if others are being send
			 */
			return;
		}
		this.sendBob = true;
		this.ellip.setColor(this.colorNextToSend);
		this.ellip2.setColor(this.colorNextToSend);
		this.x1 = this.leftEnd;
		this.x2 = this.middleCircle;
		this.y2 = this.originaly2;
		timer = new Timer(timerInterval, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Logger.debug(this.getClass().getName(),"sendToBob" , "timer event");
				if(firstTimerEventBob && !repeatPeriodically && keepFirst) {
					chooseColorToKeep(colorNextToSend, 0, label);
					firstTimerEventBob = false;
				}
				if(x1 < rightCircle) {
					x1 += 3;
					if (x1 > middleCircle && y2 > upperEnd) {
						y2 -= 3;
					}
				} else {
					sendBob = false;
					firstTimerEventBob = true;
					timer.stop();
					if(keepCircles && !repeatPeriodically) {
						for(int i=1; i < 3; i++) {
							chooseColorToKeep(colorNextToSend, i, label);
						}
					}
					if(cb != null) {
						cb.callback();
					} else if (repeatPeriodically) {
						Logger.debug(this.getClass().getName(), "", "repeat now");
						// set to orignal values, to start all over
						sendBob = true;
						x1 = leftEnd;
						x2 = middleCircle;
						y2 = originaly2;
						timer.restart();
					}
				}
				repaint();
			}
		});
		timer.start();
	}
	
	/**
	 * send a color previously set to Alice
	 * @param cb the step that should be called when this method is finished
	 * @param keepFirst do we want to show the sent color after this method is finished
	 */
	public void sendToAlice(final NextStepCallback cb, final boolean keepFirst, final String label) {
		if(sendBob) {
			/* don't want to send if there
			 * is already colors to be sent
			 */
			return;
		}
		this.sendAlice = true;
		this.ellip.setColor(this.colorNextToSend);
		this.ellip2.setColor(this.colorNextToSend);
		this.x1 = this.rightCircle;
		this.x2 = this.middleCircle;
		this.y2 = this.originaly2;
		timer = new Timer(timerInterval, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Logger.debug(this.getClass().getName(),"sendToAlice" , "timer event");
				if(firstTimerEventAlice && !repeatPeriodically && keepFirst) {
					chooseColorToKeep(colorNextToSend, 1, label);
					firstTimerEventAlice = false;
				}
				if(x1 > leftEnd) {
					x1 -= 3;
					if (x1 < middleCircle && y2 > upperEnd) {
						y2 -= 3;
					}
				} else {
					sendAlice = false;
					firstTimerEventAlice = true;
					timer.stop();
					if(keepCircles) {
						chooseColorToKeep(colorNextToSend, 0, label);
						chooseColorToKeep(colorNextToSend, 2, label);
					}
					if(cb != null) {
						cb.callback();
					} else if (repeatPeriodically) {
						Logger.debug(this.getClass().getName(), "", "repeat now");
						// set to orignal values, to start all over
						sendAlice = true;
						x1 = rightCircle;
						x2 = middleCircle;
						y2 = originaly2;
						timer.restart();
					}
				}
				repaint();
			}
		});
		timer.start();
	}
	
	/*
	 * compute the horizontal position of the next circle to add
	 * to alice/bob/eve
	 */
	private int computeXCoordinate(int numOfKeptColors, int who) {
		switch(who) {
		case 0:
			//alice
			return leftEnd-numOfKeptColors*circleSize;
		case 1:
			//bob
			return rightCircle+numOfKeptColors*circleSize;
		case 2:
			//eve
			return middleCircle+(numOfKeptColors+1)*circleSize;
		}
		//error
		return -1;
	}
	
	/*
	 * copmute the vertical position of the next circle to add
	 * to alice/bob/eve
	 */
	private int computeYCoordinate(int numOfKeptColors, int who) {
		switch(who) {
		case 0:
		case 1:
			//alice
			//bob
			return lowerEnd+circleSize/2;
		case 2:
			//eve
			return upperEnd-circleSize/2;
		}
		//error
		return -1;
	}
	
	/**
	 * add a kept circle to alice/bob/eve
	 * @param color the color of the circle to keep
	 * @param who alice, bob or eve
	 */
	public void chooseColorToKeep(Color color, int who, String label) {
		this.keptColors.add(new Ellipse2DwithLabel(computeXCoordinate(numOfCircles[who], who), computeYCoordinate(numOfCircles[who], who), circleSize, circleSize, color, label));
		this.numOfCircles[who]++;
		repaint();
	}

	/**
	 * we need to stop the timer when we unload the view,
	 * since garbage collector won't do that
	 * for us
	 */
	public void stopTimer() {
		if(timer != null) {
			timer.stop();
		}
	}

	/**
	 * basically does the same like in the constructor
	 * used if size of jpanel was changed
	 */
	public void loadView() {
		this.leftEnd = (int) (0.25*this.getWidth());
		this.rightEnd = (int) (0.75*this.getWidth());
		this.lowerEnd = (int) (0.75*this.getHeight());
		this.upperEnd = (int) (0.25*this.getHeight());
		this.rightCircle = rightEnd-circleSize;
		this.originalx1 = leftEnd;
		this.originaly1 = lowerEnd-circleSize/2;
		this.middle = (leftEnd+rightEnd)/2;
		this.middleCircle = this.middle-circleSize/2;
		this.originalx2 = this.middle-circleSize/2;
		this.originaly2 = lowerEnd-circleSize/2;
		this.x1 = originalx1;
		this.y1 = originaly1;
		this.x2 = originalx2;
		this.y2 = originaly2;
		ellip.setFrame(x1, y1, circleSize, circleSize);
		ellip2.setFrame(x2, y2, circleSize, circleSize);
	}
	
	/**
	 * the public color, this is the color
	 * send when sendPublicColor is called
	 * @param color the color we'll set the public to
	 */
	public void choosePublicColor(Color color) {
		this.model.setPublicColor(color);
	}
	
	/**
	 * chooses alices private color, and adds
	 * it to the kept colors
	 * @param color alices new private color
	 */
	public void chooseAlicePrivateColor(Color color) {
		this.model.setAlicePrivateColor(color);
		this.chooseColorToKeep(color, 0, "SA");
	}
	
	/**
	 * chooses bobs private color, and adds
	 * it to the kept colors
	 * @param color bobs new private color
	 */
	public void chooseBobPrivateColor(Color color) {
		this.model.setBobPrivateColor(color);
		this.chooseColorToKeep(color, 1, "SB");
	}
	
	/**
	 * compute alices mixed color
	 */
	public void mixAlicePrivatePublic() {
		this.model.mixAlicePrivateAndPublic();
	}
	
	/**
	 * compute bobs mixed color
	 */
	public void mixBobPrivatePublic() {
		this.model.mixBobPrivateAndPublic();
	}
	
	/**
	 * send the public color to bob
	 * @param cb our callback to call, when finished
	 */
	public void sendPublicColor(NextStepCallback cb) {
		this.setColorNextToSend(this.model.getPublicColor());
		this.sendToBob(cb, true, "P");
	}
	
	/**
	 * send alice mixed color to bob
	 * @param cb our callback to call, when finished
	 */
	public void sendAliceMixedColorToBob(NextStepCallback cb) {
		this.model.mixAlicePrivateAndPublic();
		this.setColorNextToSend(this.model.getAliceMixedColor());
		this.sendToBob(cb, true, "MA");
	}
	
	/**
	 * send bob mixed color to alice
	 * @param cb our callback to call, when finished
	 */
	public void sendBobMixedColorToAlice(NextStepCallback cb) {
		this.model.mixBobPrivateAndPublic();
		this.setColorNextToSend(this.model.getBobMixedColor());
		this.sendToAlice(cb, true, "MB");
	}
	
	/**
	 * mix alice final secret
	 * @param cb our callback to call, when finished
	 */
	public void mixAliceFinalSecret(NextStepCallback cb) {
		assert(model.getBobMixedColor() != null);
		assert(model.getAlicePrivateColor() != null);
		this.cm1.setComputeFinalMix(true);
		this.cm1.setEllipColor(0, model.getBobMixedColor());
		this.cm1.setEllipColor(1, model.getAlicePrivateColor());
		this.cm1.mixColors(true, false, cb);
	}
	
	/**
	 * mix bob final secret
	 * @param cb our callback to call, when finished
	 */
	public void mixBobFinalSecret(NextStepCallback cb) {
		assert(model.getAliceMixedColor() != null);
		assert(model.getBobPrivateColor() != null);
		this.cm2.setComputeFinalMix(true);
		this.cm2.setEllipColor(0, model.getAliceMixedColor());
		this.cm2.setEllipColor(1, model.getBobPrivateColor());
		this.cm2.mixColors(true, false, cb);
	}
	
	/**
	 * returns true, if sending should
	 * be priodically repeated
	 * @return true if repeat, else false
	 */
	public boolean isRepeat() {
		return repeatPeriodically;
	}

	/**
	 * set the repeat value
	 * @param repeat or not
	 */
	public void setRepeat(boolean repeat) {
		this.repeatPeriodically = repeat;
	}
	
	/**
	 * get the next color to send
	 * @return the color next to send
	 */
	public Color getColor() {
		return colorNextToSend;
	}

	/**
	 * let the user of this class
	 * choose the color to send
	 * @param 
	 */
	public void setColorNextToSend(Color color) {
		this.colorNextToSend = color;
	}
	
	/**
	 * if true the send colors are
	 * kept at the receivers
	 * @return true if we keep colors, else false
	 */
	public boolean isKeepColor() {
		return keepCircles;
	}

	/**
	 * set if we should keep the
	 * next sent colors
	 * @param keep color yes or no
	 */
	public void setKeepColor(boolean keepColor) {
		this.keepCircles = keepColor;
	}

	/**
	 * get the public color
	 * @return the public color
	 */
	public Color getPublicColor() {
		return this.model.getPublicColor();
	}

	/**
	 * get alices private color
	 * @return alices private color
	 */
	public Color getAlicePrivateColor() {
		return this.model.getAlicePrivateColor();
	}
	
	/**
	 * get alices mixed color
	 * @return alices mixed color
	 */
	public Color getAliceMixedColor() {
		return this.model.getAliceMixedColor();
	}
	
	/**
	 * get bobs private color
	 * @return bobs private color
	 */
	public Color getBobPrivateColor() {
		return this.model.getBobPrivateColor();
	}

	/**
	 * get bobs mixed color
	 * @return bobs mixed color if it was computed
	 */
	public Color getBobMixedColor() {
		return this.model.getBobMixedColor();
	}
	
	
}
