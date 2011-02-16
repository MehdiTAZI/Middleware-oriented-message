package fr.esiag.mezzodijava.mezzo.cosevent;


/**
 * Generated from IDL interface "ProxySynchronizableComponent".
 *
 * @author JacORB IDL compiler V 2.3.1 (JBoss patch01), 29-Jul-2009
 * @version generated at 16 févr. 2011 00:17:04
 */

public final class ProxySynchronizableComponentHelper
{
	public static void insert (final org.omg.CORBA.Any any, final fr.esiag.mezzodijava.mezzo.cosevent.ProxySynchronizableComponent s)
	{
			any.insert_Object(s);
	}
	public static fr.esiag.mezzodijava.mezzo.cosevent.ProxySynchronizableComponent extract(final org.omg.CORBA.Any any)
	{
		return narrow(any.extract_Object()) ;
	}
	public static org.omg.CORBA.TypeCode type()
	{
		return org.omg.CORBA.ORB.init().create_interface_tc("IDL:cosevent/ProxySynchronizableComponent:1.0", "ProxySynchronizableComponent");
	}
	public static String id()
	{
		return "IDL:cosevent/ProxySynchronizableComponent:1.0";
	}
	public static ProxySynchronizableComponent read(final org.omg.CORBA.portable.InputStream in)
	{
		return narrow(in.read_Object(fr.esiag.mezzodijava.mezzo.cosevent._ProxySynchronizableComponentStub.class));
	}
	public static void write(final org.omg.CORBA.portable.OutputStream _out, final fr.esiag.mezzodijava.mezzo.cosevent.ProxySynchronizableComponent s)
	{
		_out.write_Object(s);
	}
	public static fr.esiag.mezzodijava.mezzo.cosevent.ProxySynchronizableComponent narrow(final org.omg.CORBA.Object obj)
	{
		if (obj == null)
		{
			return null;
		}
		else if (obj instanceof fr.esiag.mezzodijava.mezzo.cosevent.ProxySynchronizableComponent)
		{
			return (fr.esiag.mezzodijava.mezzo.cosevent.ProxySynchronizableComponent)obj;
		}
		else if (obj._is_a("IDL:cosevent/ProxySynchronizableComponent:1.0"))
		{
			fr.esiag.mezzodijava.mezzo.cosevent._ProxySynchronizableComponentStub stub;
			stub = new fr.esiag.mezzodijava.mezzo.cosevent._ProxySynchronizableComponentStub();
			stub._set_delegate(((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate());
			return stub;
		}
		else
		{
			throw new org.omg.CORBA.BAD_PARAM("Narrow failed");
		}
	}
	public static fr.esiag.mezzodijava.mezzo.cosevent.ProxySynchronizableComponent unchecked_narrow(final org.omg.CORBA.Object obj)
	{
		if (obj == null)
		{
			return null;
		}
		else if (obj instanceof fr.esiag.mezzodijava.mezzo.cosevent.ProxySynchronizableComponent)
		{
			return (fr.esiag.mezzodijava.mezzo.cosevent.ProxySynchronizableComponent)obj;
		}
		else
		{
			fr.esiag.mezzodijava.mezzo.cosevent._ProxySynchronizableComponentStub stub;
			stub = new fr.esiag.mezzodijava.mezzo.cosevent._ProxySynchronizableComponentStub();
			stub._set_delegate(((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate());
			return stub;
		}
	}
}
