package com.esiag.calc;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import com.sun.security.auth.module.JndiLoginModule;

public class Menu extends JMenuBar implements ActionListener{
	private JMenu m;
	private JMenuItem m2;
	
	public Menu(String M[][]){
		for (int i = 0; i < M.length; i++) {
			m=new JMenu(M[i][0]);
			for (int j = 1; j < M[i].length; j++) {
				if(M[i][j].equals("-"))
					m.addSeparator();
				else{
					m2=new JMenuItem(M[i][j]);
					m2.addActionListener(this);
					m.add(m2);
				}
			}
			add(m);
		}
	}
	
	public void actionPerformed(ActionEvent e) {
		
		UIManager.LookAndFeelInfo LF[]=UIManager.getInstalledLookAndFeels();
		
		System.out.println(LF[0].getName());
		System.out.println(LF[1].getName());
		System.out.println(LF[2].getName());
		System.out.println(LF[3].getName());
		if(e.getActionCommand().equals(LF[0].getName()) || e.getActionCommand().equals(LF[1].getName()) || 
				e.getActionCommand().equals(LF[2].getName()) || e.getActionCommand().equals(LF[3].getName()))
		{
			try{ 
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); 
			}catch(Exception ex){
				ex.getMessage();
			}
			
			SwingUtilities.updateComponentTreeUI(Menu.this);
			
		}
		else if(e.getActionCommand().equals("Quitter"))
				System.exit(0);
			
	}	
	

}