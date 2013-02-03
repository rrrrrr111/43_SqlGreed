package ru.roman.bim.server.service.systask.task;

import ru.roman.bim.server.dss.dao.UserRatingDao;
import ru.roman.bim.server.dss.dao.UserSettingsDao;
import ru.roman.bim.server.dss.dao.WordDao;
import ru.roman.bim.server.service.dataws.dto.system.SystemTaskRequest;
import ru.roman.bim.server.util.EntityUtil;

/** @author Roman 03.02.13 12:35
 *
 * 4!!!!!!!!!!!
 * */
public class CheckUserRatingsConsistencyTask implements SystemTask {

    @Override
    public int executeTask(int num, String params, SystemTaskRequest req) {

        int wordsCount = EntityUtil.getCount(WordDao.ENT_NAME);
        int ratingsCount = EntityUtil.getCount(UserRatingDao.ENT_NAME);
        int usersCount = EntityUtil.getCount(UserSettingsDao.ENT_NAME);
        if (ratingsCount == usersCount * wordsCount) {
            return 1;
        }
        throw new IllegalStateException(String.format(
                "Wrong db state wordsCount : %s, usersCount : %s, ratingsCount : %s",
                wordsCount, usersCount, ratingsCount));
    }
}
