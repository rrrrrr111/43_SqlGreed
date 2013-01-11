package ru.roman.bim.server.dao;

import com.google.appengine.api.datastore.*;
import ru.roman.bim.server.service.data.dto.BimItemModel;
import ru.roman.bim.server.service.data.dto.BimItemType;
import ru.roman.bim.server.service.data.dto.GaeGetListRequest;
import ru.roman.bim.server.service.data.dto.GaeGetListResponse;
import ru.roman.bim.server.util.EntityUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import static com.google.appengine.api.datastore.FetchOptions.Builder.withLimit;


/**
 * This class handles CRUD operations related to Word entity.
 * 
 *
 */
public class WordDao {
    private static final Logger log = Logger.getLogger(WordDao.class.getName());
    public static final String ENT_NAME = "Word";
    /* поля*/
    public static final String TEXT_FACED = "textFaced";
    public static final String TEXT_SHADOWED = "textShadowed";
    public static final String TYPE = "type";
    public static final String FACED_LANG_ID = "facedLangId";
    public static final String SHADOWED_LANG_ID = "shadowedLangId";
    public static final String OWNER = "owner";
    public static final String RATING = "rating";
    public static final String EDIT_DATE = "editDate";

    public static Long createOrUpdate(BimItemModel model) {

        Entity word = null;
        if(model.getId() == null){
            word = EntityUtil.findFirstEntity(ENT_NAME, TEXT_FACED, model.getTextFaced());
            if (word != null) {
                model.setTextShadowed(
                     model.getTextShadowed() + ", " + word.getProperty(TEXT_SHADOWED));
            } else {
                word = new Entity(ENT_NAME);
            }
        } else {
            word = getWord(model.getId());
        }
        if (word == null) {
            throw new RuntimeException("Word doesn't determined");
        }

        word.setProperty(TEXT_FACED, model.getTextFaced());   // Entity ID for unique search
        word.setProperty(TEXT_SHADOWED, model.getTextShadowed());
        word.setProperty(TYPE, model.getType().ordinal());
        word.setProperty(FACED_LANG_ID, model.getFacedLangId());
        word.setProperty(SHADOWED_LANG_ID, model.getShadowedLangId());
        word.setProperty(OWNER, model.getOwner());
        word.setProperty(RATING, model.getRating());
        word.setProperty(EDIT_DATE, new Date());

        EntityUtil.persistEntity(word);
        return word.getKey().getId();
    }


    public static GaeGetListResponse getWords(GaeGetListRequest req) {

        Query q = new Query(ENT_NAME);
        q.addFilter(FACED_LANG_ID, Query.FilterOperator.EQUAL, req.getLangId());
        q.addFilter(TYPE, Query.FilterOperator.IN, BimItemType.getOrdinals(req.getTypes()));
        q.addFilter(RATING, Query.FilterOperator.IN, req.getRatingsList());
        q.addSort(req.getSortingField(), Query.SortDirection.valueOf(req.getSortingDirection()));
        PreparedQuery pq = EntityUtil.getDataStore().prepare(q);

        List<Entity> res = pq.asList(withLimit(req.getCount()).offset(req.getOffset()));
        List<BimItemModel> list = new ArrayList<BimItemModel>();
        for (Entity ent : res) {
            BimItemModel model = new BimItemModel();
            model.setId(ent.getKey().getId());
            model.setTextFaced((String) ent.getProperty(TEXT_FACED));
            model.setTextShadowed((String) ent.getProperty(TEXT_SHADOWED));
            model.setShadowedLangId((Long)ent.getProperty(SHADOWED_LANG_ID));
            model.setFacedLangId((Long)ent.getProperty(FACED_LANG_ID));
            model.setOwner((Long) ent.getProperty(OWNER));
            model.setType(BimItemType.byOrdinal(((Long) ent.getProperty(TYPE)).intValue()));
            model.setRating((Long)ent.getProperty(RATING));
            model.setEditDate((Date) ent.getProperty(EDIT_DATE));
            list.add(model);
        }

        final int size = EntityUtil.getCount(ENT_NAME);
        final GaeGetListResponse resp = new GaeGetListResponse();
        resp.setList(list);
        resp.setRecordsCount(size);
        return resp;
    }

    public static void renewRating(Long key, Integer rating) {
        Entity word = getWord(key);
        if(word == null) {
            throw new RuntimeException(String.format("%s with id=%s not found", new Object[]{ENT_NAME, key}));
        }
        word.setProperty(RATING, rating);
        EntityUtil.persistEntity(word);
    }

    public static Entity getWord(Long key) {
        return EntityUtil.findEntity(createKey(key));
    }

    public static boolean deleteWord(Long key) {
        Entity entity = getWord(key);
        if(entity != null){
            EntityUtil.deleteEntity(entity.getKey());
            return true;
        } else {
            return false;
        }
    }

    public static Key createKey(Long id) {
        return KeyFactory.createKey(ENT_NAME, id);
    }


}
