package ru.roman.bim.service;

import ru.roman.bim.service.gae.GaeConnector;
import ru.roman.bim.service.gae.GaeConnectorImpl;
import ru.roman.bim.service.http.HttpClientService;
import ru.roman.bim.service.http.HttpClientServiceImpl;
import ru.roman.bim.service.translate.TranslationService;
import ru.roman.bim.service.translate.YandexService;

/** @author Roman 22.12.12 15:37 */
public class ServiceFactory {
    private static volatile GaeConnector gaeConnector;
    private static volatile HttpClientService httpClientInstance;
    private static volatile TranslationService yandexService;

    public static synchronized GaeConnector getGaeConnector() {
        if (gaeConnector == null) {
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
