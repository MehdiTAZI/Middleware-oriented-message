package fr.esiag.mezzodijava.nuclear.visualizer.ui.swing;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

public class ListEventsListener implements TableModelListener{

	
	public ListEventsListener() {
		

	}
	@Override
	public void tableChanged(TableModelEvent e) {
		
		System.out.println("appel change");
		int row = e.getFirstRow();
        int column = e.getColumn();
        System.out.println("row "+row +" , col "+column);
        if(column < 3){
        TableModel model = (TableModel)e.getSource();
        String columnName = model.getColumnName(column);
        System.out.println(columnName);
        Object data = model.getValueAt(row, column);
        System.out.println("data selectionne--> "+data.toString());
        }
		
	}

}
