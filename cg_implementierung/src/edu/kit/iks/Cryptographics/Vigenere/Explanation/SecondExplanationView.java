package edu.kit.iks.Cryptographics.Vigenere.Explanation;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.xnap.commons.i18n.I18n;

import edu.kit.iks.Cryptographics.Vigenere.VigenereModel;
import edu.kit.iks.CryptographicsLib.CharacterFrequencyDiagramView;
import edu.kit.iks.CryptographicsLib.Configuration;
import edu.kit.iks.CryptographicsLib.KeyboardView;
import edu.kit.iks.CryptographicsLib.VisualizationView;

/**
 * This class represents the view of the first explanatation step. In this step a cipher will be given
 * to the user. He has the choice to create a histogramm and use an algorithm to get the length of 
 * the key. Then he has to extract the password. 
 * @author Aydin Tekin
 */
public class SecondExplanationView extends VisualizationView{
	private static final long serialVersionUID = 6294968461280032987L;
	
	/**
	 * Localization instance
	 */
	private static I18n i18n = Configuration.getInstance().getI18n(SecondExplanationView.class);
	
	/**
	 * histogramm of encrypted text
	 */
	private CharacterFrequencyDiagramView vigenereHistogramm;
	
	/**
	 * histogramm of average text
	 */
	private CharacterFrequencyDiagramView averageHistogramm;
	
	/**
	 * explanation
	 */
	private JLabel explanation;
	
	/**
	 * explanation
	 */
	private JLabel secondExplanation;
	
	/**
	 * explanation
	 */
	private JLabel thirdExplanation;
	
	/**
	 * error message
	 */
	private JLabel wrong;
	
	/**
	 * textfield for answer
	 */
	private JTextField answer;
	
	/**
	 * keyboard used for user input
	 */
	private KeyboardView keyboard;
	
	/**
	 * creates the keyboard
	 * @param input listener for the keyboard
	 * @param flag which type of keyboard we want
	 */
	public void createKeyboard(JTextField input, final int flag) {
		this.keyboard = new KeyboardView(input, flag);
		this.add(this.keyboard);
		Dimension size = this.keyboard.getPreferredSize();
		this.keyboard.setBounds(180, 420, size.width, size.height);
		this.validate();
	}
	
	/**
	 * changes the explanation text
	 * @param explanation text to change to
	 */
	public void setExplanation(String s){
		this.explanation.setText(s);
		Dimension size = this.explanation.getPreferredSize();
		this.explanation.setBounds(10, 10,
	             size.width, size.height);
		this.validate();
	}
	
	/**
	 * @return the keyboard
	 */
	public KeyboardView getKeyboard() {
		return this.keyboard;
	}
	
	/**
	 * @param keyboard
	 *            the keyboard to set
	 */
	public void setKeyboard(KeyboardView keyboard) {
		this.keyboard = keyboard;
	}
	
	/**
	 * returns the answer field
	 * @return answer field
	 */
	public JTextField getAnswerField() {
		return this.answer;
	}
	
	/**
	 * changes GUI if answer is right
	 */
	public void answerRight() {
		this.answer.setBorder(BorderFactory.createLineBorder(Color.blue, 5));
		this.wrong.setVisible(false);
	}
	
	/**
	 * changes GUI if answer is true
	 */
	public void answerFalse() {
		this.answer.setBorder(BorderFactory.createLineBorder(Color.red, 5));
		this.wrong.setVisible(true);
		this.answer.setText("");
	}
	
	/**
	 * returns the user input
	 * @return user input
	 */
	public String getAnswer() {
		return this.answer.getText();
	}
	
	/**
	 * changes the visibility according to first step
	 * @param b visibility according to first step
	 */
	public void visibleFirstState(boolean b) {
		this.vigenereHistogramm.setVisible(b);
		this.secondExplanation.setVisible(b);
		this.averageHistogramm.setVisible(b);
		this.thirdExplanation.setVisible(b);
		this.answer.setVisible(b);
	}
	
	/**
	 * creates and adds all GUI elements
	 */
	private void setupGUI() {
		this.setLayout(null);
		this.add(new JLabel("VIGENERE EXPLANATATION"));
		this.add(this.explanation = new JLabel("<html><div width=\"1200\">"
				+ i18n.tr("Now its your turn! You have to find the second character of the "
				+ "key. I was kind enough to give you a diagramm of every second "
				+ "character encrypted with the second part of the key."
				+ "You know what to do:")
				+ "</div></html>"));
		this.add(this.secondExplanation = new JLabel("<html><div width=\"1200\">"
				+ i18n.tr("This is the average distribution of the characters in english texts:")
				+ "</div></html>"));
		this.add(this.thirdExplanation = new JLabel("<html><div width=\"1200\">"
				+ i18n.tr("Look at the peaks of both histogramm and calculate the second part "
				+ "of the key! Your answer:")
				+ "</div></html>"));
		this.add(this.answer = new JTextField(""));
		this.add(this.wrong = new JLabel(i18n.tr("Wrong Answer! Try again!")));
		this.vigenereHistogramm = new CharacterFrequencyDiagramView(VigenereModel.getCharPositionated(1, 2, FirstExplanationView.encryptedAverageText), 600,
				100);
		this.add(this.vigenereHistogramm);
		
		this.averageHistogramm = new CharacterFrequencyDiagramView(FirstExplanationView.averageText, 600,
				100);
		this.add(this.averageHistogramm);
	}
	
	/**
	 * sets fonts to all elements
	 */
	private void setFonts() {
		this.explanation.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
		this.secondExplanation.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
		this.thirdExplanation.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
		this.answer.setFont(new Font("Comic Sans MS", Font.BOLD, 28));
		this.wrong.setFont(new Font("Comic Sans MS", Font.BOLD, 28));
	}
	
	/**
	 * customizes the created GUI elements
	 */
	private void customizeGUI() {
		Dimension size = this.explanation.getPreferredSize();
		this.explanation.setBounds(10, 10,
	             size.width, size.height);
		
		size = this.secondExplanation.getPreferredSize();
		this.secondExplanation.setBounds(10, 190,
	             size.width, size.height);
		
		size = this.thirdExplanation.getPreferredSize();
		this.thirdExplanation.setBounds(10, 330,
	             size.width, size.height);
		
		this.answer.setBounds(350, 370,
	             40, 40);
		this.answer.setBorder(BorderFactory.createLineBorder(Color.blue, 5));
		
		size = this.wrong.getPreferredSize();
		this.wrong.setBounds(300, 460,
				size.width, size.height);
		this.wrong.setVisible(false);
		
		size = this.getBackButton().getPreferredSize();
		this.getBackButton().setBounds(30, 600,
	             size.width, size.height);
		
		size = this.getNextButton().getPreferredSize();
		this.getNextButton().setBounds(1100, 600,
	             size.width, size.height);
		
		this.vigenereHistogramm.setBounds(10, 80,
	             600, 100);
		this.averageHistogramm.setBounds(10, 220,
	             600, 100);
	}
	
	public SecondExplanationView() {
		setupGUI();
		setFonts();
		customizeGUI();
	}
}