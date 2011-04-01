package mezzo.esiag.mezzodijava.mezzo.manager.client.old;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
@RemoteServiceRelativePath("shellExecuter")
public interface ShellExecuter extends RemoteService{
	public String execute(String[] command);
}
