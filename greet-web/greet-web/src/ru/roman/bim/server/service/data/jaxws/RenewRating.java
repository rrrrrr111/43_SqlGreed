
package ru.roman.bim.server.service.data.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "renewRating", namespace = "http://data.service.server.bim.roman.ru/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "renewRating", namespace = "http://data.service.server.bim.roman.ru/", propOrder = {
    "arg0",
    "arg1"
})
public class RenewRating {

    @XmlElement(name = "arg0", namespace = "")
    private Long arg0;
    @XmlElement(name = "arg1", namespace = "")
    private Integer arg1;

    /**
     * 
     * @return
     *     returns Long
     */
    public Long getArg0() {
        return this.arg0;
    }

    /**
     * 
     * @param arg0
     *     the value for the arg0 property
     */
    public void setArg0(Long arg0) {
        this.arg0 = arg0;
    }

    /**
     * 
     * @return
     *     returns Integer
     */
    public Integer getArg1() {
        return this.arg1;
    }

    /**
     * 
     * @param arg1
     *     the value for the arg1 property
     */
    public void setArg1(Integer arg1) {
        this.arg1 = arg1;
    }

}
