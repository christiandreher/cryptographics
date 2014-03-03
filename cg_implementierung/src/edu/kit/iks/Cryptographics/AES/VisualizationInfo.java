package edu.kit.iks.Cryptographics.AES;

import java.util.ArrayList;
import java.util.List;

import org.xnap.commons.i18n.I18n;

import edu.kit.iks.CryptographicsLib.AbstractVisualizationInfo;
import edu.kit.iks.CryptographicsLib.Configuration;
import edu.kit.iks.CryptographicsLib.InformationController;
import edu.kit.iks.CryptographicsLib.VisualizationDifficulty;

public class VisualizationInfo extends AbstractVisualizationInfo {
	
	/**
	 * Localization instance
	 */
	private static I18n i18n = Configuration.getInstance().getI18n(VisualizationInfo.class);
	
	/*
	 * @see edu.kit.iks.CryptographicsLib.AbstractVisualizationInfo#getId()
	 */
	@Override
	public String getId() {
		return "aes";
	}

	/*
	 * @see edu.kit.iks.CryptographicsLib.AbstractVisualizationInfo#getName()
	 */
	@Override
	public String getName() {
		return i18n.tr("AES");
	}

	/*
	 * @see edu.kit.iks.CryptographicsLib.AbstractVisualizationInfo#getDescription()
	 */
	@Override
	public String getDescription() {
		return i18n.tr("Learn about one of the most important modern ciphers, the Advanced Encryption Standard!");
	}

	/*
	 * @see edu.kit.iks.CryptographicsLib.AbstractVisualizationInfo#getTimelineOffset()
	 */
	@Override
	public float getTimelineOffset() {
		return 0.93f;
	}
	
	/*
	 * @see edu.kit.iks.CryptographicsLib.AbstractVisualizationInfo#getDifficulty()
	 */
	@Override
	public VisualizationDifficulty getDifficulty() {
		return VisualizationDifficulty.NOT_INTERACTIVE;
	}
	
	/*
	 * @see edu.kit.iks.CryptographicsLib.AbstractVisualizationInfo#getYear()
	 */
	@Override
	public int getYear() {
		return 1998;
	}
	
	/*
	 * @see edu.kit.iks.CryptographicsLib.AbstractVisualizationInfo#getQRCodeContent()
	 */
	@Override
	public String getQRCodeContent() {
		return i18n.tr("http://en.wikipedia.org/wiki/Advanced_Encryption_Standard");
	}
	
	/*
	 * @see edu.kit.iks.CryptographicsLib.AbstractVisualizationInfo#getControllerClasses()
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public List<Class> getControllerClasses() {
		List<Class> controllerClasses = new ArrayList<Class>();

		controllerClasses.add(InformationController.class);
		
		return controllerClasses;
	}
}
