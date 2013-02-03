package ru.roman.bim.gui.pane.settings;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import ru.roman.bim.StartBim;
import ru.roman.bim.gui.common.mvc.Controller;
import ru.roman.bim.gui.pane.PaineFactory;
import ru.roman.bim.service.ServiceFactory;
import ru.roman.bim.service.cache.LocalCache;
import ru.roman.bim.service.config.ConfigService;
import ru.roman.bim.service.gae.GaeConnector;
import ru.roman.bim.service.gae.wsclient.UserSettingsModel;
import ru.roman.bim.service.wordload.WordLoaderService;
import ru.roman.bim.util.GuiUtil;

import java.io.File;
import java.util.Arrays;


/** @author Roman 16.01.13 23:59 */
public class SettingsViewController extends Controller<SettingsView, SettingsViewModel> {
    private static final Log log = LogFactory.getLog(SettingsViewController.class);

    private final WordLoaderService wordLoaderService = ServiceFactory.getWordLoaderService();
    private final GaeConnector gaeConnector = ServiceFactory.getGaeConnector();
    private final SettingsViewValidator validator = new SettingsViewValidator();
    private final ConfigService configService = ServiceFactory.getConfigService();

    private State state = State.REGISTERED;
    private StartBim.RegistrationCallBack callBack;

    public SettingsViewController(SettingsView view) {
        super(view);
    }

    public void onInit() {

    }

    public void fillCredentials(StartBim.RegistrationCallBack callBack) {
        state = State.FIRST_INPUT;
        this.callBack = callBack;
        view.prepareForFirstInput();
        view.setVisible(true);
        view.selectTab(1);
        if (currModel == null) {
            currModel = new SettingsViewModel();
            currModel.setPortion(100L);
            currModel.getRatings().addAll(Arrays.asList(1, 2, 3));
        }
        modelDataToView();
    }

    public void reloadSettings(StartBim.RegistrationCallBack callBack) {
        SettingsViewModel config = configService.loadSettingsConfig();
        try {
            UserSettingsModel res = gaeConnector.registerNewAndLoadSettings(config);
            currModel = new SettingsViewModel(res);
        } catch (Exception e) {
            log.error("Error while settings loading", e);
            currModel = config;
            fillCredentials(callBack);
            return;
        }
        configService.saveSettingsConfig(currModel);
        callBack.afterRegistration();
    }

    public void showSettingsView() {
        currModel = configService.loadSettingsConfig();
        modelDataToView();
        view.setVisible(true);
    }

    public void onSaveOrRegister() {
        viewDataToModel();
        // validation
        validator.validateLogin(currModel.getLogin());
        validator.validateRatings(currModel.getRatings());

        switch (state) {
            case FIRST_INPUT:
                UserSettingsModel res = gaeConnector.registerNewAndLoadSettings(currModel);
                currModel = new SettingsViewModel(res);
                break;
            case REGISTERED:
                gaeStoreSettings();
                break;
        }

        configService.saveSettingsConfig(currModel);
        modelDataToView();

        switch (state) {
            case FIRST_INPUT:

                view.setVisible(false);
                callBack.afterRegistration();
                callBack = null;
                view.prepareSettingsView();
                state = State.REGISTERED;
                break;
            case REGISTERED:
                GuiUtil.showInfoMessage("Settings saved successfully");

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
        gaeStoreSettings();
        configService.saveSettingsConfig(currModel);
    }

    public void onCancel() {
        if (state == State.FIRST_INPUT) {
            StartBim.stop(0);
        } else {
            view.setVisible(false);
        }
    }


    public void onBroseFileForLoading() {
        File fileFroLoading = PaineFactory.createFileChooser().showSelectFileDialog();
        if (fileFroLoading != null) {
            log.info("Selected file for loading : " + fileFroLoading);
            wordLoaderService.loadFile(fileFroLoading);
            GuiUtil.showInfoMessage("Loading complete");
        }
    }

    public SettingsViewValidator getValidator() {
        return validator;
    }


}
