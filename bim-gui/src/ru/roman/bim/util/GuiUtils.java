package ru.roman.bim.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Method;

/** @author Roman 18.12.12 0:10 */
public class GuiUtils {
    private static final Log log = LogFactory.getLog(GuiUtils.class);

    public static void startSwingApp(final Starter starter) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                try {
                    ExceptionHandler.registerUncatchExceptionHandler();
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

                    starter.onStart();

                } catch (Throwable e) {
                    ExceptionHandler.showMessage(e);
                }
            }
        });
    }

    public interface Starter {
        void onStart();
    }


    // taken from: http://java.sun.com/developer/technicalArticles/GUI/translucent_shaped_windows/
    public static void setTranslucency(Window window, float opacity){
        try {
            Class<?> awtUtilitiesClass = Class.forName("com.sun.awt.AWTUtilities");
            Method mSetWindowOpacity = awtUtilitiesClass.getMethod("setWindowOpacity", Window.class, float.class);

            mSetWindowOpacity.invoke(null, window, opacity);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }


    public static Point getRightCornerPosition(Dimension size, int padding) {
        try {
            Integer width = null;
            Integer height = null;
            try {
                GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
                width = gd.getDisplayMode().getWidth();
                height = gd.getDisplayMode().getHeight();
            } catch (Exception e) {
                log.warn("Exception while screen resolution detection", e);
            } finally {
                if (width == null || height == null) {
                    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                    width = (int)screenSize.getWidth();
                    height = (int)screenSize.getHeight();
                }
            }
            final Point position = new Point(width - (int)size.getWidth() - padding,
                    height - (int)size.getHeight() - padding);
//            log.info(String.format("Display size: width=%1$s, height=%2$s, position=%3$s",
//                    new Object[]{width, height, position}));
            return position;
        } catch (RuntimeException e) {
            throw new RuntimeException("Exception while screen resolution detection", e);
        }
    }

    public static void showInfoMessage(String mess) {
        JOptionPane.showMessageDialog(null, mess);
    }

}
