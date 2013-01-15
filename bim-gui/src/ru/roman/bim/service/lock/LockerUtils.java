package ru.roman.bim.service.lock;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import ru.roman.bim.StartBim;
import ru.roman.bim.gui.pane.tray.TrayUtils;
import ru.roman.bim.util.Const;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.RandomAccessFile;
import java.nio.channels.FileLock;

/** @author Roman 15.01.13 23:29 */
public abstract class LockerUtils {
    private static final Log log = LogFactory.getLog(LockerUtils.class);


    public static void tryLockApplication() {
        if(!isApplicationAlreadyRunning()){
            TrayUtils.showTrayNotification(Const.APP_NAME + " is already running.", TrayIcon.MessageType.INFO);
            final Timer timer = new Timer(3000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    StartBim.stop(-2);
                }
            });
            timer.setRepeats(false);
            timer.start();

        } else {
            TrayUtils.showTrayNotification(Const.APP_NAME + " started", TrayIcon.MessageType.INFO);
            log.info(Const.APP_NAME + " started");
        }
    }


    private static boolean isApplicationAlreadyRunning() {
        // socket concept is shown at http://www.rbgrn.net/content/43-java-single-application-instance
        // but this one is really great

        try {
            final File lockFile = new File(Const.APP_DATA_PATH, "lock_bim");
            final RandomAccessFile randomAccessFile = new RandomAccessFile(lockFile, "rw");
            final FileLock fileLock = randomAccessFile.getChannel().tryLock();
            if (fileLock != null) {
                Runtime.getRuntime().addShutdownHook(new Thread() {
                    public void run() {
                        try {
                            fileLock.release();
                            randomAccessFile.close();
                            lockFile.delete();
                        } catch (Exception e) {
                            log.error("Unable to remove lock lockFile: " + lockFile, e);
                        }
                    }
                });
                return true;
            }
        } catch (Exception e) {
            log.error("Unable to create and/or lock file", e);
        }
        return false;
    }
}