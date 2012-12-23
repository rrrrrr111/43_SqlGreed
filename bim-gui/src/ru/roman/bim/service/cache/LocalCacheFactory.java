package ru.roman.bim.service.cache;

/** @author Roman 22.12.12 19:34 */
public class LocalCacheFactory {



    public static LocalCache createLocalCacheInstance(Integer currentNum, Integer currentOffset) {

        LocalCache instance = new LocalCacheImpl();
        instance.initCache(currentNum, currentOffset);
        return instance;
    }

    public static LocalCache createLocalCacheInstance(LocalCache cache) {

        LocalCache instance = new LocalCacheImpl(cache);
        return instance;
    }


}
