
package ru.roman.bim.service.gae.wsclient;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for gaeGetListResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="gaeGetListResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="list" type="{http://data.service.server.bim.roman.ru/}bimItemModel" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="recordsCount" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "gaeGetListResponse", propOrder = {
    "list",
    "recordsCount"
})
public class GaeGetListResponse {

    @XmlElement(nillable = true)
    protected List<BimItemModel> list;
    protected Integer recordsCount;

    /**
     * Gets the value of the list property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the list property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link BimItemModel }
     * 
     * 
     */
    public List<BimItemModel> getList() {
        if (list == null) {
            list = new ArrayList<BimItemModel>();
        }
        return this.list;
    }

    /**
     * Gets the value of the recordsCount property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getRecordsCount() {
        return recordsCount;
    }

    /**
     * Sets the value of the recordsCount property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setRecordsCount(Integer value) {
        this.recordsCount = value;
    }

}
