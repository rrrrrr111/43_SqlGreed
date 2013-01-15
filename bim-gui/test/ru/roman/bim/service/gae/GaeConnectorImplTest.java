package ru.roman.bim.service.gae;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import ru.roman.bim.service.gae.wsclient.BimItemModel;
import ru.roman.bim.service.gae.wsclient.DataProvider;
import ru.roman.bim.service.gae.wsclient.DataProvider_Service;
import ru.roman.bim.util.Const;

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

        Long r = provider.save(arg0);
        log.info("saved id : " + r);


        ru.roman.bim.service.gae.wsclient.GaeGetListRequest req = new ru.roman.bim.service.gae.wsclient.GaeGetListRequest();
        req.setCount(100);
        req.setLangId(1);
        req.setOffset(0);
        req.setSortingField(Const.DEFAULT_SORTING_FIELD);
        req.getTypes().add(1);
        ru.roman.bim.service.gae.wsclient.GaeGetListResponse res = provider.getList(req);

        log.info("items count : " + res.getList().size() + " all : " + res.getRecordsCount());

        provider.renewRating(3333L, 5);
        log.info("renew complete");
    }
}
