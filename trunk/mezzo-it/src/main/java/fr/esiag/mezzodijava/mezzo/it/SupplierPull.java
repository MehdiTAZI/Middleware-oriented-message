package fr.esiag.mezzodijava.mezzo.it;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.omg.CORBA.Any;
import org.omg.CORBA.BooleanHolder;
import org.omg.CORBA.ORB;

import fr.esiag.mezzodijava.mezzo.cosevent.AlreadyConnectedException;
import fr.esiag.mezzodijava.mezzo.cosevent.Body;
import fr.esiag.mezzodijava.mezzo.cosevent.CallbackSupplier;
import fr.esiag.mezzodijava.mezzo.cosevent.CallbackSupplierOperations;
import fr.esiag.mezzodijava.mezzo.cosevent.ChannelAdmin;
import fr.esiag.mezzodijava.mezzo.cosevent.ChannelNotFoundException;
import fr.esiag.mezzodijava.mezzo.cosevent.Event;
import fr.esiag.mezzodijava.mezzo.cosevent.Header;
import fr.esiag.mezzodijava.mezzo.cosevent.MaximalConnectionReachedException;
import fr.esiag.mezzodijava.mezzo.cosevent.ProxyForPullSupplier;
import fr.esiag.mezzodijava.mezzo.cosevent.SupplierNotFoundException;
import fr.esiag.mezzodijava.mezzo.coseventserver.factory.BFFactory;
import fr.esiag.mezzodijava.mezzo.libclient.EventClient;
import fr.esiag.mezzodijava.mezzo.libclient.EventFactory;
import fr.esiag.mezzodijava.mezzo.libclient.exception.EventClientException;
import fr.esiag.mezzodijava.mezzo.libclient.exception.TopicNotFoundException;

public class SupplierPull {
	
	public static void main(String[] args) {
		try {
			EventClient ec = EventClient.init(args);
			ChannelAdmin channelAdmin = ec.resolveChannelByTopic("injector system state");
			String idcomp = "morocco";
			ProxyForPullSupplier supplierProxy = channelAdmin
		    .getProxyForPullSupplier(idcomp);
			System.out.println("creation callback");
			CallBackSupplierImpl callbackImpl = new CallBackSupplierImpl();
    	    CallbackSupplier cbs = ec.serveCallbackSupplier(callbackImpl);
			System.out.println("connexion du supplier");
			supplierProxy.connect(cbs);
			Thread.sleep(1000);
			ORB orb = ec.getOrb();
			orb.run();
			
		} catch (EventClientException e) {
			e.printStackTrace();
		} catch (TopicNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ChannelNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MaximalConnectionReachedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (AlreadyConnectedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

	public static class CallBackSupplierImpl implements
    CallbackSupplierOperations {
	   
	   List<Event> messages = Collections
	    .synchronizedList(new ArrayList<Event>());
	   
	   int nb;

	   public CallBackSupplierImpl() {
		   nb = 0;
		   for (int i = 0; i < 10; i++) {
			   Event e = EventFactory.createEventString(1, 10120, "Test_EVENT"+ i);
			   messages.add(e);
		   }
	   }
	   
		@Override
		public Event ask(BooleanHolder hasEvent)
				throws SupplierNotFoundException {
			
			nb++;
			
    		if (nb==11){
    			System.out.println("plus d events");
    			hasEvent.value=false;
    			Any a = BFFactory.getOrb().create_any();
				Header h = new Header();
				Body b = new Body();
				b.content=a;
				return new Event(h,b);
    		}else{
    			System.out.println("nb=" + nb);
    			hasEvent.value=true;
    			Event e = messages.get(nb-1);
    			return e;
    		}
		}
   }

}
