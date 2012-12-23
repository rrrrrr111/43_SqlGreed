package ru.roman.bim.dev.dummy;

import ru.roman.bim.service.gae.dto.TypeModel;
import ru.roman.bim.gui.pane.main.MainViewModel;

/** @author Roman 22.12.12 13:41 */
public class TestHelper {



    public static MainViewModel createMainViewModel() {
        MainViewModel model = new MainViewModel();
        model.setRating(1);
        model.setTextFaced("hello it's a question... sd fs sdfasdfas sd fsdfssd safsdf sdfds s fasd sd f sdfsdf sdfsdf sfsfsdf sf sf  sdfsd sdf sdf s dfsf");
        model.setTextShadowed("         Это типа перевод...");
        model.setType(TypeModel.WORD);
        return model;
    }
}
