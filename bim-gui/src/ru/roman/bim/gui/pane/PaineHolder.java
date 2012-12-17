package ru.roman.bim.gui.pane;

import ru.roman.bim.gui.pane.main.MainView;
import ru.roman.bim.util.BimException;

/**
 *
 * User: Roman
 * DateTime: 01.09.12 0:15
 */
public class PaineHolder {

    private static MainView mainView;

    public static void setMainView(MainView mainView) {
        PaineHolder.mainView = mainView;
    }

    public static MainView getMainView() {
        if (mainView == null) {
            throw new BimException("Link to main pain hasn't set");
        }
        return mainView;
    }
}
