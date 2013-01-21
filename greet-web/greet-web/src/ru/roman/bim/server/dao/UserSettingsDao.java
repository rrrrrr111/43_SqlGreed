package ru.roman.bim.server.dao;

import org.apache.commons.beanutils.BeanUtilsBean;
import ru.roman.bim.server.service.data.dto.UserSettingsModel;

import java.util.Map;
import java.util.logging.Logger;

/** @author Roman 21.01.13 23:49 */
public class UserSettingsDao {
    private static final Logger log = Logger.getLogger(UserSettingsDao.class.getName());
    public static final String ENT_NAME = "UserSettings";
    private static final BeanUtilsBean bub = new BeanUtilsBean();
    private static Map props;

    static {
        try {
            props = bub.describe(new UserSettingsModel());
            props.remove("class");

        } catch (Exception e) {
            throw new RuntimeException("Exception while initialization BUB:UserSettingsModel", e);
        }
    }

    public static void save(UserSettingsModel model) {


    }

    public static void load(String login) {


    }

    public static Map getProps() {
        return props;
    }
}
