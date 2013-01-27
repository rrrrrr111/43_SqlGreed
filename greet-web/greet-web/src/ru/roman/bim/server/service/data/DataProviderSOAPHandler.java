package ru.roman.bim.server.service.data;

import ru.roman.bim.server.service.data.jaxws.GetList;
import ru.roman.bim.server.service.data.jaxws.RenewRating;
import ru.roman.bim.server.service.data.jaxws.Save;
import ru.roman.bim.server.service.data.jaxws.StoreSettings;

import javax.xml.bind.JAXB;
import javax.xml.namespace.QName;
import javax.xml.soap.*;
import javax.xml.transform.dom.DOMSource;
import java.util.Iterator;

/** @author Roman 07.01.13 23:47 */
public class DataProviderSOAPHandler {

    private static final String NAMESPACE_URI = "http://data.service.server.bim.roman.ru/";
    private static final QName SAVE_QNAME = new QName(NAMESPACE_URI,"save");
    private static final QName GET_LIST_QNAME = new QName(NAMESPACE_URI,"getList");
    private static final QName RENEW_RATING_QNAME = new QName(NAMESPACE_URI,"renewRating");
    private static final QName STORE_SETTINGS_QNAME = new QName(NAMESPACE_URI,"storeSettings");


    private MessageFactory messageFactory;
    private DataProviderAdapter greeterAdapter;


    public DataProviderSOAPHandler() throws SOAPException {
        messageFactory = MessageFactory.newInstance();
        greeterAdapter = new DataProviderAdapter();
    }

    @SuppressWarnings("rawtypes")
    public SOAPMessage handleSOAPRequest(SOAPMessage request) throws SOAPException {
        SOAPBody soapBody = request.getSOAPBody();
        Iterator iterator = soapBody.getChildElements();
        Object responsePojo = null;
        while (iterator.hasNext()) {
            Object next = iterator.next();
            if (next instanceof SOAPElement) {
                SOAPElement soapElement = (SOAPElement) next;
                QName qname = soapElement.getElementQName();
                if(SAVE_QNAME.equals(qname)) {
                    responsePojo = handleSave(soapElement);
                    break;
                } else if(GET_LIST_QNAME.equals(qname)) {
                    responsePojo = handleGetList(soapElement);
                    break;
                } else if(RENEW_RATING_QNAME.equals(qname)) {
                    responsePojo = handleRenewRating(soapElement);
                    break;
                } else if(STORE_SETTINGS_QNAME.equals(qname)) {
                    responsePojo = handleStoreSettings(soapElement);
                    break;
                }
            }
        }

        SOAPMessage soapResponse = messageFactory.createMessage();
        soapBody = soapResponse.getSOAPBody();
        if (responsePojo != null) {
            JAXB.marshal(responsePojo, new SAAJResult(soapBody));
        } else {
            SOAPFault fault = soapBody.addFault();
            fault.setFaultString("Unrecognized SOAP request.");
        }
        return soapResponse;
    }

    private Object handleSave(SOAPElement soapElement){
        Save res = JAXB.unmarshal(new DOMSource(soapElement), Save.class);
        return greeterAdapter.save(res);
    }

    private Object handleGetList(SOAPElement soapElement){
        GetList res = JAXB.unmarshal(new DOMSource(soapElement), GetList.class);
        return greeterAdapter.getList(res);
    }

    private Object handleRenewRating(SOAPElement soapElement){
        RenewRating res = JAXB.unmarshal(new DOMSource(soapElement), RenewRating.class);
        return greeterAdapter.renewRating(res);
    }

    private Object handleStoreSettings(SOAPElement soapElement){
        StoreSettings res = JAXB.unmarshal(new DOMSource(soapElement), StoreSettings.class);
        return greeterAdapter.storeSettings(res);
    }



}
