package me.alexanderhodes.blocktrace.util;

import javax.enterprise.inject.spi.InjectionPoint;
import javax.resource.spi.ConfigProperty;
import java.util.Properties;

/**
 * Created by alexa on 23.09.2017.
 */
public class ConfigPropertyProducer {

    private static Properties properties;

    static {
        properties = new Properties();

        try {
            properties.load(ConfigPropertyProducer.class.getResourceAsStream("/config.properties"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Returning of property that is stored in properties file
     * 
     * @param injectionPoint Point of injecting this class
     * @return value of key
     */
    public static String produceConfigProperty (InjectionPoint injectionPoint) {
        String key = injectionPoint.getAnnotated().getAnnotation(ConfigProperty.class).toString();
        return properties.getProperty(key);
    }

    public static String getBlockchainRestPath () {
    	String value = properties.getProperty("blockchain_api");
    	if (value != null && !value.isEmpty()) {
    		return value;
    	}
    	return "http://localhost:3000/api/";
    }
    
}
