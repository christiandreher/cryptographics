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

package edu.kit.iks.cryptographicslib.framework.controller;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.text.ParseException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.synth.SynthLookAndFeel;

import edu.kit.iks.cryptographicslib.common.view.partial.PopoverView;
import edu.kit.iks.cryptographicslib.framework.model.AbstractVisualizationInfo;
import edu.kit.iks.cryptographicslib.util.Configuration;
import edu.kit.iks.cryptographicslib.util.Logger;

/**
 * An instance of this class is the main controller, delegating all performed
 * tasks to its subcontrollers like StartController (teaser) or controllers
 * visualizing a cryptographic procedure.
 * 
 * @author Christian Dreher
 */
public class MainController extends AbstractController {

    /**
     * Gets the configurations supplied by the instance
     * itself or overwritten by the config.xml file.
     */
    private Configuration config = Configuration.getInstance();
    
    /**
     * A sandbox for subcontrollers to inflate their contents.
     */
    private JFrame frame;

    /**
     * An instance of the start controller, showing a teaser to draw attention.
     */
    private StartController startController;

    /**
     * An instance of a container controller, visualizing a cryptographic
     * procedure inside.
     */
    private VisualizationContainerController visualizationContainerController;

    /**
     * Just loads the frame. This controller is specially since it does not load a view.
     */
    @Override
    public void loadView() {
        // Note: every method here is strictly bound
        // to the values defined in the config.xml.
        // If, for example, <mouseCursor> is set to true,
        // the method disableCursor() will keep the cursor
        // visible.
        this.loadLookAndFeel();
        this.loadFrame();
        this.disableCursor();
        this.debugExitButton();
    }
    
    /*
     * (non-Javadoc)
     * @see edu.kit.iks.CryptographicsLib.AbstractController#unloadView()
     */
    @Override
    public void unloadView() {
        this.frame = null;
    }
    
    /**
     * Gets the JFrame.
     * 
     * @return the JFrame
     */
    public JFrame getJFrame() {
        return this.frame;
    }
    
    /*
     * (non-Javadoc)
     * @see edu.kit.iks.CryptographicsLib.AbstractController#isViewLoaded()
     */
    @Override
    public boolean isViewLoaded() {
        return (this.frame != null);
    }

    /**
     * Starts the visualization of StartController.
     */
    public final void presentStartAction() {
        
        // Unload visualization controllers not needed anymore
        if (this.visualizationContainerController != null) {
            if (this.visualizationContainerController.getHelpPopoverView() != null) {
                this.visualizationContainerController.dismissHelpPopover();
            }
            
            this.frame.getContentPane().remove(this.visualizationContainerController.getView());
            this.visualizationContainerController.unloadView();
            this.removeChildController(this.visualizationContainerController);
            this.visualizationContainerController = null;
        }
        
        // Only initialize start controller once, nut load/unload view
        if (this.startController == null) {
            this.startController = new StartController();
            this.addChildController(this.startController);
        }
        
        // Load view and append it to the frame
        this.startController.loadView();
        this.frame.getContentPane().add(this.startController.getView(), BorderLayout.CENTER);

        // Important to call validate, as some elements may
        // be invisible otherwise
        this.frame.getContentPane().revalidate();
        this.frame.getContentPane().repaint();
        this.frame.validate();
    }

    /**
     * Starts the visualization of a procedure with given {visualizationInfo}.
     * 
     * @param visualizationInfo
     *            Metadata of the cryptographic procedure to instantiate the
     *            controller from
     */
    public final void presentVisualizationAction(final AbstractVisualizationInfo visualizationInfo) {
        // Unload the view but keep the instance in memory for 
        // better response 
        if (this.startController != null) {
            this.frame.getContentPane().remove(this.startController.getView());
            this.startController.unloadView();
        }

        // load VisualizationContainerController
        this.visualizationContainerController = new VisualizationContainerController(
                visualizationInfo);
        // load view
        this.visualizationContainerController.loadView();
        this.addChildController(this.visualizationContainerController);
        this.frame.getContentPane().add(this.visualizationContainerController.getView(), BorderLayout.CENTER);
        this.visualizationContainerController.setCurrentVisualizationControllerIndex(0);
        
        this.frame.getContentPane().revalidate();
        this.frame.getContentPane().repaint();
        this.frame.revalidate();
    }
    
    /**
     * Gets the start controller.
     * 
     * @return The start controller
     */
    public final StartController getStartController() {
        return this.startController;
    }
    
    /**
     * Gets the visualization container controller.
     * 
     * @return The visualization container controller
     */
    public final VisualizationContainerController getVisualizationContainerController() {
        return this.visualizationContainerController;
    }
    
    /**
     * Loads the JFrame.
     */
    private void loadFrame() {
        this.frame = new JFrame("Cryptographics");
        this.frame.setName("main-view");
        
        if (this.config.isFullscreenModeEnabled()) {
            this.frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            this.frame.setUndecorated(true);
            this.frame.setAlwaysOnTop(true);
        } else {
            this.frame.setSize(1366, 768); // Basic size for debugging
            Logger.debug("MainController", "loadFrame", "Fullscreen mode disabled due to debugging.");
        }
        
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setVisible(true);
        
        // Extract the glassPane. We use this for displaying popovers.
        JPanel popoverContainerView = (JPanel)this.frame.getGlassPane();
        PopoverView.setContainerView(popoverContainerView);
    }
    
    /**
     * Loads the custom look and feel.
     */
    private void loadLookAndFeel() {
        if (this.config.isLookAndFeelEnabled()) {
            SynthLookAndFeel lookAndFeel = new SynthLookAndFeel();
            
            try {
                InputStream is = this.getClass().getResourceAsStream("/theme/manifest.xml");
                
                lookAndFeel.load(is, this.getClass());
            } catch (ParseException e) {
                Logger.error(e);
            }
            
            try {
                UIManager.setLookAndFeel(lookAndFeel);
            } catch (UnsupportedLookAndFeelException e) {
                Logger.error(e);
            }
        } else {
            Logger.debug("MainController", "loadLookAndFeel", "Look and feel disabled due to debugging.");
        }
    }
    
    /**
     * Disables the cursor.
     */
    private void disableCursor() {
        if (!this.config.isMouseCursorEnabled()) {
            Cursor nullCursor = null;
            Toolkit toolkit = Toolkit.getDefaultToolkit();
            Dimension dimension = toolkit.getBestCursorSize(1, 1);
            
            if ((dimension.width | dimension.height) != 0) {
                nullCursor = toolkit.createCustomCursor(new BufferedImage(dimension.width,
                        dimension.height,
                        BufferedImage.TYPE_INT_ARGB),
                        new Point(0, 0),
                        "nullCursor");
            }
            
            this.frame.setCursor(nullCursor);
        } else {
            Logger.debug("MainController", "disableCursor", "Curser is visible due to debugging.");
        }
    }
    
    /**
     * Adds a global button to immediately exit Cryptographics to ease debugging.
     */
    private void debugExitButton() {
        if (this.config.isDebugModeEnabled() && this.config.isFullscreenModeEnabled()) {
            JButton exitButton = new JButton(" ");
            exitButton.setToolTipText("Close CG: Only visible due to debug mode");
            
            ActionListener closeCG = new ActionListener() {
    
                @Override
                public void actionPerformed(ActionEvent arg0) {
                    Logger.debug("WelcomeView", "debugExitButton", "Forced shutdown via System.exit(0)"); 
                    System.exit(0);
                }
                
            };
            
            exitButton.addActionListener(closeCG);
            
            exitButton.setPreferredSize(new Dimension(1, 1));

            this.frame.getContentPane().add(exitButton, BorderLayout.EAST);
        }
    }
}
