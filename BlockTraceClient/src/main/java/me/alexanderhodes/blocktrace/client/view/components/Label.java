package me.alexanderhodes.blocktrace.client.view.components;

import me.alexanderhodes.blocktrace.client.config.ConfigurationProvider;

import javax.swing.*;
import java.awt.*;

/**
 * Created by alexa on 26.09.2017.
 */
public class Label extends JLabel {

    public Label (String text) {
        this.setFont(new Font("Arial", Font.PLAIN, ConfigurationProvider.ZOOM*13));
        this.setForeground(Color.black);

        this.setText(text);
    }

}
