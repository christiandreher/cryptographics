/**
 * Copyright (c) 2014 Matthias Jaenicke <matthias.jaenicke@student.kit.edu>,
 * Matthias Plappert <undkc@student.kit.edu>,
 * Julien Duman <uncyc@student.kit.edu>, 
 * Christian Dreher <uaeef@student.kit.edu>,
 * Wasilij Beskorovajnov <uajkm@student.kit.edu> and 
 * Aydin Tekin <aydin.tekin@student.kit.edu>
 * 
 * Released under the MIT license (refer to LICENSE.md)
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package edu.kit.iks.cryptographicslib.framework.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.util.AbstractMap.SimpleEntry;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;

import edu.kit.iks.cryptographicslib.common.view.partial.KeyboardView;
import edu.kit.iks.cryptographicslib.framework.controller.AbstractVisualizationController;
import edu.kit.iks.cryptographicslib.framework.view.partial.AbstractPartialView;

/**
 * View for UI elements which all visualizations have in common.
 * 
 * @author Christian Dreher
 */
public abstract class AbstractVisualizationView extends AbstractView {
	
	/**
	 * Serial Version UID .
	 */
	private static final long serialVersionUID = -988624050394454370L;
	
	/**
	 * JPanel wrapping the navigation buttons.
	 * 
	 * @author Christian Dreher <uaeef@student.kit.edu>
	 */
	private class NavigationContainer extends JPanel {
		
		/**
		 * Serial version UID.
		 */
		private static final long serialVersionUID = 427370017328857575L;
		
		public JButton nextButton;
		
		public JButton backButton;
		
		private Dimension buttonSize = new Dimension(300, 35);
		
		public NavigationContainer() {
			super(new BorderLayout());
		}
		
		public void setNextButton(String label) {
			if (this.nextButton == null) {
				this.nextButton = new JButton();
				this.nextButton.setName("next");
				this.nextButton.setPreferredSize(this.buttonSize);
				this.nextButton.addActionListener(AbstractVisualizationView.this.globalActionListener);
				
				this.add(this.nextButton, BorderLayout.EAST);
			}
			
			this.nextButton.setText(label);
		}
		
		public void setBackButton(String label) {
			if (this.backButton == null) {
				this.backButton = new JButton();
				this.backButton.setName("back");
				this.backButton.setPreferredSize(this.buttonSize);
				this.backButton.addActionListener(AbstractVisualizationView.this.globalActionListener);
				
				this.add(this.backButton, BorderLayout.WEST);
			}
			
			this.backButton.setText(label);
		}
		
		public void unsetNextButton() {
			this.remove(this.nextButton);
			this.nextButton = null;
		}
		
		public void unsetBackButton() {
			this.remove(this.backButton);
			this.backButton = null;
		}
	}
	
	/**
	 * JPanel wrapping the content area.
	 * 
	 * @author Christian Dreher <uaeef@student.kit.edu>
	 */
	private class ContentContainer extends AbstractPartialView {
		
		/**
		 * Serial version UID.
		 */
		private static final long serialVersionUID = -6819473343449512917L;

		/**
		 * @param variables
		 */
		public ContentContainer() {
			super(null);
		}

		/* (non-Javadoc)
		 * @see edu.kit.iks.cryptographicslib.views.partials.AbstractPartialView#preparePartialView()
		 */
		@Override
		public void preparePartialView() {
			// Nothing to do, view will be populated at runtime
		}
	}
	
	private class FooterContainer extends JPanel {
	    
	    /**
         * Serial version UID
         */
        private static final long serialVersionUID = -8879643812151705769L;
        
        private KeyboardView keyboard;
	    private JButton stepButton;
	    private GridBagConstraints gbc;
	    
	    public FooterContainer() {
	        super(new GridBagLayout());
	        
	        this.gbc = new GridBagConstraints();
	        gbc.gridx = 0;
	    }
	    
	    public void showKeyboard(KeyboardView keyboard) {
	        this.keyboard = keyboard;
	        this.gbc.gridy = 0;
	        this.gbc.insets = new Insets(0,0,0,0);
	        this.add(this.keyboard, this.gbc);
	    }
	    
	    public void hideKeyboard() {
	        this.remove(this.keyboard);
	    }
	    
	    public void showStepButton(JButton stepButton) {
	        this.stepButton = stepButton;
	        this.gbc.gridy = 1;
	        this.gbc.insets = new Insets(10,0,0,0);
	        this.add(this.stepButton, this.gbc);
	    }
	    
	    public void hideStepButton() {
	        this.remove(this.stepButton);
	    }
	}
	
	/**
	 * Instance of the navigation container.
	 */
	private NavigationContainer navigationContainer;
	
	/**
	 * Instance of the content container.
	 */
	protected AbstractPartialView contentContainer;
	
	/**
	 * Button for stepwise skipping inside one view.
	 */
	private JButton stepButton;
	
	/**
	 * Keyboard instance. Will be displayed in the footer instead of
	 * the step button if set to be visible.
	 */
	private KeyboardView keyboardView;
	
	/**
	 * JPanel wrapping the step button.
	 */
	private FooterContainer footerContainer;
	
	/**
	 * Constructor initializing a new instance of VisualizationView.
	 */
	public AbstractVisualizationView(ActionListener al, List<SimpleEntry<String, String>> variables) {
		super(variables);
		
		this.globalActionListener = al;
		
		this.setLayout(new BorderLayout());
		
		this.setupNavigationContainer();
		this.setupContentContainer();
		this.setupFooterContainer();
	}

	/**
	 * If this method is called the first time, a 'Next' button with given label will
	 * be created. If the method is called another time, only the label of the already
	 * existing button will be updated, however, a possible bound ActionListener will
	 * remain untouched. If you wish to also change the ActionListener, call the 
	 * setNextButtonActionListener(...) method once more.
	 * 
	 * @param label Label of the button
	 */
	protected void setNextButton(String label) {
		this.navigationContainer.setNextButton(label);
	}
	
	/**
	 * If this method is called the first time, a 'Back' button with given label will
	 * be created. If the method is called another time, only the label of the already
	 * existing button will be updated, however, a possible bound ActionListener will
	 * remain untouched. If you wish to also change the ActionListener, call the 
	 * setBackButtonActionListener(...) method once more.
	 * 
	 * @param label Label of the button
	 */
	protected void setBackButton(String label) {
		this.navigationContainer.setBackButton(label);
	}
	
	/**
	 * If this method is called the first time, a 'Step' button with given label will
	 * be created. If the method is called another time, only the label of the already
	 * existing button will be updated, however, a possible bound ActionListener will
	 * remain untouched. If you wish to also change the ActionListener, call the 
	 * setStepButtonActionListener(...) method once more.
	 * 
	 * @param label Label of the button
	 */
	protected void setStepButton(String label) {
		if (this.stepButton == null) {
			this.stepButton = new JButton();
			
			Dimension buttonSize = new Dimension(350, 50);
			
			this.stepButton.setName("step");
			this.stepButton.setPreferredSize(buttonSize);
			this.stepButton.addActionListener(this.globalActionListener);
			
			this.footerContainer.showStepButton(this.stepButton);
		}
		
		this.stepButton.setText(label);
		this.revalidate();
	}

	public void useCharKeyboard() {
		this.keyboardView = new KeyboardView((AbstractVisualizationController) this.globalActionListener,
				KeyboardView.CHAR_MODE);
	}
	
	public void useStringKeyboard() {
		this.keyboardView = new KeyboardView((AbstractVisualizationController) this.globalActionListener,
				KeyboardView.STRING_MODE);
	}
	
	public void setKeyboardAction(String actionName) {
		this.keyboardView.setAction(actionName);
	}
	
	public String getUserInput() {
		return this.keyboardView.getInput();
	}
	
	/**
	 * Unsets the next button.
	 */
	protected void unsetNextButton() {
		this.navigationContainer.unsetNextButton();
	}
	
	/**
	 * Unsets the back button.
	 */
	protected void unsetBackButton() {
		this.navigationContainer.unsetBackButton();
	}
	
	public void showStepButton() {
		this.footerContainer.showStepButton(this.stepButton);
		this.revalidate();
	}
	
	/**
	 * Unsets the step button.
	 */
	public void hideStepButton() {
		this.footerContainer.hideStepButton();
		this.revalidate();
	}
	
	public void showKeyboard() {
		this.footerContainer.showKeyboard(this.keyboardView);
		this.revalidate();
	}
	
	public void hideKeyboard() {
		this.footerContainer.hideKeyboard();
		this.keyboardView.clearInput();
		this.revalidate();
	}
	
	/**
	 * Sets the action name of the step button.
	 * 
	 * @param actionName New name of the action to call
	 */
	public void setStepButtonAction(String actionName) {
		this.stepButton.setName(actionName);
	}
	
	/**
	 * Sets the default step action to the step button.
	 */
	public void setDefaultStepButtonAction() {
		this.setStepButtonAction("step");
	}
	
	/**
	 * Adds a text to the content area.
	 * 
	 * @param text Text to add to content
	 * @return ID to identify the object for modifying or removing it
	 */
	protected int addTextToContent(String text) {
		return this.contentContainer.addText(text);
	}
	
	/**
	 * Adds a partial view to the content.
	 * 
	 * @param partialView Partial view to add
	 * @return ID to identify the object for modifying or removing it
	 */
	public int addPartialViewToContent(JComponent partialView) {
		return this.contentContainer.addElement(partialView);
	}
	
	/**
	 * Sets the content to a new partial view.
	 * 
	 * @param content Partial view to set
	 */
	public void setContent(AbstractPartialView content) {
		// Removes old content container from view
		this.remove(this.contentContainer);
		
		// Sets new content container
		this.contentContainer = content;
		
		// Prepares the partial view to display its contents
		this.contentContainer.preparePartialView();
		
		// Adds the new partial view to this view
		this.add(this.contentContainer);
		
		// Refresh
		this.refreshContent();
	}
	
	/**
	 * Adds an image as ImageView to the content by its resource ID.
	 * 
	 * @param resourceId Resource ID defined in resources.xml
	 * @returnID to identify the object for modifying or removing it
	 */
	protected int addImageFromResourceToContent(String resourceId) {
		return this.contentContainer.addImageFromResource(resourceId);
	}
	
	/**
	 * Resets the content area by removing all JComponents.
	 */
	protected void clearContent() {
		this.remove(this.contentContainer);
		this.contentContainer = new ContentContainer();
		this.add(this.contentContainer);
	}
	
	/**
	 * Refreshes the content area.
	 */
	public void refreshContent() {
		this.validate();
	}
	
	/**
	 * Sets up the navigation container.
	 */
	private void setupNavigationContainer() {
		this.navigationContainer = new NavigationContainer();
		
		this.add(this.navigationContainer, BorderLayout.NORTH);
	}
	
	/**
	 * Sets up the content container.
	 */
	private void setupContentContainer() {
		this.contentContainer = new ContentContainer();
		
		this.add(this.contentContainer, BorderLayout.CENTER);
	}
	
	/**
	 * Sets up the footer container.
	 */
	private void setupFooterContainer() {
		this.footerContainer = new FooterContainer();
		
		this.add(this.footerContainer, BorderLayout.SOUTH);
	}
}
