package ru.roman.greed.gui.common;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import ru.roman.greed.gui.pane.main.ColumnInfo;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

/**
* User: Roman
* Date: 01.09.12
* Time: 12:39
*/
public class ColumnInfoTableModel<M> extends AbstractTableModel {
    private static final Log log = LogFactory.getLog(ColumnInfoTableModel.class);

    private List<Object[]> data;
    private List<ColumnInfo> columnsInfo;

    public ColumnInfoTableModel() {
        this(new ArrayList<ColumnInfo>());
    }

    public ColumnInfoTableModel(List<ColumnInfo> columnsInfo) {
        this(columnsInfo, new ArrayList<Object[]>());
    }

    public ColumnInfoTableModel(List<ColumnInfo> columnsInfo, List<Object[]> data) {
        this.data = data;
        this.columnsInfo = columnsInfo;
    }

    public List<Object[]> getData() {
        return data;
    }

    public List<ColumnInfo> getColumnsInfo() {
        return columnsInfo;
    }

    public int getRowCount() {
        return data.size();
    }

    public Object getValueAt(int row, int col) {
        return data.get(row)[col];
    }

    public int getColumnCount() {
        return columnsInfo.size();
    }

    @Override
    public String getColumnName(int col) {
        return columnsInfo.get(col).getColumnLabel();
    }

    @Override
    public Class<?> getColumnClass(int col) {
        final String columnClassName = columnsInfo.get(col).getColumnClassName();
        try {
            if (columnClassName != null) {
                return Class.forName(columnClassName);
            }
        } catch (ClassNotFoundException e) {
            log.error("Error while loading column class : " + columnClassName, e);
            return Object.class;
        }
        return Object.class;
    }

    public void setData(List<ColumnInfo> columnInfo, List<Object[]> dataTable) {
        setColumnsInfo(columnInfo);
        this.data = dataTable;
    }

    public void setColumnsInfo(List<ColumnInfo> columnsInfo) {
        this.columnsInfo = columnsInfo;
    }
}
