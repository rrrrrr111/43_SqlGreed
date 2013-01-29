package ru.roman.bim.server.dao;

import com.google.appengine.api.datastore.Entity;
import ru.roman.bim.server.util.EntityUtil;

import java.util.logging.Logger;

/** @author Roman 29.01.13 23:01 */
public class UserRatingDao {
    private static final Logger log = Logger.getLogger(UserRatingDao.class.getName());
    public static final String ENT_NAME = "UserRating";
    public static final String RATING = "rating";
    public static final String USER_ID = "userId";

    public static void renewRating(Entity word, Integer rating, Long userId) {
        if (userId == null) {
            throw new RuntimeException(String.format("UserId can't be null"));
        }
        Entity userRating = EntityUtil.findFirstChild(ENT_NAME, word.getKey(), USER_ID, userId);
        if (userRating == null) {
            userRating = new Entity(ENT_NAME, word.getKey());
            userRating.setProperty(USER_ID, userId);
        }
        userRating.setProperty(RATING, rating);
        EntityUtil.persistEntity(userRating);
    }
}
