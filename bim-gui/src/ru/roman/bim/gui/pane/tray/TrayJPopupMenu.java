package ru.roman.bim.gui.pane.tray;

import ru.roman.bim.util.GuiUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/** @author Roman 23.03.13 10:39 */
public class TrayJPopupMenu extends JPopupMenu {

    private final TrayController controller;
    private final Timer mouseExitedTicker;
    private final JCheckBoxMenuItem cbDisabled;

    public TrayJPopupMenu(TrayController contr) throws HeadlessException {
        super();
        this.controller = contr;


        // Create a pop-up menu components
        final JMenuItem  aboutItem = new JMenuItem("About", GuiUtil.createMainIcon());
        final JMenuItem clearCacheItem = new JMenuItem("Clear cache");
        final JMenuItem infoItem = new JMenuItem();
        infoItem.setEnabled(false);
        cbDisabled = new JCheckBoxMenuItem("Disabled   Alt+D");
        final JMenuItem editMenu = new JMenuItem("Edit");
        final JMenuItem settingsMenu = new JMenuItem("Settings");
        final JMenuItem exitItem = new JMenuItem("Exit");

        //Add components to pop-up menu
        add(aboutItem);
        add(clearCacheItem);
        addSeparator();
        add(cbDisabled);
        addSeparator();
        add(editMenu);
        add(settingsMenu);
        add(exitItem);

        aboutItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.onShowInfo();
            }
        });
        clearCacheItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.onClearCache();
            }
        });
        cbDisabled.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                controller.onStateChanged(e);
            }
        });
        editMenu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.onShowEdit();
            }
        });
        settingsMenu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.onShowSettings();
            }
        });
        exitItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.onExit();
            }
        });

        mouseExitedTicker = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TrayJPopupMenu.this.setVisible(false);
            }
        });
        mouseExitedTicker.setCoalesce(true);
        mouseExitedTicker.setRepeats(false);
        final MouseAdapter mouseAdapter = new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                mouseExitedTicker.stop();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                mouseExitedTicker.restart();
            }
        };
        GuiUtil.addMouseListenerToChilds(this, mouseAdapter);

    }

    public void setDisableItemSelected(boolean val) {
        cbDisabled.setSelected(val);
    }

    public void showPopup() {
        setVisible(true);
        mouseExitedTicker.restart();

    }
}
