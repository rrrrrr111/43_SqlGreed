package ru.roman.greet.gui.pane.conf;

import org.apache.commons.lang3.BooleanUtils;
import ru.roman.greet.gui.common.ListTableModel;
import ru.roman.greet.service.datasource.dto.ReconnectionInfo;

import java.util.ArrayList;
import java.util.List;

/**
* @author Roman 16.09.12 1:43
*/
public class ConfigTableModel extends ListTableModel<String> {
    private ConfigManager configManager = ConfigManager.getInstance();

    public ConfigTableModel() {
        super();
        setColumnInfo(new String[] {
                "Alias",
                "URL",
                "Driver Class",
                "Login",
                "Password",
                "Auto commit"});
    }


    public String getValueAt(int row, int col) {
        if (col != 4) {
            return super.getValueAt(row, col);
        } else {
            return "***";
        }
    }

    @Override
    public boolean isCellEditable(int r, int c) {
        return true;
    }

    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        final String strVal = (String) value;
        data.get(rowIndex)[columnIndex] = strVal;
        final ReconnectionInfo connInfo = configManager.getConnectionsModel().getConnectionsInfo().get(rowIndex);
        switch (columnIndex) {
            case 0: {
                connInfo.setAlias(strVal);
                break;
            }
            case 1: {
                connInfo.setUrl(strVal);
                break;
            }
            case 2: {
                connInfo.setDriverClazz(strVal);
                break;
            }
            case 3: {
                connInfo.setUsername(strVal);
                break;
            }
            case 4: {
                connInfo.setPassword(strVal);
                break;
            }
            case 5: {
                connInfo.setAutoCommit(BooleanUtils.toBooleanObject(strVal));
                break;
            }
            default:
                throw new RuntimeException("Unknown column index : " + columnIndex);
        }
        this.fireTableDataChanged();
    }

    @Override
    public void fireTableDataChanged() {
        List<String[]> list = new ArrayList<String[]>();
        List<ReconnectionInfo> coonInfoList = configManager.getConnectionsModel().getConnectionsInfo();
        for (ReconnectionInfo info : coonInfoList) {
            list.add(info.asArray());
        }
        setData(list);
        super.fireTableDataChanged();
    }
}
