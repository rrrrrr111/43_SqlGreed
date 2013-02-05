package ru.roman.bim.service.cache;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import ru.roman.bim.gui.pane.main.MainViewModel;
import ru.roman.bim.gui.pane.settings.Settings;
import ru.roman.bim.gui.pane.settings.SettingsViewModel;
import ru.roman.bim.service.ServiceFactory;
import ru.roman.bim.service.gae.GaeConnector;
import ru.roman.bim.service.gae.wsclient.BimItemModel;
import ru.roman.bim.service.gae.wsclient.GetListRequest;
import ru.roman.bim.service.gae.wsclient.GetListResp;
import ru.roman.bim.util.BimException;
import ru.roman.bim.util.WsUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/** @author Roman 22.12.12 16:44 */
public class LocalCacheImpl implements LocalCache {
    private static final Log log = LogFactory.getLog(LocalCacheImpl.class);

    private GaeConnector gaeConnector = ServiceFactory.getGaeConnector();

    /* кэш */
    private final List<MainViewModel> cache;
    /** счетчик относительно которого идет перебор слов в БД, начинается с нуля */
    private Integer currentNum = 0;
    /** сдвиг относително начала БД для текущего набора в кэше */
    private Integer currentOffset = 0;
    /** общее кол-во записей */
    private Integer recordsCount = 0;

    protected LocalCacheImpl() {
        super();
        cache = new ArrayList<MainViewModel>(Settings.get().getCacheMaxSize().intValue());
    }

    protected LocalCacheImpl(LocalCache localCache) {
        super();
        cache = localCache.getCacheData();
        initCache(localCache.getCurrentNum(), localCache.getRecordsCount());
    }

    @Override
    public synchronized void initCache(Integer currentNum, Integer recordsCount) {
        this.currentNum = currentNum;
        this.recordsCount = recordsCount;
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
        throw new BimException(String.format("Illegal cache state, wrong value wordNum %s", num));
    }


    private void checkCacheState() {
        final SettingsViewModel sett = Settings.get();
        final int portion = sett.getPortion().intValue();
        final boolean isPortionWorking = sett.isWorkWithPortion();
        final int volume;               // определяет общее кол-во перебираемых слов
        if ((portion > recordsCount) || !isPortionWorking) {
            volume = recordsCount;
        } else {
            volume = portion;
        }

        if (currentNum >= volume) {
            currentNum = 0;
        }
        if (currentNum < 0 ) {
            currentNum = volume - 1;
        }
        int newOffset;
        final int cacheMaxSize = sett.getCacheMaxSize().intValue();
        if (currentNum >= cacheMaxSize) {
            newOffset = currentNum - currentNum % cacheMaxSize;
        } else {
            newOffset = 0;
        }

        if (currentOffset != newOffset || cache.isEmpty() || currentNum == 0) {
            // кеш перегружается в случае
            //  - перехода на новую страницу данных
            //  - при первом запуске (кэш пуст)
            //  - при прохождении полного цикла currentNum == 0

            final GetListRequest req = WsUtil.prepareRequest(new GetListRequest());
            req.setOffset(newOffset);
            req.setCount(cacheMaxSize);
            req.setSortingField(sett.getSortingField());
            req.setSortingDirection(sett.getSortingDirection());
            req.getRatingsList().addAll(sett.getRatings());
            req.getTypes().addAll(sett.getTypes());
            req.getCategories().addAll(sett.getCategories());
            req.setFacedLangId(sett.getFacedLangId().intValue());
            req.setShadowedLangId(sett.getShadowedLangId().intValue());

            GetListResp resp = gaeConnector.getList(req);
            if (resp.getList() == null || resp.getList().size() == 0) {
                if (currentNum == 0) {
                    throw new BimException("Words have not found in dictionary");
                } else {
                    currentNum = 0;
                    checkCacheState();
                }
            }
            cache.clear();
            cache.addAll(toModels(resp.getList()));
            recordsCount = resp.getRecordsCount();
            currentOffset = newOffset;
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
    public synchronized List<MainViewModel> getCacheData() {
        return new ArrayList<MainViewModel>(cache);
    }

    @Override
    public synchronized Integer getRecordsCount() {
        return recordsCount;
    }

    @Override
    public synchronized void clearCache() {
        int size = cache.size();
        cache.clear();
        log.info(String.format("Cache cleaned, %s models released", size));
    }

    @Override
    public synchronized void renewModel(MainViewModel model) {
        int idx = cache.indexOf(model);
        if (idx >=0) {
            cache.remove(idx);
            cache.add(idx, model);
        }
    }
}
