package ru.roman.bim.gui.common;

/** @author Roman 21.12.12 0:34 */
public interface View {

    <T extends View> Controller<T> getController();
}
