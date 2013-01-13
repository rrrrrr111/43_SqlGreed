package ru.roman.bim.service.translate;

import com.google.gson.Gson;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import ru.roman.bim.model.Lang;
import ru.roman.bim.service.ServiceFactory;
import ru.roman.bim.service.http.HttpClientService;
import ru.roman.bim.service.translate.dto.YandexWordTranslation;

import java.util.HashMap;
import java.util.Map;

/** @author Roman 13.01.13 16:51 */
public class YandexService implements TranslationService{
    private static final Log log = LogFactory.getLog(YandexService.class);

    public static final String TRANSLATE_YANDEX_RU = "translate.yandex.ru";
    public static final String TRANSLATE_YANDEX_RU_PATH = "/tr.json/translate";
    public static final String TRANSLATE_YANDEX_NET = "translate.yandex.net";
    public static final String TRANSLATE_YANDEX_NET_PATH = "/dicservice.json/lookup";

    private HttpClientService httpClient = ServiceFactory.getHttpClientInstance();
    private Gson gson = new Gson();


    @Override
    public String translateWord(String word, Long wordLandId, Long targetLandId) {
        //http://translate.yandex.net/dicservice.json/lookup?lang=ru-en&text=привет

        final Map<String, String> params = new HashMap<String, String>();
        params.put("lang", createLangParamValue(wordLandId, targetLandId));
        params.put("text", word);

        final String gsonRes = httpClient.executeGet(TRANSLATE_YANDEX_NET, TRANSLATE_YANDEX_NET_PATH, params);
        YandexWordTranslation res = fromGson(gsonRes);

        log.info(String.format("res >>> %s", res));
        return gsonRes;
    }

    public YandexWordTranslation fromGson(String gsonRes) {
        return gson.fromJson(gsonRes, YandexWordTranslation.class);
    }


    @Override
    public String translateExpression(String word, Long wordLandId, Long targetLandId) {
        //http://translate.yandex.ru/tr.json/translate?lang=ru-en&text=привет


        final Map<String, String> params = new HashMap<String, String>();
        params.put("lang", createLangParamValue(wordLandId, targetLandId));
        params.put("text", word);

        return httpClient.executeGet(TRANSLATE_YANDEX_RU, TRANSLATE_YANDEX_RU_PATH, params);
    }


    private String createLangParamValue(Long wordLandId, Long targetLandId) {
        return Lang.valueOf(wordLandId).getReduction() + "-" + Lang.valueOf(targetLandId).getReduction();
    }
}
