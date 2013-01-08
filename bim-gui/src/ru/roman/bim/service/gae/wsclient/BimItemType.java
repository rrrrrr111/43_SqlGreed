
package ru.roman.bim.service.gae.wsclient;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for bimItemType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="bimItemType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="WORD"/>
 *     &lt;enumeration value="EXPRESSION"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "bimItemType")
@XmlEnum
public enum BimItemType {

    WORD,
    EXPRESSION;

    public String value() {
        return name();
    }

    public static BimItemType fromValue(String v) {
        return valueOf(v);
    }

}
