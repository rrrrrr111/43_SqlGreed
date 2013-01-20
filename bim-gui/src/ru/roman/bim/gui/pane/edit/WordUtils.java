package ru.roman.bim.gui.pane.edit;

import org.apache.commons.lang3.StringUtils;
import ru.roman.bim.model.WordType;
import ru.roman.bim.service.gae.wsclient.BimItemModel;

/** @author Roman 20.01.13 23:38 */
public abstract class WordUtils {


    public static void checkIdiom(BimItemModel model) {
        if (StringUtils.containsIgnoreCase(model.getTextFaced(), "(������)")) {
            String facedText = StringUtils.removeEndIgnoreCase(model.getTextFaced(), "(������)");
            facedText = StringUtils.strip(facedText, " .,");
            model.setTextFaced(facedText);
            model.setType(WordType.IDIOM.getOrdinal());
        }
    }
}
