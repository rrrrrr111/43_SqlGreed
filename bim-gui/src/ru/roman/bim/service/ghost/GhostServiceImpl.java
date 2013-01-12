package ru.roman.bim.service.ghost;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/** @author Roman 10.01.13 23:38 */
public class GhostServiceImpl implements GhostService {
    public static final int MAIN_INTERVAL = 5 * 60 * 1000;
    public static final int THIRST_DELAY = MAIN_INTERVAL;
    public static final int SHOW_INTERVAL = 30 * 1000;


    //public static final int MAIN_INTERVAL = 15 * 1000;
    //public static final int THIRST_DELAY = 5 * 1000;
    //public static final int SHOW_INTERVAL = 3 * 1000;

    private GhostController controller;

    private Timer mainTicker;
    private Timer hideTicker;
    private Timer unfreezeTicker;

    public GhostServiceImpl(GhostController ctrl) {
        this.controller = ctrl;
        mainTicker = new Timer(MAIN_INTERVAL, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.showSlowly();
                hideTicker.restart();
            }
        });
        mainTicker.setCoalesce(true);
        mainTicker.setInitialDelay(THIRST_DELAY);
        mainTicker.setRepeats(true);

        hideTicker = new Timer(0, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.hideSlowly();
            }
        });
        hideTicker.setCoalesce(true);
        hideTicker.setInitialDelay(SHOW_INTERVAL);
        hideTicker.setRepeats(false);

        unfreezeTicker = new Timer(0, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.hideSlowly();
                start();
            }
        });
        unfreezeTicker.setCoalesce(true);
        unfreezeTicker.setInitialDelay(SHOW_INTERVAL);
        unfreezeTicker.setRepeats(false);


    }

    public void start() {
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
