package CosEvent;


/**
* CosEvent/ProxyPushSupplierPOA.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from prototype.idl
* samedi 27 novembre 2010 15 h 07 CET
*/

public abstract class ProxyPushSupplierPOA extends org.omg.PortableServer.Servant
 implements CosEvent.ProxyPushSupplierOperations, org.omg.CORBA.portable.InvokeHandler
{

  // Constructors

  private static java.util.Hashtable _methods = new java.util.Hashtable ();
  static
  {
    _methods.put ("connect", new java.lang.Integer (0));
    _methods.put ("getConsumer", new java.lang.Integer (1));
    _methods.put ("disconnect", new java.lang.Integer (2));
    _methods.put ("receive", new java.lang.Integer (3));
  }

  public org.omg.CORBA.portable.OutputStream _invoke (String $method,
                                org.omg.CORBA.portable.InputStream in,
                                org.omg.CORBA.portable.ResponseHandler $rh)
  {
    org.omg.CORBA.portable.OutputStream out = null;
    java.lang.Integer __method = (java.lang.Integer)_methods.get ($method);
    if (__method == null)
      throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);

    switch (__method.intValue ())
    {
       case 0:  // CosEvent/ProxyPushSupplier/connect
       {
         CosEvent.PushConsumer pc = CosEvent.PushConsumerHelper.read (in);
         this.connect (pc);
         out = $rh.createReply();
         break;
       }

       case 1:  // CosEvent/ProxyPushSupplier/getConsumer
       {
         CosEvent.PushConsumer $result = null;
         $result = this.getConsumer ();
         out = $rh.createReply();
         CosEvent.PushConsumerHelper.write (out, $result);
         break;
       }

       case 2:  // CosEvent/PushSupplier/disconnect
       {
         this.disconnect ();
         out = $rh.createReply();
         break;
       }

       case 3:  // CosEvent/MessageListener/receive
       {
         CosEvent.Event event = CosEvent.EventHelper.read (in);
         this.receive (event);
         out = $rh.createReply();
         break;
       }

       default:
         throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);
    }

    return out;
  } // _invoke

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:CosEvent/ProxyPushSupplier:1.0", 
    "IDL:CosEvent/PushSupplier:1.0", 
    "IDL:CosEvent/MessageListener:1.0"};

  public String[] _all_interfaces (org.omg.PortableServer.POA poa, byte[] objectId)
  {
    return (String[])__ids.clone ();
  }

  public ProxyPushSupplier _this() 
  {
    return ProxyPushSupplierHelper.narrow(
    super._this_object());
  }

  public ProxyPushSupplier _this(org.omg.CORBA.ORB orb) 
  {
    return ProxyPushSupplierHelper.narrow(
    super._this_object(orb));
  }


} // class ProxyPushSupplierPOA
