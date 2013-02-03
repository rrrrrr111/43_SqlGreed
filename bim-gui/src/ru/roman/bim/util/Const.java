package ru.roman.bim.util;

import org.apache.commons.io.FileUtils;

/**
 *
 * User: Roman
 * DateTime: 01.09.12 12:08
 */
public interface Const {

    boolean DEV_MODE = false;
    //String DEFAULT_ENDPOINT = "http://churganovroman.appspot.com/DataProvider";
    //String DEFAULT_ENDPOINT_WSDL = "http://churganovroman.appspot.com/wsdl/DataProvider.wsdl";
    String DEFAULT_ENDPOINT = "http://localhost:8080/DataProvider";
    String DEFAULT_ENDPOINT_WSDL = "http://localhost:8080/wsdl/DataProvider.wsdl";
    String VERSION = "1.24";


    String APP_NAME = "Bim";
    String APP_DATA_PATH = FileUtils.getUserDirectoryPath() + "/." + APP_NAME;
    String APP_CONFIG_PATH = APP_DATA_PATH + "/config";

    boolean SHOW_TRAY_NOTIFICATIONS = true;

    //Integer CACHE_MAX_SIZE = 100;
    //String DEFAULT_SORTING_FIELD = "editDate";
    //String DEFAULT_SORTING_DIRECTION = "DESCENDING";  // ASCENDING, DESCENDING

    //Integer DEFAULT_LANG_ID = 1;
    //List<Integer> DEFAULT_TYPES = WordType.getOrdinals(Arrays.asList(WordType.values()));
    //Long DEFAULT_OWNER_ID = 1L;
    //Collection<? extends Integer> DEFAULT_RATINGS = Arrays.asList(1, 2, 3, 4, 5);
    //Long DEFAULT_RATING = 3l;



}

