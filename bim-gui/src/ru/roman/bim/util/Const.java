package ru.roman.bim.util;

import org.apache.commons.io.FileUtils;
import ru.roman.bim.model.WordType;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 *
 * User: Roman
 * DateTime: 01.09.12 12:08
 */
public interface Const {

    boolean DEV_MODE = false;
    //String DEFAULT_ENDPOINT = "http://churganovroman.appspot.com/DataProvider";
    String DEFAULT_ENDPOINT = "http://localhost:8080/DataProvider";
    String VERSION = "1.20";


    String APP_NAME = "Bim";
    String APP_DATA_PATH = FileUtils.getUserDirectoryPath() + "/." + APP_NAME;
    String APP_CONFIG_PATH = APP_DATA_PATH + "/config";

    boolean SHOW_TRAY_NOTIFICATIONS = true;
    /* размер кеша */
    Integer CACHE_MAX_SIZE = 100;
    /* сортировка по БД */
    String DEFAULT_SORTING_FIELD = "editDate";
    String DEFAULT_SORTING_DIRECTION = "DESCENDING";  // ASCENDING, DESCENDING

    Integer DEFAULT_LANG_ID = 1;
    List<Integer> DEFAULT_TYPES = WordType.getOrdinals(Arrays.asList(WordType.values()));
    Long DEFAULT_OWNER_ID = 1L;
    Collection<? extends Integer> DEFAULT_RATINGS = Arrays.asList(1, 2, 3, 4, 5);
    Long DEFAULT_RATING = 3l;



}

