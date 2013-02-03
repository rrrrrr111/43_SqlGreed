package ru.roman.bim.server.jdo.dao;

import com.google.appengine.api.datastore.Query;
import ru.roman.bim.server.jdo.entity.UserRating;
import ru.roman.bim.server.jdo.entity.Word;
import ru.roman.bim.server.service.dataws.dto.word.BimItemModel;
import ru.roman.bim.server.service.dataws.dto.word.GetListRequest;
import ru.roman.bim.server.service.dataws.dto.word.GetListResp;
import ru.roman.bim.server.util.EntityUtil;
import ru.roman.bim.server.util.JdoUtil;
import ru.roman.bim.server.util.PropUtil;

import javax.jdo.PersistenceManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** @author Roman 02.02.13 4:11 */
public class WordDaoJdo {


    public static GetListResp getWords(final GetListRequest req) {


        List<BimItemModel> resList = JdoUtil.createQuery(Word.class, new JdoUtil.QueryCall() {
            @Override
            public Object exec(PersistenceManager pm, javax.jdo.Query query) {
                query.setFilter("facedLangId == :facedLangId" +
                        " && :types.contains(type) && :ratings.contains(rating)" +
                        //" && userRatings.contains(:userRating) " +
                        " ");
                query.setOrdering(req.getSortingField() + " " + Query.SortDirection.valueOf(req.getSortingDirection()));
                query.setRange(req.getOffset(), req.getOffset() + req.getCount());
                final List<Word> list = (List<Word>) query.executeWithArray(
                        new Object[] {req.getFacedLangId(), req.getTypes(), req.getRatingsList(),
                                new UserRating(req.getRequestInfo().getUserId())});

                List<BimItemModel> resList = new ArrayList<BimItemModel>();
                for (Word ent : list) {
                    BimItemModel model = new BimItemModel();
                    PropUtil.copyModelProperties(model, ent);
                    model.setId(ent.getKey().getId());
                    resList.add(model);
                }
                return resList;
            }
        });

        final int size = EntityUtil.getCount(ru.roman.bim.server.dss.dao.WordDao.ENT_NAME);
        final GetListResp resp = new GetListResp();
        resp.setList(resList);
        resp.setRecordsCount(size);
        return resp;
    }

    public static GetListResp getWordsJpa(final GetListRequest req) {
        List<BimItemModel> resList = JdoUtil.exec(new JdoUtil.PmCall() {
            @Override
            public Object exec(PersistenceManager pm) {
                javax.jdo.Query q = pm.newQuery("javax.jdo.query.JPQL",
                        "SELECT w FROM " + Word.class.getName() + " w WHERE" +
                                " w.facedLangId = :facedLangId" +
                                " AND w.type IN (:types) and w.rating IN (:ratings)" +
                               // " && userRatings.userId = :userId " +
                                " ");
                Map params = new HashMap();
                params.put("facedLangId", req.getFacedLangId());
                params.put("types", req.getTypes());
                params.put("ratings", req.getRatingsList());
                params.put("userId", req.getRequestInfo().getUserId());
                List<Word> list = (List)q.executeWithMap(params);

                List<BimItemModel> resList = new ArrayList<BimItemModel>();
                for (Word ent : list) {
                    BimItemModel model = new BimItemModel();
                    PropUtil.copyModelProperties(model, ent);
                    model.setId(ent.getKey().getId());
                    resList.add(model);
                }
                return resList;
            }
        });

        final int size = EntityUtil.getCount(ru.roman.bim.server.dss.dao.WordDao.ENT_NAME);
        final GetListResp resp = new GetListResp();
        resp.setList(resList);
        resp.setRecordsCount(size);
        return resp;

    }


}
