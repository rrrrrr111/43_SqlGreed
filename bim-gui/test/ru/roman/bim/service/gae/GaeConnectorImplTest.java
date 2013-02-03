package ru.roman.bim.service.gae;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import ru.roman.bim.gui.pane.main.MainViewModel;
import ru.roman.bim.service.ServiceFactory;
import ru.roman.bim.service.gae.wsclient.*;
import ru.roman.bim.util.WsUtil;

import java.util.Arrays;

/** @author Roman 14.01.13 23:56 */
public class GaeConnectorImplTest {
    private static final Log log = LogFactory.getLog(GaeConnectorImplTest.class);
    private final static GaeConnector gaeConnector = ServiceFactory.getGaeConnector();


    public static void main(String... args) {

        final MainViewModel arg0 = new MainViewModel();
        arg0.setId(null);
        arg0.setFacedLangId(1L);
        arg0.setShadowedLangId(2L);
        arg0.setTextFaced("тест1");
        arg0.setTextShadowed("test1");
        arg0.setOwner(1L);
        arg0.setRating(1L);
        arg0.setType(1L);

        Long r = gaeConnector.save(arg0);
        arg0.setId(r);
        log.info("saved id : " + r);


        GetListRequest req1 = WsUtil.prepareRequest(new GetListRequest());
        req1.setCount(100);
        req1.setFacedLangId(1);
        req1.setShadowedLangId(2);
        req1.setOffset(0);
        req1.setSortingField("editDate");
        req1.setSortingDirection("DESCENDING");
        req1.getTypes().addAll(Arrays.asList(0L,1L,2L,3L,4L,5L));
        req1.getRatingsList().addAll(Arrays.asList(1,2,3,4,5));
        ru.roman.bim.service.gae.wsclient.GetListResp res = gaeConnector.getList(req1);

        log.info("items count : " + res.getList().size() + " all : " + res.getRecordsCount());

        gaeConnector.renewRating(r, 4);
        log.info("renew complete");
    }
}
