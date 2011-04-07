package fr.esiag.mezzodijava.mezzo.admin.client;

import com.google.gwt.user.client.rpc.impl.RemoteServiceProxy;
import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.client.rpc.impl.RequestCallbackAdapter.ResponseReader;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.impl.ClientSerializationStreamWriter;

public class ShellExecuter_Proxy extends RemoteServiceProxy implements fr.esiag.mezzodijava.mezzo.admin.client.ShellExecuterAsync {
  private static final String REMOTE_SERVICE_INTERFACE_NAME = "fr.esiag.mezzodijava.mezzo.admin.client.ShellExecuter";
  private static final String SERIALIZATION_POLICY ="028D32C6EB06EDC57D8ED5417F9DC436";
  private static final fr.esiag.mezzodijava.mezzo.admin.client.ShellExecuter_TypeSerializer SERIALIZER = new fr.esiag.mezzodijava.mezzo.admin.client.ShellExecuter_TypeSerializer();
  
  public ShellExecuter_Proxy() {
    super(GWT.getModuleBaseURL(),
      "shellExecuter", 
      SERIALIZATION_POLICY, 
      SERIALIZER);
  }
  
  public void execute(java.lang.String[] command, com.google.gwt.user.client.rpc.AsyncCallback callback) {
    int requestId = getNextRequestId();
    boolean toss = isStatsAvailable() && stats(timeStat("ShellExecuter_Proxy.execute", getRequestId(), "begin"));
    ClientSerializationStreamWriter streamWriter = createStreamWriter();
    // createStreamWriter() prepared the stream
    streamWriter.writeString(REMOTE_SERVICE_INTERFACE_NAME);
    try {
      streamWriter.writeString("execute");
      streamWriter.writeInt(1);
      streamWriter.writeString("[Ljava.lang.String;");
      streamWriter.writeObject(command);
      String payload = streamWriter.toString();
      toss = isStatsAvailable() && stats(timeStat("ShellExecuter_Proxy.execute", getRequestId(), "requestSerialized"));
      doInvoke(ResponseReader.STRING, "ShellExecuter_Proxy.execute", getRequestId(), payload, callback);
    } catch (SerializationException ex) {
      callback.onFailure(ex);
    }
  }
}
