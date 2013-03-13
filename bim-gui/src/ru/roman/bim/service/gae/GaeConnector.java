package ru.roman.bim.service.gae;

import ru.roman.bim.gui.custom.widget.LoadingPanel;
import ru.roman.bim.gui.pane.main.MainViewModel;
import ru.roman.bim.service.gae.wsclient.GetListRequest;
import ru.roman.bim.service.gae.wsclient.GetListResp;
import ru.roman.bim.service.gae.wsclient.UserSettingsModel;
import ru.roman.bim.util.ExceptionHandler;


/** @author Roman 22.12.12 15:35 */
public interface GaeConnector {


    void save(MainViewModel model, GaeConnector.GaeCallBack<Long> callBack);
    /* сервис не может возвращать пустой список, всегда хотябы одно значение */
    void getList(GetListRequest request, GaeConnector.GaeCallBack<GetListResp> callBack);

    void renewRating(Long id, Integer rating);

    void storeSettings(UserSettingsModel model);

    void registerNewAndLoadSettings(UserSettingsModel model, GaeConnector.GaeCallBack<UserSettingsModel> callBack);

    void systemTask(int num);



    public static abstract class GaeCallBack<T> {

        public GaeCallBack(boolean showLoading) {
            if (showLoading) {
                showLoading();
            }
        }

        public void showLoading() {
            LoadingPanel.activateSharedLoading();
        }

        public void stopLoading() {
            LoadingPanel.stopSharedLoading();
        }

        public GaeCallBack() {
            this(true);
        }

        public void run(T result){
            stopLoading();
            onSuccess(result);
        }

        protected void exception(Exception e) {
            stopLoading();
            onFailure(e);
        }

        protected void onFailure(Exception e) {
            ExceptionHandler.showMessage(e);
        }

        protected abstract void onSuccess(T result);
    }
}
