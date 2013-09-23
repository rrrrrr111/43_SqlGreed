package ru.roman.bim.server.dss.dao;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import ru.roman.bim.server.service.dataws.dto.RequestInfo;
import ru.roman.bim.server.service.dataws.dto.settings.UserSettingsModel;
import ru.roman.bim.server.util.EntityUtil;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import static ru.roman.bim.server.util.EntityUtil.findFirstEntityByBean;
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
    public static final String EDIT_DATE = "editDate";
    public static final String LAST_ACCESS = "lastAccess";
    public static final String REGISTRATION_DATE = "registrationDate";
    public static final String DISABILITY_DURATION = "disabilityDuration";

    public static final List<String> EXCLUDE_FOR_ENTITY = Arrays.asList("id", "key");
    public static final List<String> EXCLUDE_FOR_BEAN = Arrays.asList("id", "key",
                                       LAST_ACCESS, REGISTRATION_DATE);

    public static final String MASTER_USER = "curdes@gmail.com";
    public static final long MASTER_USER_ID = 81001L;


    public static UserSettingsModel registerNewAndLoadSettings(UserSettingsModel model) {

        Entity sett = findFirstEntityByBean(ENT_NAME, LOGIN, model);
        final Date currDate = new Date();
        if (sett == null) { // новый пользователь
            if (model.getLogin().equals(MASTER_USER)) {
                model.setId(MASTER_USER_ID);
                sett = new Entity(ENT_NAME, MASTER_USER_ID);
            } else {
                sett = new Entity(ENT_NAME);
            }
            sett.setProperty(LOGIN, model.getLogin());
            sett.setProperty(PASSWORD, model.getPassword());
            sett.setProperty(REGISTRATION_DATE, currDate);
            persistEntity(sett);
            storeSettings(model);
            UserRatingDao.createNewUserRatings(sett);
        } else {
            checkPassword(model, sett);
        }

        setEntPropertyIfNull(sett, SORTING_FIELD, WordDao.EDIT_DATE);
        setEntPropertyIfNull(sett, SORTING_DIRECTION, "DESCENDING");
        setEntPropertyIfNull(sett, SHADOWED_LANG_ID, 1L);
        setEntPropertyIfNull(sett, CURRENT_NUM, 0L);
        setEntPropertyIfNull(sett, CACHE_MAX_SIZE, 100L);
        setEntPropertyIfNull(sett, FACED_LANG_ID, 2L);
        setEntPropertyIfNull(sett, LOOK_AND_FEEL, "");
        setEntPropertyIfNull(sett, PREVIEW_DURATION, 0.3);
        setEntPropertyIfNull(sett, OPACITY, 0.75);
        setEntPropertyIfNull(sett, PREVIEW_INTERVAL, 5.0);
        setEntPropertyIfNull(sett, RATINGS, Arrays.asList(1, 2, 3));
        setEntPropertyIfNull(sett, RECORDS_COUNT, 0L);
        setEntPropertyIfNull(sett, PORTION, 1000L);
        setEntPropertyIfNull(sett, WORK_WITH_PORTION, true);
        setEntPropertyIfNull(sett, TYPES, Arrays.asList(0, 1, 2, 3));
        setEntPropertyIfNull(sett, CATEGORIES, Arrays.asList(0, 1));
        setEntPropertyIfNull(sett, SUBSCRIBED, Arrays.asList(MASTER_USER_ID));
        setEntPropertyIfNull(sett, DISABILITY_DURATION, 60.0);
        setEntPropertyIfNull(sett, REGISTRATION_DATE, currDate);
        sett.setProperty(LAST_ACCESS, currDate);

        persistEntity(sett);
        setAllProperties(model, sett, EXCLUDE_FOR_BEAN);
        model.setId(sett.getKey().getId());
        return model;

    }


    public static void storeSettings(UserSettingsModel model) {
        final Entity sett = findFirstEntityByBean(ENT_NAME, LOGIN, model);
        if (sett == null) {
            throw new RuntimeException(String.format("User %s doesn't registered", model.getLogin()));
        } else {
            checkPassword(model, sett);
            copyEntProperties(sett, model, EXCLUDE_FOR_ENTITY);
            final Date currDate = new Date();
            sett.setProperty(EDIT_DATE, currDate);
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
        Entity masterUser = EntityUtil.findFirstEntityByValue(UserSettingsDao.ENT_NAME, UserSettingsDao.LOGIN, MASTER_USER);
        if (masterUser == null) {
            throw new RuntimeException("Master User not found");
        }
        return masterUser;
    }

    public static Entity checkUserAuthority(RequestInfo info) {

        return null;
    }
}

