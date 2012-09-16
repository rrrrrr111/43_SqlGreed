package ru.roman.greed.util;

/**
 *
 * User: Roman
 * DateTime: 01.09.12 12:08
 */
public interface Const {

    /** max rows in table */
    int MAX_TABLE_ROW_COUNT = 10000;
    String VERSION = "v4.0";

    String DEFAULT_CONNECTION_CLASS = "org.apache.derby.jdbc.ClientDriver";
    String DEFAULT_CONNECTION_URL = "jdbc:derby://localhost:1527/sample;create=true";
    String DEFAULT_CONNECTION_USER = "app";
    String DEFAULT_CONNECTION_PWS = "app";

}




