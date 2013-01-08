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

    public static Long createOrUpdate(BimItemModel model) {

        final Entity word;
        if(model.getId() == null){
            //final Key key = createKey(model.getId());
            word = new Entity(ENT_NAME);
        } else {
            word = getWord(model.getId());
        }
        if (word == null) {
            throw new RuntimeException("Word doesn't determined");
        }

        word.setProperty("textFaced", model.getTextFaced());   // Entity ID for unique search
        word.setProperty("textShadowed", model.getTextShadowed());
        word.setProperty("type", model.getType().ordinal());
        word.setProperty("facedLangId", model.getFacedLangId());
        word.setProperty("shadowedLangId", model.getShadowedLangId());
        word.setProperty("owner", model.getOwner());
        word.setProperty("rating", model.getRating());
        word.setProperty("editDate", new Date());

        EntityUtil.persistEntity(word);
        return word.getKey().getId();
    }


    public static GaeGetListResponse getWords(GaeGetListRequest req) {

        Query q = new Query(ENT_NAME);
        q.addFilter("facedLangId", Query.FilterOperator.EQUAL, req.getLangId());
        q.addFilter("type", Query.FilterOperator.IN, BimItemType.getOrdinals(req.getTypes()));
        q.addSort(req.getSortingField());
        PreparedQuery pq = EntityUtil.getDataStore().prepare(q);

        List<Entity> res = pq.asList(withLimit(req.getCount()).offset(req.getOffset()));
        List<BimItemModel> list = new ArrayList<BimItemModel>();
        for (Entity ent : res) {
            BimItemModel model = new BimItemModel();
            model.setId(ent.getKey().getId());
            model.setTextFaced((String)ent.getProperty("textFaced"));
            model.setTextShadowed((String)ent.getProperty("textShadowed"));
            model.setShadowedLangId((Long)ent.getProperty("shadowedLangId"));
            model.setFacedLangId((Long)ent.getProperty("facedLangId"));
            model.setOwner((Long) ent.getProperty("owner"));
            model.setType(BimItemType.byOrdinal(((Long) ent.getProperty("type")).intValue()));
            model.setRating((Long)ent.getProperty("rating"));
            model.setEditDate((Date) ent.getProperty("editDate"));
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
        word.setProperty("rating", rating);
        EntityUtil.persistEntity(word);
    }

    /**
     * Get all the items for a product
     *
     * @param kind
     *          : item kind
     * @param productName
     *          : product name
     * @return: all items of type product
     */
    public static Iterable<Entity> getWordForProduct(String kind, String productName) {
        Key ancestorKey = KeyFactory.createKey("Product", productName);
        return EntityUtil.listChildren(ENT_NAME, ancestorKey);
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
