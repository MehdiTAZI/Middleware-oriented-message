package fr.esiag.mezzodijava.mezzo.manager.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ShellExecuterAsync {
  public void execute(String[] command, AsyncCallback<String> callback);
}