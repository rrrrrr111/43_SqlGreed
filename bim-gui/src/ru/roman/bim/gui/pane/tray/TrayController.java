package ru.roman.bim.gui.pane.tray;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import ru.roman.bim.StartBim;
import ru.roman.bim.gui.pane.PaineFactory;
import ru.roman.bim.gui.pane.main.State;
import ru.roman.bim.gui.pane.settings.Settings;
import ru.roman.bim.service.ghost.GhostServiceImpl;
import ru.roman.bim.util.Const;
import ru.roman.bim.util.GuiUtil;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;

/** @author Roman 23.03.13 10:38 */
public class TrayController {
    private static final Log log = LogFactory.getLog(TrayController.class);

    final Timer disabilityTicker;

    public TrayController() {
        this.disabilityTicker = new Timer(0, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TrayUtils.getPopupMenu().setDisableItemSelected(false);
                log.info("Tray popup disabilityItem returned by timer");
            }
        });
        disabilityTicker.setCoalesce(true);
        disabilityTicker.setRepeats(false);
    }

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
            disabilityTicker.setInitialDelay(
                    GhostServiceImpl.toMilliSec(Settings.get().getDisabilityDuration())
            );
            disabilityTicker.restart();
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
