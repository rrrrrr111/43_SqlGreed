package ru.roman.greed.util;

/**
 * Created with IntelliJ IDEA.
 * User: Roman
 * Date: 01.09.12
 * Time: 2:17
 * To change this template use File | Settings | File Templates.
 */
public class AppGreedException extends RuntimeException {

    public AppGreedException() {
    }

    public AppGreedException(String message) {
        super(message);
    }

    public AppGreedException(String message, Throwable cause) {
        super(message, cause);
    }

    public AppGreedException(Throwable cause) {
        super(cause);
    }

    public AppGreedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
