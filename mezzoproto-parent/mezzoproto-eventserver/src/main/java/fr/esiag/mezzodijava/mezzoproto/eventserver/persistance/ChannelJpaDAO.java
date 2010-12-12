/**
 * Copyright (C) 2007 Laurent GRANIE.
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the
 * Free Software Foundation; either version 3, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General
 * Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 */
package fr.esiag.mezzodijava.mezzoproto.eventserver.persistance;

import fr.esiag.mezzodijava.mezzoproto.eventserver.model.EventString;

// LGE - Vive la g�n�ricit�
// Regardez du c�t� de la classe m�re 
public class ChannelJpaDAO extends JpaDAO<Integer, EventString> implements ChannelDAO {

	private static ChannelDAO instance = null;
	
	public static synchronized ChannelDAO getInstance() {
		if (instance == null) {
			instance = new ChannelJpaDAO();
		}
		return instance;
	}
}
