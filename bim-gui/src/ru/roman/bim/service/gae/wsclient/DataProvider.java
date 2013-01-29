
package ru.roman.bim.service.gae.wsclient;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.Action;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.4-b01
 * Generated source version: 2.2
 * 
 */
@WebService(name = "DataProvider", targetNamespace = "http://data.service.server.bim.roman.ru/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface DataProvider {


    /**
     * 
     * @param arg0
     * @return
     *     returns ru.roman.bim.service.gae.wsclient.RegisterNewAndLoadSettingsResp
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "registerNewAndLoadSettings", targetNamespace = "http://data.service.server.bim.roman.ru/", className = "ru.roman.bim.service.gae.wsclient.RegisterNewAndLoadSettings")
    @ResponseWrapper(localName = "registerNewAndLoadSettingsResponse", targetNamespace = "http://data.service.server.bim.roman.ru/", className = "ru.roman.bim.service.gae.wsclient.RegisterNewAndLoadSettingsResponse")
    @Action(input = "http://data.service.server.bim.roman.ru/DataProvider/registerNewAndLoadSettingsRequest", output = "http://data.service.server.bim.roman.ru/DataProvider/registerNewAndLoadSettingsResponse")
    public RegisterNewAndLoadSettingsResp registerNewAndLoadSettings(
        @WebParam(name = "arg0", targetNamespace = "")
        RegisterNewAndLoadSettingsRequest arg0);

    /**
     * 
     * @param arg0
     * @return
     *     returns ru.roman.bim.service.gae.wsclient.StoreSettingsResp
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "storeSettings", targetNamespace = "http://data.service.server.bim.roman.ru/", className = "ru.roman.bim.service.gae.wsclient.StoreSettings")
    @ResponseWrapper(localName = "storeSettingsResponse", targetNamespace = "http://data.service.server.bim.roman.ru/", className = "ru.roman.bim.service.gae.wsclient.StoreSettingsResponse")
    @Action(input = "http://data.service.server.bim.roman.ru/DataProvider/storeSettingsRequest", output = "http://data.service.server.bim.roman.ru/DataProvider/storeSettingsResponse")
    public StoreSettingsResp storeSettings(
        @WebParam(name = "arg0", targetNamespace = "")
        StoreSettingsRequest arg0);

    /**
     * 
     * @param arg0
     */
    @WebMethod
    @RequestWrapper(localName = "renewRating", targetNamespace = "http://data.service.server.bim.roman.ru/", className = "ru.roman.bim.service.gae.wsclient.RenewRating")
    @ResponseWrapper(localName = "renewRatingResponse", targetNamespace = "http://data.service.server.bim.roman.ru/", className = "ru.roman.bim.service.gae.wsclient.RenewRatingResponse")
    @Action(input = "http://data.service.server.bim.roman.ru/DataProvider/renewRatingRequest", output = "http://data.service.server.bim.roman.ru/DataProvider/renewRatingResponse")
    public void renewRating(
        @WebParam(name = "arg0", targetNamespace = "")
        RenewRatingRequest arg0);

    /**
     * 
     * @param arg0
     * @return
     *     returns ru.roman.bim.service.gae.wsclient.SystemTaskResp
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "systemTask", targetNamespace = "http://data.service.server.bim.roman.ru/", className = "ru.roman.bim.service.gae.wsclient.SystemTask")
    @ResponseWrapper(localName = "systemTaskResponse", targetNamespace = "http://data.service.server.bim.roman.ru/", className = "ru.roman.bim.service.gae.wsclient.SystemTaskResponse")
    @Action(input = "http://data.service.server.bim.roman.ru/DataProvider/systemTaskRequest", output = "http://data.service.server.bim.roman.ru/DataProvider/systemTaskResponse")
    public SystemTaskResp systemTask(
        @WebParam(name = "arg0", targetNamespace = "")
        SystemTaskRequest arg0);

    /**
     * 
     * @param arg0
     * @return
     *     returns java.lang.Long
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "save", targetNamespace = "http://data.service.server.bim.roman.ru/", className = "ru.roman.bim.service.gae.wsclient.Save")
    @ResponseWrapper(localName = "saveResponse", targetNamespace = "http://data.service.server.bim.roman.ru/", className = "ru.roman.bim.service.gae.wsclient.SaveResponse")
    @Action(input = "http://data.service.server.bim.roman.ru/DataProvider/saveRequest", output = "http://data.service.server.bim.roman.ru/DataProvider/saveResponse")
    public Long save(
        @WebParam(name = "arg0", targetNamespace = "")
        SaveRequest arg0);

    /**
     * 
     * @param arg0
     * @return
     *     returns ru.roman.bim.service.gae.wsclient.GetListResp
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getList", targetNamespace = "http://data.service.server.bim.roman.ru/", className = "ru.roman.bim.service.gae.wsclient.GetList")
    @ResponseWrapper(localName = "getListResponse", targetNamespace = "http://data.service.server.bim.roman.ru/", className = "ru.roman.bim.service.gae.wsclient.GetListResponse")
    @Action(input = "http://data.service.server.bim.roman.ru/DataProvider/getListRequest", output = "http://data.service.server.bim.roman.ru/DataProvider/getListResponse")
    public GetListResp getList(
        @WebParam(name = "arg0", targetNamespace = "")
        GetListRequest arg0);

}
