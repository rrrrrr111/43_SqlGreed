package ru.roman.bim.gui.common.cbchain;

import ru.roman.bim.util.ExceptionHandler;

/**
 * Для реализации цепочки коллбэк вызовов. Экземпляры кладутся в конструкторы друг друга
 * previous  - будет выполнен перед создаваемым колбеком
 * next  - будет выполнен после создаваемого колбека
 * Конечный сервис запускающий цепочку должен дернуть метод run(..), а при ошибке exception(..)
 * Конечный сервис может дернуть методы любого из звеньев цепочки, коллебеки будут выполнться
 * в последовательности заданной при создании. При выбросе RuntimeException одного из звеньев, остальные
 * звенья в цепочке не выполняются, ошибка пробрасывается в методе onInnerException(..) по всем звеньям,
 * при необходимости можно переопределить.
 *
 *
 * @author Roman 30.03.13 16:46 */
public abstract class CallBackChain<T> {

    private CallBackChain<T> previous = null;
    private CallBackChain<T> next = null;
    private static ThreadLocal<Boolean> defaultFailureActionExecuted = new ThreadLocal<Boolean>() {
        @Override
        protected Boolean initialValue() {
            return false;
        }
    };


    /**
     *
     * @param previous  - будет выполнен перед данным колбеком
     * @param next  - будет выполнен после данного колбека
     */
    public CallBackChain(CallBackChain<T> previous, CallBackChain<T> next) {
       if (previous != null) {
            if (previous.next == null) {
                previous.next = this;
                this.previous = previous;
            } else {
                throw new ChaneCreationException("Previous nexus already set to : " + this.previous.next);
            }
        }
        if (next != null) {
            if (next.previous == null) {
                next.previous = this;
                this.next = next;
            } else {
                throw new ChaneCreationException("Next nexus already set to : " + this.next.next);
            }
        }
    }
    /**
     *
     * @param previous  - будет выполнен перед данным колбеком
     */
    public CallBackChain(CallBackChain<T> previous) {
        this(previous, null);
    }

    protected CallBackChain() {
        this(null, null);
    }



    public void run(T result){
        if (getPrevious() == null) {
            runSuccess(result);
        } else {
            getPrevious().run(result);
        }
    }

    private void runSuccess(T result) {
        try {
            onSuccess(result);
        } catch (RuntimeException ex) {
            onInner(ex, result, null);
            return;
        }
        if (getNext() != null) {
            getNext().runSuccess(result);
        }
    }

    protected abstract void onSuccess(T result);

    public void exception(Exception e) {
        if (getPrevious() == null) {
            exceptionFailure(e);
        } else {
            getPrevious().exception(e);
        }
    }

    private void exceptionFailure(Exception e) {
        try {
            onFailure(e);
        } catch (RuntimeException ex) {
            onInner(ex, null, e);
            return;
        }
        if (getNext() != null) {
            getNext().exceptionFailure(e);
        }
    }

    protected void onFailure(Exception e) {
        if (!defaultFailureActionExecuted.get()) {
            defaultFailureActionExecuted.set(true);
            ExceptionHandler.showErrorMessage(e);
        }
    }



    private void onInner(RuntimeException nexusEx, T result, Exception failureEx) {
        if (getPrevious() == null) {
            onInnerException(nexusEx, result, failureEx);
        } else {
            getPrevious().onInner(nexusEx, result, failureEx);
        }
    }

    /**
     * Выполняется при ошибке в методе одного из коллбеков onFailure(..) или onSuccess(..)
     * Если метод переопределяется, от должен вызвать метод суперкласса onInnerException(..)
     * для обратки екзепшона остальными звеньями.
     *
     * @param nexusEx - ошибка в предыдущем коллбеке
     * @param result - если не null то ошибка в onSuccess(..)
     * @param failureEx - если не null то ошибка в onFailure(..)
     */
    protected void onInnerException(RuntimeException nexusEx, T result, Exception failureEx) {
        if (getNext() == null) {
            throw nexusEx;
        } else {
            getNext().onInnerException(nexusEx, result, failureEx);
        }
    }

    public CallBackChain<T> getPrevious() {
        return previous;
    }

    public CallBackChain<T> getNext() {
        return next;
    }

}
