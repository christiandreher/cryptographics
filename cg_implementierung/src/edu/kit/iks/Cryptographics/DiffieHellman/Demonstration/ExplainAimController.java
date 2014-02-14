package edu.kit.iks.Cryptographics.DiffieHellman.Demonstration;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import edu.kit.iks.Cryptographics.VisualizationContainerController;
import edu.kit.iks.CryptographicsLib.AbstractVisualizationController;
import edu.kit.iks.CryptographicsLib.AbstractVisualizationInfo;

/**
 * The controller of the explainaimview,
 * loads the view
 * 
 * @author kai
 *
 */
public class ExplainAimController extends AbstractVisualizationController {
	
	/** our corresponding view */
	private ExplainAimView view;
	
	/**
	 * Simple constructor
	 * @param visualizationInfo the dh info
	 */
	public ExplainAimController(AbstractVisualizationInfo visualizationInfo) {
		super(visualizationInfo);
	}

	/*
	 * (non-Javadoc)
	 * @see edu.kit.iks.CryptographicsLib.AbstractVisualizationController#getHelp()
	 */
	@Override
	public String getHelp() {
		return view.getHelp();
	}

	/*
	 * (non-Javadoc)
	 * @see edu.kit.iks.CryptographicsLib.AbstractController#loadView()
	 */
	@Override
	public void loadView() {
		this.view = new ExplainAimView();
		this.getView().getNextButton().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				((VisualizationContainerController) getParentController()).presentNextVisualizationController();
			}
		});
		
		this.getView().getBackButton().setVisible(false);

	}
	
	/*
	 * (non-Javadoc)
	 * @see edu.kit.iks.CryptographicsLib.AbstractController#unloadView()
	 */
	@Override
	public void unloadView() {
		this.getView().getColorChannel().stopTimer();
		this.view = null;
	}
	
	/*
	 * (non-Javadoc)
	 * @see edu.kit.iks.CryptographicsLib.AbstractController#getView()
	 */
	@Override
	public ExplainAimView getView() {
		return (ExplainAimView) this.view;
		
	}


}
