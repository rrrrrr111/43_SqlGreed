package ru.roman.greed.util;

/**
 *
 * @author Roman 01.09.12 2:17
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
}
