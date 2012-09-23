package ru.roman.greet.service.excel.dto;

import java.io.Serializable;
import java.util.List;

/**
 * User: Roman
 * DateTime: 02.09.12 11:41
 */
public class CreateReportRequest implements Serializable {

    private List<Object[]> columnInfo;
    private List<Object[]> data;

    public CreateReportRequest() {
    }

    public CreateReportRequest(List<Object[]> columnInfo, List<Object[]> data) {
        this.columnInfo = columnInfo;
        this.data = data;
    }

    public void setColumnInfo(List<Object[]> columnInfo) {
        this.columnInfo = columnInfo;
    }

    public void setData(List<Object[]> data) {
        this.data = data;
    }

    public List<Object[]> getColumnInfo() {
        return columnInfo;
    }

    public List<Object[]> getData() {
        return data;
    }
}
