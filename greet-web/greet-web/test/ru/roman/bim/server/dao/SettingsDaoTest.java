package ru.roman.bim.server.dao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

/** @author Roman 21.01.13 23:57 */
public class SettingsDaoTest {
    private static final Log log = LogFactory.getLog(SettingsDaoTest.class);


    @Test
    public void simplyTry() {
        log.info("UserSettingsModel properties : " + UserSettingsDao.getProps());

    }
}
