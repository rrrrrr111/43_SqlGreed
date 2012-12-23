package ru.roman.bim.service.cache;

import ru.roman.bim.gui.pane.main.MainViewModel;

import java.util.List;

/** @author Roman 22.12.12 16:44 */
public interface LocalCache {


    public void initCache(Integer currentNum, Integer currentOffset);


    MainViewModel getCurrent();
    /*
   ��� ��������� �����, ������� ����������
    */
    MainViewModel getNext();
    /*
    ��� ���������� �����, ������� ����������
     */
    MainViewModel getPrev();
    /*
   ������� �������� ��������
    */
    Integer getCurrentNum();

    Integer getCurrentOffset();

    Integer getRecordsCount();

    List<MainViewModel> getCache();

    void clearCache();

    void renewModel(MainViewModel currModel);
}
