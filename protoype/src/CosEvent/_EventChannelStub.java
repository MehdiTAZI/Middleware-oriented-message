package CosEvent;


/**
* CosEvent/_EventChannelStub.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from prototype.idl
* samedi 27 novembre 2010 15 h 07 CET
*/

public class _EventChannelStub extends org.omg.CORBA.portable.ObjectImpl implements CosEvent.EventChannel
{

  public CosEvent.ProxyPushSupplier get_Proxy_Push_Supplier ()
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("get_Proxy_Push_Supplier", true);
                $in = _invoke ($out);
                CosEvent.ProxyPushSupplier $result = CosEvent.ProxyPushSupplierHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return get_Proxy_Push_Supplier (        );
            } finally {
                _releaseReply ($in);
            }
  } // get_Proxy_Push_Supplier

  public CosEvent.ProxyPushConsumer get_Proxy_Push_Consumer ()
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("get_Proxy_Push_Consumer", true);
                $in = _invoke ($out);
                CosEvent.ProxyPushConsumer $result = CosEvent.ProxyPushConsumerHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return get_Proxy_Push_Consumer (        );
            } finally {
                _releaseReply ($in);
            }
  } // get_Proxy_Push_Consumer

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:CosEvent/EventChannel:1.0"};

  public String[] _ids ()
  {
    return (String[])__ids.clone ();
  }

  private void readObject (java.io.ObjectInputStream s) throws java.io.IOException
  {
     String str = s.readUTF ();
     String[] args = null;
     java.util.Properties props = null;
     org.omg.CORBA.Object obj = org.omg.CORBA.ORB.init (args, props).string_to_object (str);
     org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl) obj)._get_delegate ();
     _set_delegate (delegate);
  }

  private void writeObject (java.io.ObjectOutputStream s) throws java.io.IOException
  {
     String[] args = null;
     java.util.Properties props = null;
     String str = org.omg.CORBA.ORB.init (args, props).object_to_string (this);
     s.writeUTF (str);
  }
} // class _EventChannelStub
