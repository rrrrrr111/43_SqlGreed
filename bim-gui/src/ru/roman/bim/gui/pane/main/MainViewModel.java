package ru.roman.bim.gui.pane.main;

import ru.roman.bim.gui.common.Model;
import ru.roman.bim.service.gae.dto.TypeModel;

/** @author Roman 19.12.12 23:36 */
public class MainViewModel implements Model {

    private Long id;
    private String textFaced;
    private String textShadowed;

    private Integer facedLangId;
    private Integer shadowedLangId;

    private Integer rating;
    private TypeModel type;

    private Integer modelNum;
    private Integer owner;

    public MainViewModel() {}

    public MainViewModel(Long id, String textFaced, String textShadowed,
                         Integer facedLangId, Integer shadowedLangId, Integer rating,
                         TypeModel type, Integer modelNum, Integer owner) {
        this.id = id;
        this.textFaced = textFaced;
        this.textShadowed = textShadowed;
        this.facedLangId = facedLangId;
        this.shadowedLangId = shadowedLangId;
        this.rating = rating;
        this.type = type;
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

    public TypeModel getType() {
        return type;
    }

    public void setType(TypeModel type) {
        this.type = type;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getFacedLangId() {
        return facedLangId;
    }

    public void setFacedLangId(Integer facedLangId) {
        this.facedLangId = facedLangId;
    }

    public Integer getShadowedLangId() {
        return shadowedLangId;
    }

    public void setShadowedLangId(Integer shadowedLangId) {
        this.shadowedLangId = shadowedLangId;
    }

    public Integer getModelNum() {
        return modelNum;
    }

    public void setModelNum(Integer modelNum) {
        this.modelNum = modelNum;
    }

    public Integer getOwner() {
        return owner;
    }

    public void setOwner(Integer owner) {
        this.owner = owner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MainViewModel)) return false;

        MainViewModel that = (MainViewModel) o;

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
