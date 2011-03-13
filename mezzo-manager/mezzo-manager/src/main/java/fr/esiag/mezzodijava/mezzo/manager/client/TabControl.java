package fr.esiag.mezzodijava.mezzo.manager.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DecoratedTabPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class TabControl {
	TabControl()
	{
		
	}
	  /**
	   * Initialize this example.
	   */
	  @SuppressWarnings("deprecation")
	  public Widget onInitialize() {
	    // Create a tab panel
	    DecoratedTabPanel tabPanel = new DecoratedTabPanel();
	    tabPanel.setWidth("400px");
	    tabPanel.setAnimationEnabled(true);

	    // Add a home tab
	    String[] tabTitles = new String[]{"Start","Stop","Status"};
	    
	    Button startButton = new Button("Demarrer CosEvent", new ClickHandler() {
				public void onClick(ClickEvent arg0) {
					System.out.println("DEMARRER");}});
	    
	    startButton.ensureDebugId("cwBasicButton-start");

	    Button stopButton = new Button("Arreter Cos-Server", new ClickHandler() {
			public void onClick(ClickEvent arg0) {
				System.out.println("ARRETER");
			}
            });
	    stopButton.ensureDebugId("cwBasicButton-stop");
	    
	    Button statButton = new Button("Etat Cos-Server", new ClickHandler() {
			public void onClick(ClickEvent arg0) {
				System.out.println("ETAT");
			}
            });
	    stopButton.ensureDebugId("cwBasicButton-stat");
	    
	    tabPanel.add(startButton, tabTitles[0]);
	    tabPanel.add(stopButton, tabTitles[1]);

	    
	    // Add a tab
	    HTML moreInfo = new HTML("Stat from file , thx to ->>");
	    
	    tabPanel.add(moreInfo, tabTitles[3]);	    
	    



	    // Return the content
	    tabPanel.selectTab(0);
	    tabPanel.ensureDebugId("cwTabPanel");
	    return tabPanel;
	  }
}
