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

    public static String produceConfigProperty (InjectionPoint injectionPoint) {
        String key = injectionPoint.getAnnotated().getAnnotation(ConfigProperty.class).toString();
        return properties.getProperty(key);
    }

}
