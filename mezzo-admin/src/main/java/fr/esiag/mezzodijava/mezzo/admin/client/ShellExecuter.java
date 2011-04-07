package fr.esiag.mezzodijava.mezzo.admin.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
@RemoteServiceRelativePath("shellExecuter")
public interface ShellExecuter extends RemoteService{
	public String execute(String[] command);
}
