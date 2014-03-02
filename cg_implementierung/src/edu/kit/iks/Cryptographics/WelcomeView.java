package edu.kit.iks.Cryptographics;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.xnap.commons.i18n.I18n;

import edu.kit.iks.CryptographicsLib.Configuration;

/**
 * An instance of this class represents the view of the welcome screen 
 * 
 * @author Christian Dreher
 */
public class WelcomeView extends JPanel {
	
	/**
	 * Serial version UID
	 */
	private static final long serialVersionUID = -8871817760284013217L;

	/**
	 * Localization instance
	 */
	private static I18n i18n = Configuration.getInstance().getI18n(WelcomeView.class);
	
	/**
	 * String to be displayed as header
	 */
	private String welcome = i18n.tr("Welcome");
	
	/**
	 * String to be displayed as a description of how
	 * to use the timeline
	 */
	private String description = i18n.tr("This is Cryptographics, a small tool with the aim"
			+ " to show how basic and advanced cryptographic procedures work in principle."
			+ " Below you can see a timeline with some procedures. Tap on the dots representing"
			+ " their date of invention to view a small description and start the visualization.");
	
	public WelcomeView() {
		super(new GridBagLayout());
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		
		this.add(new JLabel(this.wrapHtml("<h2>" + this.welcome + "</h2>")), gbc);
		
		gbc.gridy = 1;
		
		this.add(new JLabel(this.wrapHtml("<div width=\"500\">"
				+ this.description
				+ "</div>")), gbc);
	}
	
	private String wrapHtml(String text) {
		return "<html><body>" + text + "</body></html>";
	}
}
