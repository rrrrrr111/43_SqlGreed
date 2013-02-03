package ru.roman.bim.server.service.systask.task;

import com.google.appengine.api.datastore.Entity;
import ru.roman.bim.server.dss.dao.UserRatingDao;
import ru.roman.bim.server.dss.dao.UserSettingsDao;
import ru.roman.bim.server.dss.dao.WordDao;
import ru.roman.bim.server.service.dataws.dto.system.SystemTaskRequest;
import ru.roman.bim.server.util.PropUtil;

import java.util.List;

import static ru.roman.bim.server.util.EntityUtil.getAll;
import static ru.roman.bim.server.util.EntityUtil.persistEntity;

/** @author Roman 03.02.13 12:20
 *
 * 1 !!!!!!!!!!
 * */
public class NormalizeWordsTask implements SystemTask {


    @Override
    public int executeTask(int num, String params, SystemTaskRequest req) {
        final int[] i = {0};

        Entity masterUser = UserSettingsDao.getMasterUser();

        List<Entity> list = getAll(WordDao.ENT_NAME);
        for (Entity word : list) {
            final Long type = PropUtil.getEntProperty(word, WordDao.TYPE);
            final Long category = PropUtil.getEntProperty(word, WordDao.CATEGORY);
            if (type == 3L || category == 1L) {
                word.setProperty(WordDao.RATING, 2L);
            } else if (type == 2L) {
                word.setProperty(WordDao.RATING, 4L);
            } else {
                word.setProperty(WordDao.RATING, 3L);
            }
            PropUtil.setEntPropertyIfNull(word, WordDao.CATEGORY, 0);
            word.setProperty(WordDao.OWNER, masterUser.getKey().getId());
            persistEntity(word);
            UserRatingDao.createUserRatings(word);
            i[0]++;
        }
        return i[0];
    }
}
