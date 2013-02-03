package ru.roman.bim.service.gae;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import ru.roman.bim.service.ServiceFactory;

/** @author Roman 14.01.13 23:56 */
public class GaeTaskExecutor {
    private static final Log log = LogFactory.getLog(GaeTaskExecutor.class);
    private final static GaeConnector gaeConnector = ServiceFactory.getGaeConnector();


    public static void main(String... args) {

        gaeConnector.systemTask(1);
    }
}
