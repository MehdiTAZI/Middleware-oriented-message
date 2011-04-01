package mezzo.esiag.mezzodijava.mezzo.manager.client.old;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ShellExecuterAsync {
  public void execute(String[] command, AsyncCallback<String> callback);
}