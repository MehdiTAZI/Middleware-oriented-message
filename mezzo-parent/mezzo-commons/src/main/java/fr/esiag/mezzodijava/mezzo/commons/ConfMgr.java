package fr.esiag.mezzodijava.mezzo.commons;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class ConfigurationManager
 * 
 * Pour charger des fichiers de properties.
 * 
 * Utilisation
 * 
 * Properties props =
 * ConfigurationManager.loadProperties("eventserver_default.properties"
 * ,"eventserver.properties");
 * 
 * UC n°: US14,US15 (+US children) * Program argmuments:
 * 
 * -ORBInitRef NameService=corbaloc::127.0.0.1:1050/NameService
 * -Djacorb.home=C:\\mezzodev\\jacorb-2.3.1
 * -Dorg.omg.CORBA.ORBClass=org.jacorb.orb.ORB
 * -Dorg.omg.CORBA.ORBSingletonClass=org.jacorb.orb.ORBSingleton
 * 
 * VM argmuments: -Djava.endorsed.dirs=${env_var:JACORB_HOME}/lib
 * 
 * @author Mezzo-Team
 */
public class ConfMgr {

    private static Logger log = LoggerFactory.getLogger(ConfMgr.class);

    private static final String EXTENSION = ".properties";

    /**
     * Charge plusieur fichier properties dans un Properties
     * 
     * @param pNomsFichiersProperties
     *            listes des noms des fichiers
     * @return un properties java
     */
    public static Properties loadProperties(String... pNomsFichiersProperties) {

	Properties properties = new Properties();
	ClassLoader loader = Thread.currentThread().getContextClassLoader();
	InputStream inputStream = null;
	if (loader == null) {
	    loader = ClassLoader.getSystemClassLoader();
	}
	for (String nom : pNomsFichiersProperties) {
	    try {
		String nomFichier = nom;
		nomFichier = nomFichier.replace('.', '/');
		if (!nomFichier.endsWith(EXTENSION)) {
		    nomFichier = nomFichier.concat(EXTENSION);
		}
		// Returns null on lookup failures:
		inputStream = loader.getResourceAsStream(nomFichier);
		if (inputStream != null) {
		    properties.load(inputStream); // Can throw IOException
		    // Chargement d'un fichier
		    log.info("Ajout fichier de configuration : {}", nomFichier);
		} else {
		    log.warn(
			    "Erreur de chargement d'une configuration : {} non trouve",
			    nomFichier);
		}

	    } catch (Exception e) {
		log.warn("Erreur de chargement d'une configuration : " + e);
	    }
	}
	// on vire les propriete blanches, vides ou null
	for (Object key : ((Properties) properties.clone()).keySet()) {
	    if (isEmpty(properties.getProperty((String) key))) {
		properties.remove(key);
	    }
	}
	if (log.isInfoEnabled()) {
	    StringBuffer logStr = new StringBuffer()
		    .append("Contenu fusionne des properties ci dessus :");
	    List<String> keys = new ArrayList(properties.keySet());
	    Collections.sort(keys);
	    for (Object key : keys) {
		logStr.append("\n");
		logStr.append(key);
		logStr.append("=");
		logStr.append(properties.get(key));
	    }
	    if (keys.size() == 0) {
		logStr.append(" VIDE !");
	    }
	    log.info(logStr.toString());
	}
	return properties;
    }

    /**
     * Renvoie la valeur pas defaut si la propriete n'exista pas, si elle est
     * null ou si elle est vide
     * 
     * @param properties
     *            proprietes
     * @param propertyName
     *            nom de la propriete
     * @param defaultValue
     *            valeur par defaut
     * @return valeur en String
     */
    public static String getValue(Properties properties, String propertyName,
	    String defaultValue) {
	return properties.getProperty(propertyName, defaultValue);
    }

    /**
     * Raccourci vers la configuration pour recupere une propriete chaine
     * 
     * @param properties
     *            proprietes
     * @param propertyName
     *            nom de la propriete
     * @return valeur en String
     */
    public static String getValue(Properties properties, String propertyName) {
	return properties.getProperty(propertyName);
    }

    /**
     * Renvoie un Integer.
     * 
     * @param properties
     *            proprietes
     * @param properyName
     *            nom de la propriete
     * @return valeur en Integer si possible
     */
    public static int getIntegerValue(Properties properties, String propertyName) {
	return Integer.valueOf(properties.getProperty(propertyName));
    }

    /**
     * Renvoie un Integer.
     * 
     * @param properties
     *            proprietes
     * @param propertyName
     *            nom de la propriete
     * @param defaultValue
     *            default Value.
     * @return valeur en Integer si possible
     */

    public static int getIntegerValue(Properties properties,
	    String propertyName, int defaultValue) {
	try {
	    return Integer.valueOf(properties.getProperty(propertyName));
	} catch (Exception e) {
	    return defaultValue;
	}
    }

    /**
     * Renvoie un Long.
     * 
     * @param properties
     *            proprietes
     * @param properyName
     *            nom de la propriete
     * @return valeur en Integer si possible
     */
    public static long getLongValue(Properties properties, String propertyName) {
	return Long.valueOf(properties.getProperty(propertyName));
    }

    /**
     * Renvoie un Long.
     * 
     * @param properties
     *            proprietes
     * @param propertyName
     *            nom de la propriete
     * @param defaultValue
     *            default Value.
     * @return valeur en Integer si possible
     */

    public static long getLongValue(Properties properties, String propertyName,
	    long defaultValue) {
	try {
	    return new Long(properties.getProperty(propertyName));
	} catch (Exception e) {
	    return defaultValue;
	}
    }

    /**
     * Renvoie un Integer.
     * 
     * @param properties
     *            proprietes
     * @param pProprieteNom
     *            nom de la propriete
     * @return valeur en Integer si possible
     */
    public static boolean getBooleanValue(Properties properties,
	    String propertyName) {
	return Boolean.valueOf(properties.getProperty(propertyName));
    }

    private static boolean isEmpty(String string) {
	if (string == null) {
	    return true;
	}
	int length = string.length();
	if (length != 0) {
	    for (int i = 0; i < length; i++) {
		if (!Character.isWhitespace(string.charAt(i))) {
		    return false;
		}
	    }
	}
	return true;
    }
}
