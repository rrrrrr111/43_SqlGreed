package ru.roman.bim.gui.pane.tray;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import ru.roman.bim.StartBim;
import ru.roman.bim.gui.pane.PaineFactory;
import ru.roman.bim.gui.pane.main.State;
import ru.roman.bim.util.BimException;
import ru.roman.bim.util.Const;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.net.URL;

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

        trayIcon = new TrayIcon(createImage("/resources/trayIc.gif", "tray icon"));

        // Create a pop-up menu components
        final PopupMenu popUp = new PopupMenu();
        final MenuItem aboutItem = new MenuItem("About");
        final MenuItem clearCacheItem = new MenuItem("Clear cache");
        final MenuItem infoItem = new MenuItem();
        infoItem.setEnabled(false);
        final CheckboxMenuItem cbDisabled = new CheckboxMenuItem("Disabled");
        final Menu displayMenu = new Menu("Display");
        final MenuItem errorItem = new MenuItem("Error");
        final MenuItem warningItem = new MenuItem("Warning");
        final MenuItem noneItem = new MenuItem("None");
        final MenuItem exitItem = new MenuItem("Exit");

        //Add components to pop-up menu
        popUp.add(aboutItem);
        popUp.add(clearCacheItem);
        popUp.addSeparator();
        popUp.add(cbDisabled);
        popUp.addSeparator();
        popUp.add(displayMenu);
        displayMenu.add(errorItem);
        displayMenu.add(warningItem);
        displayMenu.add(infoItem);
        displayMenu.add(noneItem);
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
                JOptionPane.showMessageDialog(null, "This is Bim");
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

        ActionListener listener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                MenuItem item = (MenuItem)e.getSource();
                //TrayIcon.MessageType type = null;
                System.out.println(item.getLabel());
                if ("Error".equals(item.getLabel())) {
                    //type = TrayIcon.MessageType.ERROR;
                    trayIcon.displayMessage("Sun TrayIcon Demo",
                            "This is an error message", TrayIcon.MessageType.ERROR);

                } else if ("Warning".equals(item.getLabel())) {
                    //type = TrayIcon.MessageType.WARNING;
                    trayIcon.displayMessage("Sun TrayIcon Demo",
                            "This is a warning message", TrayIcon.MessageType.WARNING);

                } else if ("Info".equals(item.getLabel())) {
                    //type = TrayIcon.MessageType.INFO;
                    trayIcon.displayMessage("Sun TrayIcon Demo",
                            "This is an info message", TrayIcon.MessageType.INFO);

                } else if ("None".equals(item.getLabel())) {
                    //type = TrayIcon.MessageType.NONE;
                    trayIcon.displayMessage("Sun TrayIcon Demo",
                            "This is an ordinary message", TrayIcon.MessageType.NONE);
                }
            }
        };

        errorItem.addActionListener(listener);
        warningItem.addActionListener(listener);
        noneItem.addActionListener(listener);

        final SystemTray tray = SystemTray.getSystemTray();
        exitItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    tray.remove(trayIcon);
                } catch (Exception ex){
                    log.info("Error while closing", ex);
                } finally {
                    StartBim.stop(0);
                }
            }
        });

        try {
            tray.add(trayIcon);
        } catch (AWTException e) {
            throw new BimException("TrayIcon could not be added", e);
        }


    }

    public static void showTrayNotification(String str, TrayIcon.MessageType type) {
        if (Const.SHOW_TRAY_NOTIFICATIONS && trayIcon != null) {
            trayIcon.displayMessage(Const.APP_NAME, str, type);
        }
    }

    //Obtain the image URL
    public static Image createImage(String path, String description) {
        URL imageURL = TrayUtils.class.getResource(path);

        if (imageURL == null) {
            log.info("Resource not found: " + path);
            return null;
        } else {
            return (new ImageIcon(imageURL, description)).getImage();
        }
    }

}
