package ru.roman.bim.server.jdo.entity;

import com.google.appengine.api.datastore.Key;

import javax.jdo.annotations.*;
import java.util.Date;
import java.util.List;

/** @author Roman 30.01.13 23:56 */
@PersistenceCapable
public class Word {

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;

    @Persistent
    private String textFaced;
    @Persistent
    private String textShadowed;

    @Persistent
    private Long facedLangId;
    @Persistent
    private Long shadowedLangId;

    @Persistent
    private Long rating;
    @Persistent
    private Long type;
    @Persistent
    private Long category;

    @Persistent
    private Long owner;

    @Persistent
    private Date editDate;


    //@Join
    @Persistent(name = "userRatings", mappedBy = "word")
    //@Column(name = "userRatings", allowsNull = "true")
    @Order(extensions=@Extension(vendorName="datanucleus", key="list-ordering", value="userId ASC"))
    private List<UserRating> userRatings;


    public Key getKey() {
        return key;
    }

    public void setKey(Key key) {
        this.key = key;
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

    public Long getRating() {
        return rating;
    }

    public void setRating(Long rating) {
        this.rating = rating;
    }

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }

    public Long getCategory() {
        return category;
    }

    public void setCategory(Long category) {
        this.category = category;
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

    public List<UserRating> getUserRatings() {
        return userRatings;
    }

    public void setUserRatings(List<UserRating> userRatings) {
        this.userRatings = userRatings;
    }
}
