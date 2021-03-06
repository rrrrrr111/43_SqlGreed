
package ru.roman.bim.service.gae.wsclient;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for registerNewAndLoadSettings complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="registerNewAndLoadSettings">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="arg0" type="{http://dataws.service.server.bim.roman.ru/}registerNewAndLoadSettingsRequest" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "registerNewAndLoadSettings", propOrder = {
    "arg0"
})
public class RegisterNewAndLoadSettings {

    protected RegisterNewAndLoadSettingsRequest arg0;

    /**
     * Gets the value of the arg0 property.
     * 
     * @return
     *     possible object is
     *     {@link RegisterNewAndLoadSettingsRequest }
     *     
     */
    public RegisterNewAndLoadSettingsRequest getArg0() {
        return arg0;
    }

    /**
     * Sets the value of the arg0 property.
     * 
     * @param value
     *     allowed object is
     *     {@link RegisterNewAndLoadSettingsRequest }
     *     
     */
    public void setArg0(RegisterNewAndLoadSettingsRequest value) {
        this.arg0 = value;
    }

}
