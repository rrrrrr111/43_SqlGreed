package ru.roman.greed.service;

import ru.roman.greed.service.config.ConfigService;
import ru.roman.greed.service.excel.ExcelService;
import ru.roman.greed.service.sql.ConnAnalyzerService;
import ru.roman.greed.service.sql.StatementExecutionService;

/**
 * @author Roman 15.09.12 22:43
 */
public class ServiceHolder {
    private static ConfigService cryptService;
    private static ConnAnalyzerService connAnalyzerService;
    private static ExcelService excelService;
    private static StatementExecutionService stmExecService;

    public static synchronized ConfigService getConfigService() {
        if (cryptService == null) {
            cryptService = new ConfigService();
        }
        return cryptService;
    }

    public static synchronized ConnAnalyzerService getConnAnalyzerService() {
        if (connAnalyzerService == null) {
            connAnalyzerService = new ConnAnalyzerService();
        }
        return connAnalyzerService;
    }

    public static synchronized ExcelService getExcelService() {
        if (excelService == null) {
            excelService = new ExcelService();
        }
        return excelService;
    }

    public static synchronized StatementExecutionService getStmExecService() {
        if (stmExecService == null) {
            stmExecService = new StatementExecutionService();
        }
        return stmExecService;
    }
}
