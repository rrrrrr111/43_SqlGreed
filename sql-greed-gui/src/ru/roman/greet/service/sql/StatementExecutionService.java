/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.roman.greet.service.sql;

import org.springframework.jdbc.support.JdbcUtils;
import ru.roman.greet.gui.pane.conf.ConfigManager;
import ru.roman.greet.gui.pane.main.ColumnInfo;
import ru.roman.greet.service.datasource.ReconnectionSource;
import ru.roman.greet.service.datasource.dto.ReconnectionInfo;
import ru.roman.greet.service.sql.dto.ExecuteQueryRequest;
import ru.roman.greet.service.sql.dto.ExecuteQueryResponse;
import ru.roman.greet.util.Const;
import ru.roman.greet.util.ReflectionUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StatementExecutionService {

    private ReconnectionSource source = new ReconnectionSource();
    private ConfigManager configManager = ConfigManager.getInstance();
//    private JdbcTemplate jt = new JdbcTemplate(source);
    private Statement stm = null;

    private static final String[] RS_EXCLUDES = new String[]{
            "statement", "afterLast", "beforeFirst", "class", "closed",
            "connectionCallbackInterface", "first", "last", "metaData",
            "statement", "statementCallbackInterface", "schemas"
    };



    public ExecuteQueryResponse executeSql(ExecuteQueryRequest req) {
        ResultSet rs = null;
        try {
            final String sql = req.getSql();
            final ReconnectionInfo info = req.getConnInfo();
            final ExecuteQueryResponse resp = new ExecuteQueryResponse();
            final List<Object[]> data = new ArrayList<Object[]>();
            resp.setData(data);
            final List<ColumnInfo> columnsInfo = new ArrayList<ColumnInfo>();
            resp.setColumnInfo(columnsInfo);
            final List<String[]> cursorInfo = new ArrayList<String[]>();
            resp.setCursorInfo(cursorInfo);
            final String cutSql = sql.trim().substring(0, 10);


            // select ...
            if (cutSql.matches("^(?i)select.*")) {
                rs = executeSelect(sql, info);
                ColumnInfo column;
                String schemaName;
                String tableName;
                final ResultSetMetaData md = rs.getMetaData();
                final int columnCount = md.getColumnCount();
                for (int k = 1; k <= columnCount; k++) {
                    column = new ColumnInfo();
                    column.setColumnLabel(md.getColumnLabel(k));
                    column.setColumnType(md.getColumnType(k));
                    column.setColumnDisplaySize(md.getColumnDisplaySize(k));
                    schemaName = md.getSchemaName(k);
                    tableName = md.getTableName(k);
                    column.setColumnFullName(
                            (schemaName == null ? "" : schemaName + ".") +
                            (tableName == null ? "" : tableName + ".") +
                            md.getColumnName(k));
                    column.setColumnClassName(md.getColumnClassName(k));
                    column.setColumnTypeName(md.getColumnTypeName(k));
                    column.setCatalogName(md.getCatalogName(k));
                    column.setPrecision(md.getPrecision(k));
                    column.setScale(md.getScale(k));
                    column.setAutoIncrement(md.isAutoIncrement(k));
                    column.setCaseSensitive(md.isCaseSensitive(k));
                    column.setCurrency(md.isCurrency(k));
                    column.setDefinitelyWritable(md.isDefinitelyWritable(k));
                    column.setNullable(md.isNullable(k));
                    column.setReadOnly(md.isReadOnly(k));
                    column.setSearchable(md.isSearchable(k));
                    column.setSigned(md.isSigned(k));
                    column.setWritable(md.isWritable(k));
                    columnsInfo.add(column);
                }

                cursorInfo.addAll(ReflectionUtils.retrieveGettersValues(rs, RS_EXCLUDES));

                Object[] dataRow;
                for (int i = 0; rs.next(); i++) {
                    dataRow = new Object[columnCount];
                    if (i > Const.MAX_TABLE_ROW_COUNT) {
                        break;
                    }
                    for (int j = 0; j < columnCount; j++) {
                        dataRow[j] = rs.getObject(j + 1);
                    }
                    data.add(i, dataRow);
                }

            } else if (cutSql.matches("^(?i)(?:insert|update|delete|create|grant|alter|drop).*")) {

                int res = executeUpdate(sql, info);

                final ColumnInfo inf = new ColumnInfo();
                inf.setColumnLabel("rows affected");
                columnsInfo.add(inf);
                data.add(new Object[]{res + ""});
            } else if (cutSql.matches("^(?i)exec.*")) {
                Boolean res = execute(sql, info);

                final ColumnInfo inf = new ColumnInfo();
                inf.setColumnLabel("result");
                columnsInfo.add(inf);
                data.add(new Object[]{res + ""});
            } else {
                throw new RuntimeException("Not supported query : " + sql);
            }
            return resp;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            JdbcUtils.closeResultSet(rs);
            JdbcUtils.closeStatement(stm);
        }
    }

    private Boolean execute(String sql, ReconnectionInfo info) {
        final boolean res;
        try {
            stm = source.getConnection(info).createStatement();
            res = stm.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return res;
    }

    private int executeUpdate(String sql, ReconnectionInfo info) {
        final int res;
        try {
            stm = source.getConnection(info).createStatement();
            res = stm.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return res;
    }

    private ResultSet executeSelect(String sql, ReconnectionInfo info) {
        final ResultSet rs;
        try {
            stm = source.getConnection(info).createStatement();
            rs = stm.executeQuery(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rs;
    }

    public Statement getCurrentStatement() {
        return stm;
    }


    private ArrayList<String[]> rsToArrayList(ResultSet rs) {
        try {
            final ResultSetMetaData metaData = rs.getMetaData();
            final int columnCount = metaData.getColumnCount();
            final ArrayList<String[]> res = new ArrayList<String[]>();

            String[] row = new String[columnCount];
            res.add(row);

            for (int i = 0; i < columnCount; i++) {
                row[i] = metaData.getColumnLabel(i + 1);
            }

            for (int i = 0; rs.next(); i++) {
                row = new String[columnCount];
                for (int j = 0; j < columnCount; j++) {
                    row[j] = rs.getString(j + 1);
                }
                res.add(row);
            }
            return res;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            JdbcUtils.closeResultSet(rs);
        }
    }


    private DatabaseMetaData getBaseMetaData() {
        try {
            return source.getConnection(configManager.getConnectionsModel().getDefaultConnection()).getMetaData();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<String[]> getSchemas() throws SQLException, IndexOutOfBoundsException, ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedOperationException {
        return rsToArrayList(getBaseMetaData().getSchemas());
    }

    public ArrayList<String[]> getTableTypes() throws SQLException, IndexOutOfBoundsException, ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedOperationException {
        return rsToArrayList(getBaseMetaData().getTableTypes());
    }

    public ArrayList<String[]> getCatalogs() throws SQLException, IndexOutOfBoundsException, ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedOperationException {
        return rsToArrayList(getBaseMetaData().getCatalogs());
    }

    public ArrayList<String[]> getClientInfoProperties() throws SQLException, IndexOutOfBoundsException, ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedOperationException {
        return rsToArrayList(getBaseMetaData().getClientInfoProperties());
    }

    public ArrayList<String[]> getTypeInfo() throws SQLException, IndexOutOfBoundsException, ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedOperationException {
        return rsToArrayList(getBaseMetaData().getTypeInfo());
    }
    ////////////////////////////////////schemas////////////////////////////////////////////////////////

    public ArrayList<String[]> getTables(String schema) throws SQLException, IndexOutOfBoundsException, ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedOperationException {
        return rsToArrayList(getBaseMetaData().getTables(null, schema, null, null));
    }

    public ArrayList<String[]> getSuperTables(String schema) throws SQLException, IndexOutOfBoundsException, ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedOperationException {
        return rsToArrayList(getBaseMetaData().getSuperTables(null, schema, null));
    }

    public ArrayList<String[]> getFunctions(String schema) throws SQLException, IndexOutOfBoundsException, ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedOperationException {
        return rsToArrayList(getBaseMetaData().getFunctions(null, schema, null));
    }

    public ArrayList<String[]> getProcedures(String schema) throws SQLException, IndexOutOfBoundsException, ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedOperationException {
        return rsToArrayList(getBaseMetaData().getProcedures(null, schema, null));
    }

    public ArrayList<String[]> getUDTs(String schema) throws SQLException, IndexOutOfBoundsException, ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedOperationException {
        return rsToArrayList(getBaseMetaData().getUDTs(null, schema, null, null));
    }

    public ArrayList<String[]> getSuperTypes(String schema) throws SQLException, IndexOutOfBoundsException, ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedOperationException {
        return rsToArrayList(getBaseMetaData().getSuperTypes(null, schema, null));
    }

    public ArrayList<String[]> getTablePrivileges(String schema) throws SQLException, IndexOutOfBoundsException, ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedOperationException {
        return rsToArrayList(getBaseMetaData().getTablePrivileges(null, schema, null));
    }

    public ArrayList<String[]> getAttributes(String schema) throws SQLException, IndexOutOfBoundsException, ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedOperationException {
        return rsToArrayList(getBaseMetaData().getAttributes(null, schema, null, null));
    }
    ////////////////////////////////////////////////////on tables/////////////////////////////////////////////////////////////

    public ArrayList<String[]> getVersionColumns(String table) throws SQLException, IndexOutOfBoundsException, ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedOperationException {
        return rsToArrayList(getBaseMetaData().getVersionColumns(null, null, table));
    }

    public ArrayList<String[]> getColumns(String table) throws SQLException, IndexOutOfBoundsException, ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedOperationException {
        return rsToArrayList(getBaseMetaData().getColumns(null, null, table, null));
    }

    public ArrayList<String[]> getColumnPrivileges(String table) throws SQLException, IndexOutOfBoundsException, ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedOperationException {
        return rsToArrayList(getBaseMetaData().getColumnPrivileges(null, null, table, null));
    }
    /////// references from other tables

    public ArrayList<String[]> getCrossReference(String table) throws SQLException, IndexOutOfBoundsException, ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedOperationException {
        return rsToArrayList(getBaseMetaData().getCrossReference(null, null, table, null, null, null));
    }

    public ArrayList<String[]> getExportedKeys(String table) throws SQLException, IndexOutOfBoundsException, ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedOperationException {
        return rsToArrayList(getBaseMetaData().getExportedKeys(null, null, table));
    }

    public ArrayList<String[]> getImportedKeys(String table) throws SQLException, IndexOutOfBoundsException, ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedOperationException {
        return rsToArrayList(getBaseMetaData().getImportedKeys(null, null, table));
    }

    public ArrayList<String[]> getPrimaryKeys(String table) throws SQLException, IndexOutOfBoundsException, ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedOperationException {
        return rsToArrayList(getBaseMetaData().getPrimaryKeys(null, null, table));
    }

    public ArrayList<String[]> getIndexInfo(String table) throws SQLException, IndexOutOfBoundsException, ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedOperationException {
        return rsToArrayList(getBaseMetaData().getIndexInfo(null, null, table, false, false));
    }

    public ArrayList<String[]> getTablesRowCounts(String schema) {
        ResultSet resultSet = null;
        final ReconnectionInfo info = configManager.getConnectionsModel().getDefaultConnection();
        try {
            final ArrayList<String[]> tablesList = rsToArrayList(getBaseMetaData().getTables(null, schema, null, null));
            final ArrayList<String[]> result = new ArrayList<String[]>();
            result.add(new String[]{"Table name", "Rows count"});
            for (int i = 1; i < tablesList.size(); i++) {

                resultSet = executeSelect("select count(1) from " + tablesList.get(i)[2], info);
                if (resultSet.next()) {
                    result.add(new String[]{tablesList.get(i)[2], resultSet.getString(1)});
                }
                resultSet.close();
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            JdbcUtils.closeResultSet(resultSet);
            JdbcUtils.closeStatement(stm);
        }
    }

    public Connection getConnection(ReconnectionInfo info) throws SQLException {
        return source.getConnection(info);
    }

    public void cancelCurrentStatement() {
        try {
            if (stm != null) {
                stm.cancel();
            }
        } catch (SQLException e1) {
            throw new RuntimeException(e1);
        } finally {
            JdbcUtils.closeStatement(stm);
        }
    }

    public void closeConnection() {
        source.closeConnection();
    }
}

