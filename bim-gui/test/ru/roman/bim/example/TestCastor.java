package ru.roman.bim.example;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;
import org.junit.Test;
import ru.roman.bim.dev.stub.GaeConnectorStub;
import ru.roman.bim.gui.pane.main.MainViewModel;
import ru.roman.bim.service.config.XmlUtils;

import java.io.FileReader;
import java.io.FileWriter;

/** @author Roman 26.01.13 1:20 */
public class TestCastor {
    private static final Log log = LogFactory.getLog(TestCastor.class);

    @Test
    public void marshal() throws Exception {
        Object obj = GaeConnectorStub.store.iterator().next();
        Marshaller.marshal(obj, new FileWriter(XmlUtils.prepareConfig("TestCastor.xml")));
    }


    @Test
    public void unmarshal() throws Exception {
        MainViewModel res = (MainViewModel) Unmarshaller.unmarshal(MainViewModel.class,
                new FileReader(XmlUtils.prepareConfig("TestCastor.xml")));
        log.info("result : " + res);
    }

}
