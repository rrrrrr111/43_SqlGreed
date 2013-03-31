package ru.roman.bim.gui.common.cbchain;

import junit.framework.Assert;
import org.junit.Test;

/** @author Roman 30.03.13 17:20 */
public class CallBackChainTest {


    @Test
    public void testRun() throws Exception {

        final int[] counter = new int[] {0};

        new CallBackChain<Void>(new CallBackChain(new CallBackChain() {
            @Override
            protected void onSuccess(Object result) {
                counter[0]++;
            }
        }) {
            @Override
            protected void onSuccess(Object result) {
                counter[0]++;
            }
        }) {
            @Override
            protected void onSuccess(Void result) {
                counter[0]++;
            }
        }.run(null);

        Assert.assertEquals(3, counter[0]);

    }


    @Test
    public void testInnerException() throws Exception {

        final RuntimeException exception = new RuntimeException("test Exception");
        final int[] counter = new int[] {0};

        try {
            new CallBackChain<Void>(new CallBackChain<Void>(new CallBackChain<Void>() {
                @Override
                protected void onSuccess(Void result) {
                    counter[0]++;
                }

                @Override
                protected void onInnerException(RuntimeException nexusEx, Void result, Exception failureEx) {
                    counter[0]++;
                    super.onInnerException(nexusEx, result, failureEx);
                }
            }) {
                @Override
                protected void onSuccess(Void result) {
                    throw exception;
                }

                @Override
                protected void onInnerException(RuntimeException nexusEx, Void result, Exception failureEx) {
                    counter[0]++;
                    super.onInnerException(nexusEx, result, failureEx);
                }
            }) {
                @Override
                protected void onSuccess(Void result) {
                    Assert.fail("Wrong logic 3");
                }
                @Override
                protected void onInnerException(RuntimeException nexusEx, Void result, Exception failureEx) {
                    if (nexusEx != exception) {
                        Assert.fail("Wrong logic 1");
                    } else {
                        counter[0]++;
                    }
                    super.onInnerException(nexusEx, result, failureEx);
                }
            }.run(null);
        } catch (RuntimeException e) {
            if (e != exception) {
                Assert.fail("Wrong logic 2");
            } else {
                counter[0]++;
            }
        }

        Assert.assertEquals(5, counter[0]);

    }

    @Test
    public void testException() throws Exception {

        final int[] counter = new int[] {0};
        new CallBackChain<Void>(new CallBackChain(new CallBackChain() {
            @Override
            protected void onSuccess(Object result) {
            }

            @Override
            protected void onFailure(Exception e) {
                counter[0]++;
            }
        }) {
            @Override
            protected void onSuccess(Object result) {
            }

            @Override
            protected void onFailure(Exception e) {
                counter[0]++;
            }
        }) {
            @Override
            protected void onSuccess(Void result) {
            }

            @Override
            protected void onFailure(Exception e) {
                counter[0]++;
            }
        }.exception(new Exception("Test exception"));

        Assert.assertEquals(3, counter[0]);
    }


    public static void main(String[] args) {
        new Thread() {
            @Override
            public void run() {
                new CallBackChain<Void>(new CallBackChain(new CallBackChain() {
                    @Override
                    protected void onSuccess(Object result) {
                    }
                }) {
                    @Override
                    protected void onSuccess(Object result) {
                    }
                }) {
                    @Override
                    protected void onSuccess(Void result) {
                    }
                }.exception(new Exception("Test exception 1"));
            }
        }.start();

        new Thread() {
            @Override
            public void run() {
                new CallBackChain<Void>(new CallBackChain(new CallBackChain() {
                    @Override
                    protected void onSuccess(Object result) {
                    }
                }) {
                    @Override
                    protected void onSuccess(Object result) {
                    }
                }) {
                    @Override
                    protected void onSuccess(Void result) {
                    }
                }.exception(new Exception("Test exception 2"));
            }
        }.start();

        new Thread() {
            @Override
            public void run() {
                new CallBackChain<Void>(new CallBackChain(new CallBackChain() {
                    @Override
                    protected void onSuccess(Object result) {
                    }
                }) {
                    @Override
                    protected void onSuccess(Object result) {
                    }
                }) {
                    @Override
                    protected void onSuccess(Void result) {
                    }
                }.exception(new Exception("Test exception 3"));
            }
        }.start();

        new Thread() {
            @Override
            public void run() {
                new CallBackChain<Void>(new CallBackChain(new CallBackChain() {
                    @Override
                    protected void onSuccess(Object result) {
                    }
                }) {
                    @Override
                    protected void onSuccess(Object result) {
                    }
                }) {
                    @Override
                    protected void onSuccess(Void result) {
                    }
                }.exception(new Exception("Test exception 4"));
            }
        }.start();

    }
}
