
package ru.roman.bim.service.gae.wsclient;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for registerNewAndLoadSettingsResp complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="registerNewAndLoadSettingsResp">
 *   &lt;complexContent>
 *     &lt;extension base="{http://data.service.server.bim.roman.ru/}abstractResponse">
 *       &lt;sequence>
 *         &lt;element name="userSettingsModel" type="{http://data.service.server.bim.roman.ru/}userSettingsModel" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "registerNewAndLoadSettingsResp", propOrder = {
    "userSettingsModel"
})
public class RegisterNewAndLoadSettingsResp
    extends AbstractResponse
{

    protected UserSettingsModel userSettingsModel;

    /**
     * Gets the value of the userSettingsModel property.
     * 
     * @return
     *     possible object is
     *     {@link UserSettingsModel }
     *     
     */
    public UserSettingsModel getUserSettingsModel() {
        return userSettingsModel;
    }

    /**
     * Sets the value of the userSettingsModel property.
     * 
     * @param value
     *     allowed object is
     *     {@link UserSettingsModel }
     *     
     */
    public void setUserSettingsModel(UserSettingsModel value) {
        this.userSettingsModel = value;
    }

}
