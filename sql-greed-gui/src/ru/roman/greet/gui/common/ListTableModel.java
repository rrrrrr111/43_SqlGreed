package ru.roman.greet.gui.common;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

/**
* Created with IntelliJ IDEA.
* User: Roman
* Date: 01.09.12
* Time: 12:39
*/
public class ListTableModel<T> extends AbstractTableModel {

    protected List<T[]> data;
    protected String[] columnInfo;

    public ListTableModel() {
        this(new String[0]);
    }

    public ListTableModel(String[] columnInfo) {
        this(columnInfo, new ArrayList<T[]>());
    }

    public ListTableModel(String[] columnInfo, List<T[]> data) {
        this.data = data;
        this.columnInfo = columnInfo;
    }

    public List<T[]> getData() {
        return data;
    }

    public String[] getColumnInfo() {
        return columnInfo;
    }

    public int getRowCount() {
        return data.size();
    }

    public T getValueAt(int row, int col) {
        return data.get(row)[col];
    }

    public int getColumnCount() {
        return columnInfo.length;
    }

    @Override
    public String getColumnName(int col) {
        return columnInfo[col];
    }

    public void setData(String[] columnInfo, List<T[]> dataTable) {
        this.columnInfo = columnInfo;
        this.data = dataTable;
    }

    public void setColumnInfo(String[] columnInfo) {
        this.columnInfo = columnInfo;
    }

    public void setData(List<T[]> dataTable) {
        this.data = dataTable;
    }
}
