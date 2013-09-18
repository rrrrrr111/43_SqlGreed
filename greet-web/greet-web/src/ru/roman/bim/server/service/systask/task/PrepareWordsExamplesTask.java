package ru.roman.bim.server.service.systask.task;

import com.google.appengine.api.datastore.Entity;
import ru.roman.bim.server.dss.dao.UserSettingsDao;
import ru.roman.bim.server.dss.dao.WordDao;
import ru.roman.bim.server.service.dataws.dto.system.SystemTaskRequest;
import ru.roman.bim.server.service.dataws.dto.word.BimItemModel;

import java.util.Date;

import static ru.roman.bim.server.dss.dao.WordDao.*;
import static ru.roman.bim.server.util.EntityUtil.persistEntity;

/** @author Roman 03.02.13 12:26 */
public class PrepareWordsExamplesTask implements SystemTask {
    @Override
    public int executeTask(int num, String params, SystemTaskRequest req) {
        final int[] i = {0};
        final Date currDate = new Date();

        BimItemModel word = new BimItemModel();
        word.setTextFaced("hello");
        word.setTextShadowed(null);
        word.setType(0L);
        word.setCategory(0L);
        word.setFacedLangId(2L);
        word.setShadowedLangId(1L);
        word.setOwner(UserSettingsDao.MASTER_USER_ID);
        word.setRating(3L);
        word.setEditDate(currDate);
        WordDao.createOrUpdate(word);
        ++i[0];

        word = new BimItemModel();
        word.setTextFaced("bye bye");
        word.setTextShadowed(null);
        word.setType(0L);
        word.setCategory(0L);
        word.setFacedLangId(2L);
        word.setShadowedLangId(1L);
        word.setOwner(UserSettingsDao.MASTER_USER_ID);
        word.setRating(3L);
        word.setEditDate(currDate);
        WordDao.createOrUpdate(word);
        ++i[0];

        word = new BimItemModel();
        word.setTextFaced("thanks");
        word.setTextShadowed(null);
        word.setType(0L);
        word.setCategory(0L);
        word.setFacedLangId(2L);
        word.setShadowedLangId(1L);
        word.setOwner(UserSettingsDao.MASTER_USER_ID);
        word.setRating(3L);
        word.setEditDate(currDate);
        WordDao.createOrUpdate(word);
        ++i[0];

        return i[0];
    }
}
