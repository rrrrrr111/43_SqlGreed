package ru.roman.bim.service.cache;

import ru.roman.bim.gui.common.cbchain.CallBackChain;
import ru.roman.bim.gui.pane.main.MainViewModel;

import java.util.List;

/** @author Roman 22.12.12 16:44 */
public interface LocalCache {


    void initCache(Integer currentNum, Integer recordsCount, Integer currentOffset);

    void getCurrent(CallBackChain callBack);

    MainViewModel getCurrentSync();

    /**
     * дай следующее слово, счетчик увеличится
     *
     * @param callBack
     */
    void getNext(CallBackChain callBack);

    /**
     * дай предыдущее слово, счетчик уменьшится
     *
     * @param callBack
     */
    void getPrev(CallBackChain callBack);

    /**
     * текущее значение счетчика
     *
     * @return
     */
    Integer getCurrentNum();

    Integer getCurrentOffset();

    Integer getRecordsCount();

    List<MainViewModel> getCacheData();

    void clearCache();

    void addOrRenewModel(MainViewModel currModel);

}
