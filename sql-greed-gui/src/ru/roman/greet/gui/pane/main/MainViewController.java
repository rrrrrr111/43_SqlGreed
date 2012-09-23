package ru.roman.greet.gui.pane.main;

import ru.roman.greet.gui.common.ColumnInfoTableModel;
import ru.roman.greet.gui.pane.PaineHolder;
import ru.roman.greet.gui.pane.conf.ConfigManager;
import ru.roman.greet.gui.pane.conf.ConfigView;
import ru.roman.greet.gui.pane.queryinfo.QueryInfoView;
import ru.roman.greet.old.StructureView;
import ru.roman.greet.service.ServiceHolder;
import ru.roman.greet.service.config.dto.CommandsModel;
import ru.roman.greet.service.datasource.dto.ReconnectionInfo;
import ru.roman.greet.service.excel.ExcelService;
import ru.roman.greet.service.excel.dto.CreateReportRequest;
import ru.roman.greet.service.sql.ConnAnalyzerService;
import ru.roman.greet.service.sql.StatementExecutionService;
import ru.roman.greet.service.sql.dto.ExecuteQueryRequest;
import ru.roman.greet.service.sql.dto.ExecuteQueryResponse;
import ru.roman.greet.util.Const;
import ru.roman.greet.util.ExceptionHandler;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * User: Roman
 * DateTime: 01.09.12 3:34
 */
public class MainViewController {

    private ConfigManager settingsManager = ConfigManager.getInstance();
    private StatementExecutionService stmExecService = ServiceHolder.getStmExecService();
    private ConnAnalyzerService connAnalyzerService = ServiceHolder.getConnAnalyzerService();
    private ExcelService excelService = ServiceHolder.getExcelService();

    private final MainView mainView;

    private CommandsStack stack;

    public MainViewController(MainView mainView) {
        this.mainView = mainView;
    }


    public void executeSql(final String sqlCommand, final AfterCommandCallBack callBack) {
        new Thread() {
            @Override
            public void run() {
                boolean isOk = true;
                try {

                    final ReconnectionInfo connInfo = settingsManager.getConnectionsModel().getDefaultConnection();
                    final ExecuteQueryRequest req = new ExecuteQueryRequest(sqlCommand, connInfo);
                    final ExecuteQueryResponse resp = stmExecService.executeSql(req);
                    mainView.getTableModel().setData(resp.getColumnInfo(),resp.getData());
                    mainView.setCursorInfo(resp.getCursorInfo());
                    stack.push();

                    final QueryInfoView queryInfoView = PaineHolder.getQueryInfoView();
                    if (queryInfoView.isVisible()) {
                        onShowQueryInfo(false);
                        queryInfoView.onDataChanged();
                    }

                } catch (Exception e) {
                    isOk = false;
                    ExceptionHandler.showMessage(e);
                } finally {
                    callBack.execute(isOk);
                }
            }
        }.start();
    }


    public void onAppStart() {
        settingsManager.loadConnections();
        settingsManager.loadCommands();

        final String commandsCount = settingsManager.getCommandsModel().getCommandsCount();
        stack = CommandsStack.getInstance(Integer.parseInt(commandsCount));
        stack.addAll(settingsManager.getCommandsModel().getCommandList());
        stack.renewCurrentPosition();
    }

    public String getActualConnectionAlias() {
        final ReconnectionInfo defaultConnection = settingsManager.getDefaultConnection();
        if (defaultConnection != null) {
            return defaultConnection.getAlias();
        }
        return "";
    }

    public void onRedo() {
        stack.redoString();
    }

    public void onUndo() {
        stack.undoString();
    }

    public void onAppExit() {
        try {
            final CommandsModel commandsModel = settingsManager.getCommandsModel();
            commandsModel.setCommandList(stack);
            commandsModel.setCommandsCount(Integer.valueOf(stack.getCapacity()).toString());
            stmExecService.closeConnection();
        } catch (Throwable ex) {
            ExceptionHandler.showMessage(ex);
        }
        System.exit(0);
    }

    public void onShowQueryInfo(boolean activate) {
        final List<ColumnInfo> columnsInfo = mainView.getTableModel().getColumnsInfo();
        final List<String[]> cursorInfo = mainView.getCursorInfo();
        final List<String[]> connectionInfo = connAnalyzerService.retrieveConnInfo(
                settingsManager.getConnectionsModel().getDefaultConnection()
        );
        final String sql = mainView.getTextArea().getText();
        final List<Object[]> columns = new ArrayList<Object[]>();

        for (ColumnInfo info : columnsInfo) {
            columns.add(info.asArray());
        }

        final QueryInfoView queryInfoView = PaineHolder.getQueryInfoView();
        queryInfoView.setData(columns, cursorInfo, connectionInfo, sql);
        if (activate) {
            queryInfoView.setVisible(true);
        }

    }

    public void onShowDbStructure() {
        new StructureView().setVisible(true);
    }

    public void onCancelSql() {
        stmExecService.cancelCurrentStatement();
    }

    public void onShowConnConfig() {
        new ConfigView().setVisible(true);
    }

    public void onCreateExcel() {
        final File file = PaineHolder.getFileChooser().saveFile();
        if (!(file == null)) {
            final ColumnInfoTableModel tableModel = mainView.getTableModel();
            final List<Object[]> columnInfo = tableModel.getColumnsInfo();
            final List<Object[]> data = tableModel.getData();
            CreateReportRequest req = new CreateReportRequest(
                    columnInfo, data);
            excelService.createExcelTable(file, req);
            JOptionPane.showMessageDialog(null,
                    "Report built !", "Message", JOptionPane.INFORMATION_MESSAGE);
        }

    }

    public void onShowAbout() {
        JOptionPane.showOptionDialog(null, Const.APP_NAME + "\nby Roman ", "About title",
                JOptionPane.OK_OPTION, JOptionPane.QUESTION_MESSAGE, null, new String[]{"1", "2", "3"}, null);

    }

    public interface AfterCommandCallBack {
        /**
         * @param isOk - true if all OK
         */
        void execute(boolean isOk);
    }


}
