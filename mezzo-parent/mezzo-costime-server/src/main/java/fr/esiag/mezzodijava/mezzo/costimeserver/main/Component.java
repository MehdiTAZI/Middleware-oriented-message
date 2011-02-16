package fr.esiag.mezzodijava.mezzo.costimeserver.main;


import java.util.Date;

import fr.esiag.mezzodijava.mezzo.costime.SynchronizableOperations;

public class Component implements SynchronizableOperations{
	private Date date;
	@Override
	public long date() {
		return date.getTime();
	}

	@Override
	public void date(long date) {
		this.date=new Date(date);
	}

}
