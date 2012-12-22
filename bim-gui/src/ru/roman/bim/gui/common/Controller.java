package ru.roman.bim.gui.common;

/** @author Roman 21.12.12 0:31 */
public abstract class Controller<V extends View, M extends Model> {

    protected V view;
    protected M currModel;

    public Controller(V view) {
        this.view = view;
    }

    public M getModel() {
        return currModel;
    }
}
