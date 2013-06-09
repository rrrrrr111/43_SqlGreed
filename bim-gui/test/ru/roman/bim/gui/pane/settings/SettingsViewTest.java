package ru.roman.bim.gui.pane.settings;

import org.junit.Test;
import ru.roman.bim.gui.common.cbchain.CallBackChain;
import ru.roman.bim.gui.pane.PaineFactory;
import ru.roman.bim.service.gae.wsclient.UserSettingsModel;
import ru.roman.bim.util.GuiUtil;

/** @author Roman 17.01.13 23:49 */
public class SettingsViewTest {


    @Test
    public void open() {

    }


    public static void main(String... args) {
        GuiUtil.startSwingApp(new CallBackChain<Void>() {
            @Override
            public void onSuccess(Void result) {
                final SettingsViewController settingsViewController = PaineFactory.getSettingsViewController();
                settingsViewController.fillCredentials(new CallBackChain<UserSettingsModel>() {
                    @Override
                    public void onSuccess(UserSettingsModel arg) {
                        throw new RuntimeException("not implemented");
                    }
                });
                settingsViewController.selectTab(3);

            }
        });
    }
}
