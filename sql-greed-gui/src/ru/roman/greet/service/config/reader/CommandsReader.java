package ru.roman.greet.service.config.reader;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import ru.roman.greet.service.config.dto.CommandsModel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Roman 15.09.12 23:16
 */
public class CommandsReader implements XmlReader<CommandsModel> {
    @Override
    public CommandsModel read(Document doc) throws Exception {
        final NodeList nodeList = doc.getElementsByTagName(TAG_CONNECTION);

        final CommandsModel model = new CommandsModel();
        final List<String> list = new ArrayList<String>();
        model.setCommandList(list);
        for (int s = 0; s < nodeList.getLength(); s++) {
            list.add(nodeList.item(s).getTextContent());
        }
        model.setCommandsCount(doc.getElementsByTagName(TAG_DEFAULT_CONNECTION).item(0).getTextContent());

        return model;
    }
}
