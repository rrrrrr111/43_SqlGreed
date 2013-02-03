package ru.roman.bim.server.dss.dao;

import com.google.appengine.api.datastore.*;
import ru.roman.bim.server.service.dataws.dto.word.GetListRequest;
import ru.roman.bim.server.util.EntityUtil;
import ru.roman.bim.server.util.PropUtil;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import static com.google.appengine.api.datastore.FetchOptions.Builder.withLimit;
import static ru.roman.bim.server.util.EntityUtil.getDataStore;

/** @author Roman 29.01.13 23:01 */
public class UserRatingDao {
    private static final Logger log = Logger.getLogger(UserRatingDao.class.getName());
    public static final String ENT_NAME = "UserRating";
    public static final String USER_ID = "userId";
    public static final String WORD = "word";


    public static void storeRating(Entity word, Integer rating, Long userId) {
        storeRating(word, rating, userId, new Date());
    }

    private static void storeRating(Entity word, Integer rating, Long userId, Date editDate) {
        if (userId == null) {
            throw new RuntimeException(String.format("UserId can't be null"));
        }
        Entity userRating = EntityUtil.findFirstChild(ENT_NAME, word.getKey(), USER_ID, userId);
        if (userRating == null) {
            userRating = new Entity(ENT_NAME, word.getKey());
            userRating.setProperty(USER_ID, userId);
            userRating.setProperty(WordDao.RATING, (rating == null ? word.getProperty(WordDao.RATING) : rating));
        } else if (rating != null) {
            userRating.setProperty(WordDao.RATING, rating);
        }
        userRating.setProperty(WORD, word.getKey());
        PropUtil.copyEntProperty(userRating, word, WordDao.TYPE);
        PropUtil.copyEntProperty(userRating, word, WordDao.CATEGORY);
        PropUtil.copyEntProperty(userRating, word, WordDao.FACED_LANG_ID);
        PropUtil.copyEntProperty(userRating, word, WordDao.SHADOWED_LANG_ID);
        PropUtil.copyEntProperty(userRating, word, WordDao.OWNER);
        userRating.setProperty(WordDao.EDIT_DATE, editDate);
        EntityUtil.persistEntity(userRating);
    }

    public static void createUserRatings(Entity word) {
        final List<Key> settKeyList = UserSettingsDao.getUsersKeys();
        final Date currDate = new Date();
        for (Key settKey : settKeyList) {
            storeRating(word, null, settKey.getId(), currDate);
        }
    }

    public static void createNewUserRatings(Entity sett) {
        final List<Key> wordKeyList = WordDao.getWordsKeys();
        final Date currDate = new Date();
        for (Key wordKey : wordKeyList) {
            final Entity word = WordDao.getWord(wordKey);
            storeRating(word, null, sett.getKey().getId(), currDate);
        }
    }

    public static List<Entity> getRatings(GetListRequest req) {
        Query q = new Query(ENT_NAME);
        q.addFilter(WordDao.FACED_LANG_ID, Query.FilterOperator.EQUAL, req.getFacedLangId());
        q.addFilter(WordDao.SHADOWED_LANG_ID, Query.FilterOperator.EQUAL, req.getShadowedLangId());
        q.addFilter(WordDao.TYPE, Query.FilterOperator.IN, req.getTypes());
        q.addFilter(WordDao.RATING, Query.FilterOperator.IN, req.getRatingsList());
        //q.addFilter(WordDao.CATEGORY, Query.FilterOperator.IN, req.getCategories());
        q.addSort(req.getSortingField(), Query.SortDirection.valueOf(req.getSortingDirection()));
        PreparedQuery pq = getDataStore().prepare(q);
        return pq.asList(withLimit(req.getCount()).offset(req.getOffset()));

    }

    public static int getCount(GetListRequest req) {
        Query q = new Query(ENT_NAME).setKeysOnly();
        q.addFilter(WordDao.FACED_LANG_ID, Query.FilterOperator.EQUAL, req.getFacedLangId());
        q.addFilter(WordDao.SHADOWED_LANG_ID, Query.FilterOperator.EQUAL, req.getShadowedLangId());
        q.addFilter(WordDao.TYPE, Query.FilterOperator.IN, req.getTypes());
        q.addFilter(WordDao.RATING, Query.FilterOperator.IN, req.getRatingsList());
        //q.addFilter(WordDao.CATEGORY, Query.FilterOperator.IN, req.getCategories());
        PreparedQuery pq = getDataStore().prepare(q);
        return pq.asList(FetchOptions.Builder.withDefaults()).size();
    }
}
