package ru.roman.bim.service.translate;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.junit.Assert;
import org.junit.Test;
import ru.roman.bim.service.ServiceFactory;

/** @author Roman 07.09.13 2:30 */
public class GoogleServiceTest {
    private static final Log log = LogFactory.getLog(GoogleServiceTest.class);

    TranslationService service = ServiceFactory.getGoogleService();

    @Test
    public void testTranslateWord() throws Exception {

        String translation = service.translate("привет", 1L, 2L);
        log.info(String.format("translation >>> %s", translation));
        Assert.assertNotNull("Translation can't be null", StringUtils.trimToNull(translation));
    }

    @Test
    public void testTranslateEngWord() throws Exception {

        String translation = service.translate("walk", 2L, 1L);
        log.info(String.format("translation >>> %s", translation));
        Assert.assertNotNull("Translation can't be null", StringUtils.trimToNull(translation));
    }

    @Test
    public void testTranslateExpression() throws Exception {
        String translation = service.translate("привет утро", 1L, 2L);
        log.info(String.format("translation >>> %s", translation));
        Assert.assertNotNull("Translation can't be null", StringUtils.trimToNull(translation));
    }

    @Test
    public void testTranslateEngExpression() throws Exception {

        String translation = service.translate("walk into my house", 2L, 1L);
        log.info(String.format("translation >>> %s", translation));
        Assert.assertNotNull("Translation can't be null", StringUtils.trimToNull(translation));
    }
}
