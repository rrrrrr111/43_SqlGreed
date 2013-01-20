package ru.roman.bim.gui.pane.settings;

import ru.roman.bim.gui.pane.PaineFactory;
import ru.roman.bim.util.GuiUtils;

/** @author Roman 17.01.13 23:49 */
public class SettingsViewTest {






    public static void main(String[] args) {
        GuiUtils.startSwingApp(new GuiUtils.Starter() {
            @Override
            public void onStart() {
                final SettingsView settingsView = PaineFactory.createSettingsView();
                settingsView.setVisible(true);
                settingsView.selectTab(3);
            }
        });

    }
}
