package ru.roman.bim.service.ghost;

/** @author Roman 10.01.13 23:43 */
public interface GhostService {

    void start();

    void startFromOpened();

    void stop();

    void delayedStart(DelayedAction action);

    public interface DelayedAction {
        void afterDelay();
    }
}
