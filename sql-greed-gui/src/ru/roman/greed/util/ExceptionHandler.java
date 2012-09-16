package ru.roman.greed.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.text.StrBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * User: Roman
 * Date: 01.09.12
 * Time: 0:46
 * To change this template use File | Settings | File Templates.
 */
public class ExceptionHandler {
    private static final Log log = LogFactory.getLog(ExceptionHandler.class);
    private final static NarrowOptionPane ERR_PAINE = new NarrowOptionPane();


    public static void showMessage(Throwable t){
        Validate.notNull(t);
        final StrBuilder ms = new StrBuilder();
        Throwable e = t;
        String mess;
        while (e != null) {
            if (e instanceof SQLException) {
                SQLException se = (SQLException)e;
                checkMess(se.getMessage(), ms, "");
                checkMess("SQLState=" + se.getSQLState(), ms, " ");
                checkMess("ErrorCode=" + se.getErrorCode(), ms, ", ");

                e = se.getNextException();
                if (e == null) {
                    e = se.getCause();
                }
            } else {
                checkMess(e.getMessage(), ms, " ");
                e = e.getCause();
            }
            ms.append(" : ");
        }
        log.error("grEEt exception :", t);
        mess = ms.substring(0, ms.length()-3).replace(" :  : ", " : ");
        ERR_PAINE.setMessage(mess);
        ERR_PAINE.createDialog(null, "Application error").setVisible(true);
    }

    private static void checkMess(String mess, StrBuilder ms, String prefix) {
        if (StringUtils.isNotBlank(mess) && !ms.contains(mess)) {
            ms.append(prefix).append(mess);
        }
    }

    public static class NarrowOptionPane extends JOptionPane {
        public NarrowOptionPane() {
            super();
            setMessageType(JOptionPane.ERROR_MESSAGE);
        }

        public int getMaxCharactersPerLineCount() {
            return 100;
        }
    }

    public static void registerUncatchExceptionHandler() {
        EventQueue queue = Toolkit.getDefaultToolkit().getSystemEventQueue();
        queue.push(new EventQueue () {
            @Override
            protected void dispatchEvent(AWTEvent newEvent) {
                try {
                    super.dispatchEvent(newEvent);
                } catch (Throwable t) {
                    showMessage(t);
                }
            }
        });
    }
}
