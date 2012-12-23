package ru.roman.bim.util;

import ru.roman.bim.service.gae.dto.TypeModel;

import java.util.Arrays;
import java.util.List;

/**
 *
 * User: Roman
 * DateTime: 01.09.12 12:08
 */
public interface Const {


    String VERSION = "1.0";
    String APP_NAME = "Bim";
    String APP_DATA_DIR_NAME = "/." + APP_NAME;

    boolean SHOW_TRAY_NOTIFICATIONS = false;
    /*
    размер кеша
     */
    Integer CACHE_MAX_SIZE = 100;
    /*
    сортировка по БД
     */
    String DEFAULT_SORTING_FIELD = "editDate";
    String DEFAULT_SORTING_DIRECTION = "desc";

    Integer DEFAULT_LANG_ID = 1;
    List<TypeModel> DEFAULT_TYPES = Arrays.asList(TypeModel.values());
    Integer DEFAULT_OWNER_ID = 1;
}

