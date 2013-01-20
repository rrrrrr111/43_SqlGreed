package ru.roman.bim.gui.pane.edit;

import org.apache.commons.lang3.StringUtils;
import ru.roman.bim.model.WordType;
import ru.roman.bim.service.gae.wsclient.BimItemModel;

/** @author Roman 20.01.13 23:38 */
public abstract class WordUtils {


    public static void checkIdiom(BimItemModel model) {
        if (StringUtils.containsIgnoreCase(model.getTextFaced(), "(идиома)")) {
            String facedText = StringUtils.removeEndIgnoreCase(model.getTextFaced(), "(идиома)");
            facedText = StringUtils.strip(facedText, " .,");
            model.setTextFaced(facedText);
            model.setType(WordType.IDIOM.getOrdinal());
        }
    }

    public static void fillTexts(BimItemModel currModel, String faced, String shadowed) {
        faced = StringUtils.normalizeSpace(faced);
        faced = StringUtils.strip(faced, " .,");

        shadowed = StringUtils.normalizeSpace(shadowed);
        shadowed = StringUtils.strip(shadowed, " .,");

        currModel.setTextFaced(faced);
        currModel.setTextShadowed(shadowed);
    }
}
