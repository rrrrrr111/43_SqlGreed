package ru.roman.greed.util;

import javax.swing.*;

/**
 * @author Roman 16.09.12 2:10
 */
public class GuiUtils {


    private GuiUtils() {
    }

    public static void showErrorMessage(String mess) {
        JOptionPane.showConfirmDialog(null, mess, "Error", JOptionPane.PLAIN_MESSAGE, JOptionPane.INFORMATION_MESSAGE);
    }
}
