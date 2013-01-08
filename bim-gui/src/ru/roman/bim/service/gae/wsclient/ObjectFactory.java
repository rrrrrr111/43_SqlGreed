
package ru.roman.bim.service.gae.wsclient;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the ru.roman.bim.service.gae.wsclient package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _RenewRatingResponse_QNAME = new QName("http://data.service.server.bim.roman.ru/", "renewRatingResponse");
    private final static QName _GetList_QNAME = new QName("http://data.service.server.bim.roman.ru/", "getList");
    private final static QName _GetListResponse_QNAME = new QName("http://data.service.server.bim.roman.ru/", "getListResponse");
    private final static QName _SaveResponse_QNAME = new QName("http://data.service.server.bim.roman.ru/", "saveResponse");
    private final static QName _RenewRating_QNAME = new QName("http://data.service.server.bim.roman.ru/", "renewRating");
    private final static QName _Save_QNAME = new QName("http://data.service.server.bim.roman.ru/", "save");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: ru.roman.bim.service.gae.wsclient
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Save }
     * 
     */
    public Save createSave() {
        return new Save();
    }

    /**
     * Create an instance of {@link RenewRating }
     * 
     */
    public RenewRating createRenewRating() {
        return new RenewRating();
    }

    /**
     * Create an instance of {@link SaveResponse }
     * 
     */
    public SaveResponse createSaveResponse() {
        return new SaveResponse();
    }

    /**
     * Create an instance of {@link GetListResponse }
     * 
     */
    public GetListResponse createGetListResponse() {
        return new GetListResponse();
    }

    /**
     * Create an instance of {@link GetList }
     * 
     */
    public GetList createGetList() {
        return new GetList();
    }

    /**
     * Create an instance of {@link RenewRatingResponse }
     * 
     */
    public RenewRatingResponse createRenewRatingResponse() {
        return new RenewRatingResponse();
    }

    /**
     * Create an instance of {@link GaeGetListRequest }
     * 
     */
    public GaeGetListRequest createGaeGetListRequest() {
        return new GaeGetListRequest();
    }

    /**
     * Create an instance of {@link BimItemModel }
     * 
     */
    public BimItemModel createBimItemModel() {
        return new BimItemModel();
    }

    /**
     * Create an instance of {@link GaeGetListResponse }
     * 
     */
    public GaeGetListResponse createGaeGetListResponse() {
        return new GaeGetListResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RenewRatingResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://data.service.server.bim.roman.ru/", name = "renewRatingResponse")
    public JAXBElement<RenewRatingResponse> createRenewRatingResponse(RenewRatingResponse value) {
        return new JAXBElement<RenewRatingResponse>(_RenewRatingResponse_QNAME, RenewRatingResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetList }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://data.service.server.bim.roman.ru/", name = "getList")
    public JAXBElement<GetList> createGetList(GetList value) {
        return new JAXBElement<GetList>(_GetList_QNAME, GetList.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetListResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://data.service.server.bim.roman.ru/", name = "getListResponse")
    public JAXBElement<GetListResponse> createGetListResponse(GetListResponse value) {
        return new JAXBElement<GetListResponse>(_GetListResponse_QNAME, GetListResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SaveResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://data.service.server.bim.roman.ru/", name = "saveResponse")
    public JAXBElement<SaveResponse> createSaveResponse(SaveResponse value) {
        return new JAXBElement<SaveResponse>(_SaveResponse_QNAME, SaveResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RenewRating }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://data.service.server.bim.roman.ru/", name = "renewRating")
    public JAXBElement<RenewRating> createRenewRating(RenewRating value) {
        return new JAXBElement<RenewRating>(_RenewRating_QNAME, RenewRating.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Save }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://data.service.server.bim.roman.ru/", name = "save")
    public JAXBElement<Save> createSave(Save value) {
        return new JAXBElement<Save>(_Save_QNAME, Save.class, null, value);
    }

}
