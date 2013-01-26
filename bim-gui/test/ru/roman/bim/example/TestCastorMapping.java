package ru.roman.bim.example;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.exolab.castor.mapping.Mapping;
import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;
import org.exolab.castor.xml.XMLContext;
import org.junit.Test;
import ru.roman.bim.dev.stub.GaeConnectorStub;
import ru.roman.bim.gui.pane.main.MainViewModel;
import ru.roman.bim.service.config.XmlUtils;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.Writer;
import java.net.URL;

/** @author Roman 26.01.13 1:20 */
public class TestCastorMapping {
    private static final Log log = LogFactory.getLog(TestCastorMapping.class);

    static Mapping mapping = new Mapping();
    static {
        try {
            URL url = TestCastorMapping.class.getResource("/CastorMappings.xml");
            mapping.loadMapping(url);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void marshal() throws Exception {
        Object obj = GaeConnectorStub.store.iterator().next();

        Writer writer = new FileWriter(XmlUtils.prepareConfig("TestCastorMapping.xml"));

        XMLContext context = new XMLContext();
        context.addMapping(mapping);

        Marshaller marshaller = context.createMarshaller();
        marshaller.setWriter(writer);
        marshaller.marshal(obj);
    }


    @Test
    public void unmarshal() throws Exception {

        XMLContext context = new XMLContext();
        context.addMapping(mapping);

        FileReader reader = new FileReader(XmlUtils.prepareConfig("TestCastorMapping.xml"));

        Unmarshaller unmarshaller = context.createUnmarshaller();
        unmarshaller.setClass(MainViewModel.class);

        MainViewModel res = (MainViewModel) unmarshaller.unmarshal(reader);
        log.info("result : " + res);
    }

}
