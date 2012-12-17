package ru.roman.bim.demo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Simple demo on how a translucent window
 * looks like when is used to display the system clock.
 * @author <a href="http://stackoverflow.com/users/20654/oscarryz">Oscar Reyes</a>
 */
public class TranslucencyDemo extends JPanel implements ActionListener {

    private static final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
    private final Date now = new Date();
    private final Timer timer = new Timer(1000, this);
    private final JLabel text = new JLabel();

    public TranslucencyDemo() {
        super(true);
        timer.start();
        add(text);
        actionPerformed(null);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        now.setTime(System.currentTimeMillis());
        text.setText(String.format("<html><body><font size='50'>%s</font></body></html>",sdf.format(now)));
    }

    public static void main(String[] args) {

        JFrame f = new JFrame();
        f.setUndecorated(true);
        //setTranslucency(f);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLocationRelativeTo(null);
        f.setBackground(new Color(0.5294118f, 0f, 1.0f, 0.0f));
        f.add(new TranslucencyDemo());
        f.setPreferredSize(new Dimension(300,200));
        f.setOpacity(Float.valueOf(0.75f));
        f.setResizable(false);
        f.pack();
        f.setVisible(true);
    }
    // taken from: http://java.sun.com/developer/technicalArticles/GUI/translucent_shaped_windows/
    private static void setTranslucency( Window window){
        try {
            Class<?> awtUtilitiesClass = Class.forName("com.sun.awt.AWTUtilities");
            Method mSetWindowOpacity = awtUtilitiesClass.getMethod("setWindowOpacity", Window.class, float.class);

            mSetWindowOpacity.invoke(null, window, Float.valueOf(0.75f));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}