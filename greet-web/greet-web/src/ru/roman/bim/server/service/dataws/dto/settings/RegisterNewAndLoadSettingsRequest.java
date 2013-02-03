package ru.roman.bim.server.service.dataws.dto.settings;

import ru.roman.bim.server.service.dataws.dto.AbstractRequest;

/** @author Roman 28.01.13 22:45 */
public class RegisterNewAndLoadSettingsRequest extends AbstractRequest {

    private UserSettingsModel userSettingsModel;

    public RegisterNewAndLoadSettingsRequest(UserSettingsModel userSettingsModel) {
        this.userSettingsModel = userSettingsModel;
    }

    public RegisterNewAndLoadSettingsRequest() {
    }

    public UserSettingsModel getUserSettingsModel() {
        return userSettingsModel;
    }

    public void setUserSettingsModel(UserSettingsModel userSettingsModel) {
        this.userSettingsModel = userSettingsModel;
    }
}
