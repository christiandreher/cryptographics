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

package edu.kit.iks.Cryptographics.Caesar.Demonstration;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.xnap.commons.i18n.I18n;

import edu.kit.iks.CryptographicsLib.Configuration;
import edu.kit.iks.CryptographicsLib.ImageView;
import edu.kit.iks.CryptographicsLib.VisualizationView;

/**
 * This is the first view the user gets presented when he requests the
 * visualization of Caesar's cipher. Here the user gets to know what problems Caesar faced when his
 * enemies could intercept and read his orders.
 * 
 * @author Wasilij Beskorovajnov.
 * 
 */
public class IntroductionView extends VisualizationView {

	/**
	 * Localization instance
	 */
	private static I18n i18n = Configuration.getInstance().getI18n(
			IntroductionView.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Container for the animations.
	 */
	private JPanel animationContainer;

	private ImageView caesarEvil;
	private ImageView caesarIdeaImg;
	private ImageView kryptolix;
	private ImageView orders;
	private ImageView caesarImg;
	private ImageView courier;

	/**
	 * JLabel that holds text which is needed for explaining what now happens, will happen or
	 * happened.
	 */
	private JLabel explanation;
	private GridBagLayout introLayout;
	/**
	 * Button needed for proceeding the stepwise animations.
	 */
	private JButton proceed;

	/**
	 * Constructor of this View.
	 */
	public IntroductionView() {
		super();

		this.introLayout = new GridBagLayout();
		GridBagConstraints nextConstraint = new GridBagConstraints();
		this.setLayout(this.introLayout);

		// set the container for the animations.
		this.animationContainer = new JPanel(new GridBagLayout());
		GridBagConstraints animationConstraints = new GridBagConstraints();
		animationConstraints.gridx = 0;
		animationConstraints.gridy = 0;
		animationConstraints.gridwidth = 10;
		animationConstraints.fill = GridBagConstraints.BOTH;
		this.add(this.animationContainer, animationConstraints);

		// no need of BackButton. Button for returning to start screen already
		// the "Exit" button.
		this.getBackButton().setVisible(false);
		this.getNextButton().setText(
				IntroductionView.i18n.tr("Skip the introduction"));
		this.getNextButton().setPreferredSize(new Dimension(350, 50));

		// set the alignment of NextButton.
		nextConstraint.anchor = GridBagConstraints.NORTHEAST;
		nextConstraint.weightx = 1.0;
		nextConstraint.weighty = 0.1;
		nextConstraint.gridx = 1;
		nextConstraint.gridy = 0;
		this.introLayout.setConstraints(this.getNextButton(), nextConstraint);

		// set the alignment of the proceed Button, that is needed to proceed in
		// animation.
		GridBagConstraints proceedConstraint = new GridBagConstraints();
		// proceedConstraint.anchor = GridBagConstraints.FIRST_LINE_START;
		proceedConstraint.weightx = 1.0;
		proceedConstraint.weighty = 0.1;
		proceedConstraint.gridx = 1;
		proceedConstraint.gridy = 2;
		proceedConstraint.gridwidth = 3;
		this.setProceed(new JButton(IntroductionView.i18n.tr("Tell me more")));
		this.proceed.setPreferredSize(new Dimension(500, 50));
		this.add(this.proceed, proceedConstraint);

		// set the alignment of the Explanations.
		GridBagConstraints explanationConstraint = new GridBagConstraints();
		explanationConstraint.gridx = 1;
		explanationConstraint.gridy = 1;
		explanationConstraint.gridwidth = 5;
		this.setExplanation(new JLabel(
				"<html><div style=\"width:600px;\">"
						+ IntroductionView.i18n
								.tr("One fine day, ca 70 B.C., Caesar was puzzling on an extremely intelligent plan "
										+ "to finally conquer Gallia and was about to send it to his generals.") + "</div></html>"

		));
		this.explanation.setPreferredSize(new Dimension(1000, 75));
		this.add(this.explanation, explanationConstraint);

		// layout the component of the Panel.
		this.validate();

	}
	
	
	//----------------------------------------------------------//
	//------------------------Getter/Setter---------------------//

	/**
	 * @return the animationContainer
	 */
	public JPanel getAnimationContainer() {
		return this.animationContainer;
	}

	/**
	 * @return the caesarEvil
	 */
	public ImageView getCaesarEvil() {
		return this.caesarEvil;
	}

	/**
	 * @return the masterPlan
	 */
	public ImageView getCaesarIdeaImg() {
		return this.caesarIdeaImg;
	}

	/**
	 * @return the caesar
	 */
	public ImageView getCaesarImg() {
		return this.caesarImg;
	}

	/**
	 * @return the courier
	 */
	public ImageView getCourier() {
		return this.courier;
	}

	/**
	 * @return the explanation
	 */
	public JLabel getExplanation() {
		return this.explanation;
	}

	/**
	 * @return the introLayout
	 */
	public GridBagLayout getIntroLayout() {
		return this.introLayout;
	}

	/**
	 * @return the interceptor
	 */
	public ImageView getKryptolix() {
		return this.kryptolix;
	}

	/**
	 * @return the orders
	 */
	public ImageView getOrders() {
		return this.orders;
	}

	/**
	 * @return the proceed
	 */
	public JButton getProceed() {
		return this.proceed;
	}

	/**
	 * @param animationContainer
	 *            the animationContainer to set
	 */
	public void setAnimationContainer(JPanel animationContainer) {
		this.animationContainer = animationContainer;
	}

	/**
	 * @param caesarEvil
	 *            the caesarEvil to set
	 */
	public void setCaesarEvil(ImageView caesarEvil) {
		this.caesarEvil = caesarEvil;
	}

	/**
	 * @param caesarIdaImg
	 *            the masterPlan to set
	 */
	public void setCaesarIdeaImg(ImageView caesarIdaImg) {
		this.caesarIdeaImg = caesarIdaImg;
	}

	/**
	 * @param caesar
	 *            the caesar to set
	 */
	public void setCaesarImg(ImageView caesarImg) {
		this.caesarImg = caesarImg;
	}

	/**
	 * @param courier
	 *            the courier to set
	 */
	public void setCourier(ImageView courier) {
		this.courier = courier;
	}

	/**
	 * @param jTextField
	 *            the explanation to set
	 */
	public void setExplanation(JLabel explanation) {
		this.explanation = explanation;
	}

	/**
	 * @param interceptor
	 *            the interceptor to set
	 */
	public void setKryptolix(ImageView interceptor) {
		this.kryptolix = interceptor;
	}

	/**
	 * @param orders
	 *            the orders to set
	 */
	public void setOrders(ImageView orders) {
		this.orders = orders;
	}

	/**
	 * @param proceed
	 *            the proceed to set
	 */
	public void setProceed(JButton proceed) {
		this.proceed = proceed;
	}

}
