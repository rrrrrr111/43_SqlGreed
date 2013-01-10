package ru.roman.bim.service.gae;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import ru.roman.bim.gui.pane.main.MainViewModel;
import ru.roman.bim.service.gae.wsclient.*;
import ru.roman.bim.util.Const;
import ru.roman.bim.util.ExceptionHandler;

/** @author Roman 22.12.12 15:36 */
public class GaeConnectorImpl implements GaeConnector {

    private static final Log log = LogFactory.getLog(GaeConnectorImpl.class);
    private static DataProvider provider;

    public GaeConnectorImpl() {
        try {
            provider = new DataProvider_Service().getDataProviderPort();
        } catch (Exception e) {
            ExceptionHandler.showErrorMessageAndExit(e);
        }
    }

    @Override
    public Long save(MainViewModel model) {
        Long id = provider.save(model);
        log.info(String.format("Word saved id=%s", id));
        return id;
    }

    @Override
    public GaeGetListResponse getList(GaeGetListRequest request) {
        final GaeGetListResponse list = provider.getList(request);
        log.info(String.format("List received count : %s", list.getList().size()));
        return list;
    }

    @Override
    public void renewRating(Long id, Integer rating) {
        provider.renewRating(id, rating);
        log.info("rating renew complete");
    }


    public static void main(String... args) {

        final BimItemModel arg0 = new BimItemModel();
        arg0.setId(3333L);
        arg0.setFacedLangId(1L);
        arg0.setShadowedLangId(2L);
        arg0.setTextFaced("test1");
        arg0.setTextShadowed("тест1");
        arg0.setOwner(1L);
        arg0.setRating(1L);
        arg0.setType(BimItemType.WORD);

        Long r = provider.save(arg0);
        log.info("saved id : " + r);


        ru.roman.bim.service.gae.wsclient.GaeGetListRequest req = new ru.roman.bim.service.gae.wsclient.GaeGetListRequest();
        req.setCount(100);
        req.setLangId(1);
        req.setOffset(0);
        req.setSortingField(Const.DEFAULT_SORTING_FIELD);
        req.getTypes().add(BimItemType.WORD);
        ru.roman.bim.service.gae.wsclient.GaeGetListResponse res = provider.getList(req);

        log.info("items count : " + res.getList().size() + " all : " + res.getRecordsCount());

        provider.renewRating(3333L, 5);
        log.info("renew complete");
    }
}
