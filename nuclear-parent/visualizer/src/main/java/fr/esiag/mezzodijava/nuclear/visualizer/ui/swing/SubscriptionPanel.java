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

import fr.esiag.mezzodijava.mezzo.cosevent.AlreadyRegisteredException;
import fr.esiag.mezzodijava.mezzo.cosevent.CallbackConsumer;
import fr.esiag.mezzodijava.mezzo.cosevent.ChannelNotFoundException;
import fr.esiag.mezzodijava.mezzo.cosevent.NotRegisteredException;
import fr.esiag.mezzodijava.mezzo.libclient.exception.EventClientException;
import fr.esiag.mezzodijava.mezzo.libclient.exception.TopicNotFoundException;

public class SubscriptionPanel extends JFrame implements ActionListener{

	/**
	 * 
	 */

	/**
	 * @param args
	 */
	
	
	private JLabel labelIdComponent;
	private JTextField idComponent;
	private JLabel labelChannelName;
	private JTextField channelName;
	private JButton subscription;
	private JButton close;
	private MainVisualizerMorocco frame;
	private boolean isSubscribed=false;
	


	public SubscriptionPanel(MainVisualizerMorocco frame) {				
		this.frame=frame;
		
	}



	public void initComponents() {

		setSize(400,130);
		setVisible(true);
		
		idComponent=new JTextField(20);
		channelName=new JTextField(20);
		if(frame.getStatePanel().getSubsriptionValue().getText().trim().equals("Unsubscribed")){
			System.out.println(frame.getStatePanel().getSubsriptionValue().getText().trim()+"$");
			subscription=new JButton("Subscribe");
		}
		else{
			System.out.println(frame.getStatePanel().getSubsriptionValue().getText().trim()+"$");
			subscription=new JButton("Unsubscribe");
		}
		
		close=new JButton("Close");
		
		idComponent.setText(frame.getInformationPanel().getIdComponent().getText());		
		channelName.setText(frame.getInformationPanel().getChannelName().getText());
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
						
		q.add(labelIdComponent);
		q.add(idComponent);


		JPanel r=new JPanel();
		r.setLayout(new BoxLayout(r, BoxLayout.X_AXIS));
		labelChannelName=new JLabel("Channel Name : ");
		
		r.add(labelChannelName);
		r.add(channelName);

		p.add(q);
		p.add(r);

		
		subscription.setPreferredSize(new Dimension(100,25));
		subscription.addActionListener(this);
		
		
		close.setPreferredSize(new Dimension(100,25));
		close.addActionListener(this);

		p.add(subscription,BorderLayout.SOUTH);
		p.add(close,BorderLayout.SOUTH);
		getContentPane().add(p);

	}





	@Override
	public void actionPerformed(ActionEvent e) {
		JButton b=(JButton)e.getSource();

		if(b.getText().equals("Subscribe")){
			
			try {
				//ProgressMonitorPanel pmp=new ProgressMonitorPanel(true);
				
				frame.setChannelAdmin(frame.getEc().resolveChannelByTopic(channelName.getText()));				
				frame.setConsumerProxy(frame.getChannelAdmin().getProxyForPushConsumer(idComponent.getText()));
				frame.setCallbackConsumer(frame.getEc().serveCallbackConsumer(frame.getCallback()));				
				frame.getConsumerProxy().subscribe();
				

//				if(pmp.isOK()==true){
				
				JOptionPane.showMessageDialog(
						this,
						"The Component "+ idComponent.getText() +" is successfully subscribed \n to channel "+channelName.getText(),
						"Success Subscription", 						
						JOptionPane.INFORMATION_MESSAGE);
				
				
				
//				System.out.println(channelName.getText()+" "+idComponent.getText());
				
//				}
//				else 
//					JOptionPane.showMessageDialog(
//							this,
//							"The Component "+ idComponent.getText() +" is Failled to subscribing \n to channel "+channelName.getText(),
//							"Failled Subscription", 						
//							JOptionPane.ERROR_MESSAGE);
				
				isSubscribed=true;
				subscription.setText("Unsubscribe");
				this.frame.getStatePanel().getSubsriptionValue().setText("Subscribed");
			} catch (AlreadyRegisteredException e1) {
					// TODO Auto-generated catch block
					
				JOptionPane.showMessageDialog(this,e1.getMessage() ,"Error Already Registred",JOptionPane.ERROR_MESSAGE);	
				
			}catch (ChannelNotFoundException e1) {
					// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(this,e1.getMessage() ,"Error Channel Not Found",JOptionPane.ERROR_MESSAGE);
				
				
			} catch (EventClientException e1) {
				// TODO Auto-generated catch block
				
				JOptionPane.showMessageDialog(this,e1.getMessage() ,"Error Event Client",JOptionPane.ERROR_MESSAGE);
			} catch (TopicNotFoundException e1) {
				JOptionPane.showMessageDialog(this,e1.getMessage() ,"Error Topic Not Found",JOptionPane.ERROR_MESSAGE);
				
			}
						
	}
		else if(b.getText().equals("Unsubscribe")){
			try {
				isSubscribed=false;
				frame.getConsumerProxy().unsubscribe();

				JOptionPane.showMessageDialog(
						this,
						"The Component "+ idComponent.getText() +" is successfully unsubscribed  \n to channel "+channelName.getText(),
						"Success Unsubscription", 						
						JOptionPane.INFORMATION_MESSAGE);
				
				subscription.setText("Subscribe");
				this.frame.getStatePanel().getSubsriptionValue().setText("Unsubscribed");	
			} catch (ChannelNotFoundException e1) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(this,e1.getMessage() ,"Error Channel Not Found",JOptionPane.ERROR_MESSAGE);
			} catch (NotRegisteredException e1) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(this,e1.getMessage() ,"Error Not Registred",JOptionPane.ERROR_MESSAGE);
			}
	

		}
		else if(b.getText().equals("Close")){
			
			this.setVisible(false);
			if(!idComponent.getText().equals("") && !channelName.getText().equals("")){
			this.frame.getInformationPanel().getIdComponent().setText(idComponent.getText());
			this.frame.getInformationPanel().getChannelName().setText(channelName.getText());
			}
			
			if(isSubscribed == true){
				this.frame.getStatePanel().getSubsriptionValue().setText("Subscribed                   ");
				this.frame.getInformationPanel().getIdComponent().setText(idComponent.getText());
				this.frame.getInformationPanel().getChannelName().setText(channelName.getText());
			}
			else{
				this.frame.getStatePanel().getSubsriptionValue().setText("Unsubscribed                   ");
				this.frame.getInformationPanel().getIdComponent().setText("unknown");
				this.frame.getInformationPanel().getChannelName().setText("unknown");
				idComponent.setText("unknown");
				channelName.setText("unknown");
			}

		}
	}



	public JTextField getIdComponent() {
		return idComponent;
	}



	public void setIdComponent(String idComponent) {
		this.idComponent.setText(idComponent);
	}



	public JTextField getChannelName() {
		return channelName;
	}



	public void setChannelName(String channelName) {
		this.channelName.setText( channelName);
	}




}
