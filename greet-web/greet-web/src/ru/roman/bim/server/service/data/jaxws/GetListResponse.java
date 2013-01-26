
package ru.roman.bim.server.service.data.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "getListResponse", namespace = "http://data.service.server.bim.roman.ru/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getListResponse", namespace = "http://data.service.server.bim.roman.ru/")
public class GetListResponse {

    @XmlElement(name = "return", namespace = "")
    private ru.roman.bim.server.service.data.dto.word.GetListResp _return;

    /**
     * 
     * @return
     *     returns GetListResp
     */
    public ru.roman.bim.server.service.data.dto.word.GetListResp getReturn() {
        return this._return;
    }

    /**
     * 
     * @param _return
     *     the value for the _return property
     */
    public void setReturn(ru.roman.bim.server.service.data.dto.word.GetListResp _return) {
        this._return = _return;
    }

}
