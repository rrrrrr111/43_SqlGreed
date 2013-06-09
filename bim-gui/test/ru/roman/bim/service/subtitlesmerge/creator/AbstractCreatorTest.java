package ru.roman.bim.service.subtitlesmerge.creator;

import org.junit.Assert;
import org.junit.Test;

/** @author Roman 09.06.13 23:04 */
public class AbstractCreatorTest {




    @Test
    public void testCreateSrt() throws Exception {


        String name1 = "dfghdfgdfghfdghdf";

        Assert.assertEquals(name1, AbstractCreator.removeExtension(name1));
        Assert.assertEquals(name1, AbstractCreator.removeExtension(name1 + ".srt"));
        Assert.assertEquals(name1, AbstractCreator.removeExtension(name1 + ".html"));

        name1 = "df\\ghdfJKll654gdfg_hfdghdf87";

        Assert.assertEquals(name1, AbstractCreator.removeExtension(name1));
        Assert.assertEquals(name1, AbstractCreator.removeExtension(name1 + ".srt"));
        Assert.assertEquals(name1, AbstractCreator.removeExtension(name1 + ".html"));

        name1 = "dfgh/dfgdf_.h/fd67Ighdf";

        Assert.assertEquals(name1, AbstractCreator.removeExtension(name1));
        Assert.assertEquals(name1, AbstractCreator.removeExtension(name1 + ".srt"));
        Assert.assertEquals(name1, AbstractCreator.removeExtension(name1 + ".html"));
        Assert.assertEquals(name1, AbstractCreator.removeExtension(name1 + ".SrT"));
        Assert.assertEquals(name1, AbstractCreator.removeExtension(name1 + ".1237"));
    }
}
