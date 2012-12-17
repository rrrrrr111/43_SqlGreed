package ru.roman.bim;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import ru.roman.bim.gui.pane.main.MainView;
import ru.roman.bim.util.Const;
import ru.roman.bim.util.ExceptionHandler;

import javax.swing.*;





/** @author Roman 17.12.12 23:44 */
public class StartBim {
    private static final Log log = LogFactory.getLog(StartBim.class);

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
