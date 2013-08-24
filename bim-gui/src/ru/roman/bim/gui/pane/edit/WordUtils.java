package ru.roman.bim.gui.pane.edit;

import org.apache.commons.lang3.StringUtils;
import ru.roman.bim.model.WordType;
import ru.roman.bim.service.gae.wsclient.BimItemModel;

/** @author Roman 20.01.13 23:38 */
public abstract class WordUtils {


    public static final String STRIP_CHARS = " .,-;:`'\"";

    public static void checkIdiom(BimItemModel model) {
        String facedText = StringUtils.strip(model.getTextFaced(), STRIP_CHARS);
        if (StringUtils.endsWithIgnoreCase(facedText, "(идиома)")) {
            facedText = StringUtils.removeEndIgnoreCase(facedText, "(идиома)");
            facedText = StringUtils.strip(facedText, STRIP_CHARS);
            model.setTextFaced(facedText);
            model.setType(WordType.IDIOM.getOrdinal());
        }
    }

    public static void fillTexts(BimItemModel currModel, String faced, String shadowed) {
        faced = StringUtils.normalizeSpace(faced);
        faced = StringUtils.strip(faced, STRIP_CHARS);

        shadowed = StringUtils.normalizeSpace(shadowed);
        shadowed = StringUtils.strip(shadowed, STRIP_CHARS);

        currModel.setTextFaced(faced);
        currModel.setTextShadowed(shadowed);
    }
}
