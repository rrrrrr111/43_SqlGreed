package ru.roman.bim.dev.stub;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import ru.roman.bim.gui.pane.main.MainViewModel;
import ru.roman.bim.service.gae.GaeConnector;
import ru.roman.bim.service.gae.wsclient.BimItemType;
import ru.roman.bim.service.gae.wsclient.GaeGetListRequest;
import ru.roman.bim.service.gae.wsclient.GaeGetListResponse;
import ru.roman.bim.util.WsUtil;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/** @author Roman 22.12.12 15:43 */
public class GaeConnectorStub implements GaeConnector {
    private static final Log log = LogFactory.getLog(GaeConnectorStub.class);

    static Set<MainViewModel> store = new HashSet<MainViewModel>();
    static long counter;
    public static final int SERVICE_TIMEOUT = 2000;

    static {
        Date currDate = new Date();
        store.add(new MainViewModel(counter++, "word1","transl1",1l,1l,1l, BimItemType.EXPRESSION, null, 1l, WsUtil.asXMLGregorianCalendar(currDate)));
        store.add(new MainViewModel(counter++, "word2","transl2",1l,1l,1l, BimItemType.EXPRESSION, null, 1l, WsUtil.asXMLGregorianCalendar(currDate)));
        store.add(new MainViewModel(counter++, "word3","transl3",1l,1l,4l, BimItemType.EXPRESSION, null, 1l, WsUtil.asXMLGregorianCalendar(currDate)));
        store.add(new MainViewModel(counter++, "word4","transl4",1l,1l,3l, BimItemType.WORD, null, 1l, WsUtil.asXMLGregorianCalendar(currDate)));
        store.add(new MainViewModel(counter++, "word5","transl5",1l,1l,4l, BimItemType.WORD, null, 1l, WsUtil.asXMLGregorianCalendar(currDate)));
        store.add(new MainViewModel(counter++, "word6","transl6",1l,1l,2l, BimItemType.WORD, null, 1l, WsUtil.asXMLGregorianCalendar(currDate)));
        store.add(new MainViewModel(counter++, "word7","transl7",1l,1l,5l, BimItemType.WORD, null, 1l, WsUtil.asXMLGregorianCalendar(currDate)));
    }


    @Override
    public Long save(MainViewModel model) {
        if (model.getId() == null) {
            model.setId(++counter);
        }
        if (store.contains(model)) {
            store.remove(model);
        }
        store.add(model);
        log.info("Stub save");
        sleep();
        return model.getId();

    }

    @Override
    public GaeGetListResponse getList(GaeGetListRequest request) {
        GaeGetListResponse resp = new GaeGetListResponse();
        resp.getList().addAll(store);
        resp.setRecordsCount(store.size());
        log.info("Stub getList");
        sleep();
        return resp;
    }

    @Override
    public void renewRating(Long id, Integer rating) {
        for (MainViewModel mainViewModel : store) {
            if (mainViewModel.getId().equals(id)) {
                mainViewModel.setRating(rating.longValue());
            }
        }
        log.info("Stub renewRating");
        sleep();
    }



    private void sleep() {
        try {
            Thread.sleep(SERVICE_TIMEOUT);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
