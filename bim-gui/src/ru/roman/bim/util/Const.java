package ru.roman.bim.util;

import org.apache.commons.io.FileUtils;

/**
 *
 * User: Roman
 * DateTime: 01.09.12 12:08
 */
public interface Const {

    boolean DEV_MODE = false;  // ��������� ����� ������ ��� ������
    String DEFAULT_ENDPOINT = "http://churganovroman.appspot.com/DataProvider";
    String DEFAULT_ENDPOINT_WSDL = "http://churganovroman.appspot.com/wsdl/DataProvider.wsdl";
    //String DEFAULT_ENDPOINT = "http://localhost:8080/DataProvider";
    //String DEFAULT_ENDPOINT_WSDL = "http://localhost:8080/wsdl/DataProvider.wsdl";
    String VERSION = "1.30";


    String APP_NAME = "Bim";
    String APP_DATA_PATH = FileUtils.getUserDirectoryPath() + "/." + APP_NAME;
    String APP_CONFIG_PATH = APP_DATA_PATH + "/config";

    boolean SHOW_TRAY_NOTIFICATIONS = !DEV_MODE;

}

