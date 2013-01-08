package ru.roman.bim.server.service.data;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.StringTokenizer;
import java.util.logging.Logger;

/** @author Roman 07.01.13 23:43 */
public class DataProviderServlet extends HttpServlet {
    private static final Logger log = Logger.getLogger(DataProviderServlet.class.getName());

    static MessageFactory messageFactory;
    static DataProviderSOAPHandler soapHandler;


    static {
        try {
            messageFactory = MessageFactory.newInstance();
            soapHandler = new DataProviderSOAPHandler();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }


    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            // Get all the headers from the HTTP request
            MimeHeaders headers = getHeaders(req);

            // Construct a SOAPMessage from the XML in the request body
            InputStream is = req.getInputStream();
            SOAPMessage soapRequest = messageFactory.createMessage(headers, is);

            // Handle soapReqest
            SOAPMessage soapResponse = soapHandler.handleSOAPRequest(soapRequest);

            // Write to HttpServeltResponse
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.setContentType("text/xml;charset=\"utf-8\"");
            OutputStream os = resp.getOutputStream();
            soapResponse.writeTo(os);
            os.flush();
        } catch (SOAPException e) {
            throw new IOException("Exception while creating SOAP message.", e);
        }
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @SuppressWarnings("rawtypes")
    static MimeHeaders getHeaders(HttpServletRequest req) {
        Enumeration headerNames = req.getHeaderNames();
        MimeHeaders headers = new MimeHeaders();
        while (headerNames.hasMoreElements()) {
            String headerName = (String) headerNames.nextElement();
            String headerValue = req.getHeader(headerName);
            StringTokenizer values = new StringTokenizer(headerValue, ",");
            while (values.hasMoreTokens()) {
                headers.addHeader(headerName, values.nextToken().trim());
            }
        }
        return headers;
    }
}
