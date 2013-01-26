package ru.roman.bim.server.service.data.dto.word;

import ru.roman.bim.server.service.data.dto.AbstractRequest;

import java.util.ArrayList;
import java.util.List;

/** @author Roman 22.12.12 15:58 */
public class GetListRequest extends AbstractRequest {

    private Integer offset;
    private Integer count;
    private String sortingField;
    private String sortingDirection;

    private List<Integer> types;
    private Integer langId;
    private List<Integer> ratingsList;

    public GetListRequest() {
    }

    public GetListRequest(Integer offset, Integer count, String sortingField,
                          String sortingDirection, List<Integer> types, Integer langId) {
        this.offset = offset;
        this.count = count;
        this.sortingField = sortingField;
        this.sortingDirection = sortingDirection;
        this.types = types;
        this.langId = langId;
    }

    public List<Integer> getTypes() {
        return types;
    }

    public void setTypes(List<Integer> types) {
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

    public String getSortingDirection() {
        return sortingDirection;
    }

    public void setSortingDirection(String sortingDirection) {
        this.sortingDirection = sortingDirection;
    }

    public List<Integer> getRatingsList() {
        if (ratingsList == null) {
            ratingsList = new ArrayList<Integer>();
        }
        return ratingsList;
    }

    public void setRatingsList(List<Integer> ratingsList) {
        this.ratingsList = ratingsList;
    }
}
