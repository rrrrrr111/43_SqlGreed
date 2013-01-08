package ru.roman.bim.server.service.data.dto;

import java.io.Serializable;
import java.util.List;

/** @author Roman 22.12.12 15:58 */
public class GaeGetListRequest implements Serializable {

    private Integer offset;
    private Integer count;
    private String sortingField;
    private String sortingDirection;

    private List<BimItemType> types;
    private Integer langId;

    public GaeGetListRequest() {
    }

    public GaeGetListRequest(Integer offset, Integer count, String sortingField,
                             String sortingDirection, List<BimItemType> types, Integer langId) {
        this.offset = offset;
        this.count = count;
        this.sortingField = sortingField;
        this.sortingDirection = sortingDirection;
        this.types = types;
        this.langId = langId;
    }

    public List<BimItemType> getTypes() {
        return types;
    }

    public void setTypes(List<BimItemType> types) {
        this.types = types;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getLangId() {
        return langId;
    }

    public void setLangId(Integer langId) {
        this.langId = langId;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getSortingField() {
        return sortingField;
    }

    public void setSortingField(String sortingField) {
        this.sortingField = sortingField;
    }
}
