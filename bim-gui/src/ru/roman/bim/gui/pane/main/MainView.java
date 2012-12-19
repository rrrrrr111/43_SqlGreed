package ru.roman.bim.gui.pane.main;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import ru.roman.bim.gui.pane.PaineHolder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.LinkedList;
import java.util.List;

/** @author Roman 18.12.12 0:02 */
public class MainView extends JFrame {
    private static final Log log = LogFactory.getLog(MainView.class);

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
    private final List<JCheckBox> checkBoxList = new LinkedList<JCheckBox>();

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

        //checkPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        checkPanel.add(checkBox1);
        checkPanel.add(checkBox2);
        checkPanel.add(checkBox3);
        checkPanel.add(checkBox4);
        checkPanel.add(checkBox5);
        checkBoxList.add(checkBox1);
        checkBoxList.add(checkBox2);
        checkBoxList.add(checkBox3);
        checkBoxList.add(checkBox4);
        checkBoxList.add(checkBox5);

        final ItemListener cl = new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {

                if (e.getStateChange() == ItemEvent.SELECTED) {
                    final int idx = checkBoxList.indexOf(e.getSource());
                    upTo(idx);
                } else if (e.getStateChange() == ItemEvent.DESELECTED) {
                    final int idx = checkBoxList.indexOf(e.getSource());
                    if (idx + 1 == checkBoxList.size()) {
                        checkBoxList.get(checkBoxList.size() - 1).setSelected(false);
                    } else if (checkBoxList.get(idx + 1).isSelected()) {
                        upTo(idx);
                    } else {
                        checkBoxList.get(idx).setSelected(false);
                    }
                }
            }
        };
        activateListener(cl);


        final GridBagConstraints gbc8 = new GridBagConstraints();
        gbc8.fill = GridBagConstraints.HORIZONTAL;
        gbc8.gridwidth = 2;
        gbc8.gridx = 3;
        gbc8.gridy = 2;
        panel.add(typeLabel, gbc8);

        pack();
        setVisible(true);

    }

    private void activateListener(ItemListener l) {
        for (JCheckBox cb : checkBoxList) {
            cb.addItemListener(l);
            cb.setEnabled(true);
        }
    }

    private void onRatingChange(int rating) {
        log.info("rating changed to " + rating);
    }

    private void upTo(int idx) {
        for (int i = 0; i < checkBoxList.size(); i++) {
            if (i <= idx) {
                checkBoxList.get(i).setSelected(true);
            } else {
                checkBoxList.get(i).setSelected(false);
            }
        }
    }

    public void setRating(int rating) {
        upTo(rating - 1);
    }

    public int getRating() {
        for (int i = 0; i < checkBoxList.size(); i++) {
            if (!checkBoxList.get(i).isSelected()) {
                return i;
            }
        }
        return 5;
    }

    private void initState() {

        textLabel.setText(String.format("<html><body><p style='color:red;'>%s" +
                "</p></body></html>", "hello"));

        typeLabel.setText("type...");
    }


}
