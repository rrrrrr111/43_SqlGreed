package ru.roman.greet.service.sql;

import ru.roman.greet.service.ServiceHolder;
import ru.roman.greet.service.datasource.dto.ReconnectionInfo;
import ru.roman.greet.util.ReflectionUtils;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * User: Roman
 * DateTime: 01.09.12 16:20
 */
public class ConnAnalyzerService {

    private StatementExecutionService stmExecService = ServiceHolder.getStmExecService();
    private static final String[] EXCLUDES = {"class", "inputStream",
                                    "metaData", "outputStream", "connection", "clientInfoProperties",
                                    "connection", "catalogs", "typeInfo", "tableTypes"};

    public List<String[]> retrieveConnInfo(ReconnectionInfo info) {

        try {
            final List<String[]> ci = new ArrayList<String[]>();
            final Connection conn = stmExecService.getConnection(info);
            final DatabaseMetaData connMetaData = conn.getMetaData();

            ci.addAll(ReflectionUtils.retrieveGettersValues(conn, EXCLUDES));
            ci.addAll(ReflectionUtils.retrieveGettersValues(connMetaData, EXCLUDES));

            return ci;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
