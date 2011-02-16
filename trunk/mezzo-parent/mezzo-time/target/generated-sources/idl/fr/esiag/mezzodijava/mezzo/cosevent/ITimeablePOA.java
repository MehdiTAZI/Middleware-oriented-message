package fr.esiag.mezzodijava.mezzo.cosevent;


/**
 * Generated from IDL interface "ITimeable".
 *
 * @author JacORB IDL compiler V 2.3.1 (JBoss patch01), 29-Jul-2009
 * @version generated at 16 févr. 2011 00:17:04
 */

public abstract class ITimeablePOA
	extends org.omg.PortableServer.Servant
	implements org.omg.CORBA.portable.InvokeHandler, fr.esiag.mezzodijava.mezzo.cosevent.ITimeableOperations
{
	static private final java.util.Hashtable m_opsHash = new java.util.Hashtable();
	static
	{
		m_opsHash.put ( "_set_synchroValue", new java.lang.Integer(0));
		m_opsHash.put ( "_get_synchroValue", new java.lang.Integer(1));
	}
	private String[] ids = {"IDL:cosevent/ITimeable:1.0"};
	public fr.esiag.mezzodijava.mezzo.cosevent.ITimeable _this()
	{
		return fr.esiag.mezzodijava.mezzo.cosevent.ITimeableHelper.narrow(_this_object());
	}
	public fr.esiag.mezzodijava.mezzo.cosevent.ITimeable _this(org.omg.CORBA.ORB orb)
	{
		return fr.esiag.mezzodijava.mezzo.cosevent.ITimeableHelper.narrow(_this_object(orb));
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
			case 0: // _set_synchroValue
			{
			_out = handler.createReply();
			synchroValue(_input.read_ulonglong());
				break;
			}
			case 1: // _get_synchroValue
			{
			_out = handler.createReply();
			_out.write_ulonglong(synchroValue());
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
