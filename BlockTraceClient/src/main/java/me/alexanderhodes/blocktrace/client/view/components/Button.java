package me.alexanderhodes.blocktrace.client.view.components;

import me.alexanderhodes.blocktrace.client.config.ConfigurationProvider;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;

/**
 * Created by alexa on 26.09.2017.
 */
public class Button extends JButton {

	private static final long serialVersionUID = 8218943146362038730L;

	public Button(String text) {
		this.setBackground(Color.BLACK);
		this.setForeground(Color.WHITE);

		this.setFont(new Font("Arial", Font.PLAIN, ConfigurationProvider.getZoom() * 13));
		this.setText(text);

		Border border = new LineBorder(Color.WHITE, 4);
		this.setBorderPainted(true);
		this.setBorder(border);
	}

}
