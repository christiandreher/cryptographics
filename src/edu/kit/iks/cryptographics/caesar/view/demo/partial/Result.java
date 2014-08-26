/**
 * 
 */
package edu.kit.iks.cryptographics.caesar.view.demo.partial;

import java.util.AbstractMap.SimpleEntry;
import java.util.List;

import edu.kit.iks.cryptographicslib.framework.view.partial.AbstractPartialView;

/**
 * @author Christian Dreher
 *
 */
public class Result extends AbstractPartialView {

	/**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = 4139653062499850190L;

	/**
	 * @param variables
	 */
	public Result(List<SimpleEntry<String, String>> variables) {
		super(variables);
	}

	/* (non-Javadoc)
	 * @see edu.kit.iks.cryptographicslib.framework.view.partial.AbstractPartialView#preparePartialView()
	 */
	@Override
	public void preparePartialView() {
		this.addText(this.getVariableValue("result"));
	}

}
