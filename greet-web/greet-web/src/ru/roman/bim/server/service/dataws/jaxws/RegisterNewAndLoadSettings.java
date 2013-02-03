
package ru.roman.bim.server.service.dataws.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "registerNewAndLoadSettings", namespace = "http://dataws.service.server.bim.roman.ru/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "registerNewAndLoadSettings", namespace = "http://dataws.service.server.bim.roman.ru/")
public class RegisterNewAndLoadSettings {

    @XmlElement(name = "arg0", namespace = "")
    private ru.roman.bim.server.service.dataws.dto.settings.RegisterNewAndLoadSettingsRequest arg0;

    /**
     * 
     * @return
     *     returns RegisterNewAndLoadSettingsRequest
     */
    public ru.roman.bim.server.service.dataws.dto.settings.RegisterNewAndLoadSettingsRequest getArg0() {
        return this.arg0;
    }

    /**
     * 
     * @param arg0
     *     the value for the arg0 property
     */
    public void setArg0(ru.roman.bim.server.service.dataws.dto.settings.RegisterNewAndLoadSettingsRequest arg0) {
        this.arg0 = arg0;
    }

}
