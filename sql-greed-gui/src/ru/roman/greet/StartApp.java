package ru.roman.greet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import ru.roman.greet.gui.pane.main.MainView;
import ru.roman.greet.util.Const;
import ru.roman.greet.util.ExceptionHandler;

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
                    log.info(Const.APP_NAME + " started");
                } catch (Throwable e) {
                    ExceptionHandler.showMessage(e);
                }
            }
        });
    }
}
