package ru.roman.bim.server.service.dataws.dto;

/** @author Roman 26.01.13 3:51 */
public class RequestInfo {

    private String version;
    private Long userId;
    private String passHash;
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

    public String getPassHash() {
        return passHash;
    }

    public void setPassHash(String passHash) {
        this.passHash = passHash;
    }

    @Override
    public String toString() {
        return "RequestInfo{" +
                "version='" + version + '\'' +
                ", userId=" + userId +
                ", Ip='" + Ip + '\'' +
                '}';
    }
}
