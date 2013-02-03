package ru.roman.bim.server.service.systask.task;

import ru.roman.bim.server.jdo.entity.UserRating;
import ru.roman.bim.server.jdo.entity.Word;
import ru.roman.bim.server.service.dataws.dto.system.SystemTaskRequest;
import ru.roman.bim.server.service.dataws.dto.word.BimItemModel;
import ru.roman.bim.server.util.JdoUtil;

import javax.jdo.PersistenceManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/** @author Roman 03.02.13 12:24
 *
 * 2!!!!!!!!!!
 * */
public class RenewWordsRatingsTask implements SystemTask {
    @Override
    public int executeTask(int num, String params, final SystemTaskRequest req) {
        final int[] i = {0};

        Integer count = JdoUtil.createQuery(Word.class, new JdoUtil.QueryCall() {
            @Override
            public Object exec(PersistenceManager pm, javax.jdo.Query query) {
                final List<Word> list = (List<Word>) query.execute();
                List<BimItemModel> resList = new ArrayList<BimItemModel>();
                for (Word ent : list) {
                    ent.setUserRatings(Arrays.asList(
                            new UserRating(5L, req.getRequestInfo().getUserId(), ent)));
                    pm.makePersistent(ent);
                    i[0]++;
                }
                return resList.size();
            }
        });

        return i[0];
    }
}
