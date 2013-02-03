package ru.roman.bim.server.service.dataws.dto.word;

import ru.roman.bim.server.service.dataws.dto.AbstractRequest;

import java.util.ArrayList;
import java.util.List;

/** @author Roman 22.12.12 15:58 */
public class GetListRequest extends AbstractRequest {

    private Integer offset;
    private Integer count;
    private String sortingField;
    private String sortingDirection;

    private List<Long> types;
    private Integer facedLangId;
    private Integer shadowedLangId;
    private List<Integer> ratingsList;
    private List<Long> categories;
    private List<Long> subscribed;

    public GetListRequest() {
    }

    public GetListRequest(Integer offset, Integer count, String sortingField,
                          String sortingDirection, List<Long> types, Integer facedLangId) {
        this.offset = offset;
        this.count = count;
        this.sortingField = sortingField;
        this.sortingDirection = sortingDirection;
        this.types = types;
        this.facedLangId = facedLangId;
    }

    public List<Long> getTypes() {
        return types;
    }

    public void setTypes(List<Long> types) {
        this.types = types;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getFacedLangId() {
        return facedLangId;
    }

    public void setFacedLangId(Integer facedLangId) {
        this.facedLangId = facedLangId;
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

    public Integer getShadowedLangId() {
        return shadowedLangId;
    }

    public void setShadowedLangId(Integer shadowedLangId) {
        this.shadowedLangId = shadowedLangId;
    }

    public List<Long> getCategories() {
        return categories;
    }

    public void setCategories(List<Long> categories) {
        this.categories = categories;
    }

    public List<Long> getSubscribed() {
        return subscribed;
    }

    public void setSubscribed(List<Long> subscribed) {
        this.subscribed = subscribed;
    }
}
