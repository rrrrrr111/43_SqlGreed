package ru.roman.bim.server.service.data.dto.settings;

import java.io.Serializable;
import java.util.List;

/** @author Roman 21.01.13 23:35 */
public class UserSettingsModel implements Serializable {

    private Long id;

    private String login;
    private String password;

    private Long facedLangId;
    private Long shadowedLangId;

    private List<Integer> ratings;
    private List<Long> subscribed;
    private Long portion;

    private Double opacity;
    private Long previewInterval;
    private Long previewDuration;

    private String sortingField;
    private String sortingDirection;

    private Long cacheMaxSize;
    private Long currentNum;
    private Long recordsCount;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public List<Integer> getRatings() {
        return ratings;
    }

    public void setRatings(List<Integer> ratings) {
        this.ratings = ratings;
    }

    public Long getPortion() {
        return portion;
    }

    public void setPortion(Long portion) {
        this.portion = portion;
    }

    public Double getOpacity() {
        return opacity;
    }

    public void setOpacity(Double opacity) {
        this.opacity = opacity;
    }

    public Long getPreviewInterval() {
        return previewInterval;
    }

    public void setPreviewInterval(Long previewInterval) {
        this.previewInterval = previewInterval;
    }

    public Long getPreviewDuration() {
        return previewDuration;
    }

    public void setPreviewDuration(Long previewDuration) {
        this.previewDuration = previewDuration;
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

    public Long getCacheMaxSize() {
        return cacheMaxSize;
    }

    public void setCacheMaxSize(Long cacheMaxSize) {
        this.cacheMaxSize = cacheMaxSize;
    }

    public List<Long> getSubscribed() {
        return subscribed;
    }

    public void setSubscribed(List<Long> subscribed) {
        this.subscribed = subscribed;
    }

    public Long getCurrentNum() {
        return currentNum;
    }

    public void setCurrentNum(Long currentNum) {
        this.currentNum = currentNum;
    }

    public Long getRecordsCount() {
        return recordsCount;
    }

    public void setRecordsCount(Long recordsCount) {
        this.recordsCount = recordsCount;
    }
}
