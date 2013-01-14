package ru.roman.bim.service.translate;

import ru.roman.bim.service.translate.dto.YandexWordTranslation;

/** @author Roman 13.01.13 16:55 */
public interface TranslationService {




    String translateWord(String word, Long wordLandId, Long targetLandId);

    String translateExpression(String word, Long wordLandId, Long targetLandId);

    YandexWordTranslation fromGson(String gsonRes);

    String translate(String word, Long wordLandId, Long targetLandId);
}
