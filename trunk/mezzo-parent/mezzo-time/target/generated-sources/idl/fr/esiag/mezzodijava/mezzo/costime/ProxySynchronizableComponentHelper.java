package fr.esiag.mezzodijava.mezzo.costime;


/**
 * Generated from IDL interface "ProxySynchronizableComponent".
 *
 * @author JacORB IDL compiler V 2.3.1 (JBoss patch01), 29-Jul-2009
 * @version generated at 16 févr. 2011 00:48:13
 */

public final class ProxySynchronizableComponentHelper
{
	public static void insert (final org.omg.CORBA.Any any, final fr.esiag.mezzodijava.mezzo.costime.ProxySynchronizableComponent s)
	{
			any.insert_Object(s);
	}
	public static fr.esiag.mezzodijava.mezzo.costime.ProxySynchronizableComponent extract(final org.omg.CORBA.Any any)
	{
		return narrow(any.extract_Object()) ;
	}
	public static org.omg.CORBA.TypeCode type()
	{
		return org.omg.CORBA.ORB.init().create_interface_tc("IDL:costime/ProxySynchronizableComponent:1.0", "ProxySynchronizableComponent");
	}
	public static String id()
	{
		return "IDL:costime/ProxySynchronizableComponent:1.0";
	}
	public static ProxySynchronizableComponent read(final org.omg.CORBA.portable.InputStream in)
	{
		return narrow(in.read_Object(fr.esiag.mezzodijava.mezzo.costime._ProxySynchronizableComponentStub.class));
	}
	public static void write(final org.omg.CORBA.portable.OutputStream _out, final fr.esiag.mezzodijava.mezzo.costime.ProxySynchronizableComponent s)
	{
		_out.write_Object(s);
	}
	public static fr.esiag.mezzodijava.mezzo.costime.ProxySynchronizableComponent narrow(final org.omg.CORBA.Object obj)
	{
		if (obj == null)
		{
			return null;
		}
		else if (obj instanceof fr.esiag.mezzodijava.mezzo.costime.ProxySynchronizableComponent)
		{
			return (fr.esiag.mezzodijava.mezzo.costime.ProxySynchronizableComponent)obj;
		}
		else if (obj._is_a("IDL:costime/ProxySynchronizableComponent:1.0"))
		{
			fr.esiag.mezzodijava.mezzo.costime._ProxySynchronizableComponentStub stub;
			stub = new fr.esiag.mezzodijava.mezzo.costime._ProxySynchronizableComponentStub();
			stub._set_delegate(((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate());
			return stub;
		}
		else
		{
			throw new org.omg.CORBA.BAD_PARAM("Narrow failed");
		}
	}
	public static fr.esiag.mezzodijava.mezzo.costime.ProxySynchronizableComponent unchecked_narrow(final org.omg.CORBA.Object obj)
	{
		if (obj == null)
		{
			return null;
		}
		else if (obj instanceof fr.esiag.mezzodijava.mezzo.costime.ProxySynchronizableComponent)
		{
			return (fr.esiag.mezzodijava.mezzo.costime.ProxySynchronizableComponent)obj;
		}
		else
		{
			fr.esiag.mezzodijava.mezzo.costime._ProxySynchronizableComponentStub stub;
			stub = new fr.esiag.mezzodijava.mezzo.costime._ProxySynchronizableComponentStub();
			stub._set_delegate(((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate());
			return stub;
		}
	}
}
