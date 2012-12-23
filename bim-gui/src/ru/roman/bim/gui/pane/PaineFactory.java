package ru.roman.bim.gui.pane;

import ru.roman.bim.gui.pane.edit.EditView;
import ru.roman.bim.gui.pane.edit.EditViewController;
import ru.roman.bim.gui.pane.main.MainView;
import ru.roman.bim.gui.pane.main.MainViewController;

/**
 *
 * User: Roman
 * DateTime: 01.09.12 0:15
 */
public class PaineFactory {

    private static MainView mainView;
    private static EditView editView;

    public static MainView createMainView() {
        if (mainView == null) {
            PaineFactory.mainView = new MainView();
        }
        return mainView;
    }

    public static MainViewController getMainViewController() {
        return createMainView().getController();
    }

    public static EditView createEditView() {
        if (editView == null) {
            PaineFactory.editView = new EditView();
        }
        return editView;
    }

    public static EditViewController getEditViewController() {
        return createEditView().getController();
    }
}
