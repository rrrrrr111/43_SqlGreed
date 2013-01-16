package ru.roman.bim.gui.pane.settings;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import ru.roman.bim.gui.common.View;

/** @author Roman 16.01.13 23:58 */
public class SettingsView implements View<SettingsViewModel, SettingsView, SettingsViewController> {
    private static final Log log = LogFactory.getLog(SettingsView.class);

    private final SettingsViewController controller = new SettingsViewController(this);

    public SettingsView() {

        createView();
        controller.onInit();
    }

    private void createView() {






    }


    @Override
    public SettingsViewController getController() {
        return controller;
    }

    @Override
    public void setValues(SettingsViewModel model) {
        throw new RuntimeException("not implemented");
    }
}
