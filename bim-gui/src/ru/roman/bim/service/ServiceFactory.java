package ru.roman.bim.service;

import ru.roman.bim.dev.stub.GaeConnectorStub;
import ru.roman.bim.service.gae.GaeConnector;

/** @author Roman 22.12.12 15:37 */
public class ServiceFactory {
    private static volatile GaeConnector gaeConnector;

    public static synchronized GaeConnector getGaeConnector() {
        if (gaeConnector == null) {
            gaeConnector = new GaeConnectorStub();
        }
        return gaeConnector;
    }

}
