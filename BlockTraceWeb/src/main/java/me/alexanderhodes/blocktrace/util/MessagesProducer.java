package me.alexanderhodes.blocktrace.util;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by alexa on 25.09.2017.
 */
public class MessagesProducer {

    private static ResourceBundle resourceBundle;

    static {
        try {
            resourceBundle = ResourceBundle.getBundle("messages", Locale.ENGLISH);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getValue (String key) {
        return resourceBundle.getString(key);
    }

}
