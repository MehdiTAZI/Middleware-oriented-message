package fr.esiag.mezzodijava.mezzo.manager.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;


/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Application
    implements EntryPoint
{

  /**
   * This is the entry point method.
   */
	
  public void onModuleLoad()
  {
	  final Label label = new Label ( "Bienvenu sur la console d'administration de mezzo");
	 
	    FlexTable layout = new FlexTable();
	    layout.setCellSpacing(6);
	    layout.setWidth("300px");
	    FlexCellFormatter cellFormatter = layout.getFlexCellFormatter();

	    // Add a title to the form
	    layout.setHTML(0, 0, "Administration");
	    cellFormatter.setColSpan(0, 0, 2);
	    cellFormatter.setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_CENTER);
	    
	    TabControl control = new TabControl();
	    Widget wiPanel = control.onInitialize();
	    layout.add(wiPanel);
     
	    VerticalPanel vPanel = new VerticalPanel();
		vPanel.add(label);
		vPanel.add(layout); 
	    
		DisclosurePanel advancedDisclosure = new DisclosurePanel("Critere");
		    advancedDisclosure.setAnimationEnabled(true);
		    advancedDisclosure.ensureDebugId("cwDisclosurePanel");
		    advancedDisclosure.setContent(vPanel);
		    layout.setWidget(3, 0, advancedDisclosure);
		    cellFormatter.setColSpan(3, 0, 2);

		    // Wrap the contents in a DecoratorPanel
		    DecoratorPanel decPanel = new DecoratorPanel();
		    decPanel.setWidget(vPanel);
		    
		
     RootPanel.get().add(decPanel);
     
  }
}
