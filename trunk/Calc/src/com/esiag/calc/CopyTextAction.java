package com.esiag.calc;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;

public class CopyTextAction implements ActionListener {
	private JTextField sourceTextField, targetTextField;
	private TestMain view;
	public CopyTextAction() {
	}
	public CopyTextAction(JTextField sourceTextField, JTextField targetTextField) {
		super();
		this.sourceTextField = sourceTextField;
		this.targetTextField = targetTextField;
	}
	public CopyTextAction(TestMain view) {
		this.view = view;
	//	sourceTextField = view.getT1();
	//	targetTextField = view.getT2();
	}
	
	public void actionPerformed(ActionEvent e) {
		targetTextField.setText(sourceTextField.getText());
	}

	public JTextField getSourceTextField() {
		return sourceTextField;
	}
	public void setSourceTextField(JTextField sourceTextField) {
		this.sourceTextField = sourceTextField;
	}
	public JTextField getTargetTextField() {
		return targetTextField;
	}
	public void setTargetTextField(JTextField targetTextField) {
		this.targetTextField = targetTextField;
	}
}
