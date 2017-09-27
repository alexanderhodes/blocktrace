package me.alexanderhodes.blocktrace.client.config;

import java.util.Properties;

/**
 * Created by alexa on 26.09.2017.
 */
public class ConfigurationProvider {

	private static Properties properties;

    static {
    	properties = new Properties();
    	
    	try {
    		properties.load(ConfigurationProvider.class.getResourceAsStream("/config.properties"));
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }

    public static int getZoom () {
    	String value = properties.getProperty("zoom");
    	if (value != null && !value.isEmpty()) {
    		try {
    			return Integer.parseInt(value);
    		} catch (Exception e) {
    			return 1;
    		}
    	}
    	return 1;
    }
    
    public static String getRestPath () {
    	String value = properties.getProperty("rest_application");
    	if (value != null && !value.isEmpty()) {
    		return value;
    	}
    	return "http://localhost:8080/blocktrace/rest/";
    }
    
}
