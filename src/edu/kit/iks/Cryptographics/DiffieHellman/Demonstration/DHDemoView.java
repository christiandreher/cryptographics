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
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;

import org.xnap.commons.i18n.I18n;

import edu.kit.iks.CryptographicsLib.Configuration;
import edu.kit.iks.CryptographicsLib.VisualizationView;

public class DHDemoView extends VisualizationView {
	
	/**
	 * Localization instance
	 */
	private static I18n i18n = Configuration.getInstance().getI18n(DHDemoView.class);
	
	private String explanation1 = i18n.tr("Now to our Diffie-Hellman Key-Exchange Analogy. Here " +
			"is how the protocol in principle works. " +
			"Alice chooses a public color and sends it to Bob. " +
				"As Eve listens to the channel she gets a copy.");
	
	private String explanation2 = i18n.tr("Next, Alice chooses a private color, that she keeps " +
			"for herself, she does not send it. Then she mixes her private color with " +
			"the previously sent public color and sends the mixture to Bob. " +
			"Note that because we use the mixing of colors as a one-way function, " +
			"Eve cannot compute which private color Alice used to get the mixture. " +
			"Keep this in mind, as this is critical to understand why this " +
			"protocol works. " +
			"As previously Bob and Eve have the sent color, that means the mixture of Alice.");
	
	private String explanation3 = i18n.tr("Now Bob does the exact same thing like Alice did " +
			"in the previous step. " +
			"He chooses a private color. Mixes it with the public color, " +
			"and sends the mixture to Alice. Eve gets a copy, but can't compute " +
			"the private color of Bob, because of the one-way function property");
	
	private String explanation4 = i18n.tr("Here we will " +
			"produce the secret that Alice and Bob know, but Eve doesn't. " +
			"Alice will mix her private color to the mixture of Bob " +
			"and Bob will mix his private color to the mixture of Alice. " +
			"The trick lies in the combination of the private Colors, that " +
			"are kept private, through the use of the one-way function and " +
			"that it doesn't matter in which order we mix the colors. So " +
			"mixing first the public color with Alices private color and then Bobs private color " +
			"yields the same color as mixing first the public color with Bobs private color " +
			"and then Alices private color.");
	
	private JLabel aliceExplain;
	
	private JButton skip;
	
	private ColorChannel cc;
	
	private ColorMix cm;
	
	private ActionListener remember;

	private String help = i18n.tr("No help");
	
	private Navigation n;
	
	private static final long serialVersionUID = 87178534093974249L;
	
	public DHDemoView() {
		super();
		GridBagConstraints gbc = new GridBagConstraints();
		GridBagLayout layout = new GridBagLayout();
		this.setLayout(layout);
		

		gbc.weightx = 0.1;
		gbc.weighty = 0.0;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 3;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		n = new Navigation(i18n.tr("Back to Introduction"), i18n.tr("Skip the Demonstration"));
		this.add(n, gbc);
		gbc.fill = GridBagConstraints.NONE;
		gbc.gridwidth = 1;
		
//		
		skip = new JButton(i18n.tr("Skip Introduction"));
//		
//		gbc.gridx = 2;
//		gbc.gridy = 0;
//		gbc.weightx = 0.1;
//		gbc.weighty = 0.1;
//		this.add(skip, gbc);
		
		gbc.gridx = 2;
		gbc.gridy = 3;
		gbc.weightx = 0.1;
		gbc.weighty = 0.1;
		layout.setConstraints(this.getNextButton(), gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 3;
		layout.setConstraints(this.getBackButton(), gbc);
		
		this.aliceExplain = new JLabel();
		this.aliceExplain.setText("<html><div style=\"width:650px; height:150px\">" + explanation1 + "</div></html>");
		gbc.weightx = 0.1;
		gbc.weighty = 0.1;
		gbc.gridx = 1;
		gbc.gridy = 3;
		this.add(aliceExplain, gbc);
		this.cc = new ColorChannel(new Dimension(700, 200), 50);
		
		gbc.weightx = 0.25;
		gbc.weighty = 0.25;
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.fill = GridBagConstraints.BOTH;
		this.add(this.cc, gbc);
		
		this.cc.choosePublicColor(Color.BLUE);
		this.cm = new ColorMix(50, new Dimension(200, 50));
		
		gbc.weightx = 0.1;
		gbc.weighty = 0.1;
		gbc.gridx = 0;
		gbc.gridy = 2;
//		gbc.fill = GridBagConstraints.NONE;
		this.add(this.cm, gbc);
		this.cc.loadView();
		this.cc.setRepeat(false);
		this.cc.setKeepColor(true);
		//TODO remove validate()?
		
		gbc.weightx = 0.1;
		gbc.weighty = 0.1;
		gbc.gridx = 2;
		gbc.gridy = 1;
		gbc.insets = new Insets(100, 0, 0, 0);
		this.add(new LabelExplanation(), gbc);
		this.revalidate();
		
	}

	public ColorChannel getColorChannel() {
		return this.cc;
	}
	
	public void startDemo() {
		this.cc.sendPublicColor(new NextStepCallback() {
			
			@Override
			public void callback() {
				for(ActionListener al : getNextButton().getActionListeners()) {
					getNextButton().removeActionListener(al);
				}
				getNextButton().addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						for(ActionListener al : getNextButton().getActionListeners()) {
							getNextButton().removeActionListener(al);
						}
						secondStep();
					}
				});
			}
		});
	}

	private void secondStep() {
		aliceExplain.setText("<html><div style=\"width:650px; height:150px\">" + explanation2 + "</div></html>");
		cc.chooseAlicePrivateColor(Color.GREEN);
		cc.mixAlicePrivatePublic();
		cm.setEllipColor(0, cc.getPublicColor());
		cm.setEllipColor(1, cc.getAlicePrivateColor());
		cm.mixColors(true, false, new NextStepCallback() {
			
			@Override
			public void callback() {
				for(ActionListener al : getNextButton().getActionListeners()) {
					getNextButton().removeActionListener(al);
				}
				getNextButton().addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						for(ActionListener al : getNextButton().getActionListeners()) {
							getNextButton().removeActionListener(al);
						}
						thirdStep();
					}
				});
			}
		});
	}
	
	private void thirdStep() {
		aliceExplain.setText("<html><div style=\"width:650px; height:150px\">" + explanation2 + "</div></html>");
		cc.sendAliceMixedColorToBob(new NextStepCallback() {
			
			@Override
			public void callback() {
				getNextButton().addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						for(ActionListener al : getNextButton().getActionListeners()) {
							getNextButton().removeActionListener(al);
						}
						fourthStep();
					}
				});
			}
		});
	}
	
	private void fourthStep() {
		aliceExplain.setText("<html><div style=\"width:650px; height:150px\">" + explanation3 + "</div></html>");
		cc.chooseBobPrivateColor(Color.RED);
		cm.setEllipColor(1, cc.getBobPrivateColor());
		cm.mixColors(true, false, new NextStepCallback() {
			
			@Override
			public void callback() {
				getNextButton().addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						for(ActionListener al : getNextButton().getActionListeners()) {
							getNextButton().removeActionListener(al);
						}
						fifthStep();
					}
				});
			}
		});
	}
	
	private void fifthStep() {
		aliceExplain.setText("<html><div style=\"width:650px; height:150px\">" + explanation3 + "</div></html>");
		cc.setColorNextToSend(cm.getMixedColor());
		cc.sendBobMixedColorToAlice(new NextStepCallback() {
			
			@Override
			public void callback() {
				getNextButton().addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						for(ActionListener al : getNextButton().getActionListeners()) {
							getNextButton().removeActionListener(al);
						}
						sixthStep();
					}
				});
			}
		});
	}
	
	private void sixthStep() {
		aliceExplain.setText("<html><div style=\"width:650px; height:150px\">" + explanation4 + "</div></html>");
		getNextButton().addActionListener(remember);
		cc.mixAliceFinalSecret(new NextStepCallback() {
			
			@Override
			public void callback() {
				cc.mixBobFinalSecret(null);
			}
		});
	}

	public void setRemember(ActionListener remember) {
		this.remember = remember;
	}

	public String getHelp() {
		return help ;
	}
	
	public JButton getSkip() {
		return skip;
	}

	public void stopTimers() {
		if(cm != null) {
			cm.stopTimer();
		}
		if(cc != null) {
			cc.stopTimer();
		}
	}

	public JButton getSkipButton() {
		return n.getForward();
	}

	public JButton getReturnButton() {
		return n.getBack();
	}
	
	

}
