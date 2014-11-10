package edu.kit.iks.cryptographics.caesar.view.trial.partial;

import edu.kit.iks.cryptographicslib.framework.view.partial.AbstractPartialView;

/**
 * @author Christian Dreher
 *
 */
public class ResultEncryptName extends AbstractPartialView {

	/**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = 4139653062499850190L;

	/**
	 * @param variables
	 */
	public ResultEncryptName() {
		super(null);
	}

	/* (non-Javadoc)
	 * @see edu.kit.iks.cryptographicslib.framework.view.partial.AbstractPartialView#preparePartialView()
	 */
	@Override
	public void preparePartialView() {
	    
	}

	public void displayResult(String text) {
	    this.addText(text);
	}
}
