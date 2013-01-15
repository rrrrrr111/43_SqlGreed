
package ru.roman.bim.service.gae.wsclient;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.4-b01
 * Generated source version: 2.2
 * 
 */
@WebServiceClient(name = "DataProvider", targetNamespace = "http://data.service.server.bim.roman.ru", wsdlLocation = "http://churganovroman.appspot.com/wsdl/DataProvider.wsdl")
public class DataProvider_Service
    extends Service
{

    private final static URL DATAPROVIDER_WSDL_LOCATION;
    private final static WebServiceException DATAPROVIDER_EXCEPTION;
    private final static QName DATAPROVIDER_QNAME = new QName("http://data.service.server.bim.roman.ru", "DataProvider");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("http://churganovroman.appspot.com/wsdl/DataProvider.wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        DATAPROVIDER_WSDL_LOCATION = url;
        DATAPROVIDER_EXCEPTION = e;
    }

    public DataProvider_Service() {
        super(__getWsdlLocation(), DATAPROVIDER_QNAME);
    }

    public DataProvider_Service(WebServiceFeature... features) {
        super(__getWsdlLocation(), DATAPROVIDER_QNAME, features);
    }

    public DataProvider_Service(URL wsdlLocation) {
        super(wsdlLocation, DATAPROVIDER_QNAME);
    }

    public DataProvider_Service(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, DATAPROVIDER_QNAME, features);
    }

    public DataProvider_Service(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public DataProvider_Service(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns DataProvider
     */
    @WebEndpoint(name = "DataProviderPort")
    public DataProvider getDataProviderPort() {
        return super.getPort(new QName("http://data.service.server.bim.roman.ru", "DataProviderPort"), DataProvider.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns DataProvider
     */
    @WebEndpoint(name = "DataProviderPort")
    public DataProvider getDataProviderPort(WebServiceFeature... features) {
        return super.getPort(new QName("http://data.service.server.bim.roman.ru", "DataProviderPort"), DataProvider.class, features);
    }

    private static URL __getWsdlLocation() {
        if (DATAPROVIDER_EXCEPTION!= null) {
            throw DATAPROVIDER_EXCEPTION;
        }
        return DATAPROVIDER_WSDL_LOCATION;
    }

}