package ru.roman.bim.gui.pane.settings;

import org.junit.Test;
import ru.roman.bim.StartBim;
import ru.roman.bim.gui.pane.PaineFactory;
import ru.roman.bim.util.GuiUtil;

/** @author Roman 17.01.13 23:49 */
public class SettingsViewTest {


    @Test
    public void open() {
        GuiUtil.startSwingApp(new GuiUtil.Starter() {
            @Override
            public void onStart() {
                final SettingsView settingsView = PaineFactory.createSettingsView();
                settingsView.setVisible(true);
                settingsView.selectTab(1);
            }
        });

    }


    public static void main(String... args) {
        GuiUtil.startSwingApp(new GuiUtil.Starter() {
            @Override
            public void onStart() {
                PaineFactory.getSettingsViewController().fillCredentials(new StartBim.RegistrationCallBack() {
                    @Override
                    public void afterRegistration() {
                        throw new RuntimeException("not implemented");
                    }
                });
            }
        });

    }

}
