package fr.esiag.mezzodijava.nuclear.visualizer.ui.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.plaf.FontUIResource;

public class AlerteEventPanel extends JPanel{

	/**
	 * @param args
	 */
	
	private JLabel labelAlerte;
	private JLabel labelAnimation;
	
	public AlerteEventPanel() {
		initComponents();
		JFrame f=new JFrame();
		f.getContentPane().add(this);
		f.setSize(300,300);
		f.setVisible(true);
		for (int i = 29; i >=0 ; i--) {
			try {
				
				Thread.sleep(1000);
				labelAnimation.setBackground(Color.WHITE);
				labelAnimation.setText("  "+i);
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	public void initComponents(){
		setBackground(Color.WHITE);
		labelAlerte=new JLabel(new ImageIcon("alerte.png"));
		labelAlerte.setBackground(Color.WHITE);
		labelAnimation=new JLabel("  30",10);
		
		labelAnimation.setBackground(Color.WHITE);
		//labelAnimation.setBackground(Color.BLUE);
		labelAnimation.setForeground(Color.RED);
		labelAnimation.setFont(new FontUIResource("Sans-Serif", Font.BOLD, 30));
	
		JPanel p=new JPanel();
		p.setLayout(new BoxLayout(p,BoxLayout.Y_AXIS));
		p.add(labelAlerte);
		p.add(labelAnimation);		
		add(p,BorderLayout.CENTER);
		
	}


}
