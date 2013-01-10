package ru.roman.bim.gui.pane.main;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import ru.roman.bim.gui.common.View;
import ru.roman.bim.gui.custom.widget.CheckBoxPanel;
import ru.roman.bim.util.GuiUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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

    private CheckBoxPanel checkPanel;

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
        setAlwaysOnTop(true);
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                controller.getGhostService().stop();
            }
            @Override
            public void mouseExited(MouseEvent e) {
                controller.getGhostService().startFromOpened();
            }
        });

        // �����
//        final JPanel textPanel = new JPanel(new CardLayout());
//        textPanel.add(textLabel);
//        textPanel.setMinimumSize(new Dimension(269, 130));
//        textPanel.setMaximumSize(new Dimension(269, 130));
//        textPanel.setSize(new Dimension(269, 130));

        final GridBagConstraints gbc1 = new GridBagConstraints();
        gbc1.fill = GridBagConstraints.BOTH;        // ��� ������� ��������� ������ ������������
        gbc1.anchor = GridBagConstraints.PAGE_START;  // �������� � ���� ����������
        gbc1.gridwidth = 5;                         // ���-�� ����� ����������� �� ������
        gbc1.weighty = 1.0;                         // ��� ����������, ���� ����������� ��� ��������� ���������� ������������
        gbc1.weightx = 1.0;
        gbc1.gridx = 0;                             // gridx �  gridy ���������� ���� �������� ���������
        gbc1.gridy = 0;
        //gbc1.ipady = 140;                          // ����������� ������������ �������
        //gbc1.ipadx = 270;                          // ����������� ������������ �������
        panel.add(textLabel, gbc1);

        // ������
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


        // ��������
        checkPanel = new CheckBoxPanel(new CheckBoxPanel.OnChangeCallBack() {
            @Override
            public void OnChange(int rating) {
                controller.onRatingChange(rating);
            }
        });
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

    public void setRating(Integer rating) {
        checkPanel.setRating(rating);
    }

    public Integer getRating() {
        return checkPanel.getRating();
    }

    @Override
    public void setValues(MainViewModel model) {
        setText(model.getTextFaced());
        typeLabel.setText(model.getType().toString());
        setRating(model.getRating().intValue());
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
