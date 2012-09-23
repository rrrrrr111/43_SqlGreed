package ru.roman.greet.service.config.writer;

import org.xml.sax.helpers.AttributesImpl;
import ru.roman.greet.service.config.dto.CommandsModel;

import javax.xml.transform.sax.TransformerHandler;
import java.util.List;

/**
 * @author Roman 15.09.12 23:16
 */
public class CommandsWriter implements XmlWriter<CommandsModel> {
    @Override
    public void write(TransformerHandler hd, CommandsModel model) throws Exception {
        AttributesImpl atts = new AttributesImpl();
        hd.startElement("", "", TAG_ROOT, atts);

        final List<String> list = model.getCommandList();
        String comm;
        for (int i = 0; i < list.size(); i++) {
            hd.startElement("", "", TAG_CONNECTION, atts);
            comm = list.get(i);
            hd.characters(comm.toCharArray(), 0, comm.length());
            hd.endElement("", "", TAG_CONNECTION);
        }
        atts.clear();
        hd.startElement("", "", TAG_DEFAULT_CONNECTION, atts);
        hd.characters(model.getCommandsCount().toCharArray(), 0, model.getCommandsCount().length());
        hd.endElement("", "", TAG_DEFAULT_CONNECTION);

        hd.endElement("", "", TAG_ROOT);
    }
}
