package ru.roman.bim.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/** @author Roman 18.12.12 0:10 */
public abstract class GuiUtils {
    private static final Log log = LogFactory.getLog(GuiUtils.class);
    public static final int TASK_BAR_HEIGHT = 40;
    private static Dimension screenSize;

    public static void startSwingApp(final Starter starter) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                try {
                    ExceptionHandler.registerUncatchExceptionHandler();
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

                    starter.onStart();

                } catch (Throwable e) {
                    ExceptionHandler.showErrorMessageAndExit(e);
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
        Dimension dimension = getScreenSize();
        Integer width = (int)dimension.getWidth();
        Integer height = (int)dimension.getHeight();
        final Point position = new Point(width - (int)size.getWidth() - padding,
                height - (int)size.getHeight() - padding - TASK_BAR_HEIGHT);
//            log.info(String.format("Display size: width=%1$s, height=%2$s, position=%3$s",
//                    new Object[]{width, height, position}));
        return position;
    }


    public static Point getCenterPosition(Dimension size) {
        Dimension dimension = getScreenSize();
        Integer width = (int)dimension.getWidth();
        Integer height = (int)dimension.getHeight();
        final Point position = new Point((width - (int)size.getWidth()) / 2,
                (height - (int)size.getHeight() - TASK_BAR_HEIGHT) / 2);
        return position;
    }

    public static void showInfoMessage(String mess) {
        JOptionPane.showMessageDialog(null, mess);
    }



    public static Image createMainImage() {
        return createImage(Res.MAIN_IMAGE_PATH);
    }

    private static Map<String, ImageIcon> images = new HashMap<String, ImageIcon>();

    public static Image createImage(String path) {
        if (!images.containsKey(path)) {
            final URL imageURL = GuiUtils.class.getResource(path);
            final ImageIcon icon;
            if (imageURL == null) {
                log.warn("Resource not found : " + path);
                icon = null;
            } else {
                icon = new ImageIcon(imageURL, null);
            }
            images.put(path, icon);
        }
        return images.get(path) != null ? images.get(path).getImage() : null;

    }

    private static Dimension getScreenSize() {
        try {
            if (screenSize == null) {
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
                screenSize = new Dimension(width, height);
            }
            return screenSize;
        } catch (RuntimeException e) {
            throw new RuntimeException("Exception while screen resolution detection", e);
        }
    }
}
