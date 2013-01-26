package ru.roman.bim.service.gae;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import ru.roman.bim.gui.pane.main.MainViewModel;
import ru.roman.bim.service.gae.wsclient.*;
import ru.roman.bim.util.ExceptionHandler;
import ru.roman.bim.util.WsUtil;

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
        SaveRequest req = WsUtil.prepareRequest(new SaveRequest());
        req.setModel(model);
        Long id = provider.save(req);
        log.info(String.format("Word saved id=%s", id));
        return id;
    }

    @Override
    public GetListResp getList(GetListRequest request) {
        final GetListResp list = provider.getList(request);
        log.info(String.format("List received count : %s", list.getList().size()));
        return list;
    }

    @Override
    public void renewRating(Long id, Integer rating) {
        RenewRatingRequest req = WsUtil.prepareRequest(new RenewRatingRequest());
        req.setId(id);
        req.setRating(rating);
        provider.renewRating(req);
        log.info(String.format("rating %s renew for id=%s complete", rating, id));
    }

}
