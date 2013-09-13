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
     * ��� ��������� �����, ������� ����������
     *
     * @param callBack
     */
    void getNext(CallBackChain callBack);

    /**
     * ��� ���������� �����, ������� ����������
     *
     * @param callBack
     */
    void getPrev(CallBackChain callBack);

    /**
     * ������� �������� ��������
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
