package ru.roman.bim.service.file.normalize;

import ru.roman.bim.gui.pane.choose.FileChooser;
import ru.roman.bim.gui.pane.choose.FileChooserBuilder;

import java.io.File;

/** @author Roman 30.06.13 12:54 */
public class NormalizeServiceImpl implements NormalizeService {

    private FileChooser fc;

    @Override
    public void startNormalize() {

        if (fc == null) {
            fc = new FileChooserBuilder("Choose file to convert").createChooser();
        }

        final File firstFile = fc.showSelectFileDialog();
        if (firstFile == null) {
            return;
        }


        //GuiUtil.showInfoMessage("Second file for merging hasn't selected");


    }
}
