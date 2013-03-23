package ru.roman.bim.service.ghost;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import ru.roman.bim.gui.pane.settings.Settings;
import ru.roman.bim.util.Const;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/** @author Roman 10.01.13 23:38 */
public class GhostServiceImpl implements GhostService {
    private static final Log log = LogFactory.getLog(GhostServiceImpl.class);

    public int mainInterval;
    public int thirstDelay;
    public int showInterval;

    private final GhostController controller;

    private Timer mainTicker;
    private Timer hideTicker;
    private Timer unfreezeTicker;

    private DelayedAction delayedAction;

    public GhostServiceImpl(GhostController ctrl) {
        this.controller = ctrl;

        if (Const.DEV_MODE) {
            mainInterval = 15 * 1000;
            thirstDelay = 1 * 1000;
            showInterval = 3 * 1000;
        } else {
            mainInterval = Settings.get().getPreviewInterval().intValue();
            thirstDelay = mainInterval;
            showInterval = Settings.get().getPreviewDuration().intValue();
        }

        mainTicker = new Timer(mainInterval, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (delayedAction != null) {
                    delayedAction.afterDelay();
                    delayedAction = null;
                }
                controller.showSlowly();
                hideTicker.restart();
            }
        });
        mainTicker.setCoalesce(true);
        mainTicker.setRepeats(true);

        hideTicker = new Timer(0, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.hideSlowly();
            }
        });
        hideTicker.setCoalesce(true);
        hideTicker.setRepeats(false);

        unfreezeTicker = new Timer(0, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.hideSlowly();
                start();
            }
        });
        unfreezeTicker.setCoalesce(true);
        unfreezeTicker.setRepeats(false);

        loadTimers();
    }

    private void loadTimers() {
        loadFirstDelay();
        mainTicker.setDelay(mainInterval);
        hideTicker.setInitialDelay(showInterval);
        unfreezeTicker.setInitialDelay(showInterval);
    }

    private void loadFirstDelay() {
        mainTicker.setInitialDelay(thirstDelay);
    }

    public void delayedStart(DelayedAction action) {
        thirstDelay = Settings.get().getDisabilityDuration().intValue() * 60 * 1000;
        loadFirstDelay();
        delayedAction = action;
        mainTicker.restart();
    }

    public void start() {
        thirstDelay = mainInterval;
        loadFirstDelay();
        delayedAction = null;
        mainTicker.restart();
    }

    public void startFromOpened() {
        unfreezeTicker.restart();
    }

    public void stop() {
        mainTicker.stop();
        hideTicker.stop();
        unfreezeTicker.stop();
    }


}
