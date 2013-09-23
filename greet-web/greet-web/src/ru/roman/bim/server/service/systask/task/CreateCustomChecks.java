package ru.roman.bim.server.service.systask.task;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import ru.roman.bim.server.dss.dao.UserSettingsDao;
import ru.roman.bim.server.dss.dao.WordDao;
import ru.roman.bim.server.service.dataws.dto.system.SystemTaskRequest;
import ru.roman.bim.server.util.EntityUtil;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;



/** @author Roman 03.02.13 12:24
 *
 * 2!!!!!!!!!!
 * */
public class CreateCustomChecks implements SystemTask {
    private static final Logger log = Logger.getLogger(CreateCustomChecks.class.getName());

    @Override
    public int executeTask(int num, String params, final SystemTaskRequest req) {

        final int[] i = {0};
        final Entity masterUser = UserSettingsDao.getMasterUser();
        final List<Key> list = EntityUtil.getAllKeys(WordDao.ENT_NAME);
        final int usersCount = EntityUtil.getCount(UserSettingsDao.ENT_NAME);

        for (Key wordKey : list) {
            List<Entity> childs = EntityUtil.getAllChildrenEntities(
                    UserSettingsDao.ENT_NAME, wordKey, false);
            if (childs.size() > usersCount) {
                throw new IllegalStateException(String.format(
                        "Wrong db state, %s count=%s, entities : %s",
                        UserSettingsDao.ENT_NAME, childs.size(), childs));
            }
            log.log(Level.INFO, "Good word : " + wordKey);

        }
        return i[0];
    }
}
