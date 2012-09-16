package ru.roman.greed.service.config.reader;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import ru.roman.greed.service.config.dto.ConnConfigModel;
import ru.roman.greed.service.datasource.dto.ReconnectionInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Roman 15.09.12 23:14
 */
public class ConnectionConfigReader implements XmlReader<ConnConfigModel> {
    @Override
    public ConnConfigModel read(Document doc) throws Exception {
        final NodeList nodeList = doc.getElementsByTagName(TAG_CONNECTION);
        final ConnConfigModel model = new ConnConfigModel();
        final List<ReconnectionInfo> res = new ArrayList<ReconnectionInfo>();
        model.setConnectionsInfo(res);
        Node node;
        NamedNodeMap attr;
        ReconnectionInfo info;
        for (int s = 0; s < nodeList.getLength(); s++) {
            info = new ReconnectionInfo();
            node = nodeList.item(s);
            attr = node.getAttributes();
            info.setAlias(node.getTextContent());
            info.setUrl(attr.getNamedItem(ATTR_URL).getTextContent());
            info.setDriverClazz(attr.getNamedItem(ATTR_CLASS).getTextContent());
            info.setUsername(attr.getNamedItem(ATTR_USER).getTextContent());
            info.setPassword(attr.getNamedItem(ATTR_PASSW).getTextContent());
            info.setAutoCommit(BooleanUtils.toBooleanObject(attr.getNamedItem(ATTR_AUTOCOMMIT).getTextContent()));
            res.add(info);
        }
        String defaultConn = doc.getElementsByTagName(TAG_DEFAULT_CONNECTION).item(0).getTextContent();
        if (StringUtils.isNumeric(defaultConn)) {
            model.setDefaultConnectionNum(Integer.valueOf(defaultConn));
        }
        return model;
    }
}
