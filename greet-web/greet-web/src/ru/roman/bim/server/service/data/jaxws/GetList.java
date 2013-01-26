
package ru.roman.bim.server.service.data.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "getList", namespace = "http://data.service.server.bim.roman.ru/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getList", namespace = "http://data.service.server.bim.roman.ru/")
public class GetList {

    @XmlElement(name = "arg0", namespace = "")
    private ru.roman.bim.server.service.data.dto.word.GetListRequest arg0;

    /**
     * 
     * @return
     *     returns GetListRequest
     */
    public ru.roman.bim.server.service.data.dto.word.GetListRequest getArg0() {
        return this.arg0;
    }

    /**
     * 
     * @param arg0
     *     the value for the arg0 property
     */
    public void setArg0(ru.roman.bim.server.service.data.dto.word.GetListRequest arg0) {
        this.arg0 = arg0;
    }

}
