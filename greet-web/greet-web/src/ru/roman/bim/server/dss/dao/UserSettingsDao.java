package ru.roman.bim.server.dss.dao;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import ru.roman.bim.server.service.dataws.dto.RequestInfo;
import ru.roman.bim.server.service.dataws.dto.settings.UserSettingsModel;
import ru.roman.bim.server.util.EntityUtil;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import static ru.roman.bim.server.util.EntityUtil.findFirstEntity;
import static ru.roman.bim.server.util.EntityUtil.persistEntity;
import static ru.roman.bim.server.util.PropUtil.*;

/** @author Roman 21.01.13 23:49 */
public class UserSettingsDao {
    private static final Logger log = Logger.getLogger(UserSettingsDao.class.getName());
    public static final String ENT_NAME = "UserSettings";
    public static final String SORTING_DIRECTION = "sortingDirection";
    public static final String SORTING_FIELD = "sortingField";
    public static final String SHADOWED_LANG_ID = "shadowedLangId";
    public static final String SUBSCRIBED = "subscribed";
    public static final String PASSWORD = "password";
    public static final String CURRENT_NUM = "currentNum";
    public static final String CACHE_MAX_SIZE = "cacheMaxSize";
    public static final String FACED_LANG_ID = "facedLangId";
    public static final String LOOK_AND_FEEL = "lookAndFeel";
    public static final String PREVIEW_DURATION = "previewDuration";
    public static final String OPACITY = "opacity";
    public static final String LOGIN = "login";
    public static final String PREVIEW_INTERVAL = "previewInterval";
    public static final String RATINGS = "ratings";
    public static final String TYPES = "types";
    public static final String CATEGORIES = "categories";
    public static final String RECORDS_COUNT = "recordsCount";
    public static final String PORTION = "portion";
    public static final String WORK_WITH_PORTION = "workWithPortion";
    public static final List<String> EXCLUDE_FOR_ENTITY = Arrays.asList("id", "key");


    public static UserSettingsModel registerNewAndLoadSettings(UserSettingsModel model) {

        Entity sett = findFirstEntity(ENT_NAME, LOGIN, model);
        if (sett == null) {
            sett = new Entity(ENT_NAME);
            setEntPropertyIfNull(sett, SORTING_FIELD, "editDate");
            setEntPropertyIfNull(sett, SORTING_DIRECTION, "DESCENDING");
            setEntPropertyIfNull(sett, SHADOWED_LANG_ID, 2L);
            setEntPropertyIfNull(sett, CURRENT_NUM, 0L);
            setEntPropertyIfNull(sett, CACHE_MAX_SIZE, 100L);
            setEntPropertyIfNull(sett, FACED_LANG_ID, 1L);
            setEntPropertyIfNull(sett, LOOK_AND_FEEL, "");
            setEntPropertyIfNull(sett, PREVIEW_DURATION, 5 * 60 * 1000);
            setEntPropertyIfNull(sett, OPACITY, 0.75);
            setEntPropertyIfNull(sett, PREVIEW_INTERVAL, 30 * 1000);
            setEntPropertyIfNull(sett, RATINGS, Arrays.asList(1, 2, 3));
            setEntPropertyIfNull(sett, RECORDS_COUNT, 0L);
            setEntPropertyIfNull(sett, PORTION, 100L);
            persistEntity(sett);
            UserRatingDao.createNewUserRatings(sett);

        } else {
            checkPassword(model, sett);
        }

        boolean res1 = setEntPropertyIfNull(sett, WORK_WITH_PORTION, true);
        boolean res2 = setEntPropertyIfNull(sett, TYPES, Arrays.asList(0, 1, 2, 3));
        boolean res3 = setEntPropertyIfNull(sett, CATEGORIES, Arrays.asList(0, 1));
        boolean res4 = setEntPropertyIfNull(sett, SUBSCRIBED,
                Arrays.asList(UserSettingsDao.getMasterUser().getKey().getId()));
        if (res1 || res2 || res3 || res4) {
            persistEntity(sett);
        }
        setAllProperties(model, sett);
        model.setId(sett.getKey().getId());
        return model;

    }

    public static void storeSettings(UserSettingsModel model) {
        Entity sett = findFirstEntity(ENT_NAME, LOGIN, model);
        if (sett == null) {
            throw new RuntimeException(String.format("User %s doesn't registered", model.getLogin()));
        } else {
            checkPassword(model, sett);
            copyEntProperties(sett, model, EXCLUDE_FOR_ENTITY);
            persistEntity(sett);
        }
    }

    private static void checkPassword(UserSettingsModel model, Entity sett) {
        if (!equalsProperty(sett, model, PASSWORD)) {
            throw new RuntimeException("Wrong login or password");
        }
    }

    public static List<Key> getUsersKeys() {
        return EntityUtil.getAllKeys(ENT_NAME);
    }

    public static Entity getMasterUser() {
        Entity masterUser = EntityUtil.findFirstEntityByValue(UserSettingsDao.ENT_NAME, UserSettingsDao.LOGIN, "curdes@gmail.com");
        if (masterUser == null) {
            throw new RuntimeException("Master User not found");
        }
        return masterUser;
    }

    public static Entity checkUserAuthority(RequestInfo info) {

        return null;
    }
}

