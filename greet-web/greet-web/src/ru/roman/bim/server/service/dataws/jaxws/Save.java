
package ru.roman.bim.server.service.dataws.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "save", namespace = "http://dataws.service.server.bim.roman.ru/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "save", namespace = "http://dataws.service.server.bim.roman.ru/")
public class Save {

    @XmlElement(name = "arg0", namespace = "")
    private ru.roman.bim.server.service.dataws.dto.word.SaveRequest arg0;

    /**
     * 
     * @return
     *     returns SaveRequest
     */
    public ru.roman.bim.server.service.dataws.dto.word.SaveRequest getArg0() {
        return this.arg0;
    }

    /**
     * 
     * @param arg0
     *     the value for the arg0 property
     */
    public void setArg0(ru.roman.bim.server.service.dataws.dto.word.SaveRequest arg0) {
        this.arg0 = arg0;
    }

}
