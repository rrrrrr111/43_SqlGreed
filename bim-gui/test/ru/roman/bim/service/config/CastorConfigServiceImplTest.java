package ru.roman.bim.service.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import ru.roman.bim.dev.stub.GaeConnectorStub;
import ru.roman.bim.gui.pane.main.MainViewModel;
import ru.roman.bim.service.ServiceFactory;

/** @author Roman 26.01.13 1:02 */
public class CastorConfigServiceImplTest {
    private static final Log log = LogFactory.getLog(CastorConfigServiceImplTest.class);
    ConfigService configService = ServiceFactory.getConfigService();



    @Test
    public void testSaveConfig() throws Exception {
        configService.saveConfig(GaeConnectorStub.store.iterator().next(), "test.xml");
    }

    @Test
    public void testSaveEncryptedConfig() throws Exception {
        configService.saveEncryptedConfig(GaeConnectorStub.store.iterator().next(), "testEnc.xml");
    }

    @Test
    public void testLoadConfig() throws Exception {
        MainViewModel res = configService.loadConfig("test.xml", MainViewModel.class);
        log.info("result : " + res);
    }

    @Test
    public void testLoadEncryptedConfig() throws Exception {
        MainViewModel res = configService.loadEncryptedConfig("testEnc.xml", MainViewModel.class);
        log.info("result : " + res);
    }
}
