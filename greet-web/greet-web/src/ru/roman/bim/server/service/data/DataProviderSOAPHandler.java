package ru.roman.bim.server.service.data;

import org.apache.commons.beanutils.MethodUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import ru.roman.bim.server.service.data.jaxws.*;

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
    private static final QName REGISTER_NEW_AND_LOAD_SETTINGS_QNAME = new QName(NAMESPACE_URI,"registerNewAndLoadSettings");


    private final MessageFactory messageFactory;
    private final DataProviderAdapter greeterAdapter;


    public DataProviderSOAPHandler() throws SOAPException {
        messageFactory = MessageFactory.newInstance();
        greeterAdapter = new DataProviderAdapter();
    }

    @SuppressWarnings("rawtypes")
    public SOAPMessage handleSOAPRequest(SOAPMessage request) throws SOAPException {
        SOAPBody soapBody = request.getSOAPBody();
        final Iterator iterator = soapBody.getChildElements();
        Object responsePojo = null;
        while (iterator.hasNext()) {
            final Object next = iterator.next();
            if (next instanceof SOAPElement) {
                final SOAPElement soapElement = (SOAPElement) next;
                final QName qname = soapElement.getElementQName();
                final String name = qname.getLocalPart();

                if(SAVE_QNAME.equals(qname)) {
                    responsePojo = handleRequest(soapElement, name, Save.class);
                    break;
                } else if(GET_LIST_QNAME.equals(qname)) {
                    responsePojo = handleRequest(soapElement, name, GetList.class);
                    break;
                } else if(RENEW_RATING_QNAME.equals(qname)) {
                    responsePojo = handleRequest(soapElement, name, RenewRating.class);
                    break;
                } else if(STORE_SETTINGS_QNAME.equals(qname)) {
                    responsePojo = handleRequest(soapElement, name, StoreSettings.class);
                    break;
                } else if(REGISTER_NEW_AND_LOAD_SETTINGS_QNAME.equals(qname)) {
                    responsePojo = handleRequest(soapElement, name, RegisterNewAndLoadSettings.class);
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

    private <T> Object handleRequest(SOAPElement soapElement, String name, Class<T> clazz){
        T res = JAXB.unmarshal(new DOMSource(soapElement), clazz);
        try {
            return MethodUtils.invokeMethod(greeterAdapter, name, res);
        } catch (Exception e) {
            throw new RuntimeException(ExceptionUtils.getRootCauseMessage(e), e);
        }
    }
}
