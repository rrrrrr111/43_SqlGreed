package ru.roman.bim.dev.stub;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import ru.roman.bim.service.gae.dto.TypeModel;
import ru.roman.bim.gui.pane.main.MainViewModel;
import ru.roman.bim.service.gae.GaeConnector;
import ru.roman.bim.service.gae.dto.GaeGetListRequest;
import ru.roman.bim.service.gae.dto.GaeGetListResponse;

import java.util.HashSet;
import java.util.Set;

/** @author Roman 22.12.12 15:43 */
public class GaeConnectorStub implements GaeConnector {
    private static final Log log = LogFactory.getLog(GaeConnectorStub.class);

    static Set<MainViewModel> store = new HashSet<MainViewModel>();
    static long counter;

    static {
        store.add(new MainViewModel(counter++, "word1","transl1",1,1,1, TypeModel.EXPRESSION, null));
        store.add(new MainViewModel(counter++, "word2","transl2",1,1,1, TypeModel.EXPRESSION, null));
        store.add(new MainViewModel(counter++, "word3","transl3",1,1,4, TypeModel.EXPRESSION, null));
        store.add(new MainViewModel(counter++, "word4","transl4",1,1,3, TypeModel.WORD, null));
        store.add(new MainViewModel(counter++, "word5","transl5",1,1,4, TypeModel.WORD, null));
        store.add(new MainViewModel(counter++, "word6","transl6",1,1,2, TypeModel.WORD, null));
        store.add(new MainViewModel(counter++, "word7","transl7",1,1,5, TypeModel.WORD, null));
    }


    @Override
    public void save(MainViewModel model) {
        if (model.getId() == null) {
            model.setId(++counter);
        }
        if (store.contains(model)) {
            store.remove(model);
        }
        store.add(model);
        log.info("Stub save");
    }

    @Override
    public GaeGetListResponse getList(GaeGetListRequest request) {
        GaeGetListResponse resp = new GaeGetListResponse();
        resp.setList(store);
        resp.setRecordsCount(store.size());
        log.info("Stub getList");
        return resp;
    }

    @Override
    public void renewRating(Long id, Integer rating) {
        for (MainViewModel mainViewModel : store) {
            if (mainViewModel.getId().equals(id)) {
                mainViewModel.setRating(rating);
            }
        }
        log.info("Stub renewRating");
    }
}
