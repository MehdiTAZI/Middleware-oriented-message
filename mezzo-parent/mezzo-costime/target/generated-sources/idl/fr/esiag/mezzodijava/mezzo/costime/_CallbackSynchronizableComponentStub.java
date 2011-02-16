package fr.esiag.mezzodijava.mezzo.costime;


/**
 * Generated from IDL interface "CallbackSynchronizableComponent".
 *
 * @author JacORB IDL compiler V 2.3.1 (JBoss patch01), 29-Jul-2009
 * @version generated at 16 févr. 2011 00:48:13
 */

public class _CallbackSynchronizableComponentStub
	extends org.omg.CORBA.portable.ObjectImpl
	implements fr.esiag.mezzodijava.mezzo.costime.CallbackSynchronizableComponent
{
	private String[] ids = {"IDL:costime/CallbackSynchronizableComponent:1.0"};
	public String[] _ids()
	{
		return ids;
	}

	public final static java.lang.Class _opsClass = fr.esiag.mezzodijava.mezzo.costime.CallbackSynchronizableComponentOperations.class;
	public void receive(long dateTime) throws fr.esiag.mezzodijava.mezzo.costime.UnreachableException
	{
		while(true)
		{
		if(! this._is_local())
		{
			org.omg.CORBA.portable.InputStream _is = null;
			org.omg.CORBA.portable.OutputStream _os = null;
			try
			{
				_os = _request( "receive", true);
				_os.write_ulonglong(dateTime);
				_is = _invoke(_os);
				return;
			}
			catch( org.omg.CORBA.portable.RemarshalException _rx ){}
			catch( org.omg.CORBA.portable.ApplicationException _ax )
			{
				String _id = _ax.getId();
				try
				{
					if( _id.equals("IDL:costime/UnreachableException:1.0"))
					{
						throw fr.esiag.mezzodijava.mezzo.costime.UnreachableExceptionHelper.read(_ax.getInputStream());
					}
					else 
					{
						throw new RuntimeException("Unexpected exception " + _id );
					}
				}
				finally
				{
				try
				{
					_ax.getInputStream().close();
				}
				catch (java.io.IOException e)
				{
					throw new RuntimeException("Unexpected exception " + e.toString() );
				}
			}
			}
			finally
			{
				if (_os != null)
				{
					try
					{
						_os.close();
					}
					catch (java.io.IOException e)
					{
					throw new RuntimeException("Unexpected exception " + e.toString() );
					}
				}
				this._releaseReply(_is);
			}
		}
		else
		{
			org.omg.CORBA.portable.ServantObject _so = _servant_preinvoke( "receive", _opsClass );
			if( _so == null )
				throw new org.omg.CORBA.UNKNOWN("local invocations not supported!");
			CallbackSynchronizableComponentOperations _localServant = (CallbackSynchronizableComponentOperations)_so.servant;
			try
			{
				_localServant.receive(dateTime);
			}
			finally
			{
				_servant_postinvoke(_so);
			}
			return;
		}

		}

	}

}
