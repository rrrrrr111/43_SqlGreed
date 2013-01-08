package ru.roman.bim.service.cache;

import org.junit.Before;
import org.junit.Test;

/** @author Roman 26.12.12 22:11 */
public class LocalCacheImplTest {


    LocalCache lc = new LocalCacheImpl();

    @Before
    public void setUp() {
    }


    @Test
    public void someTest() {
        lc.initCache(0, 0);
    }



}
