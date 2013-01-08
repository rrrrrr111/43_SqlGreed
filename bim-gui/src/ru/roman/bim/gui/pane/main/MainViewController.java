package ru.roman.bim.gui.pane.main;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import ru.roman.bim.gui.common.Controller;
import ru.roman.bim.gui.custom.tools.OpacityTimer;
import ru.roman.bim.gui.pane.PaineFactory;
import ru.roman.bim.gui.pane.tray.TrayUtils;
import ru.roman.bim.service.ServiceFactory;
import ru.roman.bim.service.cache.LocalCache;
import ru.roman.bim.service.cache.LocalCacheFactory;
import ru.roman.bim.service.gae.GaeConnector;

/** @author Roman 21.12.12 0:24 */
public class MainViewController extends Controller<MainView, MainViewModel> {
    private static final Log log = LogFactory.getLog(MainViewController.class);

    private final GaeConnector gaeConnector = ServiceFactory.getGaeConnector();
    private LocalCache localCache;
    private final OpacityTimer opacityTimer;


    private volatile State state;


    public MainViewController(MainView view) {
        super(view);
        opacityTimer = new OpacityTimer(view);
    }

    public void onInit() {

        TrayUtils.addTrayIcon();
        localCache = LocalCacheFactory.createLocalCacheInstance(0, 0);   // TODO - сохранять на сервисе
        currModel = localCache.getCurrent();
        view.setValues(currModel);
        showSlowly();

    }


    protected void onRatingChange(Integer rating) {
        //log.info("rating changed to " + rating);
        currModel.setRating(rating.longValue());
        gaeConnector.renewRating(currModel.getId(), rating);
    }

    protected void onEdit() {
        PaineFactory.getEditViewController().show(localCache);
    }

    protected void onPrev() {
        currModel = localCache.getPrev();
        view.setValues(currModel);
    }

    protected void onNext() {
        currModel = localCache.getNext();
        view.setValues(currModel);
    }

    protected void onTranslate() {
        view.translate();
    }

    protected void onSettings() {

    }

    public void showSlowly() {
        opacityTimer.showSlowly();
    }

    public void hideSlowly() {
        opacityTimer.hideSlowly();
    }

    public void showQuickly() {
        opacityTimer.showQuickly();

    }

    public synchronized void setState(State state) {
        this.state = state;
    }

    public LocalCache getLocalCache() {
        return localCache;
    }
}
