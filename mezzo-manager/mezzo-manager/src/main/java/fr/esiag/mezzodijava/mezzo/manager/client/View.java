package fr.esiag.mezzodijava.mezzo.manager.client;

import com.google.gwt.user.client.ui.Widget;

public abstract class View {
	protected Application view;
	public  View(Application view) {
		this.view=view;
	}
	public abstract Widget getContent();
}
