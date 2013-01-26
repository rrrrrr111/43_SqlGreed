package ru.roman.bim.server.service.data.dto.settings;

import ru.roman.bim.server.service.data.dto.AbstractRequest;

/** @author Roman 22.12.12 15:58 */
public class StoreSettingsRequest extends AbstractRequest {

    private UserSettingsModel userSettingsModel;


    public UserSettingsModel getUserSettingsModel() {
        return userSettingsModel;
    }

    public void setUserSettingsModel(UserSettingsModel userSettingsModel) {
        this.userSettingsModel = userSettingsModel;
    }
}
