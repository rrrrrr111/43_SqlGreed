package ru.roman.bim.server.dss.dao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import ru.roman.bim.server.service.dataws.dto.settings.UserSettingsModel;
import ru.roman.bim.server.util.PropUtil;

/** @author Roman 21.01.13 23:57 */
public class SettingsDaoTest {
    private static final Log log = LogFactory.getLog(SettingsDaoTest.class);


    @Test
    public void simplyTry() {
        log.info("UserSettingsModel properties : " + PropUtil.describe(new UserSettingsModel()));

    }
}
