package ru.roman.bim.service.config;

import org.exolab.castor.mapping.Mapping;
import ru.roman.bim.service.ServiceFactory;
import ru.roman.bim.service.config.reader.CastorReader;
import ru.roman.bim.service.config.writer.CastorWriter;
import ru.roman.bim.service.config.writer.XmlWriter;

import java.net.URL;

/** @author Roman 26.01.13 0:28 */
public class CastorConfigServiceImpl implements ConfigService {

    private XmlConfigService xmlConfigService = ServiceFactory.getXmlConfigService();
    private static final XmlWriter WRITER = new CastorWriter();

    @Override
    public <T> T loadConfig(String fileName, Class<T> clazz) {
        return (T)xmlConfigService.loadConfig(fileName, new CastorReader(clazz));
    }

    @Override
    public <T> T loadEncryptedConfig(String fileName, Class<T> clazz) {
        return (T)xmlConfigService.loadEncryptedConfig(fileName, new CastorReader(clazz));
    }

    @Override
    public <T> void saveConfig(T model, String fileName) {
        xmlConfigService.saveConfig(model, fileName, WRITER);
    }

    @Override
    public <T> void saveEncryptedConfig(T model, String fileName) {
        xmlConfigService.saveEncryptedConfig(model, fileName, WRITER);
    }

    public static Mapping mapping;
    public static synchronized Mapping getMapping() {
        try {
            if (mapping == null) {
                final URL url = CastorWriter.class.getResource("/CastorMappings.xml");
                mapping = new Mapping();
                mapping.loadMapping(url);
            }
            return mapping;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
