package ru.roman.bim.service.gae;

import ru.roman.bim.gui.common.cbchain.CallBackChain;
import ru.roman.bim.gui.custom.widget.LoadingPanel;
import ru.roman.bim.gui.pane.main.MainViewModel;
import ru.roman.bim.service.gae.wsclient.GetListRequest;
import ru.roman.bim.service.gae.wsclient.GetListResp;
import ru.roman.bim.service.gae.wsclient.UserSettingsModel;


/** @author Roman 22.12.12 15:35 */
public interface GaeConnector {


    void save(MainViewModel model, GaeConnector.GaeCallBack<Long> callBack);
    /* сервис не может возвращать пустой список, всегда хотябы одно значение */
    void getList(GetListRequest request, GaeConnector.GaeCallBack<GetListResp> callBack);

    void renewRating(Long id, Integer rating);

    void storeSettings(UserSettingsModel model);

    void registerNewAndLoadSettings(UserSettingsModel model, GaeConnector.GaeCallBack<UserSettingsModel> callBack);

    void systemTask(int num);



    public static abstract class GaeCallBack<T> extends CallBackChain<T> {

        /**
         *
         * @param showLoading
         * @param previous  - будет выполнен перед данным колбеком
         * @param next  - будет выполнен после данного колбека
         */
        protected GaeCallBack(boolean showLoading, CallBackChain<T> previous, CallBackChain<T> next) {
            super(previous, next);
            if (showLoading) {
                showLoading();
            }
        }

        protected GaeCallBack(CallBackChain<T> previous, CallBackChain<T> next) {
            this(true, previous, next);
        }

        public GaeCallBack(boolean showLoading, CallBackChain previous) {
            this(showLoading, previous, null);
        }

        public GaeCallBack(boolean showLoading) {
            this(showLoading, null);
        }

        protected GaeCallBack(CallBackChain previous) {
            this(true ,previous);
        }

        public GaeCallBack() {
            this(true, null);
        }

        public final void run(T result){
            stopLoading();
            super.run(result);
        }

        public final void exception(Exception e) {
            stopLoading();
            super.exception(e);
        }


        public final void showLoading() {
            LoadingPanel.activateSharedLoading();
        }

        public final void stopLoading() {
            LoadingPanel.stopSharedLoading();
        }

    }
}
