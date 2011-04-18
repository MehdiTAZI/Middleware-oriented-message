package fr.esiag.mezzodijava.nuclear.visualizer.ui.swing;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class ButtonRendrer extends JButton implements TableCellRenderer{

	
	public ButtonRendrer() {
		//setOpaque(true);
	}
	
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		
		//super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

		if(column == 3){
		JButton b=(JButton)value;
		b.setText("Detail");
		//b.setPreferredSize(new Dimension(10, 10));
		setText("Detail");
		setPreferredSize(new Dimension(10, 10));
		
		
		// TODO Auto-generated method stub
		
		return this;
		}
		else{
			System.out.println("row "+row +" , col "+column);
			return null;
		}
	}

}
