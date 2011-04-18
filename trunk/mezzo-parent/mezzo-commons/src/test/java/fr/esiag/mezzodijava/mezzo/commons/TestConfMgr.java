package fr.esiag.mezzodijava.mezzo.commons;

import java.util.Properties;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestConfMgr {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testloadProperties() {
	Properties props = ConfMgr.loadProperties("first", "second");
	assertEquals("texte1", props.get("texte1"));
	assertEquals("texte2", props.get("texte2"));
	assertEquals("texte3", props.get("texte3"));
	assertEquals("texte4", props.get("texte4"));
	assertNull(props.get("texte5"));
	assertEquals("texte6", props.get("texte6"));
	assertEquals(2, ConfMgr.getIntegerValue(props, "entier"));
	assertEquals(1L, ConfMgr.getLongValue(props, "long"));
	assertEquals(true, ConfMgr.getBooleanValue(props, "booleen"));
	// valeur par defaut
	assertEquals(3, ConfMgr.getIntegerValue(props, "entierpasla", 3));
	assertEquals(3L, ConfMgr.getLongValue(props, "longpasla", 3L));
    }

    @Test
    public void testloadVide() {
	Properties props = ConfMgr.loadProperties("vide");
	assertEquals(0, props.size());
	props = ConfMgr.loadProperties("pasla");
	assertEquals(0, props.size());
    }
}
