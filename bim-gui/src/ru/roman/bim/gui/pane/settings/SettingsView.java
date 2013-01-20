package ru.roman.bim.gui.pane.settings;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import ru.roman.bim.gui.common.View;
import ru.roman.bim.gui.custom.widget.SimpleCheckBoxPanel;
import ru.roman.bim.util.GuiUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/** @author Roman 16.01.13 23:58 */
public class SettingsView extends JFrame implements View<SettingsViewModel, SettingsView, SettingsViewController> {
    private static final Log log = LogFactory.getLog(SettingsView.class);

    private final SettingsViewController controller = new SettingsViewController(this);

    private JTabbedPane tabbedPane;
    private JPanel genericTab;
    private JPanel prevSettTab;
    private JPanel loadTab;

    public SettingsView() {

        createView();
        controller.onInit();
    }

    private void createView() {

        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setLocationRelativeTo(null);
        final Dimension preferredSize = new Dimension(400, 300);
        setPreferredSize(preferredSize);
        setResizable(false);
        setIconImage(GuiUtils.createMainImage());
        setTitle("Settings");
        setLocation(GuiUtils.getCenterPosition(preferredSize));

        tabbedPane = new JTabbedPane();
        getContentPane().add(tabbedPane);

        genericTab = new JPanel(new GridBagLayout());
        tabbedPane.addTab("Generic", genericTab);

        prevSettTab = new JPanel(new GridBagLayout());
        tabbedPane.addTab("Preview settings", prevSettTab);

        loadTab = new JPanel(new GridBagLayout());
        tabbedPane.addTab("Load", loadTab);

//        final JPanel hzTab = new JPanel();
//        tabbedPane.addTab("hz", hzTab);



        final int leftAndRightMargin = 60;
        final int behindLabelAndWidgetMargin = 10;

        ///////////////////////////////////////////////////////////////////////////////
        ///////////////////////////// Authorization ///////////////////////////////////
        ///////////////////////////////////////////////////////////////////////////////

        final JPanel authPanel = new JPanel(new GridBagLayout());
        authPanel.setBorder(BorderFactory.createTitledBorder("Authorization"));

        //loginText.setMaximumSize(new Dimension(160, 0));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;        // как элемент заполняет пустое пространство
        gbc.anchor = GridBagConstraints.NORTH;  // привязка к краю контейнера
        gbc.gridwidth = 1;                         // кол-во ячеек заполняемых по ширине
        gbc.weighty = 0.0;                         // вес компонента, веса учитываются при заполнени свободного пространства
        gbc.weightx = 0.0;
        gbc.gridx = 0;                             // gridx и  gridy координаты куда кладется компонент
        gbc.gridy = 0;
        //gbc.ipady = 140;                          // ограничение минимального размера
        //gbc.ipadx = 270;                          // ограничение минимального размера
        gbc.insets = new Insets(0, 0, 30, 0);
        genericTab.add(authPanel, gbc);

        final JTextField loginText = new JTextField();
        loginText.setPreferredSize(new Dimension(160, 0));
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;        // как элемент заполняет пустое пространство
        gbc.anchor = GridBagConstraints.CENTER;  // привязка к краю контейнера
        gbc.gridwidth = 1;                         // кол-во ячеек заполняемых по ширине
        gbc.weighty = 0.0;                         // вес компонента, веса учитываются при заполнени свободного пространства
        gbc.weightx = 0.0;
        gbc.gridx = 1;                             // gridx и  gridy координаты куда кладется компонент
        gbc.gridy = 0;
        //gbc.ipady = 140;                          // ограничение минимального размера
        //gbc.ipadx = 270;                          // ограничение минимального размера
        gbc.insets = new Insets(0, 0, 0, leftAndRightMargin);
        authPanel.add(loginText, gbc);

        JLabel loginLabel = new JLabel("login");
        loginLabel.setHorizontalAlignment(JLabel.RIGHT);
        loginLabel.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;        // как элемент заполняет пустое пространство
        gbc.anchor = GridBagConstraints.CENTER;  // привязка к краю контейнера
        gbc.gridwidth = 1;                         // кол-во ячеек заполняемых по ширине
        gbc.weighty = 0.0;                         // вес компонента, веса учитываются при заполнени свободного пространства
        gbc.weightx = 0.0;
        gbc.gridx = 0;                             // gridx и  gridy координаты куда кладется компонент
        gbc.gridy = 0;
        //gbc11.ipady = 140;                          // ограничение минимального размера
        //gbc11.ipadx = 270;                          // ограничение минимального размера
        gbc.insets = new Insets(0, leftAndRightMargin, 0, behindLabelAndWidgetMargin);
        authPanel.add(loginLabel, gbc);

        final JPasswordField passwordText = new JPasswordField();
        //passwordText.setPreferredSize(new Dimension(160, 0));
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;        // как элемент заполняет пустое пространство
        gbc.anchor = GridBagConstraints.CENTER;  // привязка к краю контейнера
        gbc.gridwidth = 1;                         // кол-во ячеек заполняемых по ширине
        gbc.weighty = 0.0;                         // вес компонента, веса учитываются при заполнени свободного пространства
        gbc.weightx = 0.0;
        gbc.gridx = 1;                             // gridx и  gridy координаты куда кладется компонент
        gbc.gridy = 1;
        //gbc2.ipady = 140;                          // ограничение минимального размера
        //gbc2.ipadx = 270;                          // ограничение минимального размера
        gbc.insets = new Insets(0, 0, 0, leftAndRightMargin);
        authPanel.add(passwordText, gbc);

        JLabel passwordLabel = new JLabel("password");
        passwordLabel.setHorizontalAlignment(JLabel.RIGHT);
        passwordLabel.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;        // как элемент заполняет пустое пространство
        gbc.anchor = GridBagConstraints.CENTER;  // привязка к краю контейнера
        gbc.gridwidth = 1;                         // кол-во ячеек заполняемых по ширине
        gbc.weighty = 0.0;                         // вес компонента, веса учитываются при заполнени свободного пространства
        gbc.weightx = 0.0;
        gbc.gridx = 0;                             // gridx и  gridy координаты куда кладется компонент
        gbc.gridy = 1;
        //gbc21.ipady = 140;                          // ограничение минимального размера
        //gbc21.ipadx = 270;                          // ограничение минимального размера
        gbc.insets = new Insets(0, leftAndRightMargin, 0, behindLabelAndWidgetMargin);
        authPanel.add(passwordLabel, gbc);



        ///////////////////////////////////////////////////////////////////////////////
        ///////////////////////////// Generic settings ////////////////////////////////
        ///////////////////////////////////////////////////////////////////////////////

        final JPanel genSettPanel = new JPanel(new GridBagLayout());
        genSettPanel.setBorder(BorderFactory.createTitledBorder("Generic settings"));
        //loginText.setMaximumSize(new Dimension(160, 0));
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;        // как элемент заполняет пустое пространство
        gbc.anchor = GridBagConstraints.CENTER;  // привязка к краю контейнера
        gbc.gridwidth = 1;                         // кол-во ячеек заполняемых по ширине
        gbc.weighty = 0.0;                         // вес компонента, веса учитываются при заполнени свободного пространства
        gbc.weightx = 0.0;
        gbc.gridx = 0;                             // gridx и  gridy координаты куда кладется компонент
        gbc.gridy = 1;
        //gbc.ipady = 140;                          // ограничение минимального размера
        //gbc.ipadx = 270;                          // ограничение минимального размера
        gbc.insets = new Insets(0, 0, 0, 0);
        genericTab.add(genSettPanel, gbc);

        final SimpleCheckBoxPanel ratingsPanel = new SimpleCheckBoxPanel();
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;        // как элемент заполняет пустое пространство
        gbc.anchor = GridBagConstraints.CENTER;  // привязка к краю контейнера
        gbc.gridwidth = 1;                         // кол-во ячеек заполняемых по ширине
        gbc.weighty = 0.0;                         // вес компонента, веса учитываются при заполнени свободного пространства
        gbc.weightx = 0.0;
        gbc.gridx = 1;                             // gridx и  gridy координаты куда кладется компонент
        gbc.gridy = 0;
        //gbc.ipady = 140;                          // ограничение минимального размера
        //gbc.ipadx = 270;                          // ограничение минимального размера
        gbc.insets = new Insets(0, 0, 0, leftAndRightMargin);
        genSettPanel.add(ratingsPanel, gbc);

        JLabel ratingsLabel = new JLabel("displayed ratings");
        ratingsLabel.setHorizontalAlignment(JLabel.RIGHT);
        ratingsLabel.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;        // как элемент заполняет пустое пространство
        gbc.anchor = GridBagConstraints.CENTER;  // привязка к краю контейнера
        gbc.gridwidth = 1;                         // кол-во ячеек заполняемых по ширине
        gbc.weighty = 0.0;                         // вес компонента, веса учитываются при заполнени свободного пространства
        gbc.weightx = 0.0;
        gbc.gridx = 0;                             // gridx и  gridy координаты куда кладется компонент
        gbc.gridy = 0;
        //gbc.ipady = 140;                          // ограничение минимального размера
        //gbc.ipadx = 270;                          // ограничение минимального размера
        gbc.insets = new Insets(0, leftAndRightMargin, 0, behindLabelAndWidgetMargin);
        genSettPanel.add(ratingsLabel, gbc);


        final JTextField portionText = new JTextField();
        portionText.setPreferredSize(new Dimension(10, 0));
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;        // как элемент заполняет пустое пространство
        gbc.anchor = GridBagConstraints.CENTER;  // привязка к краю контейнера
        gbc.gridwidth = 1;                         // кол-во ячеек заполняемых по ширине
        gbc.weighty = 0.0;                         // вес компонента, веса учитываются при заполнени свободного пространства
        gbc.weightx = 0.0;
        gbc.gridx = 1;                             // gridx и  gridy координаты куда кладется компонент
        gbc.gridy = 1;
        //gbc.ipady = 140;                          // ограничение минимального размера
        //gbc.ipadx = 270;                          // ограничение минимального размера
        gbc.insets = new Insets(0, 0, 0, leftAndRightMargin);
        genSettPanel.add(portionText, gbc);

        JLabel portionLabel = new JLabel("portion");
        portionLabel.setHorizontalAlignment(JLabel.RIGHT);
        portionLabel.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;        // как элемент заполняет пустое пространство
        gbc.anchor = GridBagConstraints.CENTER;  // привязка к краю контейнера
        gbc.gridwidth = 1;                         // кол-во ячеек заполняемых по ширине
        gbc.weighty = 0.0;                         // вес компонента, веса учитываются при заполнени свободного пространства
        gbc.weightx = 0.0;
        gbc.gridx = 0;                             // gridx и  gridy координаты куда кладется компонент
        gbc.gridy = 1;
        //gbc.ipady = 140;                          // ограничение минимального размера
        //gbc.ipadx = 270;                          // ограничение минимального размера
        gbc.insets = new Insets(0, leftAndRightMargin, 0, behindLabelAndWidgetMargin);
        genSettPanel.add(portionLabel, gbc);


        ///////////////////////////////////////////////////////////////////////////////
        ///////////////////////////// Word list loading ///////////////////////////////
        ///////////////////////////////////////////////////////////////////////////////

        final JPanel loadWordListPanel = new JPanel(new GridBagLayout());
        loadWordListPanel.setBorder(BorderFactory.createTitledBorder("Word list loading"));
        //loginText.setMaximumSize(new Dimension(160, 0));
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;        // как элемент заполняет пустое пространство
        gbc.anchor = GridBagConstraints.CENTER;  // привязка к краю контейнера
        gbc.gridwidth = 1;                         // кол-во ячеек заполняемых по ширине
        gbc.weighty = 0.0;                         // вес компонента, веса учитываются при заполнени свободного пространства
        gbc.weightx = 0.0;
        gbc.gridx = 0;                             // gridx и  gridy координаты куда кладется компонент
        gbc.gridy = 1;
        //gbc.ipady = 140;                          // ограничение минимального размера
        //gbc.ipadx = 270;                          // ограничение минимального размера
        gbc.insets = new Insets(0, 0, 0, 0);
        loadTab.add(loadWordListPanel, gbc);

        JLabel broseWordListLabel = new JLabel("select a file for loading");
        broseWordListLabel.setHorizontalAlignment(JLabel.RIGHT);
        broseWordListLabel.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;        // как элемент заполняет пустое пространство
        gbc.anchor = GridBagConstraints.CENTER;  // привязка к краю контейнера
        gbc.gridwidth = 1;                         // кол-во ячеек заполняемых по ширине
        gbc.weighty = 0.0;                         // вес компонента, веса учитываются при заполнени свободного пространства
        gbc.weightx = 0.0;
        gbc.gridx = 0;                             // gridx и  gridy координаты куда кладется компонент
        gbc.gridy = 0;
        //gbc11.ipady = 140;                          // ограничение минимального размера
        //gbc11.ipadx = 270;                          // ограничение минимального размера
        gbc.insets = new Insets(0, leftAndRightMargin, 0, behindLabelAndWidgetMargin);
        loadWordListPanel.add(broseWordListLabel, gbc);

        final JButton broseWordListButton = new JButton();
        broseWordListButton.setPreferredSize(new Dimension(100, 0));
        broseWordListButton.setAction(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.onBroseFileForLoading();
            }
        });
        broseWordListButton.setText("brose...");
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;        // как элемент заполняет пустое пространство
        gbc.anchor = GridBagConstraints.CENTER;  // привязка к краю контейнера
        gbc.gridwidth = 1;                         // кол-во ячеек заполняемых по ширине
        gbc.weighty = 0.0;                         // вес компонента, веса учитываются при заполнени свободного пространства
        gbc.weightx = 0.0;
        gbc.gridx = 1;                             // gridx и  gridy координаты куда кладется компонент
        gbc.gridy = 0;
        //gbc.ipady = 140;                          // ограничение минимального размера
        //gbc.ipadx = 270;                          // ограничение минимального размера
        gbc.insets = new Insets(0, 0, 0, leftAndRightMargin);
        loadWordListPanel.add(broseWordListButton, gbc);

        pack();
    }


    @Override
    public SettingsViewController getController() {
        return controller;
    }

    @Override
    public void setValues(SettingsViewModel model) {
        throw new RuntimeException("not implemented");
    }

    public JTabbedPane getTabbedPane() {
        return tabbedPane;
    }

    public void selectTab(int num) {
        switch (num) {
            case 1:
                tabbedPane.setSelectedComponent(genericTab);
                break;
            case 2:
                tabbedPane.setSelectedComponent(prevSettTab);
                break;
            case 3:
                tabbedPane.setSelectedComponent(loadTab);
                break;
        }
    }
}
