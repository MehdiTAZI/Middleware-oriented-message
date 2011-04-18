package fr.esiag.mezzodijava.nuclear.visualizer.ui.swing;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

public class ButtonEditor extends AbstractCellEditor implements TableCellEditor, ActionListener {


	private JButton button;
	protected static final String EDIT = "edit";

	
	public ButtonEditor() {
		button=new JButton();
		button.setActionCommand("edit");
	     button.addActionListener(this);
	     button.setBorderPainted(false);


			

	}

	public void actionPerformed(ActionEvent e) {
		
		System.out.println("test");
		if (EDIT.equals(e.getActionCommand())) {
			//The user has clicked the cell, so
			//bring up the dialog.
			
			System.out.println("appel");				
			fireEditingStopped();
			//Make the renderer reappear.

	
	}
	}
	//Implement the one CellEditor method that AbstractCellEditor doesn't.
	public Object getCellEditorValue() {
		return button;
	}

	//Implement the one method defined by TableCellEditor.
	public Component getTableCellEditorComponent(JTable table,
			Object value,
			boolean isSelected,
			int row,
			int column) {
		
		button = (JButton)value;
		System.out.println("get"+button.getText());
		return button;
	}


}
