package edu.kit.iks.CryptographicsLib;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import org.xnap.commons.i18n.I18n;

import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;

/**
 * Abstract visualization information
 * 
 * @author Christian Dreher
 */
abstract public class AbstractVisualizationInfo {
	
	/**
	 * Localization instance
	 */
	private static I18n i18n = Configuration.getInstance().getI18n(
			AbstractVisualizationInfo.class);

	
	/**
	 * Gets the ID of the procedure
	 * 
	 * @return ID of the procedure
	 */
	abstract public String getId();

	/**
	 * Gets the name of the procedure
	 * 
	 * @return Name of the procedure
	 */
	abstract public String getName();

	/**
	 * Gets the description of a procedure
	 * 
	 * @return Description of a procedure
	 */
	abstract public String getDescription();

	/**
	 * Offset on the timeline indicating the position on the timeline 
	 * from left. The offset must be in the interval (0,1).
	 * An offset equal to 0 or 1 will lead to undefined behaviour!
	 * 
	 * @return Position on the timeline in the interval (0,1)
	 */
	abstract public float getTimelineOffset();

	/**
	 * Gets the difficulty of the procedure
	 * 
	 * @return Difficulty of the procedure
	 */
	abstract public VisualizationDifficulty getDifficulty();

	/**
	 * Gets the year the procedure was invented
	 * 
	 * @return Year the procedure was invented
	 */
	abstract public int getYear();
	
	/**
	 * Gets the plain text of what the QR is encoding
	 * 
	 * @return Plain text of what the QR is encoding
	 */
	abstract public String getQRCodeContent();
	
	/**
	 * Gets a list of all controller classes belonging to the visualization
	 * 
	 * @todo fix warning here 
	 * ("Class is a raw type. References to generic type Class<T> should be parameterized")
	 * @return List of all controller classes belonging to the visualization
	 */
	@SuppressWarnings("rawtypes")
	abstract public List<Class> getControllerClasses();

	/**
	 * Gets the additional information as file path to display HTML
	 * 
	 * @return Additional information as file path to display HTML
	 */
	public String getAdditionalInformationPath() {
		// Generate path.
		String language = Configuration.getInstance().getLanguageCode();
		String path = "/" + this.getId() + "/" + language + "/" + "additional_information.html";  
		return path;
	}
	
	/**
	 * The QR code. Will be generated lazily.
	 */
	private Image qrCode = null;

	/**
	 * Gets the QR code as image 
	 * 
	 * @return QR code as image 
	 */
	public Image getQrCode() {
		if (this.qrCode == null) {
			this.generateQrCode();
		}
		return this.qrCode;
	}
	
	/**
	 * Generates the QR code.
	 */
	private void generateQrCode() {
		// Get the text
		String text = this.getQRCodeContent();
		
		// Generate QR code to output stream.
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		QRCode.from(text).to(ImageType.PNG).writeTo(output);
		
		// Create BufferedImage from input stream.
		byte[] data = output.toByteArray();
		ByteArrayInputStream input = new ByteArrayInputStream(data);
		BufferedImage image = null;
		try {
			image = ImageIO.read(input);
		} catch (IOException e) {
			Logger.e(e);
		}
		this.qrCode = image;
	}
	
	/**
	 * Gets the human-readable and localized difficulty
	 * 
	 * @return The difficulty string
	 */
	public String getHumanReadableDifficulty() {
		switch (this.getDifficulty()) {
			case EASY: return i18n.tr("Easy");
			case MEDIUM: return i18n.tr("Medium");
			case HARD: return i18n.tr("Hard");
			default: return null;
		}
	}
}
