package ru.roman.greed;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import ru.roman.greed.gui.pane.main.MainView;
import ru.roman.greed.util.ExceptionHandler;

import javax.swing.*;

/**
 *
 * User: Roman
 * DateTime: 01.09.12 0:05
 */
public class StartApp {
    private static final Log log = LogFactory.getLog(StartApp.class);

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                try {
                    ExceptionHandler.registerUncatchExceptionHandler();
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                    new MainView();
                    log.info("grEEt started");
                } catch (Throwable e) {
                    ExceptionHandler.showMessage(e);
                }
            }
        });
    }
}
