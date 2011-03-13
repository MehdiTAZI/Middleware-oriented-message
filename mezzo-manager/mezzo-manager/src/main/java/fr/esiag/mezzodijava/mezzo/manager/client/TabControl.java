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
	    
	    Button demarerButton = new Button("Demarrer Cos-Server", new ClickHandler() {
				public void onClick(ClickEvent arg0) {
					System.out.println("test");
				}
	            });
	    demarerButton.ensureDebugId("cwBasicButton-normal");

	    demarerButton = new Button("Arreter Cos-Server", new ClickHandler() {
			public void onClick(ClickEvent arg0) {
				System.out.println("test END");
			}
            });
    demarerButton.ensureDebugId("cwBasicButton-normal");
	    
	    tabPanel.add(demarerButton, tabTitles[0]);

	    // Add a tab with an image
	    VerticalPanel vPanel = new VerticalPanel();

	    tabPanel.add(demarerButton, tabTitles[1]);

	    // Add a tab
	    HTML moreInfo = new HTML("Content");
	    tabPanel.add(moreInfo, tabTitles[2]);

	    // Return the content
	    tabPanel.selectTab(0);
	    tabPanel.ensureDebugId("cwTabPanel");
	    return tabPanel;
	  }
}
