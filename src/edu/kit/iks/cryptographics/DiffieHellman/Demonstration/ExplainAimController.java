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

package edu.kit.iks.cryptographics.DiffieHellman.Demonstration;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import edu.kit.iks.cryptographics.VisualizationContainerController;
import edu.kit.iks.cryptographicslib.AbstractVisualizationInfo;
import edu.kit.iks.cryptographicslib.controller.AbstractVisualizationController;

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
		
		this.getView().getSkipButton().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				((VisualizationContainerController) getParentController()).setCurrentVisualizationControllerIndex(2);
			}
		});

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
