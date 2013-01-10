package ru.roman.bim.gui.custom.tools;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/** @author Roman 22.12.12 0:52 */
public class OpacityTimer extends Timer {
    private static final Log log = LogFactory.getLog(OpacityTimer.class);

    private static final float START_OPACITY = 0;
    private static final float OPACITY_STEP = 0.05f;
    private static final float FINAL_OPACITY = 0.75f;

    private float opacity;
    private float step;
    private final JFrame jframe;


    public OpacityTimer(final JFrame frame) {
        super(100, null);
        this.jframe = frame;
        setRepeats(true);
        addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (jframe.isVisible()) {
                    opacity = opacity + step;
                    if (opacity > FINAL_OPACITY || opacity < START_OPACITY) {
                        stop();
                    }
                    opacity = opacity > 1 ? 1 : opacity;
                    opacity = opacity < 0 ? 0 : opacity;
                    jframe.setOpacity(opacity);
                } else {
                    jframe.setOpacity(opacity);
                    jframe.setVisible(true);
                }
                //log.info("OpacityTimer opacity : " + opacity);
            }
        });
    }

    private void checkAndStart() {
        if (!isRunning()) {
            start();
        }
    }

    public void showSlowly() {
        step = OPACITY_STEP;
        checkAndStart();
    }

    public void hideSlowly() {
        step = -OPACITY_STEP;
        checkAndStart();
    }

    public void showQuickly() {
        step = FINAL_OPACITY;
        checkAndStart();
    }

    public void hideQuickly() {
        step = -FINAL_OPACITY;
        checkAndStart();
    }
}
