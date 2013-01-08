package ru.roman.bim.gui.pane.edit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import ru.roman.bim.gui.common.View;
import ru.roman.bim.gui.custom.widget.CheckBoxPanel;
import ru.roman.bim.service.gae.wsclient.BimItemType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.Vector;

/** @author Roman 18.12.12 0:02 */
public class EditView extends JFrame implements View<EditViewModel, EditView, EditViewController> {
    private static final Log log = LogFactory.getLog(EditView.class);

    private final EditViewController controller = new EditViewController(this);

    private final JTextArea facedArea = new JTextArea();
    private final JTextArea translationArea = new JTextArea();

    private final JButton prevButton = new JButton("prev");
    private final JButton nextButton = new JButton("next");
    private final JButton saveButton = new JButton("save");
    private final JButton newButton = new JButton("new");
    private final JButton closeButton = new JButton("close");

    private CheckBoxPanel checkPanel;

    private JComboBox<BimItemType> typeComboBox;

    public EditView() {

        createView();
        controller.onInit();
    }



    private void createView() {

        final JPanel panel = new JPanel(new GridBagLayout());
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setLocationRelativeTo(null);
        add(panel);
        setPreferredSize(new Dimension(470, 340));
        setResizable(true);
        //get

        Font font = new Font(Font.SANS_SERIF, Font.PLAIN, 14);
        facedArea.setEditable(true);
        facedArea.setLineWrap(true);
        facedArea.setWrapStyleWord(true);
        facedArea.setFont(font);
        JScrollPane facedAreaScrollPane = new JScrollPane(facedArea);

        translationArea.setEditable(true);
        translationArea.setLineWrap(true);
        translationArea.setWrapStyleWord(true);
        translationArea.setFont(font);
        JScrollPane translationAreaScrollPane = new JScrollPane(translationArea);

        Collection <BimItemType> types = controller.getTypes();
        typeComboBox = new JComboBox<BimItemType>(new Vector<BimItemType>(types));

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
        panel.add(facedAreaScrollPane, gbc1);

        final GridBagConstraints gbc1$1 = new GridBagConstraints();
        gbc1$1.fill = GridBagConstraints.BOTH;        // как элемент заполняет пустое пространство
        //gbc1$1.anchor = GridBagConstraints.PAGE_START;  // привязка к краю контейнера
        gbc1$1.gridwidth = 5;                         // кол-во ячеек заполняемых по ширине
        gbc1$1.weighty = 1.0;                         // вес компонента, веса учитываются при заполнени свободного пространства
        gbc1$1.weightx = 1.0;
        gbc1$1.gridx = 0;                             // gridx и  gridy координаты куда кладется компонент
        gbc1$1.gridy = 1;
        panel.add(translationAreaScrollPane, gbc1$1);

        // кнопки
        final GridBagConstraints gbc2 = new GridBagConstraints();
        gbc2.fill = GridBagConstraints.HORIZONTAL;
        gbc2.gridx = 0;
        gbc2.gridy = 3;
        gbc2.weighty = 0.0;
        panel.add(prevButton, gbc2);

        final GridBagConstraints gbc3 = new GridBagConstraints();
        gbc3.fill = GridBagConstraints.HORIZONTAL;
        gbc3.gridx = 1;
        gbc3.gridy = 3;
        gbc3.weighty = 0.0;
        panel.add(nextButton, gbc3);

        JPanel buttPanel = new JPanel();
        buttPanel.add(saveButton);
        buttPanel.add(newButton);
        buttPanel.add(closeButton);

        final GridBagConstraints gbc5 = new GridBagConstraints();
        gbc5.fill = GridBagConstraints.HORIZONTAL;
        gbc5.gridx = 3;
        gbc5.gridy = 3;
        gbc5.gridwidth = 3;
        gbc5.weighty = 0.0;
        panel.add(buttPanel, gbc5);


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
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.onSave();
            }
        });
        newButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.onNew();
            }
        });
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.onClose();
            }
        });


        // чекбоксы
        checkPanel = new CheckBoxPanel();
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
        panel.add(typeComboBox, gbc8);

        pack();
    }

    @Override
    public void setValues(EditViewModel model) {
        facedArea.setText(model.getTextFaced());
        translationArea.setText(model.getTextShadowed());
        typeComboBox.getModel().setSelectedItem(model.getType());
        checkPanel.setRating(model.getRating().intValue());
    }

    @Override
    public EditViewController getController() {
        return controller;
    }

    private boolean reverseState;

    public void fillModel(EditViewModel currModel) {

        //currModel.setId();
        currModel.setRating(Long.valueOf(checkPanel.getRating()));
        if (reverseState) {
            currModel.setTextFaced(translationArea.getText());
            currModel.setTextShadowed(facedArea.getText());
        } else {
            currModel.setTextFaced(facedArea.getText());
            currModel.setTextShadowed(translationArea.getText());
        }
        //currModel.setFacedLangId();
        //currModel.setShadowedLangId();

        currModel.setType(typeComboBox.getItemAt(typeComboBox.getSelectedIndex()));
    }
}
