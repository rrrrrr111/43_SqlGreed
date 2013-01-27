package ru.roman.bim.server.service.data.dto.settings;

import ru.roman.bim.server.service.data.dto.AbstractResponse;

/** @author Roman 22.12.12 15:58 */
public class StoreSettingsResp extends AbstractResponse {

    private UserSettingsModel userSettingsModel;

    public StoreSettingsResp(UserSettingsModel userSettingsModel) {
        this.userSettingsModel = userSettingsModel;
    }

    public StoreSettingsResp() {
    }

    public UserSettingsModel getUserSettingsModel() {
        return userSettingsModel;
    }

    public void setUserSettingsModel(UserSettingsModel userSettingsModel) {
        this.userSettingsModel = userSettingsModel;
    }
}
