package ru.roman.bim.gui.pane.main;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import ru.roman.bim.gui.common.Controller;
import ru.roman.bim.gui.custom.tools.OpacityTimer;
import ru.roman.bim.gui.pane.tray.TrayUtils;

/** @author Roman 21.12.12 0:24 */
public class MainViewController extends Controller<MainView, MainViewModel> {
    private static final Log log = LogFactory.getLog(MainViewController.class);

    private final OpacityTimer opacityTimer;


    private volatile State state;




    public MainViewController(MainView view) {
        super(view);
        opacityTimer = new OpacityTimer(view);
    }

    public void onInit() {

        TrayUtils.addTrayIcon();

        currModel = new MainViewModel();
        currModel.setRating(1);
        currModel.setTextFaced("hello it's a question... sd fs sdfasdfas sd fsdfssd safsdf sdfds s fasd sd f sdfsdf sdfsdf sfsfsdf sf sf  sdfsd sdf sdf s dfsf");
        currModel.setTextShadowed("         Это типа перевод...");
        currModel.setType("type...");
        view.setValues(currModel);

        showSlowly();

    }


    protected void onRatingChange(int rating) {
        //log.info("rating changed to " + rating);
        currModel.setRating(rating);

    }

    protected void onEdit() {


    }

    protected void onPrev() {
        hideSlowly();
    }

    protected void onNext() {
        showSlowly();

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
}
