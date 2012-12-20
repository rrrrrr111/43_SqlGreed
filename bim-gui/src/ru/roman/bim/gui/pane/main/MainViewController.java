package ru.roman.bim.gui.pane.main;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import ru.roman.bim.gui.common.Controller;

/** @author Roman 21.12.12 0:24 */
public class MainViewController extends Controller<MainView> {
    private static final Log log = LogFactory.getLog(MainViewController.class);

    public MainViewController(MainView view) {
        super(view);
    }

    public void onRatingChange(int rating) {
        //log.info("rating changed to " + rating);

    }

    public void onEdit() {


    }

    public void onPrev() {


    }

    public void onNext() {


    }

    public void onTranslate() {


    }

    public void onSettings() {


    }
}
