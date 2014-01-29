package edu.kit.iks.CryptographicsLib;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * 
 * @author Matthias Jaenicke
 *
 * A bar diagram to show the frequencies of characters in a given text in comparison.
 * 
 * This bar diagram has a vertical beam for each character, with the beam for the most used character being at
 * maximum height of the diagram. Each other beam has a corresponding fraction of this height.
 * The equivalent characters are displayed beneath each beam.
 * The number of occurrences of each character is displayed within or above each beam, 
 * depending on the height of the beam.
 */
public class CharacterFrequencyDiagramView extends JPanel{

	// Quantities of all characters
	private int [] occurrences;
	
	/**
	 * Generates a diagram showing character frequencies with the given parameters.
	 * @param text Text which is analyzed and has its character occurrences displayed. 
	 * 				Non-alphabetical characters are simply ignored.
	 * @param width Total width of the diagram. Each column and the gaps between them will be a fraction of it.
	 * 				Has to be at least 405 to work properly.
	 * @param height Total height of the diagram. Has to be at least 60 to work properly.
	 */
	public CharacterFrequencyDiagramView(String text, int width, int height) {
		super();
		
		
		this.occurrences = calculateOccurrences(text);
		
		int padding = (int)(width / occurrences.length * 0.10);
		GridLayout layout = new GridLayout(1, occurrences.length, 0, padding);
		this.setLayout(layout);
		
		generateDiagram(width, height);
		
		this.setPreferredSize(new Dimension(width, height));
		this.setVisible(true);
	}
	
	
	private void generateDiagram(int totalWidth, int totalHeight) {
		
		// Determine the width of a single beam with regard of gaps between beams.
		int singleWidth = (int)(totalWidth  * 0.9 / occurrences.length);
		
		// Determine the base value for calculating the height of each column.
		int maxOccurrences = 0;
		for (int occ: occurrences) {
			if (occ > maxOccurrences) {
				maxOccurrences = occ;
			}
		}
		// "- 20" for putting the corresponding character beneath each beam.
		float heightBase = (float)(totalHeight - 20) / maxOccurrences;
		
		int asciiA = 65;
		for (int i = 0; i < occurrences.length; i++) {
			// Calculate the rounded height for the beam
			int height = (int)(heightBase * occurrences[i] + 0.5);
			JPanel beam = generateBeam((char)(i+asciiA), occurrences[i], singleWidth, height);
			this.add(beam);
		}
		
	}
	
	// Generates an entire beam including its value and corresponding character.
	private JPanel generateBeam(char character, int occurrence, int width, int height) {
		
		JPanel beam = new JPanel();
		BoxLayout layout = new BoxLayout(beam, BoxLayout.Y_AXIS);
		beam.setLayout(layout);
		
		// A filler to get the beam to the bottom of the panel.
		beam.add(Box.createVerticalGlue());
		
		
		JLabel coloredBeam;
		// If the beam is too short, the number of occurrences will be displayed above it.
		if (height < 20) {
			// Label to display number of occurrences.
			JLabel number = new JLabel("" + occurrence);
			number.setMinimumSize(new Dimension(width, 20));
			number.setPreferredSize(new Dimension(width, 20));
			number.setMaximumSize(new Dimension(width, 20));
			number.setHorizontalAlignment(JLabel.CENTER);
			beam.add(number);
			
			// An empty label for the actual beam
			coloredBeam = new JLabel();
		} else {
			// A label for the actual beam. Contains the number of occurrences of its character.
			coloredBeam = new JLabel("" + occurrence);
			coloredBeam.setVerticalAlignment(JLabel.TOP);
			coloredBeam.setHorizontalAlignment(JLabel.CENTER);
			coloredBeam.setForeground(Color.white);
		}
		coloredBeam.setOpaque(true);
		coloredBeam.setBackground(Color.blue.darker());
		coloredBeam.setMinimumSize(new Dimension(width, height));
		coloredBeam.setPreferredSize(new Dimension(width, height));
		coloredBeam.setMaximumSize(new Dimension(width, height));
		beam.add(coloredBeam);
		
		// The "legend" of the beam, i.a. its corresponding character.
		JLabel text = new JLabel("" + character);
		text.setMinimumSize(new Dimension(width, 20));
		text.setPreferredSize(new Dimension(width, 20));
		text.setMaximumSize(new Dimension(width, 20));
		text.setHorizontalAlignment(JLabel.CENTER);
		beam.add(text);

		return beam;
	}

	// Calculates the number of occurrences of alphabetical characters in the given String. 
	// All other characters are ignored.
	// Returns an Integer array with 26 items, each representing the "index+1"th character of the alphabet
	// and containing its number of occurrences.
	private int[] calculateOccurrences(String text) {
		int[] result = new int[26];
		text = text.toUpperCase();
		
		for(int i = 0; i < result.length; i++) {
			result[i] = 0;
		}
		
		int asciiA = 65;
		for(int i = 0; i < text.length(); i++) {
			char c = text.charAt(i);
			int index = (int)c - asciiA;
			if (index >= 0 && index < result.length) {
				result[index]++;
			}
		}
		
		
		return result;
	}

	public int[] getOccurences() {
		return occurrences;
	}

	public void setOccurrences(int [] occurrences) {
		this.occurrences = occurrences;
	}
}
