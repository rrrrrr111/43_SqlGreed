package ru.roman.bim.server.service.dataws.dto;

import java.io.Serializable;

/** @author Roman 26.01.13 3:39 */
public class AbstractRequest implements Serializable {

    private RequestInfo requestInfo;


    public RequestInfo getRequestInfo() {
        return requestInfo;
    }

    public void setRequestInfo(RequestInfo requestInfo) {
        this.requestInfo = requestInfo;
    }
}
