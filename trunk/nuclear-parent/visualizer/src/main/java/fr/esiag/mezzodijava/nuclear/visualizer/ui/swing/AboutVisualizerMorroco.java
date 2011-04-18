package fr.esiag.mezzodijava.nuclear.visualizer.ui.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JWindow;

public class AboutVisualizerMorroco extends JWindow implements ActionListener{

	/**
	 * @param args
	 */
	
	public AboutVisualizerMorroco() {
		
			
			JPanel global=(JPanel)getContentPane();
			global.setLayout(new BoxLayout(global, BoxLayout.Y_AXIS));
			
			int width=565;
			int height=220;
			
			Dimension screen=Toolkit.getDefaultToolkit().getScreenSize();
			
			int x= (screen.width-width) / 2;
			int y= (screen.height-height) / 2 ;
			
			setBounds(x, y, width, height);
			
			JLabel label=new JLabel(new ImageIcon("logo.gif"));

			String s="khqhskqh s"+ "\n"+" jdjsdutjgjgjjgjgjggjgjggjgsd";
			JLabel description=new JLabel("Morocco Visualizer");
			JLabel version=new JLabel("Version: Service Release 1.0(Beta).");			
			JLabel copyr=new JLabel("Copyright 2010,2011. Mezzo Di Java.");			
			
			copyr.setFont(new Font("Sans-Serif", Font.BOLD, 12));
			version.setFont(new Font("Sans-Serif", Font.BOLD, 12));
			description.setFont(new Font("Sans-Serif", Font.BOLD, 12));
			
			JPanel visualizer=new JPanel();
			visualizer.setBackground(Color.WHITE);
			visualizer.setLayout(new BoxLayout(visualizer,BoxLayout.Y_AXIS));
			visualizer.add(description);
			visualizer.add(new JLabel("    "));
			visualizer.add(version);
			visualizer.add(new JLabel("    "));
			visualizer.add(copyr);
			
			JPanel p=new JPanel();
			p.setBackground(Color.WHITE);
			p.setLayout(new BoxLayout(p,BoxLayout.X_AXIS));
			p.add(label);
			p.add(visualizer);
			
			
			JPanel q=new JPanel();
			q.setLayout(new FlowLayout(FlowLayout.RIGHT));
			q.setBackground(Color.LIGHT_GRAY);
			JButton ok=new JButton("Ok");
			
			ok.addActionListener(this);
			ok.setPreferredSize(new Dimension(100, 20));
			q.add(ok);
			
			
			global.add(p);
			global.add(q);
			global.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY,5));
			
			setVisible(true);
			
			
			
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton b=(JButton)e.getSource();
		if(b.getText().equals("Ok")){
			this.setVisible(false);
		}
		
	}

}
