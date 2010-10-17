package com.esiag.calc;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;

public class TestMain extends JFrame implements ActionListener{
	
	private JLabel screen;
	private JButton button;
	private double value=0;
	private char op='+';
	private boolean isOp=true;
	private Calculatrice calc;
	
	public TestMain(){
	
		setTitle("Calculatrice");
		
		exp01();
		pack();
		//setSize(400,260);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	public void exp01(){
		
		
		calc=new Calculatrice();
		
		Menu m=new Menu(new String [][]{
		
			{"Edition","Copier","Couper","Coller","-","Quitter"},
			{"Affichage","CDE/Motif", "Metal","Windows","Windows Classic"}
		});
		
		setJMenuBar(m);
		
		JPanel general=(JPanel)getContentPane();
		
		screen=new JLabel("0");
		screen.setBackground(Color.white);
		screen.setOpaque(true);
		screen.setHorizontalAlignment(JLabel.RIGHT);
		screen.setBorder(BorderFactory.createLineBorder(Color.blue));
		general.add("North",screen);
		
		JPanel p=new JPanel();
		p.setLayout(new BorderLayout());
		
		JPanel q=new JPanel();
		JLabel l=new JLabel();
		l.setPreferredSize(new Dimension(40,25));
		l.setBorder(BorderFactory.createLineBorder(new Color(0)));
		//q.add("West",l);
		
		ButtonPanel b=new ButtonPanel(new String [][]{
				{"Retour arriére","        CE        " ,"       C       "}
		},false);
		b.addActionListener(this);
		q.add("East",b);
		p.add("North",q);
		
		JPanel r=new JPanel();
		r.setLayout(new FlowLayout(FlowLayout.CENTER));
		ButtonPanel b2=new ButtonPanel(new String[][]{
				{"7","8","9","/","Rac"},
				{"4","5","6","*","%"},
				{"1","2","3","-","1/x"},
				{"0","+/-",",","+","="},
		},true);
		
		b2.addActionListener(this);
		ButtonPanel b3=new ButtonPanel(new String[][]{
			{"MC"},
			{"MR"},
			{"MS"},
			{"M+"},
		},true);
		
		//r.add("West",b3);
		r.add("East",b2);
		p.add("Center",r);
		general.add("Center",p);
		
	}
	
	public static void main(String[] args) {
	new TestMain();

	}

	public JButton getButton() {
		return button;
	}

	public void setButton(JButton button) {
		this.button = button;
	}

	
	public void actionPerformed(ActionEvent al) {
		JButton b=(JButton)al.getSource();
		char c=b.getText().charAt(0);
		
		
		if (b.getText().trim().equals("CE")) {
			screen.setText("0");
			value=0;
			isOp=false;
		}
		
		else if (b.getText().trim().equals("C")) {
			screen.setText("0");
			isOp=false;
		}
		else if (c>='0' && c<='9') {
			if (isOp) screen.setText("0");
			if (screen.getText().equals("0")) {
				screen.setText("" + c);
			}
			else {
				screen.setText(screen.getText() + c);
			}
			isOp = false;
		}
		else if (c==',') {
			if (isOp) screen.setText("0");
			if (!screen.getText().contains(",")) {
				screen.setText(screen.getText() + ",");
			}
			isOp = false;
		}
		 else if ("+-*/".indexOf(c)>=0) {
			String text = screen.getText();
			text = text.replace(",", ".");
			value = calc.operation(value, Double.parseDouble(text), op);
			text = "" + value;
			text = text.replace(".", ",");
			screen.setText(text);
			op = c;
			isOp = true;
		}
		else if (c == '=') {
			String text = screen.getText();
			text = text.replace(",", ".");
			value = calc.operation(value, Double.parseDouble(text), op);
			screen.setText(""+value);
			isOp = false;
			value=0;
		}
		else if (b.getText().trim().equals("Rac")) {
						
			String text = screen.getText();
			text = text.replace(",", ".");
			value = Math.sqrt(Double.parseDouble(text));
			screen.setText(""+value);
			isOp = true;
			value=0;
		}
		else if (b.getText().trim().equals("1/x")) {
			
			String text = screen.getText();
			text = text.replace(",", ".");
			value = 1/Double.parseDouble(text);
			screen.setText(""+value);
			isOp = true;
			value=0;
		}
		else if (b.getText().trim().equals("%")) {
			
			String text = screen.getText();
			text = text.replace(",", ".");
			value = Double.parseDouble(text)/100;
			screen.setText(""+value);
			isOp = true;
			value=0;
		}
		
	}
	private double operation(double op1, double op2, char op) {
		switch (op) {
			case '+': return op1 + op2;
			case '-': return op1 - op2;
			case '*': return op1 * op2;
			case '/': return op1 / op2;
		}
		return 0;
	}
}
	