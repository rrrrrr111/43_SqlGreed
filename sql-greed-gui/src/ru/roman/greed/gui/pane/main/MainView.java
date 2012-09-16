package ru.roman.greed.gui.pane.main;

import org.apache.commons.lang3.StringUtils;
import ru.roman.greed.gui.common.ColumnInfoTableModel;
import ru.roman.greed.gui.pane.PaineHolder;
import ru.roman.greed.gui.pane.sysinfo.SysInfoView;
import ru.roman.greed.util.Const;

import javax.swing.*;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import java.util.List;

import static ru.roman.greed.gui.pane.main.MainViewController.AfterCommandCallBack;

/**
 * User: Roman
 * DateTime: 01.09.12 0:09
 */
public class MainView {
    /**
     * Widgets
     */
    private final JFrame frame = new JFrame();
    private final JPanel panel = new JPanel();
    private final JButton buttonSql = new JButton();
    private final JButton buttonStopSql = new JButton();
    private final JScrollPane tableScrollPane = new JScrollPane();
    private final JTable mainTable = new JTable();
    private final JScrollPane textAreaScrollPane = new JScrollPane();
    private final JTextArea textArea = new JTextArea();
    private final JSplitPane splitPane = new JSplitPane();
    private TableRowSorter<ColumnInfoTableModel> tableRowSorter = new TableRowSorter<ColumnInfoTableModel>();
    /**
     * Actions
     */
    private final UndoAction undoAction = new UndoAction();
    private final RedoAction redoAction = new RedoAction();
    private final OpenConnConfigAction openConfigurationAction = new OpenConnConfigAction();
    private final ShowQueryInfoAction showQueryInfoAction = new ShowQueryInfoAction();
    private final ShowDbStructureAction showDbStructureAction = new ShowDbStructureAction();
    private final CreateExcelAction createExcelAction = new CreateExcelAction();
    private final ShowSystemInfoAction showSystemInfoAction = new ShowSystemInfoAction();
    private final ExitAction exitAction = new ExitAction();
    private final List<AbstractAction> actionList = Arrays.asList(
            undoAction,
            redoAction,
            openConfigurationAction,
            showQueryInfoAction,
            showDbStructureAction,
            createExcelAction,
            showSystemInfoAction,
            exitAction
    );

    private ColumnInfoTableModel tableModel = new ColumnInfoTableModel();
    private MainViewController mainViewController = new MainViewController(this);
    private List<String[]> cursorInfo;

    public MainView() {
        PaineHolder.setMainView(this);
        mainViewController.onAppStart();
        drawPaine();
        updateSqlClientTitle();

        frame.setVisible(true);
        textArea.requestFocusInWindow();
    }

    public void updateSqlClientTitle() {
        frame.setTitle("grEEt ~Conn: "
                + mainViewController.getActualConnectionAlias());
    }

    /**
     * Initialize the contents of the frame
     */
    private void drawPaine() {

        frame.getContentPane().setLayout(new BorderLayout());
        frame.setBounds(100, 100, 800, 450);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JToolBar toolBar = new JToolBar();
        frame.getContentPane().add(toolBar, BorderLayout.PAGE_START);
        toolBar.setAlignmentX(Component.LEFT_ALIGNMENT);
        GridBagLayout ToolbarLayout = new GridBagLayout();
        toolBar.setLayout(ToolbarLayout);

        buttonSql.addActionListener(new SqlButtonActionListener());
        buttonSql.setText("SQL");

        buttonStopSql.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mainViewController.onCancelSql();

            }
        });
        buttonStopSql.setIcon(new ImageIcon(getClass().getResource("/resources/stop_enabled.gif")));
        buttonStopSql.setEnabled(false);

        GridBagConstraints gbc14 = new GridBagConstraints();
        gbc14.weightx = 1.0;
        gbc14.anchor = GridBagConstraints.EAST;

        for (int i = 0; i < actionList.size() - 1; i++) {
            if (i == 2) {
                toolBar.addSeparator();
            }
            toolBar.add(actionList.get(i));
        }
        toolBar.add(new JLabel(Const.VERSION), gbc14);


        frame.getContentPane().add(splitPane, BorderLayout.CENTER);
        splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
        splitPane.setDividerSize(7);
        splitPane.setOneTouchExpandable(true);

        splitPane.setLeftComponent(panel);
        panel.setLayout(new GridBagLayout());
        final GridBagConstraints gbc1 = new GridBagConstraints();
        gbc1.fill = GridBagConstraints.BOTH;
        gbc1.ipadx = 10;
        gbc1.ipady = 15;
        gbc1.weighty = 1;
        gbc1.gridx = 1;
        gbc1.gridy = 0;
        gbc1.insets = new Insets(2, 2, 2, 2);
        panel.add(buttonSql, gbc1);

        final GridBagConstraints gbc2 = new GridBagConstraints();
        gbc2.ipadx = 0;
        gbc2.ipady = 10;
        gbc2.fill = GridBagConstraints.BOTH;
        gbc2.gridx = 1;
        gbc2.gridy = 1;
        gbc2.insets = new Insets(0, 2, 2, 2);
        panel.add(buttonStopSql, gbc2);

        final GridBagConstraints gbc3 = new GridBagConstraints();
        gbc3.ipadx = 0;
        gbc3.ipady = 0;
        gbc3.fill = GridBagConstraints.BOTH;
        gbc3.gridheight = 2;
        gbc3.weighty = 1;
        gbc3.weightx = 1;
        gbc3.gridy = 0;
        gbc3.gridx = 0;
        panel.add(textAreaScrollPane, gbc3);
        textAreaScrollPane.setViewportView(textArea);

        textArea.setFont(new Font("Times New Roman", 0, 14));

        mainTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tableRowSorter.setModel(tableModel);
        mainTable.setModel(tableModel);
        mainTable.setRowSorter(tableRowSorter);
        mainTable.setCellSelectionEnabled(true);

        tableScrollPane.setViewportView(mainTable);
        splitPane.setRightComponent(tableScrollPane);
        splitPane.setDividerLocation(150);

        final JMenuBar menuBar = new JMenuBar();
        JMenu menu1 = new JMenu("Actions");
        JMenu menu2 = new JMenu("?");
        for (int i = 0; i < actionList.size(); i++) {
            if (i == 2 || i == 7) {
                menu1.addSeparator();
            }
            menu1.add(actionList.get(i));
        }
        menu2.add(new JMenuItem(new ShowAboutAction()));
        menuBar.add(menu1);
        menuBar.add(menu2);
        frame.setJMenuBar(menuBar);

        final JPopupMenu popup = new JPopupMenu();
        for (int i = 2; i < actionList.size() - 1; i++) {
            popup.add(actionList.get(i));
        }
        final MouseAdapter popupListener = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                showPopup(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                showPopup(e);
            }

            private void showPopup(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    popup.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        };
        mainTable.addMouseListener(popupListener);
        tableScrollPane.addMouseListener(popupListener);
        mainViewController.onUndo();
        textArea.setCaretPosition(0);

        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent w) {
                actionList.get(7).actionPerformed(null);
            }
        });
    }

    public List<String[]> getCursorInfo() {
        return cursorInfo;
    }

    private class SqlButtonActionListener implements ActionListener {

        final AfterCommandCallBack callBack = new AfterCommandCallBack() {
            @Override
            public void execute(boolean isOk) {
                buttonSql.setEnabled(true);
                buttonStopSql.setEnabled(false);
                if (isOk) {
                    tableRowSorter.modelStructureChanged();
                    tableModel.fireTableStructureChanged();
                }
            }
        };

        public void actionPerformed(ActionEvent e) {
            buttonSql.setEnabled(false);
            buttonStopSql.setEnabled(true);
            final String sqlCommand;
            if (textArea.getSelectedText() != null) {
                sqlCommand = textArea.getSelectedText();
            } else {
                sqlCommand = textArea.getText().trim();
            }
            if (StringUtils.isBlank(sqlCommand) || sqlCommand.length() < 11) {
                callBack.execute(false);
                return;
            }

            mainViewController.executeSql(sqlCommand, callBack);
        }
    }


    private class OpenConnConfigAction extends AbstractAction {

        public OpenConnConfigAction() {
            super("Connection configuration");
            putValue(SMALL_ICON, new ImageIcon(getClass().getResource("/resources/newOptions.png")));
            putValue(SHORT_DESCRIPTION, "Connection configuration");
            putValue(MNEMONIC_KEY, new Integer(KeyEvent.VK_F));
            putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK));
            putValue(ACTION_COMMAND_KEY, "OpenConnConfigAction");
            putValue(LONG_DESCRIPTION, "LONG_DESCRIPTION");
            putValue(NAME, "Connection configuration");
        }

        public void actionPerformed(ActionEvent e) {
            mainViewController.onShowConnConfig();
        }
    }

    private class ShowQueryInfoAction extends AbstractAction {

        public ShowQueryInfoAction() {
            super("Current query information");
            putValue(SMALL_ICON, new ImageIcon(getClass().getResource("/resources/TABLE16.png")));
            putValue(SHORT_DESCRIPTION, "Current query information");
            putValue(MNEMONIC_KEY, new Integer(KeyEvent.VK_I));
            putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.CTRL_MASK));
            putValue(ACTION_COMMAND_KEY, "ShowQueryInfoAction");
            putValue(LONG_DESCRIPTION, "LONG_DESCRIPTION");
            putValue(NAME, "Query information");
        }

        public void actionPerformed(ActionEvent e) {
            mainViewController.onShowQueryInfo(true);
        }
    }

    private class ShowDbStructureAction extends AbstractAction {

        public ShowDbStructureAction() {
            super("Date base structure");
            putValue(SMALL_ICON, new ImageIcon(getClass().getResource("/resources/traces_to.png")));
            putValue(SHORT_DESCRIPTION, "DB structure");
            putValue(MNEMONIC_KEY, new Integer(KeyEvent.VK_B));
            putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_H, ActionEvent.CTRL_MASK));
            putValue(ACTION_COMMAND_KEY, "ShowDbStructureAction");
            putValue(LONG_DESCRIPTION, "LONG_DESCRIPTION");
            putValue(NAME, "Date base structure");
        }

        public void actionPerformed(ActionEvent e) {
            mainViewController.onShowDbStructure();
        }
    }

    private class CreateExcelAction extends AbstractAction {

        public CreateExcelAction() {
            super("Create Excel report");
            putValue(SMALL_ICON, new ImageIcon(getClass().getResource("/resources/excel.jpg")));
            putValue(SHORT_DESCRIPTION, "Create Excel report");
            putValue(MNEMONIC_KEY, new Integer(KeyEvent.VK_R));
            putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.CTRL_MASK));
            putValue(ACTION_COMMAND_KEY, "CreateExcelAction");
            putValue(LONG_DESCRIPTION, "LONG_DESCRIPTION");
            putValue(NAME, "Create Excel report");
        }

        public void actionPerformed(ActionEvent e) {
            mainViewController.onCreateExcel();
        }
    }

    private class ShowSystemInfoAction extends AbstractAction {

        public ShowSystemInfoAction() {
            super("Local system info");
            putValue(SMALL_ICON, new ImageIcon(getClass().getResource("/resources/properties.gif")));
            putValue(SHORT_DESCRIPTION, "System info");
            putValue(MNEMONIC_KEY, new Integer(KeyEvent.VK_L));
            putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
            putValue(ACTION_COMMAND_KEY, "ShowSystemInfoAction");
            putValue(LONG_DESCRIPTION, "LONG_DESCRIPTION");
            putValue(NAME, "Local system info");
        }

        public void actionPerformed(ActionEvent e) {
            new SysInfoView().setVisible(true);

        }
    }

    private class ShowAboutAction extends AbstractAction {
        public ShowAboutAction() {
            super("About");
            putValue(ACTION_COMMAND_KEY, "ShowAboutAction");
            putValue(NAME, "About");
        }

        public void actionPerformed(ActionEvent e) {
            mainViewController.onShowAbout();
        }
    }

    private class ExitAction extends AbstractAction {
        public ExitAction() {
            super("Exit");
            putValue(ACTION_COMMAND_KEY, "ExitAction");
            putValue(NAME, "Exit");
        }

        public void actionPerformed(ActionEvent e) {
            mainViewController.onAppExit();
        }
    }

    private class UndoAction extends AbstractAction {

        public UndoAction() {
            super("Previous one in stack");
            putValue(ACTION_COMMAND_KEY, "UndoAction");
            putValue(SMALL_ICON, new ImageIcon(getClass().getResource("/resources/undo.gif")));
            putValue(SHORT_DESCRIPTION, "Undo commands");
            putValue(NAME, "Previous one in stack");
        }

        public void actionPerformed(ActionEvent e) {
            mainViewController.onUndo();
        }
    }

    private class RedoAction extends AbstractAction {

        public RedoAction() {
            super("Next one in stack");
            putValue(ACTION_COMMAND_KEY, "RedoAction");
            putValue(SMALL_ICON, new ImageIcon(getClass().getResource("/resources/redo.gif")));
            putValue(SHORT_DESCRIPTION, "Redo commands");
            putValue(NAME, "Next one in stack");
        }

        public void actionPerformed(ActionEvent e) {
            mainViewController.onRedo();
        }
    }

    public JTextArea getTextArea() {
        return textArea;
    }

    public ColumnInfoTableModel getTableModel() {
        return tableModel;
    }

    public List<AbstractAction> getActionList() {
        return actionList;
    }

    public MainViewController getMainViewController() {
        return mainViewController;
    }

    public void setCursorInfo(List<String[]> cursorInfo) {
        this.cursorInfo = cursorInfo;
    }

    public AbstractAction getUndoAction() {
        return undoAction;
    }

    public AbstractAction getRedoAction() {
        return redoAction;
    }
}
