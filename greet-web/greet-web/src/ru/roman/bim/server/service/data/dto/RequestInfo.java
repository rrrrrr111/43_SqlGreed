package ru.roman.bim.server.service.data.dto;

/** @author Roman 26.01.13 3:51 */
public class RequestInfo {

    private String version;
    private Long userId;
    private String Ip;


    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getIp() {
        return Ip;
    }

    public void setIp(String ip) {
        Ip = ip;
    }
}
