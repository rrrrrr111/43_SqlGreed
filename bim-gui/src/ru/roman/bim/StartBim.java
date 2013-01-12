package ru.roman.bim;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import ru.roman.bim.gui.pane.PaineFactory;
import ru.roman.bim.gui.pane.tray.TrayUtils;
import ru.roman.bim.util.Const;
import ru.roman.bim.util.GuiUtils;

import java.awt.*;


/** @author Roman 17.12.12 23:44 */
public class StartBim {
    private static final Log log = LogFactory.getLog(StartBim.class);

    public static void main(String args[]) {
        GuiUtils.startSwingApp(new GuiUtils.Starter() {
            @Override
            public void onStart() {
                PaineFactory.createMainView();

                TrayUtils.showTrayNotification("Bim started", TrayIcon.MessageType.INFO);
                log.info(Const.APP_NAME + " started");
            }
        });
    }

    public static void stop(int exitCode) {
        try {
            TrayUtils.removeTrayIcon();
            log.info(Const.APP_NAME + " closed");
        } catch (Exception ex){
            log.info("Error while closing", ex);
        } finally {
            System.exit(exitCode);
        }
    }
}
