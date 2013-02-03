package ru.roman.bim.server.service.dataws.dto.word;

import ru.roman.bim.server.service.dataws.dto.AbstractResponse;

import java.util.Collection;

/** @author Roman 22.12.12 15:58 */
public class GetListResp extends AbstractResponse {

    private Integer recordsCount;
    private Collection<BimItemModel> list;


    public GetListResp() {
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
