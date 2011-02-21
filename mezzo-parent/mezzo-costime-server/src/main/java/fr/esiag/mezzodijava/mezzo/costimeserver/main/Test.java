package fr.esiag.mezzodijava.mezzo.costimeserver.main;

import java.io.IOException;
import java.util.Properties;

import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

import fr.esiag.mezzodijava.mezzo.costime.SynchronizablePOATie;
import fr.esiag.mezzodijava.mezzo.costime.TimeService;
import fr.esiag.mezzodijava.mezzo.costime.TimeServiceHelper;
import fr.esiag.mezzodijava.mezzo.costime.TimeServicePOATie;
import fr.esiag.mezzodijava.mezzo.costime._TimeServiceStub;

public class Test {

	public Test(String [] args){
		Properties props = new Properties();
		try {
		    props.load(this.getClass().getClassLoader()
			    .getResourceAsStream("eventserver.properties"));
		} catch (IOException e) {
		    // TODO log here
		    
		}
		ORB orb=null;
		try{
		orb=ORB.init(args,props);
		POA rootPOA=POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
		rootPOA.the_POAManager().activate();
		Object objRef= orb.resolve_initial_references("NameService");
		NamingContextExt ncRef =NamingContextExtHelper.narrow(objRef);
		TimeService service=TimeServiceHelper.narrow(ncRef.resolve_str("cosTime"));
		SynchronizablePOATie tie=new SynchronizablePOATie(new Component());
		service.subscribe(tie._this(orb));
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		orb.run();
	}

	public static void main(String[] args) {
		new Test(args);
	}

}
