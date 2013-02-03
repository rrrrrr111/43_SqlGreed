package ru.roman.bim.server.service.dataws.dto.settings;

import ru.roman.bim.server.service.dataws.dto.AbstractResponse;

/** @author Roman 28.01.13 22:46 */
public class RegisterNewAndLoadSettingsResp extends AbstractResponse {

    private UserSettingsModel userSettingsModel;

    public RegisterNewAndLoadSettingsResp(UserSettingsModel userSettingsModel) {
        this.userSettingsModel = userSettingsModel;
    }

    public RegisterNewAndLoadSettingsResp() {
    }

    public UserSettingsModel getUserSettingsModel() {
        return userSettingsModel;
    }

    public void setUserSettingsModel(UserSettingsModel userSettingsModel) {
        this.userSettingsModel = userSettingsModel;
    }
}
