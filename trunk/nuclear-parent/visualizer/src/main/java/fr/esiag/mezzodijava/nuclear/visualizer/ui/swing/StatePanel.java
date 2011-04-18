package fr.esiag.mezzodijava.nuclear.visualizer.ui.swing;


import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

public class StatePanel extends JPanel{

	
	private JLabel labelSubscription;
	private JLabel subsriptionValue;	
	private JLabel labelConnection;
	private JLabel connectionValue;
	
	
		
	
	public StatePanel() {

		initComponent();
				
	}

	public void initComponent() {
		
		labelSubscription=new JLabel("Subscription : ");
		subsriptionValue=new JLabel("Unsubscribed                   ");
		subsriptionValue.setForeground(Color.RED);
		labelConnection=new JLabel("Connection : ");
		connectionValue=new JLabel("Disconnected                   ");
		connectionValue.setForeground(Color.RED);
					
		setBorder(new TitledBorder(null," Morocco State ",TitledBorder.LEFT,0,null,Color.BLACK));
		setLayout(new GridLayout(2, 2,0,0));
		add(labelSubscription);
		add(subsriptionValue);
		add(labelConnection);
		add(connectionValue);
	}

	public JLabel getSubsriptionValue() {
		return subsriptionValue;
	}

	public void setSubsriptionValue(JLabel subsriptionValue) {
		this.subsriptionValue = subsriptionValue;
	}

	public JLabel getConnectionValue() {
		return connectionValue;
	}

	public void setConnectionValue(JLabel connectionValue) {
		this.connectionValue = connectionValue;
	}
	
	
	
	

}
