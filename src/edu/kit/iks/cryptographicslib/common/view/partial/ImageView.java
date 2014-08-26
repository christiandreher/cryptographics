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

package edu.kit.iks.cryptographicslib.common.view.partial;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import edu.kit.iks.cryptographicslib.util.Logger;

/**
 * Class to display an image in the view.
 * 
 * @author Christian Dreher
 */
public class ImageView extends JPanel {

	/**
	 * Serial Version UID.
	 */
	private static final long serialVersionUID = 6272421643783212805L;
	
	/**
	 * Image to be displayed.
	 */
	private BufferedImage image;

	/**
	 * Constructor initializing a new instance of {ImageView}
	 * with given {img}.
	 * 
	 * @param img Image to be displayed
	 */
    public ImageView(Image img) {
		if (img instanceof BufferedImage) {
	        this.image = (BufferedImage) img;
	    }
		
	    // Create a buffered image with transparency
	    BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
	    // Draw the image on to the buffered image
	    Graphics2D bGr = bimage.createGraphics();
	    bGr.drawImage(img, 0, 0, null);
	    bGr.dispose();
	    this.image = bimage;
	    this.setPreferredSize(new Dimension(this.image.getWidth(), this.image.getHeight()));
    }
    
    /**
     * Constructor initializing a new instance of {ImageView}
     * with given {filePath}.
     * 
     * @param filePath File path to the image to be displayed
     */
    public ImageView(String filePath) {
		try {                
			InputStream is = this.getClass().getResourceAsStream(filePath);
		    image = ImageIO.read(is);
		} catch (IOException e) {
			Logger.error(e);
		}
		
    	this.setPreferredSize(new Dimension(this.image.getWidth(), this.image.getHeight()));
    }

    /*
     * (non-Javadoc)
     * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, null); // see javadoc for more info on the parameters            
    }

	/**
	 * Gets the buffered image.
	 * 
	 * @return the image
	 */
	public BufferedImage getImage() {
		return image;
	}
}
