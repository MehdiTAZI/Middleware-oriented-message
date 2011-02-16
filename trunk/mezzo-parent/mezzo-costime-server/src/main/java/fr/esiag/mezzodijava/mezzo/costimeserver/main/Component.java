package fr.esiag.mezzodijava.mezzo.costimeserver.main;


import fr.esiag.mezzodijava.mezzo.costime.SynchronizableOperations;

public class Component implements SynchronizableOperations{
	long date;
	@Override
	public long date() {
		return 0;
	}

	@Override
	public void date(long arg) {
		this.date=arg;
	}

}
