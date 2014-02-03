package edu.kit.iks.Cryptographics.Example;

import java.awt.Color;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;

import edu.kit.iks.Cryptographics.VisualizationContainerController;
import edu.kit.iks.CryptographicsLib.AbstractVisualizationController;
import edu.kit.iks.CryptographicsLib.AbstractVisualizationInfo;
import edu.kit.iks.CryptographicsLib.VisualizationView;
import edu.kit.iks.CryptographicsLib.MouseClickListener;

public class FirstController extends AbstractVisualizationController {

	public FirstController(AbstractVisualizationInfo visualizationInfo) {
		super(visualizationInfo);
	}

	@Override
	public String getHelp() {
		// TODO Auto-generated method stub
		return "This is an example help. You can even use <strong>HTML</strong>.";
	}

	@Override
	public void loadView() {
		VisualizationView v = new VisualizationView();
		v.getNextButton().addMouseListener(new MouseClickListener() {
			@Override
			public void clicked(MouseEvent event) {
				VisualizationContainerController containerController = (VisualizationContainerController)getParentController();
				containerController.presentNextVisualizationController();
			}
		});
		
		this.view = v;
		this.view.add(new JLabel("First"));
		this.view.setBackground(Color.BLUE);
		this.view.validate();
	}

	/*
	 * (non-Javadoc)
	 * @see edu.kit.iks.CryptographicsLib.AbstractController#unloadView()
	 */
	@Override
	public void unloadView() {
		this.view = null;
	}
	
}
