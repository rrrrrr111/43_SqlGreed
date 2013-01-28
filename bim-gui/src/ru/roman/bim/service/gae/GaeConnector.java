package ru.roman.bim.service.gae;

import ru.roman.bim.gui.pane.main.MainViewModel;
import ru.roman.bim.service.gae.wsclient.GetListRequest;
import ru.roman.bim.service.gae.wsclient.GetListResp;
import ru.roman.bim.service.gae.wsclient.UserSettingsModel;


/** @author Roman 22.12.12 15:35 */
public interface GaeConnector {


    Long save(MainViewModel model);
    /* предполагаем что сервис не будет возвращать пустой список, всегда хотябы одно значение */
    GetListResp getList(GetListRequest request);

    void renewRating(Long id, Integer rating);

    void storeSettings(UserSettingsModel model);

    UserSettingsModel registerNewAndLoadSettings(UserSettingsModel model);
}
