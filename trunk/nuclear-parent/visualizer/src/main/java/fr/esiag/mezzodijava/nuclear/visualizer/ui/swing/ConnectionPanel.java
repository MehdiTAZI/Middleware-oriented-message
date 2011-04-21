package fr.esiag.mezzodijava.nuclear.visualizer.ui.swing;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;


import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import fr.esiag.mezzodijava.mezzo.cosevent.AlreadyConnectedException;
import fr.esiag.mezzodijava.mezzo.cosevent.ChannelNotFoundException;
import fr.esiag.mezzodijava.mezzo.cosevent.MaximalConnectionReachedException;
import fr.esiag.mezzodijava.mezzo.cosevent.NotConnectedException;
import fr.esiag.mezzodijava.mezzo.cosevent.NotRegisteredException;

public class ConnectionPanel extends JFrame implements ActionListener{

 

		/**
		 * @param args
		 */
		private JLabel labelIdComponent;
		private JTextField idComponent;
		private JLabel labelChannelName;
		private JTextField channelName;
		private boolean isConnected=false;
		
		private JLabel initConnection;
		private JButton connection;
		
		private JProgressBar pbar;
		private final int MIN=0;
		private final int MAX=100;
		
		private MainVisualizerMorocco frame;
		private JButton close;
		
		private static int counter=0; 
		
		public ConnectionPanel(MainVisualizerMorocco frame) {
			this.frame=frame;
			
			
		}
		
		
		
		public void initComponents() {			
			
			setSize(400,130);
						
			channelName=new JTextField(20);
			idComponent=new JTextField(20);
			connection=new JButton("Connect");
			close=new JButton("Close");
			
			
			
			 // Get the size of the screen
		    Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

			  // Determine the new location of the window
		    int w = getSize().width;
		    int h = getSize().height;
		    int x = (dim.width-w)/2;
		    int y = (dim.height-h)/2;
		    
		    // Move the window
		    setBounds(x, y, w, h);
			
			JPanel p=new JPanel();
			p.setLayout(new FlowLayout(FlowLayout.CENTER));
			
			JPanel q=new JPanel();
			q.setLayout(new BoxLayout(q, BoxLayout.X_AXIS));
			labelIdComponent=new JLabel("Component ID: ");
						
			idComponent.setEnabled(false);
			q.add(labelIdComponent);
			q.add(idComponent);
			
			
			JPanel r=new JPanel();
			r.setLayout(new BoxLayout(r, BoxLayout.X_AXIS));
			labelChannelName=new JLabel("Channel Name : ");
			
			channelName.setEnabled(false);
			r.add(labelChannelName);
			r.add(channelName);
			
			if(frame.getInformationPanel().getIdComponent().getText()!=null && frame.getInformationPanel().getChannelName().getText()!=null){
				idComponent.setText(frame.getInformationPanel().getIdComponent().getText());
				channelName.setText(frame.getInformationPanel().getChannelName().getText());
			}
			
			p.add(q);
			p.add(r);
			
			
			connection.setPreferredSize(new Dimension(100,25));
			connection.addActionListener(this);
						
			close.setPreferredSize(new Dimension(100,25));
			close.addActionListener(this);
			
			p.add(connection,BorderLayout.SOUTH);
			p.add(close,BorderLayout.SOUTH);
			
			getContentPane().add(p);
			
			setVisible(true);
			
		}
	

		@Override
		public void actionPerformed(ActionEvent e) {
			JButton b=(JButton)e.getSource();
			
			if(b.getText().equals("Connect")){
																
				try {
					//ProgressMonitorPanel pmp=new ProgressMonitorPanel(true);
					
					frame.getConsumerProxy().connect(frame.getCallbackConsumer());
					
					
//					if(pmp.isOK()==true){
					JOptionPane.showMessageDialog(
							this,
							"The Component "+ idComponent.getText() +" is successfully connected \n to channel "+channelName.getText(),
							"Success Connection", 
							JOptionPane.INFORMATION_MESSAGE);
//					}
//					else 
//						JOptionPane.showMessageDialog(
//								this,
//								"The Component "+ idComponent.getText() +" is Failled to conecting \n to channel "+channelName.getText(),
//								"Failled Connection", 						
//								JOptionPane.ERROR_MESSAGE);
					
					isConnected=true;
					connection.setText("Disconnect");
					//this.frame.getStatePanel().getConnectionValue().setText("Connected                   ");
					
				} catch (ChannelNotFoundException e1) {
					
					JOptionPane.showMessageDialog(this,e1.getMessage() ,"Error Channel Not Found",JOptionPane.ERROR_MESSAGE);
				} catch (NotRegisteredException e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(this,e1.getMessage() ,"Error Not Registred ",JOptionPane.ERROR_MESSAGE);
				} catch (MaximalConnectionReachedException e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(this,e1.getMessage() ,"Error Maximal Connected Reached ",JOptionPane.ERROR_MESSAGE);
				} catch (AlreadyConnectedException e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(this,e1.getMessage() ,"Error Already Connected",JOptionPane.ERROR_MESSAGE);
				}
				
				
				
				
				
				}
			else if(b.getText().equals("Disconnect")){
				try {
					isConnected=false;
					frame.getConsumerProxy().disconnect();
					
					JOptionPane.showMessageDialog(
							this,
							"The Component "+ idComponent.getText() +" is successfully disconnected \n to channel "+channelName.getText(),
							"Success Disconnection", 
							JOptionPane.INFORMATION_MESSAGE);
					connection.setText("Connect");
					//this.frame.getStatePanel().getConnectionValue().setText("Not Connected                   ");
				} catch (ChannelNotFoundException e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(this,e1.getMessage() ,"Error Channel Not Found",JOptionPane.ERROR_MESSAGE);
				} catch (NotRegisteredException e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(this,e1.getMessage() ,"Error Not Registred",JOptionPane.ERROR_MESSAGE);
				} catch (NotConnectedException e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(this,e1.getMessage() ,"Error Not Connected",JOptionPane.ERROR_MESSAGE);
				}
		}
			
			else if(b.getText().equals("Close")){
				this.setVisible(false);
				if(isConnected == true){
					this.frame.getStatePanel().getConnectionValue().setText("Connected                   ");
				}
				else{ 
					this.frame.getStatePanel().getConnectionValue().setText("Disconnected                   ");
					
				}

			}
			}



		public JTextField getChannelName() {
			return channelName;
		}



		public void setChannelName(JTextField channelName) {
			this.channelName = channelName;
		}



		public boolean isConnected() {
			return isConnected;
		}



		public void setConnected(boolean isConnected) {
			this.isConnected = isConnected;
		}




	

}
