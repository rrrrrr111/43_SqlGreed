package ru.roman.bim;


import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import ru.roman.bim.gui.pane.PaineFactory;
import ru.roman.bim.gui.pane.settings.Settings;
import ru.roman.bim.gui.pane.settings.SettingsViewController;
import ru.roman.bim.gui.pane.tray.TrayUtils;
import ru.roman.bim.service.ServiceFactory;
import ru.roman.bim.service.config.ConfigService;
import ru.roman.bim.service.lock.LockerUtils;
import ru.roman.bim.util.Const;
import ru.roman.bim.util.GuiUtil;

import java.io.File;
import java.io.IOException;


/** @author Roman 17.12.12 23:44 */
public class StartBim {
    private static final Log log = LogFactory.getLog(StartBim.class);
    private static SettingsViewController settingsController = PaineFactory.getSettingsViewController();

    public static void main(String args[]) {
        GuiUtil.startSwingApp(new GuiUtil.Starter() {
            @Override
            public void onStart() {
                prepareEnvironment();
                prepareCredentials(new RegistrationCallBack() {
                    @Override
                    public void afterRegistration() {
                        PaineFactory.createMainView();
                        LockerUtils.tryLockApplication();
                    }
                });
            }
        });
    }

    public static void stop(int exitCode) {
        try {
            TrayUtils.removeTrayIcon();
            settingsController.saveConfig();
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

    private static void prepareCredentials(RegistrationCallBack callBack) {
        if (Settings.get() == null) {
            settingsController.fillCredentials(callBack);
        } else {
            settingsController.reloadSettings(callBack);
        }

    }

    public interface RegistrationCallBack {
        void afterRegistration();
    }

}
