package ru.roman.bim.service.gae;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import ru.roman.bim.gui.pane.main.MainViewModel;
import ru.roman.bim.service.gae.wsclient.*;
import ru.roman.bim.util.Const;
import ru.roman.bim.util.ExceptionHandler;
import ru.roman.bim.util.WsUtil;

import javax.xml.ws.BindingProvider;
import java.net.URL;

/** @author Roman 22.12.12 15:36 */
public class GaeConnectorImpl implements GaeConnector {
    private static final Log log = LogFactory.getLog(GaeConnectorImpl.class);

    private static DataProvider provider;

    public GaeConnectorImpl() {
        try {
            provider = new DataProvider_Service(new URL(Const.DEFAULT_ENDPOINT_WSDL)).getDataProviderPort();
            BindingProvider bp = (BindingProvider)provider;
            bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, Const.DEFAULT_ENDPOINT);

            bp.getRequestContext().put(BindingProvider.USERNAME_PROPERTY, "bim");
            bp.getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, "technoBim");
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

    @Override
    public void storeSettings(UserSettingsModel model) {
        StoreSettingsRequest req = WsUtil.prepareRequest(new StoreSettingsRequest());
        req.setUserSettingsModel(model);
        StoreSettingsResp resp = provider.storeSettings(req);
        log.info(String.format("Settings stored %s", ToStringBuilder.reflectionToString(model)));
    }

    @Override
    public UserSettingsModel registerNewAndLoadSettings(UserSettingsModel model) {
        RegisterNewAndLoadSettingsRequest req = WsUtil.prepareRequest(new RegisterNewAndLoadSettingsRequest());
        req.setUserSettingsModel(model);
        RegisterNewAndLoadSettingsResp resp = provider.registerNewAndLoadSettings(req);
        log.info(String.format("Settings stored %s", ToStringBuilder.reflectionToString(resp.getUserSettingsModel())));
        return resp.getUserSettingsModel();
    }
}
