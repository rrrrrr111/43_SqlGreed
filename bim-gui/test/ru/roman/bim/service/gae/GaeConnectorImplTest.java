package ru.roman.bim.service.gae;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import ru.roman.bim.service.gae.wsclient.*;
import ru.roman.bim.util.WsUtil;

/** @author Roman 14.01.13 23:56 */
public class GaeConnectorImplTest {
    private static final Log log = LogFactory.getLog(GaeConnectorImplTest.class);
    private static DataProvider provider = new DataProvider_Service().getDataProviderPort();


    public static void main(String... args) {

        final BimItemModel arg0 = new BimItemModel();
        arg0.setId(3333L);
        arg0.setFacedLangId(1L);
        arg0.setShadowedLangId(2L);
        arg0.setTextFaced("test1");
        arg0.setTextShadowed("тест1");
        arg0.setOwner(1L);
        arg0.setRating(1L);
        arg0.setType(1L);

        SaveRequest req = new SaveRequest();
        req.setModel(arg0);
        Long r = provider.save(req);
        log.info("saved id : " + r);


        GetListRequest req1 = WsUtil.prepareRequest(new GetListRequest());
        req1.setCount(100);
        req1.setLangId(1);
        req1.setOffset(0);
        req1.setSortingField("editDate");
        req1.getTypes().add(1);
        ru.roman.bim.service.gae.wsclient.GetListResp res = provider.getList(req1);

        log.info("items count : " + res.getList().size() + " all : " + res.getRecordsCount());

        RenewRatingRequest req3 = WsUtil.prepareRequest(new RenewRatingRequest());
        req3.setId(1L);
        req3.setRating(4);
        provider.renewRating(req3);
        log.info("renew complete");
    }
}
