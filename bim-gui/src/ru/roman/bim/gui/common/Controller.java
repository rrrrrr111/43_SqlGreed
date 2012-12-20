package ru.roman.bim.gui.common;

/** @author Roman 21.12.12 0:31 */
public abstract class Controller<T extends View> {

    protected T view;

    public Controller(T view) {
        this.view = view;
    }
}
