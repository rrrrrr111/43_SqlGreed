/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.roman.greet.gui.pane.main;

import ru.roman.greet.gui.pane.PaineHolder;

import javax.swing.*;
import java.util.Stack;

/**
 *
 *
 *
 */
public class CommandsStack extends Stack<String> {

    private final MainView mainView = PaineHolder.getMainView();
    private int currentPosition;
    private int capacity;
    private static CommandsStack instance;

    public static CommandsStack getInstance(int capacity) {
        if (instance == null) {
            synchronized (CommandsStack.class) {
                if (instance == null) {
                    instance = new CommandsStack(capacity);
                }
            }
        }
        return instance;
    }

    private CommandsStack(int capacity) {
        super();
        this.capacity = capacity;
        renewCurrentPosition();
    }

    public void renewCurrentPosition() {
        currentPosition = size();
        actualizeActions();
    }

    public void push() {
        String someString = mainView.getTextArea().getText().trim();
        if (size() > 0) {
            if (someString.equals(get(size() - 1))) {
                return;
            }
        }
        if (size() == capacity) {
            remove(0);
        }
        super.push(someString);
        renewCurrentPosition();
    }

    public void undoString() {
        if (currentPosition == 0) {
            return;
        }
        currentPosition--;

        if (mainView.getTextArea().getText().trim().equals(get(currentPosition))) {
            undoString();
        }
        mainView.getTextArea().setText(get(currentPosition));
        actualizeActions();

    }

    public void redoString() {
        if (currentPosition > size() - 2) {
            return;
        }
        currentPosition++;
        mainView.getTextArea().setText(get(currentPosition));
        actualizeActions();
    }

    public void actualizeActions() {

        int someInt = size();

        final AbstractAction undoAction = mainView.getUndoAction();
        final AbstractAction redoAction = mainView.getRedoAction();
        if (someInt == 0) {
            undoAction.setEnabled(false);
            redoAction.setEnabled(false);
        } else {
            if (currentPosition == someInt) {
                undoAction.setEnabled(true);
                redoAction.setEnabled(false);
                return;
            }
            if (currentPosition == 0) {
                undoAction.setEnabled(false);
            } else {
                undoAction.setEnabled(true);
            }
            if (size() > 1) {
                redoAction.setEnabled(true);
            }
            if (currentPosition == size() - 1) {
                redoAction.setEnabled(false);
            }
        }
    }

    public int getCapacity() {
        return capacity;
    }
}
