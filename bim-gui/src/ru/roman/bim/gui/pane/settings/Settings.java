package ru.roman.bim.gui.pane.settings;

import ru.roman.bim.service.ServiceFactory;

/** @author Roman 29.01.13 22:37 */
public class Settings {

    public static SettingsViewModel get() {
        return ServiceFactory.getConfigService().loadSettingsConfig();
    }
}
