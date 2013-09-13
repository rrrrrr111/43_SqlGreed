package ru.roman.bim.server.service.systask.task;

import com.google.appengine.api.datastore.Entity;
import ru.roman.bim.server.service.dataws.dto.system.SystemTaskRequest;

import java.util.Date;

import static ru.roman.bim.server.dss.dao.WordDao.*;
import static ru.roman.bim.server.util.EntityUtil.persistEntity;

/** @author Roman 03.02.13 12:26 */
public class PrepareWordsExamplesTask implements SystemTask {
    @Override
    public int executeTask(int num, String params, SystemTaskRequest req) {
        final int[] i = {0};
        final Date currDate = new Date();

        Entity word = new Entity(ENT_NAME);
        word.setProperty(TEXT_FACED, "привет");
        word.setProperty(TEXT_SHADOWED, "hello");
        word.setProperty(TYPE, 0);
        word.setProperty(CATEGORY, 0);
        word.setProperty(FACED_LANG_ID, 2);
        word.setProperty(SHADOWED_LANG_ID, 1);
        word.setProperty(OWNER, 81001);
        word.setProperty(RATING, 3);
        word.setProperty(EDIT_DATE, currDate);
        //word.setProperty(USER_RATING, Collections.EMPTY_LIST);
        persistEntity(word);
        ++i[0];

        word = new Entity(ENT_NAME);
        word.setProperty(TEXT_FACED, "пока");
        word.setProperty(TEXT_SHADOWED, "bye bye");
        word.setProperty(TYPE, 0);
        word.setProperty(CATEGORY, 0);
        word.setProperty(FACED_LANG_ID, 2);
        word.setProperty(SHADOWED_LANG_ID, 1);
        word.setProperty(OWNER, 81001);
        word.setProperty(RATING, 3);
        word.setProperty(EDIT_DATE, currDate);
        //word.setProperty(USER_RATING, Collections.EMPTY_LIST);
        persistEntity(word);
        ++i[0];

        word = new Entity(ENT_NAME);
        word.setProperty(TEXT_FACED, "спасибо");
        word.setProperty(TEXT_SHADOWED, "thanks");
        word.setProperty(TYPE, 0);
        word.setProperty(CATEGORY, 0);
        word.setProperty(FACED_LANG_ID, 2);
        word.setProperty(SHADOWED_LANG_ID, 1);
        word.setProperty(OWNER, 81001);
        word.setProperty(RATING, 3);
        word.setProperty(EDIT_DATE, currDate);
        //word.setProperty(USER_RATING, Collections.EMPTY_LIST);
        persistEntity(word);
        ++i[0];

        return i[0];
    }
}
