package ru.roman.bim.service.cache;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import ru.roman.bim.gui.pane.main.MainViewModel;
import ru.roman.bim.service.ServiceFactory;
import ru.roman.bim.service.gae.GaeConnector;
import ru.roman.bim.service.gae.wsclient.BimItemModel;
import ru.roman.bim.service.gae.wsclient.GaeGetListRequest;
import ru.roman.bim.service.gae.wsclient.GaeGetListResponse;
import ru.roman.bim.util.BimException;
import ru.roman.bim.util.Const;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/** @author Roman 22.12.12 16:44 */
public class LocalCacheImpl implements LocalCache {
    private static final Log log = LogFactory.getLog(LocalCacheImpl.class);

    GaeConnector gaeConnector = ServiceFactory.getGaeConnector();
    /*
   кэш
    */
    private final List<MainViewModel> cache;
    /**
    счетчик относительно которого идет перебор слов в БД
     */
    private Integer currentNum = 0;
    /**
    сдвиг относително начала БД для текущего набора в кэше
     */
    private Integer currentOffset = 0;
    /**
    общее кол-во записей
     */
    private Integer recordsCount = 0;

    protected LocalCacheImpl(LocalCache localCache) {
        super();
        cache = localCache.getCache();
        recordsCount = localCache.getRecordsCount();
        initCache(localCache.getCurrentNum(), localCache.getCurrentOffset());
    }

    protected LocalCacheImpl() {
        super();
        cache = new ArrayList<MainViewModel>(Const.CACHE_MAX_SIZE);
    }


    @Override
    public synchronized void initCache(Integer currentNum, Integer currentOffset) {
        this.currentNum = currentNum;
        this.currentOffset = currentOffset;
        checkCacheState();
    }

    @Override
    public synchronized MainViewModel getCurrent() {
        checkCacheState();
        return getFromCache(currentNum);
    }

    @Override
    public synchronized MainViewModel getNext() {
        ++currentNum;
        checkCacheState();
        return getFromCache(currentNum);
    }

    @Override
    public synchronized MainViewModel getPrev() {
        --currentNum;
        checkCacheState();
        return getFromCache(currentNum);
    }

    private MainViewModel getFromCache(Integer num) {
        num = num - currentOffset;
        if (num < cache.size()) {
            final MainViewModel model = cache.get(num);
            model.setModelNum(currentNum.longValue());
            return model;
        }
        throw new BimException(String.format("Illegal cache state, value %s too big", num));
    }


    private void checkCacheState() {
        if (currentNum <= 0 || currentNum > recordsCount) {
            currentNum = 0;
            currentOffset = 0;
        }
        if (currentNum < currentOffset) {
            currentOffset = currentNum;
        }
        if ((currentNum > (currentOffset + Const.CACHE_MAX_SIZE)) || cache.isEmpty()) {
            final Integer offset;
            if (cache.isEmpty()) {
                offset = currentOffset;
            } else {
                offset = currentOffset + Const.CACHE_MAX_SIZE;
            }

            final GaeGetListRequest req = new GaeGetListRequest();
            req.setOffset(offset);
            req.setCount(Const.CACHE_MAX_SIZE);
            req.setSortingField(Const.DEFAULT_SORTING_FIELD);
            req.getTypes().addAll(Const.DEFAULT_TYPES);
            req.setLangId(Const.DEFAULT_LANG_ID);

            GaeGetListResponse resp = gaeConnector.getList(req);

            cache.clear();
            cache.addAll(toModels(resp.getList()));
            recordsCount = resp.getRecordsCount() - 1;  // отнимаем еденицу т к у нас все счетчики относительно индексов списка
            currentOffset = offset;
        }
    }

    private Collection<MainViewModel> toModels(List<BimItemModel> list) {
        Collection<MainViewModel> res = new ArrayList<MainViewModel>();
        for (BimItemModel model : list) {
            res.add(new MainViewModel(model));
        }
        return res;
    }

    @Override
    public synchronized Integer getCurrentNum() {
        return currentNum;
    }

    @Override
    public synchronized Integer getCurrentOffset() {
        return currentOffset;
    }

    @Override
    public synchronized List<MainViewModel> getCache() {
        return new ArrayList<MainViewModel>(cache);
    }

    @Override
    public synchronized Integer getRecordsCount() {
        return recordsCount;
    }

    @Override
    public synchronized void clearCache() {
        cache.clear();
        log.info("Cache cleaned");
    }

    @Override
    public synchronized void renewModel(MainViewModel model) {
        int idx = cache.indexOf(model);
        if (idx >=0 ) {
            cache.remove(idx);
            cache.add(idx, model);
        }
    }
}
