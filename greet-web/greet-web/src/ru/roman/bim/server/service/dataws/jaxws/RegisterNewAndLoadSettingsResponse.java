
package ru.roman.bim.server.service.dataws.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "registerNewAndLoadSettingsResponse", namespace = "http://dataws.service.server.bim.roman.ru/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "registerNewAndLoadSettingsResponse", namespace = "http://dataws.service.server.bim.roman.ru/")
public class RegisterNewAndLoadSettingsResponse {

    @XmlElement(name = "return", namespace = "")
    private ru.roman.bim.server.service.dataws.dto.settings.RegisterNewAndLoadSettingsResp _return;

    /**
     * 
     * @return
     *     returns RegisterNewAndLoadSettingsResp
     */
    public ru.roman.bim.server.service.dataws.dto.settings.RegisterNewAndLoadSettingsResp getReturn() {
        return this._return;
    }

    /**
     * 
     * @param _return
     *     the value for the _return property
     */
    public void setReturn(ru.roman.bim.server.service.dataws.dto.settings.RegisterNewAndLoadSettingsResp _return) {
        this._return = _return;
    }

}
