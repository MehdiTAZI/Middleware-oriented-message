package fr.esiag.mezzodijava.mezzo.coseventserver.ctr;

import static org.junit.Assert.*;

import java.nio.channels.AlreadyConnectedException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.omg.CORBA.Context;
import org.omg.CORBA.ContextList;
import org.omg.CORBA.DomainManager;
import org.omg.CORBA.ExceptionList;
import org.omg.CORBA.NVList;
import org.omg.CORBA.NamedValue;
import org.omg.CORBA.Object;
import org.omg.CORBA.Policy;
import org.omg.CORBA.Request;
import org.omg.CORBA.SetOverrideType;

import com.mchange.util.AssertException;

import fr.esiag.mezzodijava.mezzo.cosevent.AlreadyRegisteredException;
import fr.esiag.mezzodijava.mezzo.cosevent.CallbackConsumer;
import fr.esiag.mezzodijava.mezzo.cosevent.ConsumerNotFoundException;
import fr.esiag.mezzodijava.mezzo.cosevent.Event;
import fr.esiag.mezzodijava.mezzo.cosevent.MaximalConnectionReachedException;
import fr.esiag.mezzodijava.mezzo.cosevent.NotRegisteredException;
import fr.esiag.mezzodijava.mezzo.coseventserver.factory.BFFactory;
import fr.esiag.mezzodijava.mezzo.coseventserver.impl.ProxyForPushConsumerImpl;

public class TestChannelCtr {

	private static ChannelCtr channelCtr;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		String topic = "bla";
		channelCtr = BFFactory.createChannelCtr(topic);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	//TODO
	@Test
	public void testAddProxyForPushConsumerToSubscribedListNull() {
		
			try {
				channelCtr.addProxyForPushConsumerToSubscribedList(null);
				fail();
			} catch (AlreadyRegisteredException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		
	}

	@Test
	public void testAddProxyForPushConsumerToSubscribedListAlreadyConnected() {
		try {
			ProxyForPushConsumerImpl ppc = new ProxyForPushConsumerImpl("bla");
			CallbackConsumer cb = new CallbackConsumer() {
				
				@Override
				public Object _set_policy_override(Policy[] policies,
						SetOverrideType set_add) {
					// TODO Auto-generated method stub
					return null;
				}
				
				@Override
				public Request _request(String operation) {
					// TODO Auto-generated method stub
					return null;
				}
				
				@Override
				public void _release() {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public boolean _non_existent() {
					// TODO Auto-generated method stub
					return false;
				}
				
				@Override
				public boolean _is_equivalent(Object other) {
					// TODO Auto-generated method stub
					return false;
				}
				
				@Override
				public boolean _is_a(String repositoryIdentifier) {
					// TODO Auto-generated method stub
					return false;
				}
				
				@Override
				public int _hash(int maximum) {
					// TODO Auto-generated method stub
					return 0;
				}
				
				@Override
				public Policy _get_policy(int policy_type) {
					// TODO Auto-generated method stub
					return null;
				}
				
				@Override
				public Object _get_interface_def() {
					// TODO Auto-generated method stub
					return null;
				}
				
				@Override
				public DomainManager[] _get_domain_managers() {
					// TODO Auto-generated method stub
					return null;
				}
				
				@Override
				public Object _duplicate() {
					// TODO Auto-generated method stub
					return null;
				}
				
				@Override
				public Request _create_request(Context ctx, String operation,
						NVList arg_list, NamedValue result, ExceptionList exclist,
						ContextList ctxlist) {
					// TODO Auto-generated method stub
					return null;
				}
				
				@Override
				public Request _create_request(Context ctx, String operation,
						NVList arg_list, NamedValue result) {
					// TODO Auto-generated method stub
					return null;
				}
				
				@Override
				public void receive(Event evt) throws ConsumerNotFoundException {
					// TODO Auto-generated method stub
					
				}
			};
			try {
				channelCtr.addProxyForPushConsumerToSubscribedList(ppc);
				channelCtr.addProxyForPushConsumerToSubscribedList(ppc);
				fail();
			} 
			catch (AlreadyRegisteredException e) {
			 e.printStackTrace();	
			}
		}	
		catch(Exception e){
			
		}
			
			
	}
	
	@Test
	public void testAddProxyForPushConsumerToSubscribedListNormal() {
		try {
			ProxyForPushConsumerImpl ppc = new ProxyForPushConsumerImpl("bla");
			CallbackConsumer cb = new CallbackConsumer() {
				
				@Override
				public Object _set_policy_override(Policy[] policies,
						SetOverrideType set_add) {
					// TODO Auto-generated method stub
					return null;
				}
				
				@Override
				public Request _request(String operation) {
					// TODO Auto-generated method stub
					return null;
				}
				
				@Override
				public void _release() {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public boolean _non_existent() {
					// TODO Auto-generated method stub
					return false;
				}
				
				@Override
				public boolean _is_equivalent(Object other) {
					// TODO Auto-generated method stub
					return false;
				}
				
				@Override
				public boolean _is_a(String repositoryIdentifier) {
					// TODO Auto-generated method stub
					return false;
				}
				
				@Override
				public int _hash(int maximum) {
					// TODO Auto-generated method stub
					return 0;
				}
				
				@Override
				public Policy _get_policy(int policy_type) {
					// TODO Auto-generated method stub
					return null;
				}
				
				@Override
				public Object _get_interface_def() {
					// TODO Auto-generated method stub
					return null;
				}
				
				@Override
				public DomainManager[] _get_domain_managers() {
					// TODO Auto-generated method stub
					return null;
				}
				
				@Override
				public Object _duplicate() {
					// TODO Auto-generated method stub
					return null;
				}
				
				@Override
				public Request _create_request(Context ctx, String operation,
						NVList arg_list, NamedValue result, ExceptionList exclist,
						ContextList ctxlist) {
					// TODO Auto-generated method stub
					return null;
				}
				
				@Override
				public Request _create_request(Context ctx, String operation,
						NVList arg_list, NamedValue result) {
					// TODO Auto-generated method stub
					return null;
				}
				
				@Override
				public void receive(Event evt) throws ConsumerNotFoundException {
					// TODO Auto-generated method stub
					
				}
			};
			try {
				channelCtr.addProxyForPushConsumerToSubscribedList(ppc);
			} 
			catch (AlreadyRegisteredException e) {
				fail();
			 e.printStackTrace();	
			}
		}	
		catch(Exception e){
			
		}
			
			
	}
	
	
}
