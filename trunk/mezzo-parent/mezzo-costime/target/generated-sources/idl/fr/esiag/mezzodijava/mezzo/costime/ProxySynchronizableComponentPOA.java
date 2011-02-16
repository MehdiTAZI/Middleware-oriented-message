package fr.esiag.mezzodijava.mezzo.costime;


/**
 * Generated from IDL interface "ProxySynchronizableComponent".
 *
 * @author JacORB IDL compiler V 2.3.1 (JBoss patch01), 29-Jul-2009
 * @version generated at 16 févr. 2011 00:48:13
 */

public abstract class ProxySynchronizableComponentPOA
	extends org.omg.PortableServer.Servant
	implements org.omg.CORBA.portable.InvokeHandler, fr.esiag.mezzodijava.mezzo.costime.ProxySynchronizableComponentOperations
{
	static private final java.util.Hashtable m_opsHash = new java.util.Hashtable();
	static
	{
		m_opsHash.put ( "subscribe", new java.lang.Integer(0));
		m_opsHash.put ( "_get_synchroValue", new java.lang.Integer(1));
		m_opsHash.put ( "_set_synchroValue", new java.lang.Integer(2));
		m_opsHash.put ( "unsubscribe", new java.lang.Integer(3));
	}
	private String[] ids = {"IDL:costime/ProxySynchronizableComponent:1.0","IDL:costime/ITimeable:1.0"};
	public fr.esiag.mezzodijava.mezzo.costime.ProxySynchronizableComponent _this()
	{
		return fr.esiag.mezzodijava.mezzo.costime.ProxySynchronizableComponentHelper.narrow(_this_object());
	}
	public fr.esiag.mezzodijava.mezzo.costime.ProxySynchronizableComponent _this(org.omg.CORBA.ORB orb)
	{
		return fr.esiag.mezzodijava.mezzo.costime.ProxySynchronizableComponentHelper.narrow(_this_object(orb));
	}
	public org.omg.CORBA.portable.OutputStream _invoke(String method, org.omg.CORBA.portable.InputStream _input, org.omg.CORBA.portable.ResponseHandler handler)
		throws org.omg.CORBA.SystemException
	{
		org.omg.CORBA.portable.OutputStream _out = null;
		// do something
		// quick lookup of operation
		java.lang.Integer opsIndex = (java.lang.Integer)m_opsHash.get ( method );
		if ( null == opsIndex )
			throw new org.omg.CORBA.BAD_OPERATION(method + " not found");
		switch ( opsIndex.intValue() )
		{
			case 0: // subscribe
			{
			try
			{
				fr.esiag.mezzodijava.mezzo.costime.CallbackSynchronizableComponent _arg0=fr.esiag.mezzodijava.mezzo.costime.CallbackSynchronizableComponentHelper.read(_input);
				_out = handler.createReply();
				subscribe(_arg0);
			}
			catch(fr.esiag.mezzodijava.mezzo.costime.AlreadyRegisteredException _ex0)
			{
				_out = handler.createExceptionReply();
				fr.esiag.mezzodijava.mezzo.costime.AlreadyRegisteredExceptionHelper.write(_out, _ex0);
			}
			catch(fr.esiag.mezzodijava.mezzo.costime.UnreachableException _ex1)
			{
				_out = handler.createExceptionReply();
				fr.esiag.mezzodijava.mezzo.costime.UnreachableExceptionHelper.write(_out, _ex1);
			}
				break;
			}
			case 1: // _get_synchroValue
			{
			_out = handler.createReply();
			_out.write_ulonglong(synchroValue());
				break;
			}
			case 2: // _set_synchroValue
			{
			_out = handler.createReply();
			synchroValue(_input.read_ulonglong());
				break;
			}
			case 3: // unsubscribe
			{
			try
			{
				_out = handler.createReply();
				unsubscribe();
			}
			catch(fr.esiag.mezzodijava.mezzo.costime.UnreachableException _ex0)
			{
				_out = handler.createExceptionReply();
				fr.esiag.mezzodijava.mezzo.costime.UnreachableExceptionHelper.write(_out, _ex0);
			}
			catch(fr.esiag.mezzodijava.mezzo.costime.NotRegisteredException _ex1)
			{
				_out = handler.createExceptionReply();
				fr.esiag.mezzodijava.mezzo.costime.NotRegisteredExceptionHelper.write(_out, _ex1);
			}
				break;
			}
		}
		return _out;
	}

	public String[] _all_interfaces(org.omg.PortableServer.POA poa, byte[] obj_id)
	{
		return ids;
	}
}
