package com.esiag.calc;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class ButtonPanel extends JPanel{

	
	public ButtonPanel(String labels[][],boolean layout){
		if (layout==true)
			setLayout(new GridLayout(labels.length,labels[0].length,5,5));
		else 
			setLayout(new FlowLayout(FlowLayout.LEFT));
		for (int i = 0; i < labels.length; i++) {
			for (int j = 0; j < labels[i].length; j++) {
				JButton b=new JButton(labels[i][j]);
				add(b);
			}
		}
	}

	public void addActionListener(ActionListener al){
		for (int i = 0; i <this.getComponentCount(); i++) {
			//for (int j = 0; j < this.getComponentCount(); j++) {
				JButton b=(JButton)this.getComponent(i);
				b.addActionListener(al);	
			//}
		}
	}
	public void addActionListener(ActionListener al,int index){
		JButton b=(JButton)this.getComponent(index);
		b.addActionListener(al);

	}
}