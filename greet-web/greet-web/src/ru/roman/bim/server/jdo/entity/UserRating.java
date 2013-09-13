package ru.roman.bim.server.jdo.entity;

import com.google.appengine.api.datastore.Key;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

/**
 *
 * @author Roman 02.02.13 4:05
 */
@PersistenceCapable
public class UserRating {

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;

    @Persistent
    private Long rating;
    @Persistent
    private Long userId;


    @Persistent(mappedBy = "userRatings")
    private Word word;

    public UserRating() {
    }

    public UserRating(Long userId) {
        this.userId = userId;
    }

    public UserRating(Long rating, Long userId) {
        this(rating);
        this.userId = userId;
    }

    public UserRating(Long rating, Long userId, Word word) {
        this(rating, userId);
        this.word = word;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserRating)) return false;
        UserRating that = (UserRating) o;
        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;
        return true;
    }

    @Override
    public int hashCode() {
        return userId != null ? userId.hashCode() : 0;
    }

    public Key getKey() {
        return key;
    }

    public void setKey(Key key) {
        this.key = key;
    }

    public Long getRating() {
        return rating;
    }

    public void setRating(Long rating) {
        this.rating = rating;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Word getWord() {
        return word;
    }

    public void setWord(Word word) {
        this.word = word;
    }
}
