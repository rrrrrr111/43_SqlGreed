package ru.roman.bim.gui.pane.main;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import ru.roman.bim.gui.common.View;
import ru.roman.bim.gui.custom.widget.SilentJCheckBox;
import ru.roman.bim.util.GuiUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.LinkedList;
import java.util.List;

/** @author Roman 18.12.12 0:02 */
public class MainView extends JFrame implements View<MainViewModel, MainView, MainViewController> {
    private static final Log log = LogFactory.getLog(MainView.class);

    private final MainViewController controller = new MainViewController(this);

    private final JLabel textLabel = new JLabel();

    private final JButton prevButton = new JButton("pr");
    private final JButton nextButton = new JButton("nx");
    private final JButton translateButton = new JButton("translate");
    private final JButton editButton = new JButton("edit");
    private final JButton settingsButton = new JButton("sett");

    private final SilentJCheckBox checkBox1 = new SilentJCheckBox();
    private final SilentJCheckBox checkBox2 = new SilentJCheckBox();
    private final SilentJCheckBox checkBox3 = new SilentJCheckBox();
    private final SilentJCheckBox checkBox4 = new SilentJCheckBox();
    private final SilentJCheckBox checkBox5 = new SilentJCheckBox();
    private final List<SilentJCheckBox> checkBoxList = new LinkedList<SilentJCheckBox>();

    private final JLabel typeLabel = new JLabel();

    public MainView() {

        createView();
        controller.onInit();
    }



    private void createView() {

        final JPanel panel = new JPanel(new GridBagLayout());
        //panel.setPreferredSize(new Dimension(300, 200));
        setUndecorated(true);
        //setTranslucency(f);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        setBackground(new Color(0.5294118f, 0f, 1.0f, 0.0f));
        add(panel);
        setPreferredSize(new Dimension(270, 140));
        //setOpacity(Float.valueOf(0.75f));
        setResizable(false);

        // текст
//        final JPanel textPanel = new JPanel(new CardLayout());
//        textPanel.add(textLabel);
//        textPanel.setMinimumSize(new Dimension(269, 130));
//        textPanel.setMaximumSize(new Dimension(269, 130));
//        textPanel.setSize(new Dimension(269, 130));

        final GridBagConstraints gbc1 = new GridBagConstraints();
        gbc1.fill = GridBagConstraints.BOTH;        // как элемент заполняет пустое пространство
        gbc1.anchor = GridBagConstraints.PAGE_START;  // привязка к краю контейнера
        gbc1.gridwidth = 5;                         // кол-во ячеек заполняемых по ширине
        gbc1.weighty = 1.0;                         // вес компонента, веса учитываются при заполнени свободного пространства
        gbc1.weightx = 1.0;
        gbc1.gridx = 0;                             // gridx и  gridy координаты куда кладется компонент
        gbc1.gridy = 0;
        //gbc1.ipady = 140;                          // ограничение минимального размера
        //gbc1.ipadx = 270;                          // ограничение минимального размера
        panel.add(textLabel, gbc1);

        // кнопки
        final GridBagConstraints gbc2 = new GridBagConstraints();
        gbc2.fill = GridBagConstraints.HORIZONTAL;
        gbc2.gridx = 0;
        gbc2.gridy = 1;
        gbc2.weighty = 0.0;
        panel.add(prevButton, gbc2);

        final GridBagConstraints gbc3 = new GridBagConstraints();
        gbc3.fill = GridBagConstraints.HORIZONTAL;
        gbc3.gridx = 1;
        gbc3.gridy = 1;
        gbc3.weighty = 0.0;
        panel.add(nextButton, gbc3);

        final GridBagConstraints gbc4 = new GridBagConstraints();
        gbc4.fill = GridBagConstraints.HORIZONTAL;
        gbc4.gridx = 2;
        gbc4.gridy = 1;
        gbc4.weighty = 0.0;
        panel.add(translateButton, gbc4);

        final GridBagConstraints gbc5 = new GridBagConstraints();
        gbc5.fill = GridBagConstraints.HORIZONTAL;
        gbc5.gridx = 3;
        gbc5.gridy = 1;
        gbc5.weighty = 0.0;
        panel.add(editButton, gbc5);

        final GridBagConstraints gbc6 = new GridBagConstraints();
        gbc6.fill = GridBagConstraints.HORIZONTAL;
        gbc6.gridx = 4;
        gbc6.gridy = 1;
        gbc6.weighty = 0.0;
        panel.add(settingsButton, gbc6);

        prevButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.onPrev();
            }
        });
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.onNext();
            }
        });
        translateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.onTranslate();
            }
        });
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.onEdit();
            }
        });
        settingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.onSettings();
            }
        });



        // чекбоксы

        final JPanel checkPanel = new JPanel();
        final GridBagConstraints gbc7 = new GridBagConstraints();
        gbc7.fill = GridBagConstraints.NONE;
        gbc7.anchor = GridBagConstraints.LAST_LINE_START;
        gbc7.gridwidth = 3;
        //gbc7.insets = new Insets(0,0,0,10);  // padding
        //gbc7.weighty = 0.1;
        gbc7.gridx = 0;
        gbc7.gridy = 2;
        gbc7.weighty = 0.0;
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
                switch (e.getStateChange()) {
                    case ItemEvent.SELECTED :
                    case ItemEvent.DESELECTED :
                        final int idx = checkBoxList.indexOf(e.getSource());
                        if (e.getStateChange() == ItemEvent.SELECTED) {
                            upTo(idx);
                        } else if (e.getStateChange() == ItemEvent.DESELECTED) {
                            if (idx + 1 == checkBoxList.size()) {
                                checkBoxList.get(checkBoxList.size() - 1).setSelectedSilent(false);
                            } else if (checkBoxList.get(idx + 1).isSelected()) {
                                upTo(idx);
                            } else {
                                checkBoxList.get(idx).setSelectedSilent(false);
                            }
                        }
                        //log.info("rating changed to " + rating);
                        controller.onRatingChange(getRating());
                }
            }
        };
        activateListener(cl);

        final GridBagConstraints gbc8 = new GridBagConstraints();
        gbc8.fill = GridBagConstraints.HORIZONTAL;
        gbc8.gridwidth = 2;
        gbc8.gridx = 3;
        gbc8.gridy = 2;
        gbc8.weighty = 0.0;
        gbc8.weightx = 1.0;
        panel.add(typeLabel, gbc8);

        pack();
        Point pos = GuiUtils.getRightCornerPosition(getSize(), 3);
        setLocation(pos);
    }

    private void activateListener(ItemListener l) {
        for (JCheckBox cb : checkBoxList) {
            cb.addItemListener(l);
        }
    }

    private void upTo(int idx) {
        for (int i = 0; i < checkBoxList.size(); i++) {
            if (i <= idx) {
                checkBoxList.get(i).setSelectedSilent(true);
            } else {
                checkBoxList.get(i).setSelectedSilent(false);
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

    public void setValues(MainViewModel model) {
        setText(model.getTextFaced());
        typeLabel.setText(model.getType());
        setRating(model.getRating());
    }

    private void setText(String str) {
        textLabel.setText(String.format("<html><body><p align='center' width='100%%'" +
                "style='color:blue;font:10px;'>%s</p></body></html>", str));
    }

    private boolean translatedState;

    public void translate() {
        if (translatedState) {
            setText(controller.getModel().getTextFaced());
        } else {
            setText(controller.getModel().getTextShadowed());
        }
        translatedState = !translatedState;
    }

    @Override
    public MainViewController getController() {
        return controller;
    }

}
