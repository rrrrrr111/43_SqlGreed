package ru.roman.bim.gui.pane.main;

import ru.roman.bim.gui.pane.PaineHolder;

import javax.swing.*;
import java.awt.*;

/** @author Roman 18.12.12 0:02 */
public class MainView extends JFrame {

    private final JLabel textLabel = new JLabel();

    private final JButton prevButton = new JButton("pr");
    private final JButton nextButton = new JButton("nx");
    private final JButton translateButton = new JButton("translate");
    private final JButton editButton = new JButton("edit");
    private final JButton settingsButton = new JButton("sett");

    private final JCheckBox checkBox1 = new JCheckBox();
    private final JCheckBox checkBox2 = new JCheckBox();
    private final JCheckBox checkBox3 = new JCheckBox();
    private final JCheckBox checkBox4 = new JCheckBox();
    private final JCheckBox checkBox5 = new JCheckBox();

    private final JLabel typeLabel = new JLabel();

    public MainView() {

        PaineHolder.setMainView(this);
        createView();
        initState();
    }



    private void createView() {

        final JPanel panel = new JPanel(new GridBagLayout());
        //panel.setPreferredSize(new Dimension(300, 200));
        setUndecorated(true);
        //setTranslucency(f);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setBackground(new Color(0.5294118f, 0f, 1.0f, 0.0f));
        add(panel);
        setPreferredSize(new Dimension(270, 150));
        setOpacity(Float.valueOf(0.75f));
        setResizable(false);

        // текст
        final GridBagConstraints gbc1 = new GridBagConstraints();
        gbc1.fill = GridBagConstraints.HORIZONTAL;
        gbc1.gridwidth = 5;
        gbc1.gridx = 0;
        gbc1.gridy = 0;
        gbc1.ipady = 40;
        panel.add(textLabel, gbc1);

        // кнопки
        final GridBagConstraints gbc2 = new GridBagConstraints();
        gbc2.fill = GridBagConstraints.HORIZONTAL;
        gbc2.gridx = 0;
        gbc2.gridy = 1;
        panel.add(prevButton, gbc2);

        final GridBagConstraints gbc3 = new GridBagConstraints();
        gbc3.fill = GridBagConstraints.HORIZONTAL;
        gbc3.gridx = 1;
        gbc3.gridy = 1;
        panel.add(nextButton, gbc3);

        final GridBagConstraints gbc4 = new GridBagConstraints();
        gbc4.fill = GridBagConstraints.HORIZONTAL;
        gbc4.gridx = 2;
        gbc4.gridy = 1;
        panel.add(translateButton, gbc4);

        final GridBagConstraints gbc5 = new GridBagConstraints();
        gbc5.fill = GridBagConstraints.HORIZONTAL;
        gbc5.gridx = 3;
        gbc5.gridy = 1;
        panel.add(editButton, gbc5);

        final GridBagConstraints gbc6 = new GridBagConstraints();
        gbc6.fill = GridBagConstraints.HORIZONTAL;
        gbc6.gridx = 4;
        gbc6.gridy = 1;
        panel.add(settingsButton, gbc6);

        // чекбоксы

        final JPanel checkPanel = new JPanel();
        final GridBagConstraints gbc7 = new GridBagConstraints();
        gbc7.fill = GridBagConstraints.HORIZONTAL;
        gbc7.gridwidth = 3;
        //gbc7.insets = new Insets(0,0,0,10);  // padding
        //gbc7.weighty = 0.1;
        gbc7.gridx = 0;
        gbc7.gridy = 2;
        panel.add(checkPanel, gbc7);

        checkPanel.add(checkBox1);
        checkPanel.add(checkBox2);
        checkPanel.add(checkBox3);
        checkPanel.add(checkBox4);
        checkPanel.add(checkBox5);

        final GridBagConstraints gbc8 = new GridBagConstraints();
        gbc8.fill = GridBagConstraints.HORIZONTAL;
        gbc8.gridwidth = 2;
        gbc8.gridx = 3;
        gbc8.gridy = 2;
        panel.add(typeLabel, gbc8);

        pack();
        setVisible(true);

    }

    private void initState() {

        textLabel.setText(String.format("<html><body><center><font size='250px'>%s" +
                "</font></center></body></html>", "hello"));

        typeLabel.setText("type...");
    }



}
