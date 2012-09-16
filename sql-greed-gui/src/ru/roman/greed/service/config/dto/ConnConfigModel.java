package ru.roman.greed.service.config.dto;

import ru.roman.greed.service.datasource.dto.ReconnectionInfo;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

/**
 * @author Roman 15.09.12 19:08
 */
public class ConnConfigModel implements Serializable {

    private List<ReconnectionInfo> connectionsInfo;
    private ReconnectionInfo defaultConnection;

    public ConnConfigModel(List<ReconnectionInfo> connectionsInfo, ReconnectionInfo defaultConnection) {
        this.connectionsInfo = connectionsInfo;
        this.defaultConnection = defaultConnection;
    }

    public ConnConfigModel() {
    }

    public void setConnectionsInfo(List<ReconnectionInfo> connectionsInfo) {
        this.connectionsInfo = connectionsInfo;
    }

    public List<ReconnectionInfo> getConnectionsInfo() {
        return connectionsInfo;
    }

    public void setDefaultConnection(ReconnectionInfo defaultConnection) {
        this.defaultConnection = defaultConnection;
    }

    public ReconnectionInfo getDefaultConnection() {
        return defaultConnection;
    }

    public void setDefaultConnectionNum(int index) {
        if (connectionsInfo.isEmpty()) {
            defaultConnection = null;
        } else if (index >= connectionsInfo.size()) {
            defaultConnection = connectionsInfo.get(0);
        } else {
            defaultConnection = connectionsInfo.get(index);
        }
    }

    public int getDefaultConnectionNum() {
        if (connectionsInfo.isEmpty() || defaultConnection == null) {
            return 0;
        }
        final Iterator<ReconnectionInfo> iterator = connectionsInfo.iterator();
        ReconnectionInfo info;
        for (int i = 0; iterator.hasNext(); i++) {
            info  =  iterator.next();
            if (info.equals(defaultConnection)) {
                return i;
            }
        }
        return 0;
    }
}
