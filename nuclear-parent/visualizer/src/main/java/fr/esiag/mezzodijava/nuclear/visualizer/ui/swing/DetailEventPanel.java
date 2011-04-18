package fr.esiag.mezzodijava.nuclear.visualizer.ui.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

public class DetailEventPanel extends JPanel{

	/**
	 * @param args
	 */
	
	private JLabel labelCode;
	private JLabel labelTime;
	private JLabel labelTTL;
	private JLabel labelPriority;
	private JLabel labelTypeEvent;
	private JLabel labelContentEvent;
	
	private JTextField textCode;
	private JTextField textTime;
	private JTextField textTTL;
	private JTextField textPriority;
	private JTextField textTypeEvent;
	private JTextField textCotentEvent;
	
	
	
	
	public DetailEventPanel() {
		initComponents();
	
	}
	
	public void initComponents() {
	
		setBorder(new TitledBorder(null," Selected Event Details",TitledBorder.CENTER,0,null,Color.BLACK));
		setSize(600, 220);
		JPanel p=new JPanel(new GridLayout(4,2,10,10));
		
		p.setBorder(new TitledBorder(null," Header ",TitledBorder.LEFT,0,null,Color.BLACK));

		labelCode=new JLabel("Code : ");
		labelTime=new JLabel("Time : ");
		labelTTL=new JLabel("Time To Live : ");
		labelPriority=new JLabel("Priority : ");
		
		
		textCode=new JTextField(20);
		textCode.setForeground(Color.BLUE);
		textCode.setEditable(false);
		textTime=new JTextField(20);
		textTime.setForeground(Color.BLUE);
		textTime.setEditable(false);
		textTTL=new JTextField(20);
		textTTL.setForeground(Color.BLUE);
		textTTL.setEditable(false);
		textPriority=new JTextField(20);
		textPriority.setForeground(Color.BLUE);
		textPriority.setEditable(false);
		
		
		p.add(labelCode);
		p.add(textCode);
		p.add(labelTime);
		p.add(textTime);
		p.add(labelTTL);
		p.add(textTTL);
		p.add(labelPriority);
		p.add(textPriority);
		
		
		
		
		
		JPanel q=new JPanel();
		q.setBorder(new TitledBorder(null," Body ",TitledBorder.LEFT,0,null,Color.BLACK));
		q.setLayout(new GridLayout(2,2,10,10));
		
		JPanel panel1=new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel panel2=new JPanel(new FlowLayout(FlowLayout.LEFT));
		labelTypeEvent=new JLabel("Event Type : ");
		labelContentEvent=new JLabel("Content : ");
		
		textTypeEvent=new JTextField(20);
		textTypeEvent.setForeground(Color.BLUE);
		textTypeEvent.setEditable(false);

		textCotentEvent=new JTextField(20);
		textCotentEvent.setForeground(Color.BLUE);
		textCotentEvent.setEditable(false);
		
		panel1.add(labelTypeEvent);
		panel1.add(textTypeEvent);
		panel2.add(labelContentEvent);
		panel2.add(textCotentEvent);
		
		
		q.add(panel1);
		q.add(panel2);
		
		setLayout(new GridLayout(1,2,10,10));
		add(p);
		add(q);
		
	}

	public static void main(String[] args) {
		new DetailEventPanel();

	}

	public JTextField getTextCode() {
		return textCode;
	}

	public void setTextCode(JTextField textCode) {
		this.textCode = textCode;
	}

	public JTextField getTextTime() {
		return textTime;
	}

	public void setTextTime(JTextField textTime) {
		this.textTime = textTime;
	}

	public JTextField getTextTTL() {
		return textTTL;
	}

	public void setTextTTL(JTextField textTTL) {
		this.textTTL = textTTL;
	}

	public JTextField getTextPriority() {
		return textPriority;
	}

	public void setTextPriority(JTextField textPriority) {
		this.textPriority = textPriority;
	}

	public JTextField getTextTypeEvent() {
		return textTypeEvent;
	}

	public void setTextTypeEvent(JTextField textTypeEvent) {
		this.textTypeEvent = textTypeEvent;
	}

	public JTextField getTextCotentEvent() {
		return textCotentEvent;
	}

	public void setTextCotentEvent(JTextField textCotentEvent) {
		this.textCotentEvent = textCotentEvent;
	}

	
}
