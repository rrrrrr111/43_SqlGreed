package ru.roman.bim.gui.pane.settings;

import ru.roman.bim.gui.common.mvc.Model;
import ru.roman.bim.service.gae.wsclient.UserSettingsModel;
import ru.roman.bim.util.PropUtil;

/** @author Roman 16.01.13 23:59 */
public class SettingsViewModel extends UserSettingsModel implements Model{


    public SettingsViewModel() {
    }

    public SettingsViewModel(UserSettingsModel res) {
        PropUtil.copyProperties(this, res);
        getRatings().addAll(res.getRatings());
    }

    @Override
    public int compareTo(Object o) {
        throw new RuntimeException("not implemented");
    }
}
