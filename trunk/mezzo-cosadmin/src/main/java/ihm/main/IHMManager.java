package ihm.main;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;

import fr.esiag.mezzodijava.mezzo.cosadmin.CosManager;

public class IHMManager extends JFrame{
	JList listServeur;
	JList listTimeServeur;
	JList listChannels;
	JPanel panelMethod;
	JPanel panelModify;
	JButton buttonNew;
	JButton buttonModify;
	JButton buttonDelete;
	JLabel labelCapacity;
	JTextField textFieldCapacity;
	public IHMManager(){
		final CosManager manager = new CosManager(args, properties);
		
		listServeur = new JList(new Vector<String>(manager.listEventServers()));
		listTimeServeur = new JList(new Vector<String>(manager.listTimeServers()));
		listChannels = new JList(new Vector<String>(manager.listEventChannel()));
		
		panelMethod = new JPanel(new BorderLayout());
		panelModify = new JPanel(new GridLayout(1, 3));
		
		buttonNew = new JButton("new channel");
		buttonModify = new JButton("modify channel");
		buttonDelete = new JButton("delete channel");
		
		labelCapacity = new JLabel("new capacity");
		
		textFieldCapacity = new JTextField();
		
		this.setLayout(new GridLayout(4, 1));
		this.add(listServeur);
		this.add(listTimeServeur);
		this.add(listChannels);
		this.add(panelMethod);
		
		panelMethod.add(buttonNew);
		panelMethod.add(panelModify);
		panelMethod.add(buttonDelete);
		
		panelModify.add(labelCapacity);
		panelModify.add(textFieldCapacity);
		panelModify.add(buttonModify);
		
		buttonNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (checkNew()){
					manager.addChannel((String)listServeur.getSelectedValue(), (String)listChannels.getSelectedValue());
				}
				
			}
		});
		
		buttonModify.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (checkModify()){
					manager.modChannel((String)listChannels.getSelectedValue(), Integer.valueOf(textFieldCapacity.getSelectedText()));
				}
				
			}
		});
		
		buttonDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (checkDeleted()){
					manager.delChannel((String)listChannels.getSelectedValue());
				}
				
			}
		});
		
		
	}
	protected boolean checkDeleted() {
		if (listChannels.isSelectionEmpty())
			return false;
		return true;
	}
	protected boolean checkModify() {
		if (listChannels.isSelectionEmpty()){
			try {
				int n =Integer.valueOf(textFieldCapacity.getSelectedText());
			}
			catch (Exception e){
				return false;
			}
		}
		return true;
	}
	protected boolean checkNew() {
		if (listServeur.isSelectionEmpty() || listChannels.isSelectionEmpty())
			return false;
		return true;
	}
	public static void main(String[] args) {
		IHMManager ihm = new IHMManager();
		ihm.pack();
		ihm.setVisible(true);
	}

}
