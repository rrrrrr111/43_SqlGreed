
package ru.roman.bim.service.gae.wsclient;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for systemTaskRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="systemTaskRequest">
 *   &lt;complexContent>
 *     &lt;extension base="{http://data.service.server.bim.roman.ru/}abstractRequest">
 *       &lt;sequence>
 *         &lt;element name="taskNum" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "systemTaskRequest", propOrder = {
    "taskNum"
})
public class SystemTaskRequest
    extends AbstractRequest
{

    protected int taskNum;

    /**
     * Gets the value of the taskNum property.
     * 
     */
    public int getTaskNum() {
        return taskNum;
    }

    /**
     * Sets the value of the taskNum property.
     * 
     */
    public void setTaskNum(int value) {
        this.taskNum = value;
    }

}
