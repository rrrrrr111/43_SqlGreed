package ru.roman.bim;


import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import ru.roman.bim.gui.pane.PaineFactory;
import ru.roman.bim.gui.pane.tray.TrayUtils;
import ru.roman.bim.service.lock.LockerUtils;
import ru.roman.bim.util.Const;
import ru.roman.bim.util.GuiUtils;

import java.io.File;
import java.io.IOException;


/** @author Roman 17.12.12 23:44 */
public class StartBim {
    private static final Log log = LogFactory.getLog(StartBim.class);

    public static void main(String args[]) {
        GuiUtils.startSwingApp(new GuiUtils.Starter() {
            @Override
            public void onStart() {
                prepareEnvironment();
                PaineFactory.createMainView();
                LockerUtils.tryLockApplication();


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

    private static void prepareEnvironment() {
        try {
            File configDir = new File(Const.APP_CONFIG_PATH);
            if (!configDir.exists()) {
                FileUtils.forceMkdir(configDir);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
