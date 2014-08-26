/**
 * 
 */
package edu.kit.iks.cryptographics.caesar.view.trial.partial;

import java.util.AbstractMap.SimpleEntry;
import java.util.List;

import edu.kit.iks.cryptographicslib.framework.view.partial.AbstractPartialView;

/**
 * @author Christian Dreher
 *
 */
public class EnterName extends AbstractPartialView {

    /**
     * Serial version UID
     */
    private static final long serialVersionUID = -4431691560844814000L;

    /**
     * @param variables
     */
    public EnterName(List<SimpleEntry<String, String>> variables) {
        super(variables);
    }

    /* (non-Javadoc)
     * @see edu.kit.iks.cryptographicslib.framework.view.partial.AbstractPartialView#preparePartialView()
     */
    @Override
    public void preparePartialView() {
        this.addText(this.getVariableValue("explanation"));
    }
}
