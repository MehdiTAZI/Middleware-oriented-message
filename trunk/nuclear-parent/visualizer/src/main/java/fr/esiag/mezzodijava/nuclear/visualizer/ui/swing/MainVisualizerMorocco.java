package fr.esiag.mezzodijava.nuclear.visualizer.ui.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.omg.CORBA.ORB;

import fr.esiag.mezzodijava.mezzo.cosevent.AlreadyConnectedException;
import fr.esiag.mezzodijava.mezzo.cosevent.AlreadyRegisteredException;
import fr.esiag.mezzodijava.mezzo.cosevent.CallbackConsumer;
import fr.esiag.mezzodijava.mezzo.cosevent.CallbackConsumerOperations;
import fr.esiag.mezzodijava.mezzo.cosevent.ChannelAdmin;
import fr.esiag.mezzodijava.mezzo.cosevent.ChannelNotFoundException;
import fr.esiag.mezzodijava.mezzo.cosevent.ConsumerNotFoundException;
import fr.esiag.mezzodijava.mezzo.cosevent.Event;
import fr.esiag.mezzodijava.mezzo.cosevent.MaximalConnectionReachedException;
import fr.esiag.mezzodijava.mezzo.cosevent.NotRegisteredException;
import fr.esiag.mezzodijava.mezzo.cosevent.ProxyForPushConsumer;
import fr.esiag.mezzodijava.mezzo.libclient.EventClient;
import fr.esiag.mezzodijava.mezzo.libclient.exception.EventClientException;
import fr.esiag.mezzodijava.mezzo.libclient.exception.TopicNotFoundException;
import fr.esiag.nuclear.commons.model.Pression;
import fr.esiag.nuclear.commons.model.RadioActivite;
import fr.esiag.nuclear.commons.model.Temperature;

public class MainVisualizerMorocco extends JFrame implements ListSelectionListener,ActionListener{

	private InformationPanel informationPanel=new InformationPanel();
	private StatePanel statePanel=new StatePanel();
	private StatisticPanel statPanel=new StatisticPanel();
	private EventsPanel eventsPanel=new EventsPanel();
	private DetailEventPanel detailEventPanel=new DetailEventPanel();	
	private EventClient ec;
    private ChannelAdmin channelAdmin;    
    private ProxyForPushConsumer consumerProxy;     
    private Callback callback = new Callback(this);
    private CallbackConsumer callbackConsumer;
    private static Sound sound;
    private static List<Event> listEvents=new ArrayList<Event>();
    private ConnectionPanel connectionPanel;
    private SubscriptionPanel subscriptionPanel;
	
	
	
	public MainVisualizerMorocco(String[] args) {
		
		try {
			ec=EventClient.init(args);
		} catch (EventClientException e) {
			// TODO Auto-generated catch block			
			JOptionPane.showMessageDialog(this,e.getMessage() ,"Error Event Client",JOptionPane.ERROR_MESSAGE);
		}
		
						
		connectionPanel=new ConnectionPanel(this);
		subscriptionPanel=new SubscriptionPanel(this);
		
		ListSelectionModel lsm= eventsPanel.getListEventsTable().getSelectionModel();
		
		lsm.addListSelectionListener(this);
		setTitle("Visualizer Morocco");
		//setResizable(false);
		
		 // Get the size of the screen
	    Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	  
		
		
		Menu m=new Menu(new String[][]{
				{"File","Subscription","Connection","-","Quitter"},
				//{"Edit","Options","outils"},
				{"Help","About Visualizer Morocco"}
				
		},this);
		m.addActionListener(this);
		setJMenuBar(m);
		
		
		JPanel p=new JPanel();
		p.setLayout(new BorderLayout());
	
		p.add(statePanel,BorderLayout.WEST);
		//p.add(alerteEventPanel,BorderLayout.CENTER);
		p.add(statPanel,BorderLayout.EAST);		
		
		

		
					
		
		JPanel global=new JPanel();
		//global.setLayout(new GridLayout(4, 1, 20,10));
		global.setLayout(new BoxLayout(global,BoxLayout.Y_AXIS));
		global.add(informationPanel);
		global.add(new JLabel("          "));
		global.add(p);
						
		global.add(new JLabel("          "));
		global.add(eventsPanel);
		global.add(new JLabel("          "));
		global.add(detailEventPanel);
		
		getContentPane().add(global);
		setSize(800, 700);
		
		
		
		  
	    // Determine the new location of the window
	    int w = getSize().width;
	    int h = getSize().height;
	    int x = (dim.width-w)/2;
	    int y = (dim.height-h)/2;
	    
	    // Move the window
	    setBounds(x, y, w, h);
		
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	public static void main(String[] args) {
		SplashScreen sp=new SplashScreen();
		new MainVisualizerMorocco(args);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

	public InformationPanel getInformationPanel() {
		return informationPanel;
	}

	public void setInformationPanel(InformationPanel informationPanel) {
		this.informationPanel = informationPanel;
	}

	public StatePanel getStatePanel() {
		return statePanel;
	}

	public void setStatePanel(StatePanel statePanel) {
		this.statePanel = statePanel;
	}

	public StatisticPanel getStatPanel() {
		return statPanel;
	}

	public void setStatPanel(StatisticPanel statPanel) {
		this.statPanel = statPanel;
	}

	public EventsPanel getEventsPanel() {
		return eventsPanel;
	}

	public void setEventsPanel(EventsPanel eventsPanel) {
		this.eventsPanel = eventsPanel;
	}



	public EventClient getEc() {
		return ec;
	}



	public void setEc(EventClient ec) {
		this.ec = ec;
	}



	public ChannelAdmin getChannelAdmin() {
		return channelAdmin;
	}



	public void setChannelAdmin(ChannelAdmin channelAdmin) {
		this.channelAdmin = channelAdmin;
	}



	public ProxyForPushConsumer getConsumerProxy() {
		return consumerProxy;
	}



	public void setConsumerProxy(ProxyForPushConsumer consumerProxy) {
		this.consumerProxy = consumerProxy;
	}



	public Callback getCallback() {
		return callback;
	}



	public void setCallback(Callback callback) {
		this.callback = callback;
	}



	public CallbackConsumer getCallbackConsumer() {
		return callbackConsumer;
	}



	public void setCallbackConsumer(CallbackConsumer callbackConsumer) {
		this.callbackConsumer = callbackConsumer;
	}

	
	 public static class Callback implements CallbackConsumerOperations {

		 private MainVisualizerMorocco frame;
		 private int changeStateCounter=1;
		 private int alerteCounter=1;
		 public Callback(MainVisualizerMorocco frame) {
			this.frame=frame;
		}
			@Override
			public void receive(Event evt) throws ConsumerNotFoundException {
				sound=new Sound("alerte.wav");
				listEvents.add(evt);
				if(evt.header.priority == 2)
					frame.statPanel.getNbrChangeStateValue().setText("   "+changeStateCounter++);
				if(evt.header.priority == 3){
					frame.statPanel.getNbrAlerteValue().setText("   "+alerteCounter++);
					
				}
				
			    if (evt.body.type.equals("String")) {
//			    	String s="priority:" + evt.header.priority + ",date:"
//					+ (new Date(evt.header.creationdate)).toGMTString()
//					+ " content:" + evt.body.content.extract_string();
			    	
		//	    	JOptionPane.showMessageDialog( frame,s,"" ,JOptionPane.INFORMATION_MESSAGE);
//			    	
//			    	frame.getEventsPanel().getModel().setValueAt(evt.header.code, frame.getEventsPanel().getCurrentRow(), 0);
//			    	frame.getEventsPanel().getModel().setValueAt(""+evt.header.creationdate, frame.getEventsPanel().getCurrentRow(), 1);			    	
//			    	frame.getEventsPanel().getModel().setValueAt(evt.body.type, frame.getEventsPanel().getCurrentRow(), 2);
//			    	
//			    	frame.getEventsPanel().setCurrentRow(frame.getEventsPanel().getCurrentRow()+1);
			    	 String[] tmp=new String[]{""+evt.header.code,new Date(evt.header.creationdate).toGMTString(),""+evt.header.priority,evt.body.type};
					    frame.getEventsPanel().getTableModel().addRow(tmp);		    	
			    	
//				System.out.println("priority:" + evt.header.priority + ",date:"
//					+ (new Date(evt.header.creationdate)).toGMTString()
//					+ " content:" + evt.body.content.extract_string());
			    }

			    if (evt.body.type.equals("Temperature")) {
				Temperature t = (Temperature) evt.body.content.extract_Value();
//				System.out.println("priority:" + evt.header.priority + ",date:"
//					+ (new Date(evt.header.creationdate)).toGMTString()
//					+ " content:" + t.getValue() + "|" + t.getUnite());
					
				
//				frame.getEventsPanel().getModel().setValueAt(evt.header.code, frame.getEventsPanel().getCurrentRow(), 0);
//		    	frame.getEventsPanel().getModel().setValueAt(""+evt.header.creationdate, frame.getEventsPanel().getCurrentRow(), 1);			    	
//		    	frame.getEventsPanel().getModel().setValueAt(evt.body.type, frame.getEventsPanel().getCurrentRow(), 2);
//		    	
//		    	frame.getEventsPanel().setCurrentRow(frame.getEventsPanel().getCurrentRow()+1);
		    	
		    	 String[] tmp=new String[]{""+evt.header.code,new Date(evt.header.creationdate).toGMTString(),""+evt.header.priority,evt.body.type};
				    frame.getEventsPanel().getTableModel().addRow(tmp);
				
			    }
			    if (evt.body.type.equals("Pression")) {
				Pression t = (Pression) evt.body.content.extract_Value();
//				System.out.println("priority:" + evt.header.priority + ",date:"
//					+ (new Date(evt.header.creationdate)).toGMTString()
//					+ ", contenu: Pression" + " Value: " + t.getValue()
//					+ "| Unite: " + t.getUnite());

//			    
//				frame.getEventsPanel().getModel().setValueAt(evt.header.code, frame.getEventsPanel().getCurrentRow(), 0);
//		    	frame.getEventsPanel().getModel().setValueAt(""+evt.header.creationdate, frame.getEventsPanel().getCurrentRow(), 1);			    	
//		    	frame.getEventsPanel().getModel().setValueAt(evt.body.type, frame.getEventsPanel().getCurrentRow(), 2);
//		    	
//		    	frame.getEventsPanel().setCurrentRow(frame.getEventsPanel().getCurrentRow()+1);
		    	
				 String[] tmp=new String[]{""+evt.header.code,new Date(evt.header.creationdate).toGMTString(),""+evt.header.priority,evt.body.type};
				    frame.getEventsPanel().getTableModel().addRow(tmp);
			    }

			    if (evt.body.type.equals("RadioActivite")) {
				RadioActivite t = (RadioActivite) evt.body.content
					.extract_Value();
//				System.out.println("priority:" + evt.header.priority + ",date:"
//					+ (new Date(evt.header.creationdate)).toGMTString()
//					+ ", contenu: RadioActivite" + " Value: "
//					+ t.getValue() + "| Unite: " + t.getUnite());

			  
//			    
//				frame.getEventsPanel().getTableModel().setValueAt(evt.header.code, frame.getEventsPanel().getCurrentRow(), 0);
//		    	frame.getEventsPanel().getTableModel().setValueAt(""+evt.header.creationdate, frame.getEventsPanel().getCurrentRow(), 1);			    	
//		    	frame.getEventsPanel().getTableModel().setValueAt(evt.body.type, frame.getEventsPanel().getCurrentRow(), 2);
//		    	
		    	//frame.getEventsPanel().setCurrentRow(frame.getEventsPanel().getCurrentRow()+1);
				  String[] tmp=new String[]{""+evt.header.code,new Date(evt.header.creationdate).toGMTString(),""+evt.header.priority,evt.body.type};
			    frame.getEventsPanel().getTableModel().addRow(tmp);
			    }
			    
			    

			}
			
			
		    }


	@Override
	public void valueChanged(ListSelectionEvent e) {
		if (e.getValueIsAdjusting())
            return;
        ListSelectionModel lsm = (ListSelectionModel)e.getSource();
        if (lsm.isSelectionEmpty()) {
            JOptionPane.showMessageDialog(this,"No rows selected");
        	//System.out.println("No rows selected");
        }
        else{
            int selectedRow = lsm.getMinSelectionIndex();
            //System.out.println("The row "+selectedRow+" is now selected");
            Event event=listEvents.get(selectedRow);
            String content="";
            if(event.body.type.equals("String"))
            	content=event.body.content.extract_string();
            if(event.body.type.equals("Temperature")){
            	Temperature t=(Temperature)event.body.content.extract_Value();
            	content=t.getValue()+" " + t.getUnite();
            }
            if(event.body.type.equals("RadioActivite")){
            	RadioActivite r=(RadioActivite)event.body.content.extract_Value();
            	content=r.getValue()+" " + r.getUnite();
            	
            }
            if(event.body.type.equals("Pression")){
            	Pression p=(Pression)event.body.content.extract_Value();
            	content=p.getValue()+" " + p.getUnite();
            	
            }
            
            detailEventPanel.getTextCode().setText(""+event.header.code);
            detailEventPanel.getTextTime().setText(new Date(event.header.creationdate).toGMTString());
            detailEventPanel.getTextTTL().setText(""+event.header.timetolive);
            detailEventPanel.getTextPriority().setText(""+event.header.priority);
            
            detailEventPanel.getTextTypeEvent().setText(""+event.body.type);
            detailEventPanel.getTextCotentEvent().setText(content);
            //JOptionPane.showMessageDialog(this,"The row "+selectedRow+" is now selected");
            
        }
		
	}

	public ConnectionPanel getConnectionPanel() {
		return connectionPanel;
	}

	public void setConnectionPanel(ConnectionPanel connectionPanel) {
		this.connectionPanel = connectionPanel;
	}

	public SubscriptionPanel getSubscriptionPanel() {
		return subscriptionPanel;
	}

	public void setSubscriptionPanel(SubscriptionPanel subscriptionPanel) {
		this.subscriptionPanel = subscriptionPanel;
	}



	
}
