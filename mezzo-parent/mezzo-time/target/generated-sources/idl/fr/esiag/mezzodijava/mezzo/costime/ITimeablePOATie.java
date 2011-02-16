package fr.esiag.mezzodijava.mezzo.costime;

import org.omg.PortableServer.POA;

/**
 * Generated from IDL interface "ITimeable".
 *
 * @author JacORB IDL compiler V 2.3.1 (JBoss patch01), 29-Jul-2009
 * @version generated at 16 févr. 2011 00:48:13
 */

public class ITimeablePOATie
	extends ITimeablePOA
{
	private ITimeableOperations _delegate;

	private POA _poa;
	public ITimeablePOATie(ITimeableOperations delegate)
	{
		_delegate = delegate;
	}
	public ITimeablePOATie(ITimeableOperations delegate, POA poa)
	{
		_delegate = delegate;
		_poa = poa;
	}
	public fr.esiag.mezzodijava.mezzo.costime.ITimeable _this()
	{
		return fr.esiag.mezzodijava.mezzo.costime.ITimeableHelper.narrow(_this_object());
	}
	public fr.esiag.mezzodijava.mezzo.costime.ITimeable _this(org.omg.CORBA.ORB orb)
	{
		return fr.esiag.mezzodijava.mezzo.costime.ITimeableHelper.narrow(_this_object(orb));
	}
	public ITimeableOperations _delegate()
	{
		return _delegate;
	}
	public void _delegate(ITimeableOperations delegate)
	{
		_delegate = delegate;
	}
	public POA _default_POA()
	{
		if (_poa != null)
		{
			return _poa;
		}
		return super._default_POA();
	}
	public void synchroValue(long a)
	{
		_delegate.synchroValue(a);
	}

	public long synchroValue()
	{
		return _delegate.synchroValue();
	}

}
