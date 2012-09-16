package ru.roman.greed.service.config.writer;

import ru.roman.greed.service.config.reader.Tags;

import javax.xml.transform.sax.TransformerHandler;

/**
 * @author Roman 15.09.12 22:58
 */
public interface XmlWriter<T> extends Tags {

    void write(TransformerHandler handler, T data) throws Exception;
}
