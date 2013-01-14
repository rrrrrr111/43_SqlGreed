package ru.roman.bim.service.gae;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import ru.roman.bim.gui.pane.main.MainViewModel;
import ru.roman.bim.service.gae.wsclient.DataProvider;
import ru.roman.bim.service.gae.wsclient.DataProvider_Service;
import ru.roman.bim.service.gae.wsclient.GaeGetListRequest;
import ru.roman.bim.service.gae.wsclient.GaeGetListResponse;
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

}
