package edu.kit.iks.Cryptographics.DiffieHellman.Demonstration;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import edu.kit.iks.CryptographicsLib.VisualizationView;

public class ExplainAimView extends VisualizationView {
	
	private JLabel aimExplain;

	private ColorChannel cc;
	


	/**
	 * 
	 */
	private static final long serialVersionUID = 5986978581223106407L;

	public ExplainAimView() {
		super();
		this.setLayout(new GridBagLayout());
		this.aimExplain = new JLabel();
		this.aimExplain.setText("<html><div style=\"width:200px\">Our aim is to exchange a secret on a public channel, " +
				"without Eve getting the secret too. Therefore we can't simply send the secret, as shown" +
				"in the figure above as Eve would get the secret too. Thus we need" +
				"to exchange the secret in a different way</div></html>");
		this.add(aimExplain);
		this.cc = new ColorChannel(460, 155, 600, 155);
		this.cc.setPreferredSize(new Dimension(900, 700));
		this.add(this.cc);
	}
	


}
