package fr.esiag.mezzodijava.mezzo.manager.client;

import com.google.gwt.user.client.ui.Widget;

public abstract class View {
	protected Test2 view;
	public  View(Test2 view) {
		this.view=view;
	}
	public abstract Widget getContent();
}
