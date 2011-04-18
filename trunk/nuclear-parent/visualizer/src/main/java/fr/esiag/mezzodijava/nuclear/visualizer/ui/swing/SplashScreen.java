package fr.esiag.mezzodijava.nuclear.visualizer.ui.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JWindow;

public class SplashScreen {

	/**
	 * @param args
	 */
	
	public SplashScreen() {
		showSplash(5000);
		
	}
	
	public void showSplash(int duration) {
	JWindow splash=new JWindow();
	
	JPanel p=(JPanel)splash.getContentPane();
	int width=660;
	int height=220;
	
	Dimension screen=Toolkit.getDefaultToolkit().getScreenSize();
	
	int x= (screen.width-width) / 2;
	int y= (screen.height-height) / 2 ;
	
	splash.setBounds(x, y, width, height);
	
	JLabel label=new JLabel(new ImageIcon("splash.gif"));
	JLabel copyr=new JLabel("Copyright 2010/2011, Mezzo Di Java.",JLabel.CENTER);
	copyr.setFont(new Font("Sans-Serif", Font.BOLD, 12));
	p.add(label,BorderLayout.CENTER);
	p.add(copyr,BorderLayout.SOUTH);
	
	p.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY,10));
	splash.setVisible(true);
	try {
		
		Thread.sleep(duration);
	} catch (Exception e) {
		e.printStackTrace();
	}
		
	splash.setVisible(false);
	}

	public static void main(String[] args) {
		new SplashScreen();
		
		
	}

}
