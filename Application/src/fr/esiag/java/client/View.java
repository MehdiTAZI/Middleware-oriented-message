package fr.esiag.java.client;

import com.google.gwt.user.client.ui.Widget;

import fr.esiag.java.shared.ChannelInfosCollector;

public abstract class View {
	protected Application view;
	public  View(Application view) {
		this.view=view;
	}
	public abstract Widget getContent();
	public abstract void setData(ChannelInfosCollector[] channelInfosCollector);
}