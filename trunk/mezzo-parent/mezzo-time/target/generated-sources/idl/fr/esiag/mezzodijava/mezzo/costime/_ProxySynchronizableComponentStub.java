package fr.esiag.mezzodijava.mezzo.costime;


/**
 * Generated from IDL interface "ProxySynchronizableComponent".
 *
 * @author JacORB IDL compiler V 2.3.1 (JBoss patch01), 29-Jul-2009
 * @version generated at 16 févr. 2011 00:48:13
 */

public class _ProxySynchronizableComponentStub
	extends org.omg.CORBA.portable.ObjectImpl
	implements fr.esiag.mezzodijava.mezzo.costime.ProxySynchronizableComponent
{
	private String[] ids = {"IDL:costime/ProxySynchronizableComponent:1.0","IDL:costime/ITimeable:1.0"};
	public String[] _ids()
	{
		return ids;
	}

	public final static java.lang.Class _opsClass = fr.esiag.mezzodijava.mezzo.costime.ProxySynchronizableComponentOperations.class;
	public void subscribe(fr.esiag.mezzodijava.mezzo.costime.CallbackSynchronizableComponent csc) throws fr.esiag.mezzodijava.mezzo.costime.AlreadyRegisteredException,fr.esiag.mezzodijava.mezzo.costime.UnreachableException
	{
		while(true)
		{
		if(! this._is_local())
		{
			org.omg.CORBA.portable.InputStream _is = null;
			org.omg.CORBA.portable.OutputStream _os = null;
			try
			{
				_os = _request( "subscribe", true);
				fr.esiag.mezzodijava.mezzo.costime.CallbackSynchronizableComponentHelper.write(_os,csc);
				_is = _invoke(_os);
				return;
			}
			catch( org.omg.CORBA.portable.RemarshalException _rx ){}
			catch( org.omg.CORBA.portable.ApplicationException _ax )
			{
				String _id = _ax.getId();
				try
				{
					if( _id.equals("IDL:costime/AlreadyRegisteredException:1.0"))
					{
						throw fr.esiag.mezzodijava.mezzo.costime.AlreadyRegisteredExceptionHelper.read(_ax.getInputStream());
					}
					else 
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
			org.omg.CORBA.portable.ServantObject _so = _servant_preinvoke( "subscribe", _opsClass );
			if( _so == null )
				throw new org.omg.CORBA.UNKNOWN("local invocations not supported!");
			ProxySynchronizableComponentOperations _localServant = (ProxySynchronizableComponentOperations)_so.servant;
			try
			{
				_localServant.subscribe(csc);
			}
			finally
			{
				_servant_postinvoke(_so);
			}
			return;
		}

		}

	}

	public long synchroValue()
	{
		while(true)
		{
		if(! this._is_local())
		{
			org.omg.CORBA.portable.InputStream _is = null;
			org.omg.CORBA.portable.OutputStream _os = null;
			try
			{
				_os = _request("_get_synchroValue",true);
				_is = _invoke(_os);
				return _is.read_ulonglong();
			}
			catch( org.omg.CORBA.portable.RemarshalException _rx ){}
			catch( org.omg.CORBA.portable.ApplicationException _ax )
			{
				String _id = _ax.getId();
					try
					{
							_ax.getInputStream().close();
					}
					catch (java.io.IOException e)
					{
					throw new RuntimeException("Unexpected exception " + e.toString() );
					}
				throw new RuntimeException("Unexpected exception " + _id );
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
		org.omg.CORBA.portable.ServantObject _so = _servant_preinvoke( "_get_synchroValue", _opsClass);
		if( _so == null )
			throw new org.omg.CORBA.UNKNOWN("local invocations not supported!");
			ProxySynchronizableComponentOperations _localServant = (ProxySynchronizableComponentOperations)_so.servant;
			long _result;
		try
		{
			_result = _localServant.synchroValue();
		}
		finally
		{
			_servant_postinvoke(_so);
		}
		return _result;
		}
		}

	}

	public void synchroValue(long a)
	{
		while(true)
		{
		if(! this._is_local())
		{
			org.omg.CORBA.portable.InputStream _is = null;
			org.omg.CORBA.portable.OutputStream _os = null;
			try
			{
				_os = _request("_set_synchroValue",true);
				_os.write_ulonglong(a);
				_is = _invoke(_os);
				return;
			}
			catch( org.omg.CORBA.portable.RemarshalException _rx ){}
			catch( org.omg.CORBA.portable.ApplicationException _ax )
			{
				String _id = _ax.getId();
					try
					{
							_ax.getInputStream().close();
					}
					catch (java.io.IOException e)
					{
					throw new RuntimeException("Unexpected exception " + e.toString() );
					}
				throw new RuntimeException("Unexpected exception " + _id );
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
			org.omg.CORBA.portable.ServantObject _so = _servant_preinvoke( "_set_synchroValue", _opsClass);
			if( _so == null )
				throw new org.omg.CORBA.UNKNOWN("local invocations not supported!");
			ProxySynchronizableComponentOperations _localServant = (ProxySynchronizableComponentOperations)_so.servant;
				try
				{
					_localServant.synchroValue(a);
				}
				finally
				{
					_servant_postinvoke(_so);
				}
				return;
			}
		}

	}

	public void unsubscribe() throws fr.esiag.mezzodijava.mezzo.costime.UnreachableException,fr.esiag.mezzodijava.mezzo.costime.NotRegisteredException
	{
		while(true)
		{
		if(! this._is_local())
		{
			org.omg.CORBA.portable.InputStream _is = null;
			org.omg.CORBA.portable.OutputStream _os = null;
			try
			{
				_os = _request( "unsubscribe", true);
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
					if( _id.equals("IDL:costime/NotRegisteredException:1.0"))
					{
						throw fr.esiag.mezzodijava.mezzo.costime.NotRegisteredExceptionHelper.read(_ax.getInputStream());
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
			org.omg.CORBA.portable.ServantObject _so = _servant_preinvoke( "unsubscribe", _opsClass );
			if( _so == null )
				throw new org.omg.CORBA.UNKNOWN("local invocations not supported!");
			ProxySynchronizableComponentOperations _localServant = (ProxySynchronizableComponentOperations)_so.servant;
			try
			{
				_localServant.unsubscribe();
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
