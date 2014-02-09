package edu.kit.iks.Cryptographics.DiffieHellman.Experiment;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import edu.kit.iks.Cryptographics.DiffieHellman.Demonstration.ColorChannel;
import edu.kit.iks.Cryptographics.DiffieHellman.Demonstration.ColorMix;

public class ChoosePublicColorView extends JPanel {

	private static final long serialVersionUID = 5764374133753732451L;
	
	private JLabel choosePublicLbl;

	private ColorChannel cc;

	private ColorMix cm;
	
	public ChoosePublicColorView() {
		super();
		GridBagConstraints gbc = new GridBagConstraints();
		GridBagLayout layout = new GridBagLayout();
		this.setLayout(layout);
		
		this.choosePublicLbl = new JLabel();
		this.choosePublicLbl.setText("<html><div style=\"width:120px\">Alice chooses a public color and sends it to Bob" +
				"Eve listens to the channel and gets a copy</div></html>");
		gbc.weightx = 0.1;
		gbc.weighty = 0.1;
		gbc.gridx = 1;
		gbc.gridy = 2;
		this.add(choosePublicLbl, gbc);
		this.cc = new ColorChannel(new Dimension(700, 200), 50);
		
		gbc.weightx = 0.1;
		gbc.weighty = 0.1;
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.fill = GridBagConstraints.BOTH;
		this.add(this.cc, gbc);
		
		this.cm = new ColorMix(50, new Dimension(200, 200));
		
		gbc.weightx = 0.1;
		gbc.weighty = 0.1;
		gbc.gridx = 2;
		gbc.gridy = 0;
		this.add(this.cm, gbc);
	}

}
