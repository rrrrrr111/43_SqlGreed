package ru.roman.bim.gui.pane.main;

import ru.roman.bim.gui.pane.PaineHolder;

import javax.swing.*;
import java.awt.*;

/** @author Roman 18.12.12 0:02 */
public class MainView extends JFrame {

    private final JLabel text = new JLabel();

    public MainView() {

        PaineHolder.setMainView(this);
        createView();
        initState();
    }



    private void createView() {

        JPanel panel = new JPanel(true);
        panel.add(text);

        setUndecorated(true);
        //setTranslucency(f);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setBackground(new Color(0.5294118f, 0f, 1.0f, 0.0f));
        add(panel);
        setPreferredSize(new Dimension(300, 200));
        setOpacity(Float.valueOf(0.75f));
        setResizable(false);
        pack();
        setVisible(true);

    }

    private void initState() {

        text.setText(String.format("<html><body><font size='50'>%s</font></body></html>", "hello"));
    }



}
