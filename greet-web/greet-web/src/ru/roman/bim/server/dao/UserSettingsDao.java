package ru.roman.bim.server.dao;

import com.google.appengine.api.datastore.Entity;
import ru.roman.bim.server.service.data.dto.settings.UserSettingsModel;

import java.util.Arrays;
import java.util.logging.Logger;

import static ru.roman.bim.server.util.EntityUtil.*;

/** @author Roman 21.01.13 23:49 */
public class UserSettingsDao {
    private static final Logger log = Logger.getLogger(UserSettingsDao.class.getName());
    public static final String ENT_NAME = "UserSettings";
    private static final String SORTING_DIRECTION = "sortingDirection";
    private static final String SORTING_FIELD = "sortingField";
    private static final String SHADOWED_LANG_ID = "shadowedLangId";
    private static final String SUBSCRIBED = "subscribed";
    private static final String PASSWORD = "password";
    private static final String CURRENT_NUM = "currentNum";
    private static final String CACHE_MAX_SIZE = "cacheMaxSize";
    private static final String FACED_LANG_ID = "facedLangId";
    private static final String LOOK_AND_FEEL = "lookAndFeel";
    private static final String PREVIEW_DURATION = "previewDuration";
    private static final String OPACITY = "opacity";
    private static final String LOGIN = "login";
    private static final String PREVIEW_INTERVAL = "previewInterval";
    private static final String RATINGS = "ratings";
    private static final String RECORDS_COUNT = "recordsCount";
    private static final String PORTION = "portion";


    public static UserSettingsModel storeSettings(UserSettingsModel model) {

        Entity sett = findFirstEntity(ENT_NAME, LOGIN, model);
        if (sett == null) {
            sett = new Entity(ENT_NAME);
            setProperty(sett, LOGIN, model);
            setProperty(sett, PASSWORD, model);
        } else {
            if (!equalsProperty(sett, model, PASSWORD)) {
                throw new RuntimeException("Wrong login or password");
            }
        }
        setPropertyIfNull(model, SORTING_FIELD, "editDate");
        setPropertyIfNull(model, SORTING_DIRECTION, "DESCENDING");
        setPropertyIfNull(model, SHADOWED_LANG_ID, 2L);
        setPropertyIfNull(model, SUBSCRIBED, Arrays.asList(1L));
        setPropertyIfNull(model, CURRENT_NUM, 0L);
        setPropertyIfNull(model, CACHE_MAX_SIZE, 100L);
        setPropertyIfNull(model, FACED_LANG_ID, 1L);
        setPropertyIfNull(model, LOOK_AND_FEEL, "");
        setPropertyIfNull(model, PREVIEW_DURATION, 0L);
        setPropertyIfNull(model, OPACITY, 0.75);
        setPropertyIfNull(model, PREVIEW_INTERVAL, 0L);
        setPropertyIfNull(model, RATINGS, Arrays.asList(1, 2, 3));
        setPropertyIfNull(model, RECORDS_COUNT, 0L);
        setPropertyIfNull(model, PORTION, 100L);

        setProperty(sett, SORTING_FIELD, model);
        setProperty(sett, SORTING_DIRECTION, model);
        setProperty(sett, SHADOWED_LANG_ID, model);
        setProperty(sett, SUBSCRIBED, model);
        setProperty(sett, CURRENT_NUM, model);
        setProperty(sett, CACHE_MAX_SIZE, model);
        setProperty(sett, FACED_LANG_ID, model);
        setProperty(sett, LOOK_AND_FEEL, model);
        setProperty(sett, PREVIEW_DURATION, model);
        setProperty(sett, OPACITY, model);
        setProperty(sett, PREVIEW_INTERVAL, model);
        setProperty(sett, RATINGS, model);
        setProperty(sett, RECORDS_COUNT, model);
        setProperty(sett, PORTION, model);

        persistEntity(sett);
        model.setId(sett.getKey().getId());
        return model;
    }
}
