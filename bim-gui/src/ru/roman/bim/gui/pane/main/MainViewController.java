package ru.roman.bim.gui.pane.main;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import ru.roman.bim.gui.common.mvc.Controller;
import ru.roman.bim.gui.custom.tools.OpacityTimer;
import ru.roman.bim.gui.pane.PaineFactory;
import ru.roman.bim.gui.pane.tray.TrayUtils;
import ru.roman.bim.service.ServiceFactory;
import ru.roman.bim.service.cache.LocalCache;
import ru.roman.bim.service.cache.LocalCacheFactory;
import ru.roman.bim.service.gae.GaeConnector;
import ru.roman.bim.service.ghost.GhostController;
import ru.roman.bim.service.ghost.GhostService;
import ru.roman.bim.service.ghost.GhostServiceImpl;

/** @author Roman 21.12.12 0:24 */
public class MainViewController extends Controller<MainView, MainViewModel> implements GhostController {
    private static final Log log = LogFactory.getLog(MainViewController.class);

    private final GaeConnector gaeConnector = ServiceFactory.getGaeConnector();
    private LocalCache localCache;
    private final OpacityTimer opacityTimer;
    private final GhostService ghostService;


    private volatile State state;


    public MainViewController(MainView view) {
        super(view);
        opacityTimer = new OpacityTimer(view);
        ghostService = new GhostServiceImpl(this);
    }

    public void onInit() {

        TrayUtils.addTrayIcon();
        localCache = LocalCacheFactory.createLocalCacheInstance(0, 0);   // TODO - сохранять на сервисе
        currModel = localCache.getCurrent();
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
        currModel = localCache.getPrev();
        view.fillWidgets(currModel);
    }

    protected void onNext() {
        currModel = localCache.getNext();
        view.fillWidgets(currModel);
    }

    protected void onTranslate() {
        view.translate();
    }

    public void showSlowly() {
        onNext();
        opacityTimer.showSlowly();
    }

    public void hideSlowly() {
        opacityTimer.hideSlowly();
    }

    public void hideQuickly() {
        opacityTimer.hideQuickly();
    }

    public void showQuickly() {
        opacityTimer.showQuickly();
    }

    public synchronized void changeState(State state) {
        switch (state) {
            case DISABLED:
                ghostService.stop();
                opacityTimer.hideQuickly();
                break;
            case SCHEDULED:
                ghostService.start();
                break;
        }
        this.state = state;
    }

    public LocalCache getLocalCache() {
        return localCache;
    }


    public GhostService getGhostService() {
        return ghostService;
    }
}
