package ru.roman.bim.gui.pane.edit;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import ru.roman.bim.gui.common.cbchain.CallBackChain;
import ru.roman.bim.gui.common.mvc.Controller;
import ru.roman.bim.gui.pane.main.MainViewModel;
import ru.roman.bim.gui.pane.settings.Settings;
import ru.roman.bim.model.WordCategory;
import ru.roman.bim.model.WordType;
import ru.roman.bim.service.ServiceFactory;
import ru.roman.bim.service.cache.LocalCache;
import ru.roman.bim.service.cache.LocalCacheFactory;
import ru.roman.bim.service.gae.GaeConnector;
import ru.roman.bim.service.translate.TranslationService;
import ru.roman.bim.util.GuiUtil;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.Collection;

/** @author Roman 21.12.12 0:24 */
public class EditViewController extends Controller<EditView, EditViewModel> {
    private static final Log log = LogFactory.getLog(EditViewController.class);

    private State state;
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
        switch (state) {
            case FILL_NEW:
                state = State.LOADING;
                localCache.getCurrent(
                        new CallBackChain<MainViewModel>() {
                            @Override
                            public void onSuccess(MainViewModel model) {
                                showModel(model);
                            }
                        }
                );
                break;
            default:
                state = State.LOADING;
                localCache.getPrev(
                        new CallBackChain<MainViewModel>() {
                            @Override
                            public void onSuccess(MainViewModel model) {
                                showModel(model);
                            }
                        }
                );
        }
    }

    private synchronized void showModel(MainViewModel model) {
        if (state == State.LOADING) {
            currModel = new EditViewModel(model);
            view.fillWidgets(currModel);
            originalModel = currModel.clone();
            state = State.EDIT;
        }
    }

    protected synchronized void onNext() {
        state = State.LOADING;
        localCache.getNext(new CallBackChain<MainViewModel>() {
            @Override
            public void onSuccess(MainViewModel model) {
                showModel(model);
            }
        });
    }

    public synchronized void onNew() {
        final EditViewModel old = currModel;
        currModel = new EditViewModel();
        currModel.setFacedLangId(old.getFacedLangId());
        currModel.setShadowedLangId(old.getShadowedLangId());
        currModel.setRating(3L);
        currModel.setType(old.getType());
        currModel.setCategory(old.getCategory());
        currModel.setOwner(Settings.get().getId());
        view.fillWidgets(currModel);
        state = State.FILL_NEW;
    }

    protected synchronized void onSave() {

        view.fillModel(currModel);
        if (StringUtils.isBlank(currModel.getTextFaced()) ||
                StringUtils.isBlank(currModel.getTextShadowed())) {
            GuiUtil.showInfoMessage("Cue word and the translation can not be empty");
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
            state = State.LOADING;
            gaeConnector.save(currModel, new GaeConnector.GaeCallBack<Long>() {
                @Override
                protected void onSuccess(Long id) {
                    currModel.setId(id);
                    localCache.addOrRenewModel(currModel);
                    showModel(currModel);
                }
            });
        }
    }

    protected void onClose() {
        view.setVisible(false);
    }

    public synchronized void show(LocalCache cache) {
        state = State.LOADING;
        this.localCache = LocalCacheFactory.createLocalCacheInstance(cache);
        localCache.getCurrent(new CallBackChain<MainViewModel>() {
            @Override
            public void onSuccess(MainViewModel model) {
                currModel = new EditViewModel(model);
                view.fillWidgets(currModel);
                originalModel = currModel.clone();
                view.setVisible(true);
                view.setState(Frame.NORMAL);
                state = State.EDIT;
            }
        });
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
