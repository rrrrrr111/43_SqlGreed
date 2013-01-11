package ru.roman.bim.gui.pane.edit;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import ru.roman.bim.gui.common.Controller;
import ru.roman.bim.service.ServiceFactory;
import ru.roman.bim.service.cache.LocalCache;
import ru.roman.bim.service.cache.LocalCacheFactory;
import ru.roman.bim.service.gae.GaeConnector;
import ru.roman.bim.service.gae.wsclient.BimItemType;
import ru.roman.bim.util.Const;
import ru.roman.bim.util.GuiUtils;

import java.util.Arrays;
import java.util.Collection;

/** @author Roman 21.12.12 0:24 */
public class EditViewController extends Controller<EditView, EditViewModel> {
    private static final Log log = LogFactory.getLog(EditViewController.class);

    private final GaeConnector gaeConnector = ServiceFactory.getGaeConnector();
    private LocalCache localCache;


    public EditViewController(EditView view) {
        super(view);
    }

    protected void onInit() {

    }

    protected synchronized void onPrev() {
        currModel = new EditViewModel(localCache.getPrev());
        view.setValues(currModel);
    }

    protected synchronized void onNext() {
        currModel = new EditViewModel(localCache.getNext());
        view.setValues(currModel);
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
            Long id = gaeConnector.save(currModel);
            currModel.setId(id);
            localCache.renewModel(currModel);
        }
    }

    protected void onClose() {
        view.setVisible(false);
    }

    public synchronized void show(LocalCache cache) {
        this.localCache = LocalCacheFactory.createLocalCacheInstance(cache);
        currModel = new EditViewModel(localCache.getCurrent());
        view.setValues(currModel);
        view.setVisible(true);
    }

    public Collection<BimItemType> getTypes() {
        return Arrays.asList(BimItemType.values());
    }


}
