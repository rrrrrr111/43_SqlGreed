package ru.roman.greet.gui.pane;

import ru.roman.greet.gui.pane.chooser.FileChooser;
import ru.roman.greet.gui.pane.main.MainView;
import ru.roman.greet.gui.pane.queryinfo.QueryInfoView;
import ru.roman.greet.util.AppGreedException;

/**
 *
 * User: Roman
 * DateTime: 01.09.12 0:15
 */
public class PaineHolder {

    private static MainView mainView;
    private static FileChooser fileChooser;
    private static QueryInfoView queryInfoView;

    public static MainView getMainView() {
        if (mainView == null) {
            throw new AppGreedException("Link to main pain hasn't set");
        }
        return mainView;
    }

    public static void setMainView(MainView mainView) {
        PaineHolder.mainView = mainView;
    }

    public static FileChooser getFileChooser() {
        if (fileChooser == null) {
            fileChooser = new FileChooser();
        }
        return fileChooser;
    }

    public static QueryInfoView getQueryInfoView() {
        if (queryInfoView == null) {
            queryInfoView = new QueryInfoView();
        }
        return queryInfoView;
    }
}
