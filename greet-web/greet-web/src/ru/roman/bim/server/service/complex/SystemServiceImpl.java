package ru.roman.bim.server.service.complex;

import com.google.appengine.api.datastore.Entity;
import ru.roman.bim.server.dao.WordDao;
import ru.roman.bim.server.service.data.dto.system.SystemTaskRequest;
import ru.roman.bim.server.service.data.dto.system.SystemTaskResp;

import java.util.List;

import static ru.roman.bim.server.util.EntityUtil.getAll;
import static ru.roman.bim.server.util.EntityUtil.persistEntity;

/** @author Roman 29.01.13 23:08 */
public class SystemServiceImpl implements SystemService{


    @Override
    public SystemTaskResp systemTask(SystemTaskRequest req) {

        int i = 0;
        switch (req.getTaskNum()) {
            case 1:
                List<Entity> list = getAll(WordDao.ENT_NAME);
                for (Entity entity : list) {
                    final Long type = (Long) entity.getProperty(WordDao.TYPE);
                    final Long category = (Long) entity.getProperty(WordDao.CATEGORY);
                    if (type == 3L || category == 1L) {
                        entity.setProperty(WordDao.RATING, 2L);
                    } else if (type == 2L) {
                        entity.setProperty(WordDao.RATING, 4L);
                    } else {
                        entity.setProperty(WordDao.RATING, 3L);
                    }
                    persistEntity(entity);
                    i++;
                }
                break;
        }
        final SystemTaskResp resp = new SystemTaskResp();
        resp.setResultCount(i);
        return resp;
    }
}
