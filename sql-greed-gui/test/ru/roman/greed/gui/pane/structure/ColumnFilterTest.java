package ru.roman.greed.gui.pane.structure;

import javax.swing.*;
import java.awt.*;

/**
 * User: Roman
 * DateTime: 02.09.12 13:32
 */
public class ColumnFilterTest {

    public static void main(String args[]) throws Exception {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {

                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

                JFrame frame = new JFrame();
                frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                frame.setLocationByPlatform(true);
                frame.setLayout(new BorderLayout());
                frame.add(new ColumnFilter(), BorderLayout.CENTER);
                frame.setSize(600, 400);
                frame.setVisible(true);
            }
        });
    }
}
