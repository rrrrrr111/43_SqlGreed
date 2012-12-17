package ru.roman.bim.util;

import java.awt.*;
import java.lang.reflect.Method;

/** @author Roman 18.12.12 0:10 */
public class GuiUtils {


    // taken from: http://java.sun.com/developer/technicalArticles/GUI/translucent_shaped_windows/
    public static void setTranslucency( Window window){
        try {
            Class<?> awtUtilitiesClass = Class.forName("com.sun.awt.AWTUtilities");
            Method mSetWindowOpacity = awtUtilitiesClass.getMethod("setWindowOpacity", Window.class, float.class);

            mSetWindowOpacity.invoke(null, window, Float.valueOf(0.75f));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
