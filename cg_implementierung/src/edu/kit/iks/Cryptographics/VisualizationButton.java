package edu.kit.iks.Cryptographics;

import javax.swing.JButton;

import edu.kit.iks.CryptographicsLib.AbstractVisualizationInfo;

public class VisualizationButton extends JButton {
	private AbstractVisualizationInfo visualizationInfo;
	
	public VisualizationButton(AbstractVisualizationInfo vInfo) {
		visualizationInfo = vInfo;
		this.setText(visualizationInfo.getName());
	}
	
	public AbstractVisualizationInfo getVisualizationInfo() {
		return visualizationInfo;
	}
}