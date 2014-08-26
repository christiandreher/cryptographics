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

package edu.kit.iks.Cryptographics;

import static org.junit.Assert.*;

import javax.swing.JFrame;

import org.junit.Before;
import org.junit.Test;

import edu.kit.iks.cryptographics.MainController;
import edu.kit.iks.cryptographics.CaesarOld.CaesarVisualizationInfo;
import edu.kit.iks.cryptographicslib.AbstractVisualizationInfo;
import edu.kit.iks.cryptographicslib.controller.AbstractController;

/**
 * Tests for the MainController
 * 
 * @author Christian Dreher
 */
public class MainControllerTest {
	
	/**
	 * Unit under test
	 */
	private MainController uut;

	@Before
	public void setUp() throws Exception {
		this.uut = new MainController();
	}

	@Test
	public void testLoadView() {
		// Init origin
		JFrame actual = this.uut.getJFrame();
		
		// Should be null, since it is not yet loaded
		assertEquals(null, actual);
		
		// Now the view gets loaded
		this.uut.loadView();
		actual = this.uut.getJFrame();
		
		// Confirm that the JFrame was loaded by testing
		// against its component name and window title
		assertEquals("main-view", actual.getName());
		assertEquals("Cryptographics", actual.getTitle());
	}

	@Test
	public void testUnloadView() {
		// Init origin
		this.uut.loadView();
		JFrame actual = this.uut.getJFrame();
		
		// Confirm that the JFrame was loaded by testing
		// against its component name and window title
		assertEquals("main-view", actual.getName());
		assertEquals("Cryptographics", actual.getTitle());
		
		// Now the view gets unloaded
		this.uut.unloadView();
		actual = this.uut.getJFrame();
		
		// Should be null, since it was unloaded before
		assertEquals(null, actual);
	}

	@Test
	public void testPresentStartAction() {
		// Init origin
		this.uut.loadView();
		this.uut.presentStartAction();
		
		// Now that the start controller should be loaded, we can obtain
		// the actual value
		AbstractController actual = this.uut.getStartController();
		
		// Confirm that the view was loaded by testing
		// against its component name
		assertEquals("start-controller-view", actual.getView().getName());
		
		// Now load any other view
		this.uut.presentVisualizationAction(new CaesarVisualizationInfo());
		
		// Now the view of the start controller should be set
		// to null to free some memory
		assertEquals(null, actual.getView());
	}

	@Test
	public void testPresentVisualizationAction() {
		// Init origin
		this.uut.loadView();
		this.uut.presentStartAction();
		this.uut.presentVisualizationAction(new CaesarVisualizationInfo());
		
		// Now that the caesar visualization should be loaded, we can obtain
		// the actual value
		AbstractVisualizationInfo actual = this.uut.getVisualizationContainerController().getVisualizationInfo();
		
		// First, we check that the start controllers view, which was loaded before
		// the visualization, is set to null
		assertEquals(null, this.uut.getStartController().getView());
		
		// Confirm that the visualization view was loaded by testing
		// against id of the procedure passed through the visualization info
		assertEquals("caesar", actual.getId());
		
		// Now load start controller view again
		this.uut.presentStartAction();
		
		// Now the container controller should be completely unloaded again
		assertEquals(null, this.uut.getVisualizationContainerController());
	}

	@Test
	public void testAddChildController() {
		// Init origin
		this.uut.loadView();
		
		// Since the main controller has just been initialized 
		// at this point, there shouldn't be any child controllers
		assertEquals(0, this.uut.getChildControllers().size());
		
		// Now we load the start controller
		this.uut.presentStartAction();
		
		// Now we loaded the start controller, the size of the 
		// child controller list should be 1
		assertEquals(1, this.uut.getChildControllers().size());
	}

	@Test
	public void testRemoveChildController() {
		// Init origin:
		this.uut.loadView();
		this.uut.presentStartAction();
		this.uut.presentVisualizationAction(new CaesarVisualizationInfo());
		
		// Loading the start controller, and then a visualization,
		// should result in having 2 child controllers
		assertEquals(2, this.uut.getChildControllers().size());
		
		// Now we load the start controller again, which
		// should result in the removal of the visualization
		// from the child controllers list
		this.uut.presentStartAction();
		
		// Check if the assumption is correct
		assertEquals(1, this.uut.getChildControllers().size());
	}
	
	@Test
	public void testGetParentController() {
		// Init origin
		AbstractController actual = this.uut.getParentController();
		
		// Should be null, because the main controller does not have any parent
		assertEquals(null, actual);
	}

	@Test
	public void testIsViewLoaded() {
		// Since nothing is initialized, the function should return false
		assertFalse(this.uut.isViewLoaded());
		
		// Now we load the view
		this.uut.loadView();
		
		// Which should result in having the function returl true
		assertTrue(this.uut.isViewLoaded());
		
		// Note: With "View" in the context of the main controller
		// the JFrame is meant
	}

}
