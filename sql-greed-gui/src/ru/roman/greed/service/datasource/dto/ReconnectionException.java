package ru.roman.greed.service.datasource.dto;

/**
 * @author Roman 15.09.12 13:24
 */
public class ReconnectionException extends RuntimeException {

    public ReconnectionException() {
    }

    public ReconnectionException(String message) {
        super(message);
    }

    public ReconnectionException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReconnectionException(Throwable cause) {
        super(cause);
    }

}
