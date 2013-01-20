package ru.roman.bim.server.service.data.dto;

import java.io.Serializable;
import java.util.Date;

/** @author Roman 19.12.12 23:36 */
public class BimItemModel implements Serializable {

    private Long id;
    private String textFaced;
    private String textShadowed;

    private Long facedLangId;
    private Long shadowedLangId;

    private Long rating;
    private Long type;
    private Long category;

    private Long modelNum;
    private Long owner;

    private Date editDate;

    public BimItemModel() {}

    public BimItemModel(Long id, String textFaced, String textShadowed,
                        Long facedLangId, Long shadowedLangId, Long rating,
                        Long type, Long category, Long modelNum, Long owner) {
        this.id = id;
        this.textFaced = textFaced;
        this.textShadowed = textShadowed;
        this.facedLangId = facedLangId;
        this.shadowedLangId = shadowedLangId;
        this.rating = rating;
        this.type = type;
        this.category = category;
        this.modelNum = modelNum;
        this.owner = owner;
    }

    public String getTextFaced() {
        return textFaced;
    }

    public void setTextFaced(String textFaced) {
        this.textFaced = textFaced;
    }

    public String getTextShadowed() {
        return textShadowed;
    }

    public void setTextShadowed(String textShadowed) {
        this.textShadowed = textShadowed;
    }

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }

    public Long getRating() {
        return rating;
    }

    public void setRating(Long rating) {
        this.rating = rating;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFacedLangId() {
        return facedLangId;
    }

    public void setFacedLangId(Long facedLangId) {
        this.facedLangId = facedLangId;
    }

    public Long getShadowedLangId() {
        return shadowedLangId;
    }

    public void setShadowedLangId(Long shadowedLangId) {
        this.shadowedLangId = shadowedLangId;
    }

    public Long getModelNum() {
        return modelNum;
    }

    public void setModelNum(Long modelNum) {
        this.modelNum = modelNum;
    }

    public Long getOwner() {
        return owner;
    }

    public void setOwner(Long owner) {
        this.owner = owner;
    }

    public Date getEditDate() {
        return editDate;
    }

    public void setEditDate(Date editDate) {
        this.editDate = editDate;
    }

    public Long getCategory() {
        return category;
    }

    public void setCategory(Long category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BimItemModel)) return false;

        BimItemModel that = (BimItemModel) o;

        if (id != null) {
            return id.equals(that.id);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }



}
