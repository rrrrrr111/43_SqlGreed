package ru.roman.bim.server.service.systask.task;

import com.google.appengine.api.datastore.Entity;
import ru.roman.bim.server.dss.dao.UserRatingDao;
import ru.roman.bim.server.dss.dao.UserSettingsDao;
import ru.roman.bim.server.dss.dao.WordDao;
import ru.roman.bim.server.service.dataws.dto.system.SystemTaskRequest;
import ru.roman.bim.server.util.PropUtil;

import java.util.List;

import static ru.roman.bim.server.util.EntityUtil.getAllEntities;
import static ru.roman.bim.server.util.EntityUtil.persistEntity;

/** @author Roman 03.02.13 12:20
 *
 * 1 !!!!!!!!!!
 * */
public class NormalizeWordsTask implements SystemTask {


    @Override
    public int executeTask(int num, String params, SystemTaskRequest req) {
        final int[] i = {0};
        final Entity masterUser = UserSettingsDao.getMasterUser();
        final List<Entity> list = getAllEntities(WordDao.ENT_NAME);

        for (Entity word : list) {
            final Long currType = PropUtil.getEntProperty(word, WordDao.TYPE);
            final Long currCategory = PropUtil.getEntProperty(word, WordDao.CATEGORY);
            final Long currRating = PropUtil.getEntProperty(word, WordDao.RATING);
            final Long currOwner = PropUtil.getEntProperty(word, WordDao.OWNER);


            boolean changed = false;
            if ((Long.valueOf(3L).equals(currType) || Long.valueOf(1L).equals(currCategory))
                    && !Long.valueOf(2L).equals(currRating)) {
                word.setProperty(WordDao.RATING, 2L);
                changed = true;
            } else if (Long.valueOf(2L).equals(currType)
                    && !Long.valueOf(4L).equals(currRating)) {
                word.setProperty(WordDao.RATING, 4L);
                changed = true;
            } else if (!Long.valueOf(3L).equals(currRating)){
                word.setProperty(WordDao.RATING, 3L);
                changed = true;
            }

            if (null == currType) {
                PropUtil.setEntPropertyIfNull(word, WordDao.TYPE, 0);
                changed = true;
            }
            if (null == currCategory) {
                PropUtil.setEntPropertyIfNull(word, WordDao.CATEGORY, 0);
                changed = true;
            }
            if (currOwner != masterUser.getKey().getId()) {
                word.setProperty(WordDao.OWNER, masterUser.getKey().getId());
                changed = true;
            }
            if (changed) {
                persistEntity(word);
                UserRatingDao.createUserRatings(word);
                i[0]++;
            }

        }

        final List<Entity> userRatingList = getAllEntities(UserRatingDao.ENT_NAME);

        for (Entity userRating : userRatingList) {
            final Long currRating = PropUtil.getEntProperty(userRating, WordDao.RATING);

            boolean changed = false;
            if (currRating > 5) {
                userRating.setProperty(WordDao.RATING, 3L);
                changed = true;
            } else if (currRating < 0) {
                userRating.setProperty(WordDao.RATING, 0L);
                changed = true;
            }

            if (changed) {
                persistEntity(userRating);
                i[0]++;
            }
        }

        return i[0];
    }
}
