package fr.esiag.mezzodijava.nuclear.visualizer.ui.swing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;



public class Menu extends JMenuBar implements ActionListener{
	
	private MainVisualizerMorocco frame;
	private long indexSubscription=1;
	private long indexConnection=1;
	
	public Menu(String M[][],MainVisualizerMorocco frame){
		this.frame=frame;
		for (int i = 0; i < M.length; i++) {
			JMenu m=new JMenu(M[i][0]);
			for (int j = 1; j < M[i].length; j++) {
				if(M[i][j].equals("-"))
					m.addSeparator();
				else{
					JMenuItem mi=new JMenuItem(M[i][j]);
					mi.addActionListener(this);
					m.add(mi);
				}
			}
			add(m);
		}
	}

	public void addActionListener(ActionListener al){
		for (int i = 0; i <this.getComponentCount(); i++) {
				JMenuItem m3=(JMenuItem)this.getComponent(i);
				m3.addActionListener(al);	
			
		}
	}

	
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("Quitter"))
			System.exit(0);
		
		else if(e.getActionCommand().equals("Subscription")){
				//SubscriptionPanel sp=new SubscriptionPanel(frame);
			if(indexSubscription == 1){
			    frame.getSubscriptionPanel().initComponents();
			    indexSubscription++;
			}
			else
				frame.getSubscriptionPanel().setVisible(true);
											
				
		}
		else if(e.getActionCommand().equals("Connection")){
			
			if(!frame.getInformationPanel().getIdComponent().getText().equals("unknown")){
				//ConnectionPanel cp=new ConnectionPanel(frame);
				if(indexConnection == 1){
					frame.getConnectionPanel().initComponents();
					indexConnection++;
				}
				else
					frame.getConnectionPanel().setVisible(true);
			}
			else {
				JOptionPane.showMessageDialog(frame,"You must subscribe before connecting." ,"Warning Message",JOptionPane.WARNING_MESSAGE);
			}
			
		}
		else if(e.getActionCommand().equals("About Visualizer Morocco")){
			
			AboutVisualizerMorroco avm=new AboutVisualizerMorroco();
			
		}
		
	}
}
