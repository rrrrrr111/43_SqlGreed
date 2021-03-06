package ru.roman.bim.gui.pane.main;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.StrBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import ru.roman.bim.gui.common.cbchain.CallBackChain;
import ru.roman.bim.gui.common.mvc.Controller;
import ru.roman.bim.gui.custom.tools.OpacityTimer;
import ru.roman.bim.gui.custom.widget.TransparentWindowSupport;
import ru.roman.bim.gui.pane.PaineFactory;
import ru.roman.bim.gui.pane.settings.SettingsViewModel;
import ru.roman.bim.gui.pane.tray.TrayUtils;
import ru.roman.bim.service.ServiceFactory;
import ru.roman.bim.service.cache.LocalCache;
import ru.roman.bim.service.cache.LocalCacheFactory;
import ru.roman.bim.service.config.ConfigService;
import ru.roman.bim.service.gae.GaeConnector;
import ru.roman.bim.service.ghost.GhostController;
import ru.roman.bim.service.ghost.GhostService;
import ru.roman.bim.service.ghost.GhostServiceImpl;
import ru.roman.bim.service.translate.TranslationService;

/** @author Roman 21.12.12 0:24 */
public class MainViewController extends Controller<MainView, MainViewModel> implements GhostController {
    private static final Log log = LogFactory.getLog(MainViewController.class);

    private final GaeConnector gaeConnector = ServiceFactory.getGaeConnector();
    private final ConfigService configService = ServiceFactory.getConfigService();
    private LocalCache localCache;
    private final OpacityTimer opacityTimer;
    private final GhostService ghostService;
    private final TranslationService yaTranslator = ServiceFactory.getYandexService();
    private final TranslationService gooTranslator = ServiceFactory.getGoogleService();

    private final TransparentWindowSupport supp = new TransparentWindowSupport();

    private volatile State state;
    private static final String NO_TRANSLATION = "no translation";


    public MainViewController(MainView view) {
        super(view);
        opacityTimer = new OpacityTimer(view);
        ghostService = new GhostServiceImpl(this);
    }

    public void onInit() {

        state = State.SCHEDULED;
        TrayUtils.addTrayIcon();
        final SettingsViewModel sett = configService.loadSettingsConfig();
        localCache = LocalCacheFactory.createLocalCacheInstance(sett.getCurrentNum(), sett.getRecordsCount());
        currModel = localCache.getCurrentSync();
        view.fillWidgets(currModel);
        ghostService.start();

    }


    protected void onRatingChange(Integer rating) {
        //log.info("rating changed to " + rating);
        currModel.setRating(rating.longValue());
        gaeConnector.renewRating(currModel.getId(), rating);
    }

    public void onEdit() {
        PaineFactory.getEditViewController().show(localCache);
    }

    protected void onPrev() {
        localCache.getPrev(new CallBackChain<MainViewModel>() {
            @Override
            public void onSuccess(MainViewModel model) {
                currModel = model;
                view.fillWidgets(currModel);
            }
        });
    }

    protected void onNext(CallBackChain<MainViewModel> nextCallBack) {
        localCache.getNext(new CallBackChain<MainViewModel>(null, nextCallBack) {
            @Override
            public void onSuccess(MainViewModel model) {
                currModel = model;
                view.fillWidgets(currModel);
            }
        });
    }

    private static enum TranslationState {
        FACED,
        GOOGLE,
        YANDEX_WORD,
        YANDEX_EXPRESSION,
    }




    protected void onTranslate() {
        if (StringUtils.startsWith(currModel.getTextShadowed(), "_")) {

            final StrBuilder translation = new StrBuilder();
            final String gooTranslation = gooTranslator.translate(currModel.getTextFaced(),
                    currModel.getFacedLangId(), currModel.getShadowedLangId());
            if (checkForAdd(translation, gooTranslation, currModel)) {
                translation.append(gooTranslation);
            }
            final String yaWordTranslation = yaTranslator.translateWord(currModel.getTextFaced(),
                        currModel.getFacedLangId(), currModel.getShadowedLangId());
            if (checkForAdd(translation, yaWordTranslation, currModel)) {
                translation.append("\n\n").append(yaWordTranslation);
            }
            final String yaExprTranslation = yaTranslator.translateExpression(currModel.getTextFaced(),
                            currModel.getFacedLangId(), currModel.getShadowedLangId());
            if (checkForAdd(translation, yaExprTranslation, currModel)) {
                translation.append("\n\n").append(yaExprTranslation);
            }
            if (StringUtils.isBlank(translation)) {
                translation.append("NO TRANSLATION");
            }
            currModel.setTextShadowed(StringUtils.replace(translation.toString(), "\n", "<br/>"));
        }
        view.translate();
    }

    private boolean checkForAdd(StrBuilder translation, String wordTranslation, MainViewModel model) {
        return StringUtils.isNotBlank(wordTranslation) &&
                !StringUtils.containsIgnoreCase(translation, wordTranslation) &&
                !StringUtils.containsIgnoreCase(model.getTextFaced(), wordTranslation);
    }

    public void onShow() {
        onNext(new CallBackChain<MainViewModel>() {
            @Override
            protected void onSuccess(MainViewModel result) {
                opacityTimer.showSlowly();
            }
        });
    }

    public void onHide() {
        opacityTimer.hideSlowly();
    }

    public void hideQuickly() {
        opacityTimer.hideQuickly();
    }

    public void showQuickly() {
        opacityTimer.showQuickly();
        supp.setVisible(true);
    }

    public synchronized void changeState(State state) {
        switch (state) {
            case DISABLED:
                opacityTimer.hideQuickly();
                ghostService.stop();
                break;
            case SCHEDULED:
                ghostService.start();
                break;
        }
        log.info("Changing main state to " + state);
        this.state = state;
    }

    public LocalCache getLocalCache() {
        return localCache;
    }

    public void startGhostFromOpened() {
        switch (state) {
            case SCHEDULED:
                ghostService.startFromOpened();
                break;
            case DISABLED:
                opacityTimer.hideSlowly();
                break;
        }
        supp.setVisible(false);
    }

    public void stopGhost() {
        switch (state) {
            case SCHEDULED:
                ghostService.stop();
                break;
        }
    }

    public State getState() {
        return state;
    }
}
