package ru.roman.bim.service.cache;

import ru.roman.bim.gui.pane.main.MainViewModel;

import java.util.List;

/** @author Roman 22.12.12 16:44 */
public interface LocalCache {


    void initCache(Integer currentNum, Integer recordsCount);

    void getCurrent(CacheCallBack callBack);

    MainViewModel getCurrentSync();
    /*
   дай следующее слово, счетчик увеличится
    */
    void getNext(CacheCallBack callBack);
    /*
    дай предыдущее слово, счетчик уменьшится
     */
    void getPrev(CacheCallBack callBack);
    /*
   текущее значение счетчика
    */
    Integer getCurrentNum();

    Integer getCurrentOffset();

    Integer getRecordsCount();

    List<MainViewModel> getCacheData();

    void clearCache();

    void renewModel(MainViewModel currModel);


    public interface CacheCallBack {
        void onGot(MainViewModel model);
    }

}
