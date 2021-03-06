package ru.roman.bim.util;

import ru.roman.bim.gui.pane.settings.Settings;
import ru.roman.bim.gui.pane.settings.SettingsViewModel;
import ru.roman.bim.service.gae.wsclient.AbstractRequest;
import ru.roman.bim.service.gae.wsclient.RequestInfo;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.GregorianCalendar;

/** @author Roman 08.01.13 17:30 */
public abstract class WsUtil {

    /**
     * Needed to create XMLGregorianCalendar instances
     */
    private static DatatypeFactory df = null;
    static {
        try {
            df = DatatypeFactory.newInstance();
        } catch (DatatypeConfigurationException dce) {
            throw new IllegalStateException(
                    "Exception while obtaining DatatypeFactory instance", dce);
        }
    }

    /**
     * Converts a java.util.Date into an instance of XMLGregorianCalendar
     *
     * @param date Instance of java.util.Date or a null reference
     * @return XMLGregorianCalendar instance whose value is based upon the
     *  value in the date parameter. If the date parameter is null then
     *  this method will simply return null.
     */
    public static XMLGregorianCalendar asXMLGregorianCalendar(java.util.Date date) {
        if (date == null) {
            return null;
        } else {
            GregorianCalendar gc = new GregorianCalendar();
            gc.setTimeInMillis(date.getTime());
            return df.newXMLGregorianCalendar(gc);
        }
    }

    public static XMLGregorianCalendar getCurrGregorian() {
        return asXMLGregorianCalendar(new Date());
    }

    /**
     * Converts an XMLGregorianCalendar to an instance of java.util.Date
     *
     * @param xgc Instance of XMLGregorianCalendar or a null reference
     * @return java.util.Date instance whose value is based upon the
     *  value in the xgc parameter. If the xgc parameter is null then
     *  this method will simply return null.
     */
    public static java.util.Date asDate(XMLGregorianCalendar xgc) {
        if (xgc == null) {
            return null;
        } else {
            return xgc.toGregorianCalendar().getTime();
        }
    }

    public static <T extends AbstractRequest> T prepareRequest(T req) {
        final RequestInfo requestInfo = new RequestInfo();
        final SettingsViewModel sett = Settings.get();
        if (sett == null) {
            requestInfo.setUserId(-1L);
            requestInfo.setPassHash(null);
        } else {
            requestInfo.setUserId(sett.getId());
            requestInfo.setPassHash(sett.getPassword());
        }
        requestInfo.setVersion(Const.VERSION);
        try {
            requestInfo.setIp(InetAddress.getLocalHost().toString());
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
        req.setRequestInfo(requestInfo);
        return req;
    }
}
