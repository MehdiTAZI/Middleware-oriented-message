package fr.esiag.mezzodijava.nuclear.visualizer.ui.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

public class EventsPanel extends JPanel{

	private JTable listEventsTable;	
	private DefaultTableModel tableModel;
	private String headers[]={"Code","Date","Priority","Type Event"};
	private int currentRow;
	
	
	public EventsPanel() {
		initComponents();
	}

	public void initComponents() {
		
						

		tableModel=new DefaultTableModel(new String[][]{},headers);
		

		setLayout(new GridLayout(1,1,10,10));
		CompoundBorder comp=new CompoundBorder(new TitledBorder(null,"Alerts Received Events List",TitledBorder.CENTER,0,null,Color.BLACK), new BevelBorder(BevelBorder.RAISED));
		setBorder(comp);
		listEventsTable=new JTable(tableModel);
		
		listEventsTable.setShowHorizontalLines( false );
		listEventsTable.setRowSelectionAllowed( true );
		listEventsTable.setColumnSelectionAllowed( true );
		listEventsTable.getTableHeader().setBackground(Color.LIGHT_GRAY);
		
		
		JScrollPane jsp=new JScrollPane(listEventsTable);
		add(jsp);
		
	}

	public JTable getListEventsTable() {
		return listEventsTable;
	}

	public void setListEventsTable(JTable listEventsTable) {
		this.listEventsTable = listEventsTable;
	}

	public DefaultTableModel getModel() {
		return tableModel;
	}

	public void setModel(DefaultTableModel model) {
		this.tableModel = model;
	}

	public int getCurrentRow() {
		return currentRow;
	}

	public void setCurrentRow(int currentRow) {
		this.currentRow = currentRow;
	}

	public DefaultTableModel getTableModel() {
		return tableModel;
	}

	public void setTableModel(DefaultTableModel tableModel) {
		this.tableModel = tableModel;
	}
	
	
	
	
}
