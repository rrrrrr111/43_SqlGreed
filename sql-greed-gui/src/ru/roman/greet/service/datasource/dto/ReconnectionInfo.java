package ru.roman.greet.service.datasource.dto;

import org.apache.commons.lang3.ObjectUtils;

import java.io.Serializable;

/**
 * @author Roman 15.09.12 13:01
 */
public class ReconnectionInfo implements Serializable, Cloneable {

    private String alias;
    private String url;
    private String driverClazz;
    private String username;
    private String password;
    private Boolean autoCommit;


    public ReconnectionInfo() {
    }

    public ReconnectionInfo(String alias, String url, String driverClazz, String username, String password, Boolean autoCommit) {
        this.alias = alias;
        this.url = url;
        this.driverClazz = driverClazz;
        this.username = username;
        this.password = password;
        this.autoCommit = autoCommit;
    }

    public String[] asArray() {
        return new String[] {
                alias,
                url,
                driverClazz,
                username,
                password,
                ObjectUtils.toString(autoCommit)
        };
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDriverClazz() {
        return driverClazz;
    }

    public void setDriverClazz(String driverClazz) {
        this.driverClazz = driverClazz;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public Boolean getAutoCommit() {
        return autoCommit;
    }

    public void setAutoCommit(Boolean autoCommit) {
        this.autoCommit = autoCommit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReconnectionInfo)) return false;

        ReconnectionInfo that = (ReconnectionInfo) o;

        if (alias != null ? !alias.equals(that.alias) : that.alias != null) return false;
        if (autoCommit != null ? !autoCommit.equals(that.autoCommit) : that.autoCommit != null) return false;
        if (driverClazz != null ? !driverClazz.equals(that.driverClazz) : that.driverClazz != null) return false;
        if (password != null ? !password.equals(that.password) : that.password != null) return false;
        if (url != null ? !url.equals(that.url) : that.url != null) return false;
        return !(username != null ? !username.equals(that.username) : that.username != null);

    }

    @Override
    public int hashCode() {
        int result = alias != null ? alias.hashCode() : 0;
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + (driverClazz != null ? driverClazz.hashCode() : 0);
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (autoCommit != null ? autoCommit.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ReconnectionInfo{" +
                "alias='" + alias + '\'' +
                ", url='" + url + '\'' +
                ", driverClazz='" + driverClazz + '\'' +
                ", username='" + username + '\'' +
                ", autoCommit=" + autoCommit +
                '}';
    }

    @Override
    public ReconnectionInfo clone()  {
        try {
            return (ReconnectionInfo)super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
}
