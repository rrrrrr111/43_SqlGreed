
package ru.roman.bim.server.service.dataws.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "saveResponse", namespace = "http://dataws.service.server.bim.roman.ru/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "saveResponse", namespace = "http://dataws.service.server.bim.roman.ru/")
public class SaveResponse {

    @XmlElement(name = "return", namespace = "")
    private Long _return;

    /**
     * 
     * @return
     *     returns Long
     */
    public Long getReturn() {
        return this._return;
    }

    /**
     * 
     * @param _return
     *     the value for the _return property
     */
    public void setReturn(Long _return) {
        this._return = _return;
    }

}
