package ru.roman.bim.service;

import ru.roman.bim.dev.stub.GaeConnectorStub;
import ru.roman.bim.service.gae.GaeConnector;
import ru.roman.bim.service.gae.GaeConnectorImpl;
import ru.roman.bim.service.http.HttpClientService;
import ru.roman.bim.service.http.HttpClientServiceImpl;
import ru.roman.bim.service.translate.TranslationService;
import ru.roman.bim.service.translate.YandexService;
import ru.roman.bim.util.Const;

/** @author Roman 22.12.12 15:37 */
public class ServiceFactory {
    private static volatile GaeConnector gaeConnector;
    private static volatile HttpClientService httpClientInstance;
    private static volatile TranslationService yandexService;

    public static synchronized GaeConnector getGaeConnector() {
        if (gaeConnector == null) {
            if (Const.DEV_MODE)
                gaeConnector = new GaeConnectorStub();
            else
                gaeConnector = new GaeConnectorImpl();
        }
        return gaeConnector;
    }

    public static synchronized HttpClientService getHttpClientInstance() {
        if (httpClientInstance == null) {
            httpClientInstance = new HttpClientServiceImpl();
        }
        return httpClientInstance;
    }

    public static synchronized TranslationService getYandexService() {
        if (yandexService == null) {
            yandexService = new YandexService();
        }
        return yandexService;
    }

}
