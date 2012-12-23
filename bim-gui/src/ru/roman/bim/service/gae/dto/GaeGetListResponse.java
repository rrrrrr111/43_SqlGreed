package ru.roman.bim.service.gae.dto;

import ru.roman.bim.gui.pane.main.MainViewModel;

import java.io.Serializable;
import java.util.Collection;

/** @author Roman 22.12.12 15:58 */
public class GaeGetListResponse implements Serializable{

    private Integer recordsCount;
    private Collection<MainViewModel> list;



    public Collection<MainViewModel> getList() {
        return list;
    }

    public void setList(Collection<MainViewModel> list) {
        this.list = list;
    }

    public Integer getRecordsCount() {
        return recordsCount;
    }

    public void setRecordsCount(Integer recordsCount) {
        this.recordsCount = recordsCount;
    }
}
