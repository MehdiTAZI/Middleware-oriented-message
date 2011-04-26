package ihm.main;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Properties;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import fr.esiag.mezzodijava.mezzo.cosadmin.CosManager;

public class IHMManager extends JFrame {
	final CosManager manager;
    JFrame frame;
    JScrollPane paneServeur;
    JScrollPane paneTimeServeur;
    JScrollPane paneChannels;
    JScrollPane paneMethod;
    JList listServeur;
    JList listTimeServeur;
    JList listChannels;
    JPanel panelMethod;
    JPanel panelModify;
    JPanel panelNew;
    JButton buttonNew;
    JButton buttonModify;
    JButton buttonDelete;
    JLabel labelCapacity;
    JLabel labelNew;
    JTextField textFieldNew;
    JTextField textFieldCapacity;
    
    

    public IHMManager(String[] args, Properties properties) {
	frame = this;
	manager = new CosManager(args, properties);
	try {
	    listServeur = new JList(new Vector<String>(
		    manager.listEventServers()));	    
	    listTimeServeur = new JList(new Vector<String>(
		    manager.listTimeServers()));
	    listChannels = new JList(new Vector<String>(
		    manager.listEventChannel()));
	    
	    listChannels.addMouseListener(new MouseRight());
	    	
	    
	} catch (Exception e) {
	    JOptionPane.showMessageDialog(frame, "Erreur: " + e.toString());
	}
	frame = this;
	panelMethod = new JPanel(new GridLayout(3,1));
	panelModify = new JPanel(new GridLayout(1, 3));
	panelNew = new JPanel(new GridLayout(1,3));
	
	
	paneServeur = new JScrollPane(listServeur);
	paneServeur.setBorder(BorderFactory.createTitledBorder("Serveur"));
	paneTimeServeur = new JScrollPane(listTimeServeur);
	paneTimeServeur.setBorder(BorderFactory.createTitledBorder("Time Serveur"));
	paneChannels = new JScrollPane(listChannels);
	paneChannels.setBorder(BorderFactory.createTitledBorder("Channels"));
	paneMethod = new JScrollPane(panelMethod);

	buttonNew = new JButton("new channel");
	buttonModify = new JButton("modify channel");
	buttonDelete = new JButton("delete channel");

	labelNew = new JLabel("channel's name");
	labelCapacity = new JLabel("capacity");

	textFieldNew = new JTextField();
	textFieldCapacity = new JTextField();

	this.setLayout(new GridLayout(4, 1));
	this.add(paneServeur);
	this.add(paneTimeServeur);
	this.add(paneChannels);
	this.add(paneMethod);

	panelMethod.add(panelNew);
	panelMethod.add(panelModify);
	panelMethod.add(buttonDelete);

	panelNew.add(labelNew);
	panelNew.add(textFieldNew);
	panelNew.add(buttonNew);
	
	panelModify.add(labelCapacity);
	panelModify.add(textFieldCapacity);
	panelModify.add(buttonModify);

	buttonNew.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent arg0) {
		if (checkNew()) {
		    try {
			manager.addChannel((String) listServeur
				.getSelectedValue(), textFieldNew.getText(), Integer
				.valueOf(textFieldCapacity.getText()));
			refresh();
			IHMManager.this.listChannels.repaint();
		    } catch (Exception e) {
		    	
		    	e.printStackTrace();
			JOptionPane.showMessageDialog(frame,
				"Erreur NEW: " + e.toString());
		    }
		}
		else
			System.out.println("can't create");

	    }
	});

	buttonModify.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		if (checkModify()) {
		    try {
			manager.modChannel((String) listChannels
				.getSelectedValue(), Integer
				.valueOf(textFieldCapacity.getText()));
			refresh();
			IHMManager.this.repaint();
		    } catch (Exception ex) {
		    	ex.printStackTrace();
			JOptionPane.showMessageDialog(frame,
				"Erreur MODIFY: " + ex.toString());
		    }
		}

	    }
	});

	buttonDelete.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent arg0) {
		if (checkDeleted()) {
		    try {
		    	System.out.println(listChannels.getSelectedValue().getClass());
			manager.delChannel((String) listChannels
				.getSelectedValue());
			refresh();
			IHMManager.this.repaint();
		    } catch (Exception e) {
		    	e.printStackTrace();
			JOptionPane.showMessageDialog(frame,
				"Erreur DELETE: " + e.toString());
		    }
		}

	    }
	});

	
    }
    private void refresh(){
    	try {
    		
    	    listServeur = new JList(new Vector<String>(
    		    manager.listEventServers()));
    	    listTimeServeur = new JList(new Vector<String>(
    		    manager.listTimeServers()));
    	    listChannels = new JList(new Vector<String>(
    		    manager.listEventChannel()));
    	    paneServeur.setViewportView(listServeur);
    	    paneTimeServeur.setViewportView(listTimeServeur);
    	    paneChannels.setViewportView(listChannels);
    	    listChannels.addMouseListener(new MouseRight());
    	    repaint();
    	} catch (Exception e) {
    	    JOptionPane.showMessageDialog(frame, "Erreur: " + e.toString());
    	}
    }

    protected boolean checkDeleted() {
	if (listChannels.isSelectionEmpty()){
		System.out.println("del false");
	    return false;
	}
	System.out.println("del true");
	return true;
    }

    protected boolean checkModify() {
	if (listChannels.isSelectionEmpty()) {
	    try {
		int n = Integer.valueOf(textFieldCapacity.getSelectedText());
	    } catch (Exception e) {
		return false;
	    }
	}
	return true;
    }

    protected boolean checkNew() {
	if (listServeur.isSelectionEmpty() || textFieldNew.getText().equals(""))
	    return false;
	return true;
    }
    

    public static void main(String[] args) {
	IHMManager ihm = new IHMManager(args, null);
	ihm.pack();
	ihm.setVisible(true);
    }
   
public class MouseRight extends MouseAdapter{
	private void action(MouseEvent e){
		
		System.out.println(e.getButton());
		if(e.getButton()== MouseEvent.BUTTON3 & IHMManager.this.listChannels.getSelectedValue()!=null){
			System.out.println("Remove");
			try {
				if (manager.listEventChannel().contains(IHMManager.this.listChannels.getSelectedValue()));
				manager.delChannel((String)IHMManager.this.listChannels.getSelectedValue());
				refresh();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		action(e);
		super.mouseReleased(e);
	}
	@Override
	public void mousePressed(MouseEvent e) {
		action(e);
		super.mousePressed(e);
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		action(e);
		super.mouseClicked(e);
	}
	
    }
    

}

