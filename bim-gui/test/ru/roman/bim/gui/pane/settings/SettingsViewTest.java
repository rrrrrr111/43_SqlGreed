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
        GuiUtil.startSwingApp(new CallBackChain<Void>() {
            @Override
            protected void onSuccess(Void result) {
                final SettingsView settingsView = PaineFactory.createSettingsView();
                settingsView.setVisible(true);
                settingsView.selectTab(1);
            }
        });

    }


    public static void main(String... args) {
        GuiUtil.startSwingApp(new CallBackChain<Void>() {
            @Override
            public void onSuccess(Void result) {
                PaineFactory.getSettingsViewController().fillCredentials(new CallBackChain<UserSettingsModel>() {
                    @Override
                    public void onSuccess(UserSettingsModel arg) {
                        throw new RuntimeException("not implemented");
                    }
                });
            }
        });
    }
}
