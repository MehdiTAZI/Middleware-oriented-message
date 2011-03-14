package fr.esiag.mezzodijava.mezzo.manager.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

interface ShellExecuterAsync {
  public void execute(String command, AsyncCallback<String> callback);
}