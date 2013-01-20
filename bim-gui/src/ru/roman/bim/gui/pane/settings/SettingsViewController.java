package ru.roman.bim.gui.pane.settings;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import ru.roman.bim.gui.common.Controller;
import ru.roman.bim.gui.pane.PaineFactory;
import ru.roman.bim.service.ServiceFactory;
import ru.roman.bim.service.wordLoad.WordLoaderService;
import ru.roman.bim.util.GuiUtils;

import java.io.File;


/** @author Roman 16.01.13 23:59 */
public class SettingsViewController extends Controller<SettingsView, SettingsViewModel> {
    private static final Log log = LogFactory.getLog(SettingsViewController.class);

    private final WordLoaderService wordLoaderService = ServiceFactory.getWordLoaderService();

    public SettingsViewController(SettingsView view) {
        super(view);
    }

    public void onInit() {


    }

    public void onBroseFileForLoading() {
        File fileFroLoading = PaineFactory.createFileChooser().showSelectFileDialog();
        if (fileFroLoading != null) {
            log.info("Selected file for loading : " + fileFroLoading);
            wordLoaderService.loadFile(fileFroLoading);
            GuiUtils.showInfoMessage("Loading complete");
        }
    }
}
