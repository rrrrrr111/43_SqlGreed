package ru.roman.bim.server.service.data.dto;

import java.io.Serializable;
import java.util.Collection;

/** @author Roman 22.12.12 15:58 */
public class GaeGetListResponse implements Serializable {

    private Integer recordsCount;
    private Collection<BimItemModel> list;


    public GaeGetListResponse() {
    }

    public Collection<BimItemModel> getList() {
        return list;
    }

    public void setList(Collection<BimItemModel> list) {
        this.list = list;
    }

    public Integer getRecordsCount() {
        return recordsCount;
    }

    public void setRecordsCount(Integer recordsCount) {
        this.recordsCount = recordsCount;
    }
}
