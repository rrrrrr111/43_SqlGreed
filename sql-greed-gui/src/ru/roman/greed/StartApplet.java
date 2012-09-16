/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.roman.greed;

import ru.roman.greed.gui.pane.main.MainView;
import ru.roman.greed.util.ExceptionHandler;

import javax.swing.JApplet;
import javax.swing.UIManager;

/**
 *
 * @author Roman 01.09.12 0:05
 */
public class StartApplet extends JApplet {

    /**
     * Initialization method that will be called after the applet is loaded
     * into the browser.
     */
    @Override
    public void init() {
        // start asynchronous download of heavy resources
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            new MainView();
        } catch (Throwable e) {
            ExceptionHandler.showMessage(e);
        } 
    }
    // overwrite start(), stop() and destroy() methods
}
