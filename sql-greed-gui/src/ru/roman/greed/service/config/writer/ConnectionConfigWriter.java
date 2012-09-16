package ru.roman.greed.service.config.writer;

import org.apache.commons.lang3.ObjectUtils;
import org.xml.sax.helpers.AttributesImpl;
import ru.roman.greed.service.config.dto.ConnConfigModel;
import ru.roman.greed.service.datasource.dto.ReconnectionInfo;

import javax.xml.transform.sax.TransformerHandler;
import java.util.List;

/**
 * @author Roman 15.09.12 22:59
 */
public class ConnectionConfigWriter implements XmlWriter<ConnConfigModel> {

    @Override
    public void write(TransformerHandler handler, ConnConfigModel model) throws Exception {


        final AttributesImpl attrs = new AttributesImpl();
        handler.startElement("", "", TAG_ROOT, attrs);

        final List<ReconnectionInfo> connectionsInfo = model.getConnectionsInfo();

        for (ReconnectionInfo connInfo : connectionsInfo) {
            attrs.addAttribute("", "", ATTR_URL, "CDATA", connInfo.getUrl());
            attrs.addAttribute("", "", ATTR_CLASS, "CDATA", connInfo.getDriverClazz());
            attrs.addAttribute("", "", ATTR_USER, "CDATA", connInfo.getUsername());
            attrs.addAttribute("", "", ATTR_PASSW, "CDATA", connInfo.getPassword());
            attrs.addAttribute("", "", ATTR_AUTOCOMMIT, "CDATA", ObjectUtils.toString(connInfo.getAutoCommit()));

            handler.startElement("", "", TAG_CONNECTION, attrs);
            handler.characters(connInfo.getAlias().toCharArray(), 0, connInfo.getAlias().length());
            handler.endElement("", "", TAG_CONNECTION);
        }
        attrs.clear();
        handler.startElement("", "", TAG_DEFAULT_CONNECTION, attrs);
        final String defaultAlias = ObjectUtils.toString(model.getDefaultConnectionNum());
        handler.characters(defaultAlias.toCharArray(), 0, defaultAlias.length());
        handler.endElement("", "", TAG_DEFAULT_CONNECTION);
        handler.endElement("", "", TAG_ROOT);
    }
}
