package fr.esiag.mezzodijava.mezzo.manager.client;

import com.google.gwt.user.client.ui.Widget;

import fr.esiag.mezzodijava.mezzo.manager.shared.ChannelInfosCollector;

public abstract class View {
	protected Application view;
	public  View(Application view) {
		this.view=view;
	}
	public abstract void setContent();
	public abstract Widget getContent();
	public abstract void setData(ChannelInfosCollector[] channelInfosCollector);
}
