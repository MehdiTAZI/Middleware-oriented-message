package fr.esiag.mezzodijava.nuclear.visualizer.ui.swing;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class CustomTableCellRendrer extends DefaultTableCellRenderer 
{
	public Component getTableCellRendererComponent
	(JTable table, Object value, boolean isSelected,
			boolean hasFocus, int row, int column) 
	{
		Component cell = super.getTableCellRendererComponent
		(table, value, isSelected, hasFocus, row, column);
		
		if( value instanceof Integer )
		{
			Integer amount = (Integer) value;
			if( amount.intValue() == 3 )
			{
				cell.setBackground( Color.red );
				// You can also customize the Font and Foreground this way
				// cell.setForeground();
				// cell.setFont();
			}
			else if( amount.intValue() == 2 )
			{
				cell.setBackground( Color.orange );
				// You can also customize the Font and Foreground this way
				// cell.setForeground();
				// cell.setFont();
			}
			else
			{
				cell.setBackground( Color.white );
			}
		}
		return cell;
	}
}

