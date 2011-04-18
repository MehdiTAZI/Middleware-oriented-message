package fr.esiag.mezzodijava.nuclear.visualizer.ui.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.TitledBorder;

public class InformationPanel extends JPanel{

	
	private JLabel labelIdComponent;
	private JLabel idComponent;
	private JLabel labelChannelName;
	private JLabel channelName;
	
	public InformationPanel() {
		initComponent();	
	}


	public void initComponent() {
		
		labelIdComponent=new JLabel("Component ID: ");
		idComponent=new JLabel("unknown");
		idComponent.setForeground(Color.BLUE);
		labelChannelName=new JLabel("Channel Name : ");
		channelName=new JLabel("unknown");
		channelName.setForeground(Color.BLUE);

		JPanel p=new JPanel();
		p.setLayout(new FlowLayout());		
		CompoundBorder comp=new CompoundBorder(
				new TitledBorder(null,"Component Information",TitledBorder.CENTER,0,null,Color.BLACK), 
				new BevelBorder(BevelBorder.RAISED)
				);		
		setBorder(comp);
		
		JPanel q=new JPanel();
		q.setLayout(new FlowLayout());
		
		p.add(labelIdComponent);
		p.add(idComponent);
		q.add(labelChannelName);
		q.add(channelName);
		setLayout(new GridLayout(1, 2,30,30));
		add(p,BorderLayout.WEST);
		add(q,BorderLayout.EAST);
	}


	public JLabel getIdComponent() {
		return idComponent;
	}


	public void setIdComponent(JLabel idComponent) {
		this.idComponent = idComponent;
	}


	public JLabel getChannelName() {
		return channelName;
	}


	public void setChannelName(JLabel channelName) {
		this.channelName = channelName;
	}
	
	
}