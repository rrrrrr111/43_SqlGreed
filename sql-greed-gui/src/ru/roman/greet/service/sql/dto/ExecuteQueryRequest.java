package ru.roman.greet.service.sql.dto;

import ru.roman.greet.service.datasource.dto.ReconnectionInfo;

import java.io.Serializable;

/**
 * @author Roman 08.09.12 10:56
 */
public class ExecuteQueryRequest implements Serializable {

    private String sql;
    private ReconnectionInfo connInfo;


    public ExecuteQueryRequest() {
    }

    public ExecuteQueryRequest(String sql, ReconnectionInfo connInfo) {
        this.sql = sql;
        this.connInfo = connInfo;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public ReconnectionInfo getConnInfo() {
        return connInfo;
    }

    public void setConnInfo(ReconnectionInfo connInfo) {
        this.connInfo = connInfo;
    }
}
