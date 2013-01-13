package ru.roman.bim.service.translate;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import ru.roman.bim.service.ServiceFactory;
import ru.roman.bim.service.translate.dto.YandexWordTranslation;
import ru.roman.bim.util.GsonUtil;

/** @author Roman 13.01.13 17:21 */
public class YandexServiceTest {
    private static final Log log = LogFactory.getLog(YandexServiceTest.class);

    TranslationService service = ServiceFactory.getYandexService();

    @Test
    public void testTranslateWord() throws Exception {

        String translation = service.translateWord("привет", 1L, 2L);
        log.info(String.format("translation >>> %s", translation));
    }

    @Test
    public void testTranslateExpression() throws Exception {
        String translation = service.translateExpression("привет", 1L, 2L);
        log.info(String.format("translation >>> %s", translation));

    }

    @Test
    public void fromGsonTest() throws Exception {
        String res = "{\"head\":{},\"def\":[{\"text\":\"привет\",\"pos\":\"существительное\",\"tr\":[{\"text\":\"hi\",\"pos\":\"noun\",\"syn\":[{\"text\":\"hello\"},{\"text\":\"Hiya\"},{\"text\":\"Hallo\"},{\"text\":\"Hullo\"}],\"mean\":[{\"text\":\"приветик\"},{\"text\":\"превед\"}],\"ex\":[{\"text\":\"привет видеокамеры\",\"tr\":[{\"text\":\"hi camcorder\"}]},{\"text\":\"огромный привет\",\"tr\":[{\"text\":\"huge hello\"}]}]},{\"text\":\"hey\",\"pos\":\"noun\",\"mean\":[{\"text\":\"хей\"}]},{\"text\":\"greetings\",\"pos\":\"noun\",\"ex\":[{\"text\":\"братский привет\",\"tr\":[{\"text\":\"fraternal greetings\"}]}]},{\"text\":\"howdy\",\"pos\":\"noun\"}]}],\"link\":[{\"name\":\"lingvo\",\"href\":\"http://slovari.yandex.ru/%D0%BF%D1%80%D0%B8%D0%B2%D0%B5%D1%82/ru-en/\"}]}";
        YandexWordTranslation translation = service.fromGson(res);
        log.info(String.format("translation json >>> %s", translation));

        log.info(String.format("translation json default >>> %s", GsonUtil.processGson(translation.getDef())));

        log.info(String.format("translation for user >>> %s", translation.toUserString()));
    }
}
