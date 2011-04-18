package fr.esiag.mezzodijava.nuclear.visualizer.ui.swing;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

public class StatisticPanel extends JPanel{

	private JLabel labelNbrAlerte;
	private JLabel nbrAlerteValue;
	
	private JLabel labelNbrChangeState;
	private JLabel nbrChangeStateValue;
	
	
	public StatisticPanel() {
		initComponents();
	}

	public void initComponents() {
		
		labelNbrAlerte=new JLabel("Alerts Number : ",JLabel.RIGHT);
		nbrAlerteValue=new JLabel("   0", 10);
		nbrAlerteValue.setForeground(Color.RED);
		labelNbrChangeState=new JLabel("State Changes Number : ",JLabel.RIGHT);
		nbrChangeStateValue=new JLabel("   0", 10);
		nbrChangeStateValue.setForeground(Color.RED);
		
		setBorder(new TitledBorder(null," Events Statistics",TitledBorder.LEFT,0,null,Color.BLACK));
		setLayout(new GridLayout(2, 2,10,5));
		add(labelNbrAlerte);
		add(nbrAlerteValue);
		add(labelNbrChangeState);
		add(nbrChangeStateValue);
		
		
	}

	public JLabel getNbrAlerteValue() {
		return nbrAlerteValue;
	}

	public void setNbrAlerteValue(JLabel nbrAlerteValue) {
		this.nbrAlerteValue = nbrAlerteValue;
	}

	public JLabel getNbrChangeStateValue() {
		return nbrChangeStateValue;
	}

	public void setNbrChangeStateValue(JLabel nbrChangeStateValue) {
		this.nbrChangeStateValue = nbrChangeStateValue;
	}
	
	
}
