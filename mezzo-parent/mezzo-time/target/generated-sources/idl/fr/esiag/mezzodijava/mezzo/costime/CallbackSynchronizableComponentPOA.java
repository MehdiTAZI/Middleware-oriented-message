package fr.esiag.mezzodijava.mezzo.costime;


/**
 * Generated from IDL interface "CallbackSynchronizableComponent".
 *
 * @author JacORB IDL compiler V 2.3.1 (JBoss patch01), 29-Jul-2009
 * @version generated at 16 févr. 2011 00:48:13
 */

public abstract class CallbackSynchronizableComponentPOA
	extends org.omg.PortableServer.Servant
	implements org.omg.CORBA.portable.InvokeHandler, fr.esiag.mezzodijava.mezzo.costime.CallbackSynchronizableComponentOperations
{
	static private final java.util.Hashtable m_opsHash = new java.util.Hashtable();
	static
	{
		m_opsHash.put ( "receive", new java.lang.Integer(0));
	}
	private String[] ids = {"IDL:costime/CallbackSynchronizableComponent:1.0"};
	public fr.esiag.mezzodijava.mezzo.costime.CallbackSynchronizableComponent _this()
	{
		return fr.esiag.mezzodijava.mezzo.costime.CallbackSynchronizableComponentHelper.narrow(_this_object());
	}
	public fr.esiag.mezzodijava.mezzo.costime.CallbackSynchronizableComponent _this(org.omg.CORBA.ORB orb)
	{
		return fr.esiag.mezzodijava.mezzo.costime.CallbackSynchronizableComponentHelper.narrow(_this_object(orb));
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
			case 0: // receive
			{
			try
			{
				long _arg0=_input.read_ulonglong();
				_out = handler.createReply();
				receive(_arg0);
			}
			catch(fr.esiag.mezzodijava.mezzo.costime.UnreachableException _ex0)
			{
				_out = handler.createExceptionReply();
				fr.esiag.mezzodijava.mezzo.costime.UnreachableExceptionHelper.write(_out, _ex0);
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
