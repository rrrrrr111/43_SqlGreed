package ru.roman.bim.gui.pane.tray;

import ru.roman.bim.StartBim;
import ru.roman.bim.gui.pane.PaineFactory;
import ru.roman.bim.gui.pane.main.State;
import ru.roman.bim.util.Const;
import ru.roman.bim.util.GuiUtil;

import java.awt.event.ItemEvent;

/** @author Roman 23.03.13 10:38 */
public class TrayController {

    public void onShowInfo() {
        GuiUtil.showInfoMessage("This is Bim  v." + Const.VERSION);
    }

    public void onClearCache() {
        PaineFactory.getMainViewController().getLocalCache().clearCache();
    }

    public void onStateChanged(ItemEvent e) {
        int cb1Id = e.getStateChange();
        if (cb1Id == ItemEvent.SELECTED){
            PaineFactory.getMainViewController().changeState(State.DISABLED);
        } else {
            PaineFactory.getMainViewController().changeState(State.SCHEDULED);
        }
    }

    public void onShowEdit() {
        PaineFactory.getMainViewController().onEdit();
    }


    public void onShowSettings() {
        PaineFactory.getSettingsViewController().showSettingsView();
    }

    public void onExit() {
        StartBim.stop(0);
    }

    public void onShowQuickly() {
        PaineFactory.getMainViewController().showQuickly();
    }
}
