package ru.roman.bim.gui.pane.edit;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import ru.roman.bim.gui.common.Controller;
import ru.roman.bim.model.WordCategory;
import ru.roman.bim.model.WordType;
import ru.roman.bim.service.ServiceFactory;
import ru.roman.bim.service.cache.LocalCache;
import ru.roman.bim.service.cache.LocalCacheFactory;
import ru.roman.bim.service.gae.GaeConnector;
import ru.roman.bim.service.translate.TranslationService;
import ru.roman.bim.util.Const;
import ru.roman.bim.util.GuiUtils;

import javax.swing.*;
import java.util.Arrays;
import java.util.Collection;

/** @author Roman 21.12.12 0:24 */
public class EditViewController extends Controller<EditView, EditViewModel> {
    private static final Log log = LogFactory.getLog(EditViewController.class);

    private final GaeConnector gaeConnector = ServiceFactory.getGaeConnector();
    private final TranslationService yaTranslator = ServiceFactory.getYandexService();
    private LocalCache localCache;
    private EditViewModel originalModel;

    public EditViewController(EditView view) {
        super(view);
    }

    protected void onInit() {

    }

    protected synchronized void onPrev() {
        currModel = new EditViewModel(localCache.getPrev());
        view.setValues(currModel);
        originalModel = currModel.clone();
    }

    protected synchronized void onNext() {
        currModel = new EditViewModel(localCache.getNext());
        view.setValues(currModel);
        originalModel = currModel.clone();
    }

    public synchronized void onNew() {
        EditViewModel old = currModel;
        currModel = new EditViewModel();
        currModel.setFacedLangId(old.getFacedLangId());
        currModel.setShadowedLangId(old.getShadowedLangId());
        currModel.setRating(Const.DEFAULT_RATING);
        currModel.setType(old.getType());
        currModel.setOwner(Const.DEFAULT_OWNER_ID);
        view.setValues(currModel);
    }

    protected synchronized void onSave() {

        view.fillModel(currModel);
        if (StringUtils.isBlank(currModel.getTextFaced()) ||
                StringUtils.isBlank(currModel.getTextShadowed())) {
            GuiUtils.showInfoMessage("Cue word and the translation can not be empty");
        } else {

            WordUtils.checkIdiom(currModel);

            if (currModel.getId() != null &&
                    !currModel.getTextFaced().equals(originalModel.getTextFaced()) &&
                    !currModel.getTextShadowed().equals(originalModel.getTextShadowed())) {
                //Custom button text
                Object[] options = {"Yes, override", "No, create new", "Cancel"};
                int n = JOptionPane.showOptionDialog(view,
                        "Would you like to override old value \"" + originalModel.getTextFaced() + "\"?",
                        "Sorry, one question",
                        JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        options,
                        options[2]);

                switch (n) {
                    case 1:
                        currModel.setId(null);
                        break;
                    case 2:
                        return;
                }
            }
            Long id = gaeConnector.save(currModel);
            currModel.setId(id);
            localCache.renewModel(currModel);
            view.setValues(currModel);
            originalModel = currModel.clone();
        }
    }

    protected void onClose() {
        view.setVisible(false);
    }

    public synchronized void show(LocalCache cache) {
        this.localCache = LocalCacheFactory.createLocalCacheInstance(cache);
        currModel = new EditViewModel(localCache.getCurrent());
        view.setValues(currModel);
        originalModel = currModel.clone();
        view.setVisible(true);
    }

    public Collection<WordType> getTypes() {
        return Arrays.asList(WordType.values());
    }


    public void onTranslateFacedYandex() {
        view.fillTexts(currModel);
        String tr = yaTranslator.translate(currModel.getTextFaced(),
                currModel.getFacedLangId(), currModel.getShadowedLangId());
        currModel.setTextShadowed(tr);
        view.setTexts(currModel);
    }

    public void onTranslateTranslationYandex() {
        view.fillTexts(currModel);
        String tr = yaTranslator.translate(currModel.getTextShadowed(),
                currModel.getShadowedLangId(), currModel.getFacedLangId());
        currModel.setTextFaced(tr);
        view.setTexts(currModel);
    }

    public Collection<WordCategory> getCategories() {
        return Arrays.asList(WordCategory.values());
    }
}
