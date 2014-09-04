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

package edu.kit.iks.Cryptographics.DiffieHellman.Experiment;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.xnap.commons.i18n.I18n;

import edu.kit.iks.CryptographicsLib.Configuration;
import edu.kit.iks.Cryptographics.DiffieHellman.Demonstration.ColorChannel;
import edu.kit.iks.Cryptographics.DiffieHellman.Demonstration.ColorChooser;
import edu.kit.iks.Cryptographics.DiffieHellman.Demonstration.ColorMix;
import edu.kit.iks.Cryptographics.DiffieHellman.Demonstration.LabelExplanation;
import edu.kit.iks.Cryptographics.DiffieHellman.Demonstration.Navigation;
import edu.kit.iks.Cryptographics.DiffieHellman.Demonstration.NextStepCallback;

public class DHExperimentView extends JPanel {

	/**
	 * Localization instance
	 */
	private static I18n i18n = Configuration.getInstance().getI18n(DHExperimentView.class);
	
	private static final long serialVersionUID = 5764374133753732451L;
	
	private String explanation1 = i18n.tr("Now it is your turn! Choose a public color, which " +
			"will be the basis for the coming operations. When you're are done, " +
			"click the 'send color' button to send it to Bob.");
	
	private String explanation2 = i18n.tr("Now you can choose a private color which you can mix with the " +
			"public color you previously send to Bob. When you're done, click " +
			"the 'Mix with public color' button.");
	
	private String explanation3 = i18n.tr("The next step is to send the right color to Bob, as " +
			"shown in the demonstration. You have three possible colors to send and " +
			"one is the right color. If you need help click the 'Help' button.");
	
	private String wrongPrivate = i18n.tr("Oh no. You chose the private color. This color " +
			"is meant to be private. If you had sent this color, Eve would be able " +
			"to get the secret too, afterwards. " +
			"Please try again, by choosing another color and clicking " +
			"the 'send color' button.");
	
	private String wrongPublic = i18n.tr("Oh no. You chose the public color. While Eve " +
			"won't be able to get the secret shared color, you still didn't follow " +
			"the protocol instructions. Try again by choosing another color.");
	
	private String rightColor = i18n.tr("Good. You sent your mixture to Bob. This is " +
			"the right choice.");
	
	private String bobsTurn = i18n.tr("Now it is Bobs turn. Click the continue button, so that " +
			"Bob will choose his private color and send you his mixture.");
	
	private String finalSecret = i18n.tr("This is the last step in the protocol. " +
			"Choose the right colors to produce the final secret, just like " +
			"how it was explained at the demonstration.");
	
	private String congrats = i18n.tr("Congratulations, you chose the right colors to mix, " +
			"you and Bob established a shared secret that Eve does not know.");
	
	/*
	 * the current help String
	 */
	private String help;
	
	private String help1 = i18n.tr("You have to choose a public color, " +
			"to choose a different color you have to click the '->' or " +
			"'<-' Button. You can't do much wrong. When you are ready " +
			"click the 'send color' button.");
	
	private String help2 = i18n.tr("You have to choose a private color, which " +
			"is not the same as the public color. To choose a color, " +
			"you have to click the '->' or the '<-' Button. You can't do " +
			"much wrong. When you are ready, click the 'mix colors' button.");
	
	private String help3 = i18n.tr("You have to send the right color, the right color is the " +
			"one you have not already sent, and the one you don't want to keep private. " +
			"If you still have no clue, try the three different colors.");
	
	private String help4 = i18n.tr("Just click the continue button and Bob will mix his color " +
			"and will send it to you.");
	
	private String help5 = i18n.tr("You have to choose the two colors that will be mixed to " +
			"the final shared secret. Tip: Why can't Eve compute the secret. Right, " +
			"you have a color that Eve does not have, so you have to use that one. It is the color " +
			"with the label SA. The other color is the one you got from Bob which is the mixture of Bob, " +
			"MB");
	
	private String mix = i18n.tr("Mix with public color");
	
	private String mixFinal = i18n.tr("Mix colors to final secret");
	
	private String contin = i18n.tr("Continue");
	
	private String notSame = i18n.tr("The private color can't be the same" +
			" as the public color");
	
	private String send = i18n.tr("Send color");
	
	private String wrongColor = i18n.tr("You chose the wrong colors, try again");
	
	private JLabel explainLbl;
	
	private JButton multiBtn;

	private ColorChannel cc;

	private ColorMix cm;
	
	private ColorChooser chooser, chooser2;
	
	private GridBagConstraints gbc;
	
	private Color[] toChooseFrom = {Color.BLUE, Color.GREEN, Color.YELLOW,
			Color.RED,
	};
	
	private Color[] rememberColors = toChooseFrom;

	private ActionListener remember;
	
	private Navigation n;

	
	/**
	 * Build the used objects and lay them out
	 */
	public DHExperimentView() {
		super();
		help = help1; //the first help text
		gbc = new GridBagConstraints();
		GridBagLayout layout = new GridBagLayout();
		this.setLayout(layout);
		
		gbc.weightx = 0.1;
		gbc.weighty = 0.0;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 3;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		n = new Navigation(i18n.tr("Back to Demonstration"), i18n.tr("Skip the Experiment"));
		this.add(n, gbc);
		gbc.fill = GridBagConstraints.NONE;
		gbc.gridwidth = 1;
		
		this.explainLbl = new JLabel();
		this.explainLbl.setText("<html><div style=\"width:300px; height:100px\">" + explanation1 + "</div></html>");
		gbc.weightx = 0.1;
		gbc.weighty = 0.1;
		gbc.gridx = 1;
		gbc.gridy = 3;
		this.add(explainLbl, gbc);
		this.cc = new ColorChannel(new Dimension(700, 200), 50);
		
		gbc.weightx = 0.2;
		gbc.weighty = 0.2;
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.fill = GridBagConstraints.BOTH;
		this.add(this.cc, gbc);
		
		this.cm = new ColorMix(50, new Dimension(200, 200));
		
		gbc.weightx = 0.1;
		gbc.weighty = 0.1;
		gbc.gridx = 0;
		gbc.gridy = 1;
		this.add(this.cm, gbc);
		
		this.chooser = new ColorChooser(new Dimension(50, 50), Color.BLUE, toChooseFrom);
		
		gbc.weightx = 0.1;
		gbc.weighty = 0.1;
		gbc.gridx = 0;
		gbc.gridy = 2;
		this.add(this.chooser, gbc);
		
		this.multiBtn = new JButton("Send Color");
		this.multiBtn.setPreferredSize(new Dimension(250, 40));
		
		gbc.weightx = 0.1;
		gbc.weighty = 0.1;
		gbc.gridx = 2;
		gbc.gridy = 3;
		gbc.fill = GridBagConstraints.NONE;
		this.add(this.multiBtn, gbc);
		this.multiBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				for(ActionListener al : multiBtn.getActionListeners()) {
					multiBtn.removeActionListener(al);
				}
				sendPublicColor();
			}
		});
		
		gbc.weightx = 0.1;
		gbc.weighty = 0.1;
		gbc.gridx = 2;
		gbc.gridy = 2;
		this.add(new LabelExplanation(), gbc);
		validate();
	}
	
	private void sendPublicColor() {
		this.cc.setRepeat(false);
		this.cc.setKeepColor(true);
		this.cc.choosePublicColor(this.chooser.getCurrentColor());
		this.cc.sendPublicColor(new NextStepCallback() {
			
			@Override
			public void callback() {
				help = help2;
				explainLbl.setText("<html><div style=\"width:300px; height:100px\">" + explanation2 + "</div></html>");
				multiBtn.setText(mix);
				multiBtn.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						if(!chooser.getCurrentColor().equals(cc.getPublicColor())) {
							for(ActionListener al : multiBtn.getActionListeners()) {
								multiBtn.removeActionListener(al);
							}
							mixPrivateColorStep();
							
						} else {
							explainLbl.setText("<html><div style=\"width:300px; height:100px\">" + notSame + "</div></html>");
						}
					}
				});
			}
		});
	}

	private void mixPrivateColorStep() {
		cc.chooseAlicePrivateColor(chooser.getCurrentColor());
		cc.mixAlicePrivatePublic();
		cm.setEllipColor(0, cc.getPublicColor());
		cm.setEllipColor(1, cc.getAlicePrivateColor());
		cm.setComputeFinalMix(false);
		cm.mixColors(true, false, new NextStepCallback() {
			
			@Override
			public void callback() {
				help = help3;
				chooser.setToChooseFrom(new Color[]{cc.getPublicColor(), cc.getAlicePrivateColor(),
						cm.getMixedColor()
				});
				toChooseFrom = chooser.getToChooseFrom();
				explainLbl.setText("<html><div style=\"width:300px; height:100px\">" + explanation3 + "</div></html>");
				multiBtn.setText(send);
				multiBtn.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						sendMixedColor();						
					}
				});
			}
		});
	}
	
	private void sendMixedColor() {
		if(chooser.getCurrentColor().equals(cc.getAliceMixedColor())) {
			for(ActionListener al : multiBtn.getActionListeners()) {
				multiBtn.removeActionListener(al);
			}
			chooser.setHide(true);
			revalidate();
			explainLbl.setText("<html><div style=\"width:300px; height:100px\">" + rightColor + "</div></html>");
			cc.sendAliceMixedColorToBob(new NextStepCallback() {
				
				@Override
				public void callback() {
					help = help4;
					explainLbl.setText("<html><div style=\"width:300px; height:100px\">" + bobsTurn + "</div></html>");
					multiBtn.setText(contin);
					multiBtn.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent arg0) {
							for(ActionListener al : multiBtn.getActionListeners()) {
								multiBtn.removeActionListener(al);
							}
							sendBobMixedColor();
						}
					});
				}
			});
		} else {
			if(chooser.getCurrentColor().equals(cc.getAlicePrivateColor())) {
				explainLbl.setText("<html><div style=\"width:300px; height:100px\">" + wrongPrivate + "</div></html>");
			} else {
				explainLbl.setText("<html><div style=\"width:300px; height:100px\">" + wrongPublic + "</div></html>");
			}
		}
	}
	
	private void sendBobMixedColor() {
		cc.chooseBobPrivateColor(randomColor());
		cm.setEllipColor(1, cc.getBobPrivateColor());
		cm.mixColors(true, false, new NextStepCallback() {
			
			@Override
			public void callback() {
				cc.sendBobMixedColorToAlice(new NextStepCallback() {
					
					@Override
					public void callback() {
						help = help5;
						Color[] param = new Color[]{cc.getPublicColor(),
								cc.getAlicePrivateColor(), cc.getAliceMixedColor(),
								cc.getBobMixedColor()};
						chooser2 = new ColorChooser(new Dimension(50, 50), param[0], param);
						gbc.gridx = 0;
						gbc.gridy = 3;
						add(chooser2, gbc);
						chooser.setToChooseFrom(param);
						explainLbl.setText("<html><div style=\"width:300px; height:100px\">" + finalSecret + "</div></html>");
						multiBtn.setText(mixFinal);
						validate();
						chooser.setHide(false);
						revalidate();
						multiBtn.addActionListener(new ActionListener() {
							
							@Override
							public void actionPerformed(ActionEvent e) {
								mixFinalColors();
							}
						});
					}
				});
			}
		});
	}
	
	private void mixFinalColors() {
		if((chooser.getCurrentColor().equals(cc.getAlicePrivateColor())
				&& chooser2.getCurrentColor().equals(cc.getBobMixedColor()))
				|| (chooser.getCurrentColor().equals(cc.getBobMixedColor())
						&& chooser2.getCurrentColor().equals(cc.getAlicePrivateColor()))) {
			//chose the right colors
			for(ActionListener al : multiBtn.getActionListeners()) {
				multiBtn.removeActionListener(al);
			}
			explainLbl.setText("<html><div style=\"width:300px; height:100px\">" + congrats + "</div></html>");
			cc.mixAliceFinalSecret(new NextStepCallback() {
				
				@Override
				public void callback() {
					cc.mixBobFinalSecret(new NextStepCallback() {
						
						@Override
						public void callback() {
							multiBtn.setText(contin);
							chooser.setHide(true);
							chooser2.setHide(true);
							revalidate();
							multiBtn.addActionListener(remember);
						}
					});
				}
			});
		} else {
			explainLbl.setText("<html><div style=\"width:300px; height:100px\">" +
					wrongColor + "</div></html>");
			
			validate();
		}
	}
		
	/*
	 * randomColor from remember - toChooseFrom
	 */
	private Color randomColor() {
		Color randColor = rememberColors[randInt(0, rememberColors.length-1)];
		while(in(randColor, toChooseFrom)) {
			randColor = rememberColors[randInt(0, rememberColors.length-1)];
		}
		return randColor;
	}
	
	private boolean in(Color rand, Color[] colors) {
		for(int i=0; i < colors.length; i++) {
			if(rand.equals(colors[i])) {
				return true;
			}
		}
		return false;
	}
	
	private int randInt(int min, int max) {
		Random rand = new Random();
		
		int randNum = rand.nextInt((max-min) + 1) + min;
		
		return randNum;
	}
	
	/**
	 * remember an actionlistener
	 * @param remember remember this actionlistener
	 */
	public void setRemember(ActionListener remember) {
		this.remember = remember;
	}

	/** return help string */
	public String getHelp() {
		return help;
	}
	
	public JButton getSkipButton() {
		return n.getForward();
	}

	public JButton getReturnButton() {
		return n.getBack();
	}
	
	
}
