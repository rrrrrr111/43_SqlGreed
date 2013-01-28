package ru.roman.bim.dev.stub;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import ru.roman.bim.gui.pane.main.MainViewModel;
import ru.roman.bim.model.WordCategory;
import ru.roman.bim.model.WordType;
import ru.roman.bim.service.gae.GaeConnector;
import ru.roman.bim.service.gae.wsclient.GetListRequest;
import ru.roman.bim.service.gae.wsclient.GetListResp;
import ru.roman.bim.service.gae.wsclient.UserSettingsModel;
import ru.roman.bim.util.WsUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeSet;

/** @author Roman 22.12.12 15:43 */
public class GaeConnectorStub implements GaeConnector {
    private static final Log log = LogFactory.getLog(GaeConnectorStub.class);

    public static TreeSet<MainViewModel> store = new TreeSet<MainViewModel>();
    public static List<UserSettingsModel> settings = new ArrayList<UserSettingsModel>();
    static long counter;
    public static final int SERVICE_TIMEOUT = 1000;

    static {
        Date currDate = new Date();
        store.add(new MainViewModel(counter++, "1 word1","transl1",1l,2l,1l, WordType.EXPRESSION.getOrdinal()
                , WordCategory.COMMON.getOrdinal(), null, 1l, WsUtil.asXMLGregorianCalendar(currDate)));
        store.add(new MainViewModel(counter++, "2 Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
                "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
                1l,2l,1l, WordType.EXPRESSION.getOrdinal(),
                WordCategory.COMMON.getOrdinal(), null, 1l, WsUtil.asXMLGregorianCalendar(currDate)));
        store.add(new MainViewModel(counter++, "3 word3","transl3",1l,2l,4l, WordType.EXPRESSION.getOrdinal()
                , WordCategory.COMMON.getOrdinal(), null, 1l, WsUtil.asXMLGregorianCalendar(currDate)));
        store.add(new MainViewModel(counter++, "4 word4","transl4",1l,2l,3l, WordType.WORD.getOrdinal()
                , WordCategory.COMMON.getOrdinal(), null, 1l, WsUtil.asXMLGregorianCalendar(currDate)));
        store.add(new MainViewModel(counter++, "5 word5","transl5",1l,2l,4l, WordType.IDIOM.getOrdinal()
                , WordCategory.COMMON.getOrdinal(), null, 1l, WsUtil.asXMLGregorianCalendar(currDate)));
        store.add(new MainViewModel(counter++, "6 word6","transl6",1l,2l,2l, WordType.WORD.getOrdinal()
                , WordCategory.COMMON.getOrdinal(), null, 1l, WsUtil.asXMLGregorianCalendar(currDate)));
        store.add(new MainViewModel(counter++, "7 word7","transl7",1l,2l,5l, WordType.WORD.getOrdinal()
                , WordCategory.COMMON.getOrdinal(), null, 1l, WsUtil.asXMLGregorianCalendar(currDate)));
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
        log.info("Stub save : " + ToStringBuilder.reflectionToString(model));
        sleep();
        return model.getId();

    }

    @Override
    public GetListResp getList(GetListRequest request) {
        List<MainViewModel> list = new ArrayList<MainViewModel>(store);
        final MainViewModel from = list.get(request.getOffset());
        int toIdx = request.getOffset() + request.getCount() - 1;
        if (toIdx >= list.size()) {
            toIdx = list.size() - 1;
        }
        final MainViewModel to = list.get(toIdx);

        GetListResp resp = new GetListResp();
        resp.getList().addAll(store.subSet(from, true, to, true));
        resp.setRecordsCount(store.size());
        log.info(String.format("Stub getList : %s, return : %s models",
                ToStringBuilder.reflectionToString(request), resp.getList().size()));
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
        log.info(String.format("Stub renewRating id=%s, rating=%s", id, rating));
        sleep();
    }

    @Override
    public void storeSettings(UserSettingsModel model) {
        registerNewAndLoadSettings(model);
    }

    @Override
    public UserSettingsModel registerNewAndLoadSettings(UserSettingsModel model) {
        UserSettingsModel stored = null;
        for (UserSettingsModel setting : settings) {
            if (setting.getLogin().equalsIgnoreCase(model.getLogin())) {
                stored = setting;
                break;
            }
        }
        if (stored != null) {
            if (!stored.getPassword().equals(model.getPassword())) {
                throw new RuntimeException("password or login wrong");
            }
            settings.remove(stored);
        }
        settings.add(model);
        sleep();
        return model;
    }


    private void sleep() {
        try {
            Thread.sleep(SERVICE_TIMEOUT);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
