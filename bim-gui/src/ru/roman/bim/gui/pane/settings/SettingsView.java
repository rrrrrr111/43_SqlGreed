package ru.roman.bim.gui.pane.settings;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import ru.roman.bim.gui.common.mvc.View;
import ru.roman.bim.gui.custom.widget.SimpleCheckBoxPanel;
import ru.roman.bim.util.Const;
import ru.roman.bim.util.GuiUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/** @author Roman 16.01.13 23:58 */
public class SettingsView extends JFrame implements View<SettingsViewModel, SettingsView, SettingsViewController> {
    private static final Log log = LogFactory.getLog(SettingsView.class);

    private final SettingsViewController controller = new SettingsViewController(this);

    private JTabbedPane tabbedPane;
    private JPanel genericTab;
    private JPanel prevSettTab;
    private JPanel loadTab;

    private JTextField loginText;
    private JPasswordField passwordText;

    private SimpleCheckBoxPanel ratingsPanel;
    private JTextField portionText;
    private JButton saveButton;
    private JButton cancelButton;
    private static final String PASSWORD_STUB = "pass word";

    public SettingsView() {

        createView();
        controller.onInit();
    }

    private void createView() {

        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setLocationRelativeTo(null);
        final Dimension preferredSize = new Dimension(400, 350);
        setPreferredSize(preferredSize);
        setResizable(true);
        setIconImage(GuiUtil.createMainImage());
        setTitle(Const.APP_NAME + " settings");
        setLocation(GuiUtil.getCenterPosition(preferredSize));

        tabbedPane = new JTabbedPane();
        tabbedPane.setPreferredSize(preferredSize);
        final Container contentPane = getContentPane();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
        contentPane.add(tabbedPane);

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
        final int upAndUnderMargin = 20;

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
        gbc.insets = new Insets(0, 0, 0, 0);
        genericTab.add(authPanel, gbc);

        final JLabel fillYourCredLabel = new JLabel("Fill your credentials:");
        //fillYourCredLabel.setBorder(BorderFactory.createBevelBorder(1));
        //fillYourCredLabel.setPreferredSize(new Dimension(260, 0));
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;        // как элемент заполняет пустое пространство
        gbc.anchor = GridBagConstraints.CENTER;  // привязка к краю контейнера
        gbc.gridwidth = 2;                         // кол-во ячеек заполняемых по ширине
        gbc.weighty = 0.0;                         // вес компонента, веса учитываются при заполнени свободного пространства
        gbc.weightx = 0.0;
        gbc.gridx = 0;                             // gridx и  gridy координаты куда кладется компонент
        gbc.gridy = 0;
        //gbc.ipady = 140;                          // ограничение минимального размера
        //gbc.ipadx = 270;                          // ограничение минимального размера
        gbc.insets = new Insets(upAndUnderMargin, leftAndRightMargin, 5, 0);
        authPanel.add(fillYourCredLabel, gbc);

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
        gbc.gridy = 1;
        //gbc11.ipady = 140;                          // ограничение минимального размера
        //gbc11.ipadx = 270;                          // ограничение минимального размера
        gbc.insets = new Insets(0, leftAndRightMargin, 0, behindLabelAndWidgetMargin);
        authPanel.add(loginLabel, gbc);

        loginText = new JTextField();
        //loginText.setPreferredSize(new Dimension(120, 0));
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;        // как элемент заполняет пустое пространство
        gbc.anchor = GridBagConstraints.CENTER;  // привязка к краю контейнера
        gbc.gridwidth = 1;                         // кол-во ячеек заполняемых по ширине
        gbc.weighty = 0.0;                         // вес компонента, веса учитываются при заполнени свободного пространства
        gbc.weightx = 0.5;
        gbc.gridx = 1;                             // gridx и  gridy координаты куда кладется компонент
        gbc.gridy = 1;
        //gbc.ipady = 140;                          // ограничение минимального размера
        //gbc.ipadx = 270;                          // ограничение минимального размера
        gbc.insets = new Insets(0, 0, 0, leftAndRightMargin);
        authPanel.add(loginText, gbc);

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
        gbc.gridy = 2;
        //gbc21.ipady = 140;                          // ограничение минимального размера
        //gbc21.ipadx = 270;                          // ограничение минимального размера
        gbc.insets = new Insets(0, leftAndRightMargin, upAndUnderMargin, behindLabelAndWidgetMargin);
        authPanel.add(passwordLabel, gbc);

        passwordText = new JPasswordField();
        //passwordText.setPreferredSize(new Dimension(160, 0));
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;        // как элемент заполняет пустое пространство
        gbc.anchor = GridBagConstraints.CENTER;  // привязка к краю контейнера
        gbc.gridwidth = 1;                         // кол-во ячеек заполняемых по ширине
        gbc.weighty = 0.0;                         // вес компонента, веса учитываются при заполнени свободного пространства
        gbc.weightx = 0.5;
        gbc.gridx = 1;                             // gridx и  gridy координаты куда кладется компонент
        gbc.gridy = 2;
        //gbc2.ipady = 140;                          // ограничение минимального размера
        //gbc2.ipadx = 270;                          // ограничение минимального размера
        gbc.insets = new Insets(0, 0, upAndUnderMargin, leftAndRightMargin);
        authPanel.add(passwordText, gbc);


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

        ratingsPanel = new SimpleCheckBoxPanel();
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
        gbc.insets = new Insets(upAndUnderMargin, 0, 0, leftAndRightMargin);
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
        gbc.insets = new Insets(upAndUnderMargin, leftAndRightMargin, 0, behindLabelAndWidgetMargin);
        genSettPanel.add(ratingsLabel, gbc);

        portionText = new JTextField();
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
        gbc.insets = new Insets(0, 0, upAndUnderMargin, leftAndRightMargin);
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
        gbc.insets = new Insets(0, leftAndRightMargin, upAndUnderMargin, behindLabelAndWidgetMargin);
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
        gbc.insets = new Insets(upAndUnderMargin, leftAndRightMargin, upAndUnderMargin, behindLabelAndWidgetMargin);
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
        gbc.insets = new Insets(upAndUnderMargin, 0, upAndUnderMargin, leftAndRightMargin);
        loadWordListPanel.add(broseWordListButton, gbc);


        final JPanel buttonsPanel = new JPanel();
        saveButton = new JButton("Save");
        cancelButton = new JButton("Cancel");
        buttonsPanel.add(saveButton);
        buttonsPanel.add(cancelButton);
        contentPane.add(buttonsPanel);

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.onSaveOrRegister();
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.onCancel();
            }
        });

        pack();
    }

    public void prepareForFirstInput() {
        saveButton.setText("Save/Register");

        ratingsPanel.setRatings(1, 2, 3);
        portionText.setText(String.valueOf(100));
    }

    @Override
    public SettingsViewController getController() {
        return controller;
    }

    @Override
    public void fillWidgets(SettingsViewModel model) {

        loginText.setText(model.getLogin());
        passwordText.setText(PASSWORD_STUB);
        portionText.setText(ObjectUtils.toString(model.getPortion()));
        ratingsPanel.setRatings(model.getRatings());
    }

    @Override
    public void fillModel(SettingsViewModel model) {
        //model.setCacheMaxSize();
        //model.setCurrentNum();
        model.setFacedLangId(Const.DEFAULT_LANG_ID.longValue());
        //model.setId();
        model.setLogin(loginText.getText());
        //model.setOpacity();
        final char[] passChars = passwordText.getPassword();
        final String pass = new String(passChars);
        if (!PASSWORD_STUB.equals(pass)) {
            controller.getValidator().validatePassword(pass);
            model.setPassword(GuiUtil.createDigest(passChars));
        }
        model.setPortion(Long.valueOf(portionText.getText()));
        //model.setPreviewDuration();
        //model.setPreviewInterval();
        //model.setRecordsCount();
        //model.setShadowedLangId(Const.);
        model.setSortingDirection(Const.DEFAULT_SORTING_DIRECTION);
        model.setSortingField(Const.DEFAULT_SORTING_FIELD);
        model.getRatings().clear();
        model.getRatings().addAll(ratingsPanel.getRatings());
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
