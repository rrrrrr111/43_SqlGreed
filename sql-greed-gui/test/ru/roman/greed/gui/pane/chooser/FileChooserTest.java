package ru.roman.greed.gui.pane.chooser;

import javax.swing.*;

/**
 * User: Roman
 * DateTime: 02.09.12 13:34
 */
public class FileChooserTest {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                //Turn off metal's use of bold fonts
                UIManager.put("swing.boldMetal", Boolean.FALSE);
                new FileChooser().saveFile();
            }
        });
    }
}
