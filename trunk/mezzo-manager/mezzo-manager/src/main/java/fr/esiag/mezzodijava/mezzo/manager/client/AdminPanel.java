package fr.esiag.mezzodijava.mezzo.manager.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DecoratedTabPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;



public class AdminPanel {
	private HTML moreInfo;
	// (1) Create the client proxy
	private final ShellExecuterAsync defaultShellExectuer = (ShellExecuterAsync) GWT.create(ShellExecuter.class);	
	private final ShellExecuterAsync ShellExectuer = (ShellExecuterAsync) GWT.create(ShellExecuter.class);
	public AdminPanel()
	{

	}
	@SuppressWarnings("deprecation")
	public Widget onInitialize() {
		  // (2) Create an asynchronous callback to handle the result.
		  final AsyncCallback<String> defaultCallback = new AsyncCallback<String>() {
			public void onFailure(Throwable arg0) {
			}
			public void onSuccess(String arg0) {	
			}
		  };
		  final AsyncCallback<String> callback = new AsyncCallback<String>() {
				public void onFailure(Throwable arg0) {
					moreInfo.setText("Error RPC CALL");
				}
				public void onSuccess(String arg0) {
					moreInfo.setText(arg0);
				}
			  };

		String[] tabTitles = new String[]{"Start","Stop","Status"};

		// Create a tab panel
		DecoratedTabPanel tabPanel = new DecoratedTabPanel();
		tabPanel.setWidth("400px");
		tabPanel.setAnimationEnabled(true);

		// Add a home tab

		Button startButton = new Button("Demarrer CosEvent", new ClickHandler() {
			public void onClick(ClickEvent arg0) {
				  // (3) Make the call
				  defaultShellExectuer.execute(new String[]{"calc"}, defaultCallback);
			}});

		startButton.ensureDebugId("cwBasicButton-start");

		Button stopButton = new Button("Arreter Cos-Server", new ClickHandler() {
			public void onClick(ClickEvent arg0) {
				 // (3) Make the call
				  defaultShellExectuer.execute(new String[]{"mspaint"}, defaultCallback);
			}
		});

		stopButton.ensureDebugId("cwBasicButton-stop");

		tabPanel.add(startButton, tabTitles[0]);
		tabPanel.add(stopButton, tabTitles[1]);

		// Add a tab
		Button statButton = new Button("Etat Cos-Server", new ClickHandler() {
			public void onClick(ClickEvent arg0) {
				  // (2) Create an asynchronous callback to handle the result.
				  
				ShellExectuer.execute(new String[]{"cmd.exe","/C","dir"}, callback);
			}
		});

		statButton.ensureDebugId("cwBasicButton-stat");


		moreInfo = new HTML("");

		VerticalPanel vPanel = new VerticalPanel();
		vPanel.add(statButton);
		vPanel.add(moreInfo);

		tabPanel.add(vPanel, tabTitles[2]);	    

		// Return the content
		tabPanel.selectTab(0);
		tabPanel.ensureDebugId("cwTabPanel");
		return tabPanel;
	}
}
