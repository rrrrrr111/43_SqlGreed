package ru.roman.bim.gui.pane.tray;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import ru.roman.bim.StartBim;
import ru.roman.bim.gui.pane.PaineFactory;
import ru.roman.bim.gui.pane.main.State;
import ru.roman.bim.util.BimException;
import ru.roman.bim.util.Const;
import ru.roman.bim.util.GuiUtil;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/** @author Roman 22.12.12 3:14 */
public class TrayUtils {
    private static final Log log = LogFactory.getLog(TrayUtils.class);

    private static TrayIcon trayIcon;

    private TrayUtils(){;}


    public static void addTrayIcon() {

        //Check the SystemTray is supported
        if (!SystemTray.isSupported()) {
            throw new BimException("SystemTray is not supported by platform");
        }

        removeTrayIcon();
        trayIcon = new TrayIcon(GuiUtil.createMainImage());

        // Create a pop-up menu components
        final PopupMenu popUp = new PopupMenu();
        final MenuItem aboutItem = new MenuItem("About");
        final MenuItem clearCacheItem = new MenuItem("Clear cache");
        final MenuItem infoItem = new MenuItem();
        infoItem.setEnabled(false);
        final CheckboxMenuItem cbDisabled = new CheckboxMenuItem("Disabled");
        final MenuItem editMenu = new MenuItem("Edit");
        final MenuItem settingsMenu = new MenuItem("Settings");
        final MenuItem exitItem = new MenuItem("Exit");

        //Add components to pop-up menu
        popUp.add(aboutItem);
        popUp.add(clearCacheItem);
        popUp.addSeparator();
        popUp.add(cbDisabled);
        popUp.addSeparator();
        popUp.add(editMenu);
        popUp.add(settingsMenu);
        popUp.add(exitItem);

        trayIcon.setPopupMenu(popUp);
        trayIcon.setToolTip(Const.APP_NAME + " " + Const.VERSION);
        trayIcon.setImageAutoSize(true);

//        popUp.seaddActionListener(new PopupMenuListener(){
//
//            @Override
//            public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
//                PaineFactory.getMainViewController().getLocalCache().getCurrent();
//            }
//
//            @Override
//            public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
//                throw new RuntimeException("not implemented");
//            }
//
//            @Override
//            public void popupMenuCanceled(PopupMenuEvent e) {
//                throw new RuntimeException("not implemented");
//            }
//        });

        trayIcon.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                PaineFactory.getMainViewController().showQuickly();
            }
        });

        aboutItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                GuiUtil.showInfoMessage("This is Bim  v." + Const.VERSION);
            }
        });

        clearCacheItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                PaineFactory.getMainViewController().getLocalCache().clearCache();
            }
        });

        cbDisabled.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                int cb1Id = e.getStateChange();
                if (cb1Id == ItemEvent.SELECTED){
                    PaineFactory.getMainViewController().changeState(State.DISABLED);
                } else {
                    PaineFactory.getMainViewController().changeState(State.SCHEDULED);
                }
            }
        });

        editMenu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                PaineFactory.getMainViewController().onEdit();
            }
        });

        settingsMenu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                PaineFactory.getSettingsViewController().showView();
            }
        });


        final SystemTray tray = SystemTray.getSystemTray();
        exitItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                StartBim.stop(0);
            }
        });

        try {
            tray.add(trayIcon);
        } catch (AWTException e) {
            throw new BimException("TrayIcon could not be added", e);
        }


    }

    public static void removeTrayIcon() {
        if (trayIcon != null) {
            final SystemTray tray = SystemTray.getSystemTray();
            tray.remove(trayIcon);
        }
    }

    public static void showTrayNotification(String str, TrayIcon.MessageType type) {
        if (Const.SHOW_TRAY_NOTIFICATIONS && trayIcon != null) {
            trayIcon.displayMessage(Const.APP_NAME, str, type);
        }
    }




}
