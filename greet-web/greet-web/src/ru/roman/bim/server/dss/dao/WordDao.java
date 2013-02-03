package ru.roman.bim.server.dss.dao;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import ru.roman.bim.server.service.dataws.dto.word.BimItemModel;
import ru.roman.bim.server.service.dataws.dto.word.GetListRequest;
import ru.roman.bim.server.service.dataws.dto.word.GetListResp;
import ru.roman.bim.server.util.EntityUtil;
import ru.roman.bim.server.util.PropUtil;

import java.util.*;
import java.util.logging.Logger;

import static ru.roman.bim.server.util.EntityUtil.*;


/**
 * This class handles CRUD operations related to Word entity.
 * 
 *
 */
public class WordDao {
    private static final Logger log = Logger.getLogger(WordDao.class.getName());
    public static final String ENT_NAME = "Word";
    /* пол€*/
    public static final String TEXT_FACED = "textFaced";
    public static final String TEXT_SHADOWED = "textShadowed";
    public static final String TYPE = "type";
    public static final String CATEGORY = "category";
    public static final String FACED_LANG_ID = "facedLangId";
    public static final String SHADOWED_LANG_ID = "shadowedLangId";
    public static final String OWNER = "owner";
    public static final String RATING = "rating";
    public static final String EDIT_DATE = "editDate";
    public static final String USER_RATING = "userRatings";

    public static final List<String> EXCLUDE_FOR_ENTITY = Arrays.asList("id", "key", "modelNum");

    public static Long createOrUpdate(BimItemModel model) {

        // логика исключени€ дублировани€
        Entity word = findFirstEntity(ENT_NAME, TEXT_FACED, model);
        boolean newEntity = false;
        String oldTransl = null;
        if(model.getId() == null){
            if (word == null) {
                word = new Entity(ENT_NAME);
                newEntity = true;
            } else {         // дл€ новых слов, если в Ѕƒ уже есть такое, дописываем перевод
                oldTransl = (String)word.getProperty(TEXT_SHADOWED);
            }
        } else {               // при редактировании, удал€ем если уже есть такое слово и дописываем перевод
            if (word != null) {
                if (word.getKey().getId() != model.getId()) {
                    oldTransl = (String)word.getProperty(TEXT_SHADOWED);
                    deleteWord(model.getId());
                }
            } else {
                word = getWord(model.getId());
            }
        }
        if (word == null) {
            throw new RuntimeException("Word doesn't determined");
        }
        final String newTransl = model.getTextShadowed();
        if (oldTransl != null && newTransl != null) {
            if (oldTransl.contains(newTransl)) {
                model.setTextShadowed(oldTransl);
            } else if (!newTransl.contains(oldTransl)) {
                model.setTextShadowed(newTransl + ", " + oldTransl);
            }
        }
        if(model.getEditDate() == null) {
            model.setEditDate(new Date());
        }

        // сохран€ем сущность
        PropUtil.copyEntProperties(word, model, EXCLUDE_FOR_ENTITY);
        persistEntity(word);
        if (newEntity) {
            UserRatingDao.createUserRatings(word);
        }

        return word.getKey().getId();
    }


    public static GetListResp getWords(GetListRequest req) {

        final List<Entity> ratings = UserRatingDao.getRatings(req);
        if (ratings.isEmpty()) {
            throw new RuntimeException("Words list empty");
        }
        final List<Key> wordKeys = new ArrayList<Key>();
        final Map<Key, Entity> ratingsMap = new HashMap<Key, Entity>();
        for (Entity rating : ratings) {
            final Key wordKey = (Key) rating.getProperty(UserRatingDao.WORD);
            wordKeys.add(wordKey);
            ratingsMap.put(wordKey, rating);
        }
        Map<Key, Entity> wordsMap = EntityUtil.findEntities(wordKeys);

        List<BimItemModel> list = new ArrayList<BimItemModel>();
        BimItemModel model;
        for (Map.Entry<Key, Entity> wordEntry : wordsMap.entrySet()) {
            model = new BimItemModel();
            PropUtil.setAllProperties(model, wordEntry.getValue());
            PropUtil.setBeanProperty(model, RATING, ratingsMap.get(wordEntry.getKey()).getProperty(RATING));
            model.setId(wordEntry.getKey().getId());
            list.add(model);
        }

        final int size = UserRatingDao.getCount(req);
        final GetListResp resp = new GetListResp();
        resp.setList(list);
        resp.setRecordsCount(size);
        return resp;
    }

    public static void renewRating(Long key, Integer rating, Long userId) {
        Entity word = getWord(key);
        if(word == null) {
            throw new RuntimeException(String.format("%s with id=%s not found", new Object[]{ENT_NAME, key}));
        }
        UserRatingDao.storeRating(word, rating, userId);
    }

    public static Entity getWord(Long key) {
        return findEntity(createKey(key));
    }

    public static Entity getWord(Key key) {
        return findEntity(key);
    }

    public static boolean deleteWord(Long key) {
        Entity entity = getWord(key);
        if(entity != null){
            deleteEntity(entity.getKey());
            return true;
        } else {
            return false;
        }
    }

    public static Key createKey(Long id) {
        return KeyFactory.createKey(ENT_NAME, id);
    }


    public static List<Key> getWordsKeys() {
        return EntityUtil.getAllKeys(ENT_NAME);
    }


}
