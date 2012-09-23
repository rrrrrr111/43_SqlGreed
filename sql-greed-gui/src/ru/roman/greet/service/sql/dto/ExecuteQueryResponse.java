package ru.roman.greet.service.sql.dto;

import ru.roman.greet.gui.pane.main.ColumnInfo;

import java.io.Serializable;
import java.util.List;

/**
 * @author Roman 08.09.12 10:57
 */
public class ExecuteQueryResponse implements Serializable {
    
    private List<String[]> cursorInfo;
    private List<ColumnInfo> columnInfo;
    private List<Object[]> data;

    public void setCursorInfo(List<String[]> cursorInfo) {
        this.cursorInfo = cursorInfo;
    }

    public void setColumnInfo(List<ColumnInfo> columnInfo) {
        this.columnInfo = columnInfo;
    }

    public void setData(List<Object[]> data) {
        this.data = data;
    }

    public List<String[]> getCursorInfo() {
        return cursorInfo;
    }

    public List<ColumnInfo> getColumnInfo() {
        return columnInfo;
    }

    public List<Object[]> getData() {
        return data;
    }
}
