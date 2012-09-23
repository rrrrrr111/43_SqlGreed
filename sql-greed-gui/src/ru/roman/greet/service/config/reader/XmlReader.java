package ru.roman.greet.service.config.reader;

import org.w3c.dom.Document;

/**
 * @author Roman 15.09.12 22:57
 */
public interface XmlReader<T> extends Tags {

    T read(Document doc) throws Exception;
}
