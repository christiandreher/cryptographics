package edu.kit.iks.CryptographicsLib;

import java.awt.Color;

import javax.swing.*;

public class UITest {
	
    public static void main(String[] args) {
        JFrame meinJFrame = new JFrame();
        meinJFrame.setTitle("JButton Beispiel");
        meinJFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        
        KeyboardView panel = new KeyboardView();
        
        meinJFrame.add(panel);
 
        // Fenstergr��e wird so angepasst, dass 
        // der Inhalt reinpasst    
        meinJFrame.pack();
 
        meinJFrame.setVisible(true);
    }
}