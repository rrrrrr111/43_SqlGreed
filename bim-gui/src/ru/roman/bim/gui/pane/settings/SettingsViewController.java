package ru.roman.bim.gui.pane.settings;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import ru.roman.bim.StartBim;
import ru.roman.bim.gui.common.cbchain.CallBackChain;
import ru.roman.bim.gui.common.mvc.Controller;
import ru.roman.bim.gui.pane.PaineFactory;
import ru.roman.bim.service.ServiceFactory;
import ru.roman.bim.service.cache.LocalCache;
import ru.roman.bim.service.config.ConfigService;
import ru.roman.bim.service.file.subtitlesmerge.SubtitlesMergeService;
import ru.roman.bim.service.file.wordload.WordLoaderService;
import ru.roman.bim.service.gae.GaeConnector;
import ru.roman.bim.service.gae.wsclient.UserSettingsModel;
import ru.roman.bim.util.BimException;
import ru.roman.bim.util.GuiUtil;

import java.awt.*;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;


/** @author Roman 16.01.13 23:59 */
public class SettingsViewController extends Controller<SettingsView, SettingsViewModel> {
    private static final Log log = LogFactory.getLog(SettingsViewController.class);

    private final WordLoaderService wordLoaderService = ServiceFactory.getWordLoaderService();
    private final GaeConnector gaeConnector = ServiceFactory.getGaeConnector();
    private final SettingsViewValidator validator = new SettingsViewValidator();
    private final ConfigService configService = ServiceFactory.getConfigService();
    private final SubtitlesMergeService subtitlesMergeService = ServiceFactory.getSubtitlesMergeService();

    private State state = State.REGISTERED;
    private CallBackChain<UserSettingsModel> callBack;

    public SettingsViewController(SettingsView view) {
        super(view);
    }

    public void onInit() {

    }

    public void fillCredentials(CallBackChain<UserSettingsModel> callBack) {
        state = State.FIRST_INPUT;
        this.callBack = callBack;
        view.prepareForFirstInput();
        view.setVisible(true);
        view.selectTab(1);
        if (currModel == null) {
            // заполнение настройками по умолчанию
            currModel = new SettingsViewModel();
            currModel.setPortion(100L);
            currModel.getRatings().addAll(Arrays.asList(1, 2, 3));
        }
        modelDataToView();
    }

    public void reloadSettings(final CallBackChain<UserSettingsModel> callBack) {
        final SettingsViewModel config = configService.loadSettingsConfig();
        gaeConnector.registerNewAndLoadSettings(config, new GaeConnector.GaeCallBack<UserSettingsModel>() {
            @Override
            public void onSuccess(UserSettingsModel result) {
                currModel = new SettingsViewModel(result);
                configService.saveSettingsConfig(currModel);
                callBack.run(result);
            }
            @Override
            public void onFailure(Exception e) {
                log.error("Error while settings loading", e);
                currModel = config;
                fillCredentials(callBack);
            }
        });
    }

    public void showSettingsView() {
        currModel = configService.loadSettingsConfig();
        modelDataToView();
        view.setVisible(true);
        view.setState(Frame.NORMAL);
    }

    /**
     * По кнопке сохранить или зарегистрироваться
     *
     *
     */
    public void onSaveOrRegister() {
        viewDataToModel();
        // validation
        validator.validateLogin(currModel.getLogin());
        validator.validateRatings(currModel.getRatings());

        switch (state) {
            case FIRST_INPUT:
                try {
                    view.setVisible(false);

                    final UserSettingsModel param = currModel;
                    currModel = null;
                    // тут асинхронный вызов меняем на синхронный,
                    // перед запуском приложения должны быть загружены настройки
                    final CountDownLatch signal = new CountDownLatch(1);
                    gaeConnector.registerNewAndLoadSettings(param, new GaeConnector.GaeCallBack<UserSettingsModel>() {
                        @Override
                        protected void onSuccess(UserSettingsModel result) {
                            currModel = new SettingsViewModel(result);
                            signal.countDown();
                        }
                        @Override
                        protected void onFailure(Exception e) {
                            signal.countDown();
                            callBack.exception(e);
                        }
                    });
                    if (!signal.await(5, TimeUnit.MINUTES)) {
                        throw new BimException("Illegal state of synchronously invocation, CountDownLatch" +
                                " waits more then 5 minutes");
                    } else if (currModel == null) {
                        return;
                    }
                } catch (Exception e) {
                    callBack.exception(e);
                    return;
                }
                break;
            case REGISTERED:
                gaeStoreSettings();
                break;
        }

        configService.saveSettingsConfig(currModel);
        modelDataToView();

        switch (state) {
            case FIRST_INPUT:
                callBack.run(currModel);            // колбек запуска приложения, послее первого ввода учетных данных
                callBack = null;
                view.prepareSettingsView();
                state = State.REGISTERED;
                break;
            case REGISTERED:
                GuiUtil.showInfoMessage("Settings saved successfully");
                view.setVisible(false);
                break;
        }
    }

    private void gaeStoreSettings() {// additional properties
        LocalCache mainCache = PaineFactory.getMainViewController().getLocalCache();
        //model.setCacheMaxSize();
        currModel.setCurrentNum(mainCache.getCurrentNum().longValue());
        //model.setFacedLangId(Const.DEFAULT_LANG_ID.longValue());
        //model.setId();
        //model.setLogin(loginText.getText());
        //model.setOpacity();
        //model.setPassword(GuiUtil.createDigest(passwordText.getPassword()));
        //model.setPortion(Long.valueOf(portionText.getText()));
        //model.setPreviewDuration();
        //model.setPreviewInterval();
        currModel.setRecordsCount(mainCache.getRecordsCount().longValue());
        //model.setShadowedLangId(Const.);
        //model.setSortingDirection(Const.DEFAULT_SORTING_DIRECTION);
        //model.setSortingField(Const.DEFAULT_SORTING_FIELD);
        //model.getRatings().addAll(ratingsPanel.getRatings());

        gaeConnector.storeSettings(currModel);
    }


    public void saveConfig() {
        if (currModel == null) {
            log.info("Settings is null, will not be saved to config file");
        } else {
            gaeStoreSettings();
            configService.saveSettingsConfig(currModel);
        }
    }

    public void onCancel() {
        if (state == State.FIRST_INPUT) {
            StartBim.stop(0);
        } else {
            view.setVisible(false);
        }
    }


    public void onBroseFileForLoading() {
        File fileFroLoading = PaineFactory.createXlsFileChooser().showSelectFileDialog();
        if (fileFroLoading != null) {
            log.info("Selected file for loading : " + fileFroLoading);
            wordLoaderService.loadFile(fileFroLoading);
            GuiUtil.showInfoMessage("Loading complete");
        }
    }

    public SettingsViewValidator getValidator() {
        return validator;
    }

    public void selectTab(int tabNum) {
        view.selectTab(tabNum);
    }

    public void startMerge(List<String> formatsList) {
        subtitlesMergeService.startMerge(formatsList);
    }
}
