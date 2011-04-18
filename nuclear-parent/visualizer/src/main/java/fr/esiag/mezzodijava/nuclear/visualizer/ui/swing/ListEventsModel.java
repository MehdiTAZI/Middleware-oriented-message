package fr.esiag.mezzodijava.nuclear.visualizer.ui.swing;

import javax.swing.JButton;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

public class ListEventsModel extends  AbstractTableModel{

	private Object data[][]={
			{"YRYA","DATE","RadioActivite",new JButton("Detail")},
			{"JGFYT","DATE","Strig",new JButton("Detail")},
			{"ONYA","DATE","Pression",new JButton("Detail")},
			{"PMLO","DATE","Temperature",new JButton("Detail")},
	};
	
	private String headers[]={"Code","Date","Type Event","Details"};

	
	public ListEventsModel() {
		
	}
		
	
	@Override
	public int getColumnCount() {
		
		return headers.length;
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return data.length;
	}

	@Override
	public Object getValueAt(int row, int col) {
		
		return data[row][col];
	}
	
	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		
		//super.setValueAt(aValue, rowIndex, columnIndex);
		data[rowIndex][columnIndex]=aValue;
		fireTableRowsUpdated(rowIndex, columnIndex);
	}

	
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
	    return true; //Toutes les cellules éditables
	}
	

}
