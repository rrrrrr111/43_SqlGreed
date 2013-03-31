package ru.roman.bim;


import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import ru.roman.bim.gui.common.cbchain.CallBackChain;
import ru.roman.bim.gui.pane.PaineFactory;
import ru.roman.bim.gui.pane.settings.Settings;
import ru.roman.bim.gui.pane.settings.SettingsViewController;
import ru.roman.bim.gui.pane.tray.TrayUtils;
import ru.roman.bim.service.gae.wsclient.UserSettingsModel;
import ru.roman.bim.service.lock.LockerUtils;
import ru.roman.bim.util.Const;
import ru.roman.bim.util.ExceptionHandler;
import ru.roman.bim.util.GuiUtil;

import java.io.File;
import java.io.IOException;


/** @author Roman 17.12.12 23:44 */
public class StartBim {
    private static final Log log = LogFactory.getLog(StartBim.class);
    private static SettingsViewController settingsController;

    public static void main(String args[]) {

        GuiUtil.startSwingApp(new CallBackChain<Void>() {
            @Override
            protected void onSuccess(Void result) {
                settingsController = PaineFactory.getSettingsViewController();
                prepareEnvironment();
                prepareCredentials(new CallBackChain<UserSettingsModel>() {
                    @Override
                    public void onSuccess(UserSettingsModel sett) {
                        PaineFactory.createMainView();
                        LockerUtils.tryLockApplication();
                    }
                    @Override
                    protected void onFailure(Exception e) {
                        ExceptionHandler.showErrorMessageAndExit(e);
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

    private static void prepareCredentials(CallBackChain<UserSettingsModel> callBack) {
        if (Settings.get() == null) {
            settingsController.fillCredentials(callBack);
        } else {
            settingsController.reloadSettings(callBack);
        }
    }

}
