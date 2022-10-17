
package com.dinstone.intercept;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.junit.Test;

public class ContextHandlerTest {

    @Test
    public void test01() throws InterruptedException, ExecutionException {
        Handler bh = new Handler() {

            @Override
            public CompletableFuture<Object> reqeust(Object request) {
                return CompletableFuture.completedFuture("reply response");
            }
        };
        ContextHandler ch = new ContextHandler(bh, null);
        CompletableFuture<Object> rf = ch.reqeust("call request");
        assertEquals("reply response", rf.get().toString());
    }

    @Test
    public void test02() throws InterruptedException, ExecutionException {
        Handler bh = new Handler() {

            @Override
            public CompletableFuture<Object> reqeust(Object request) {
                return CompletableFuture.completedFuture("reply response");
            }
        };

        List<Interceptor> is = new ArrayList<>();
        is.add(new Interceptor() {

            @Override
            public CompletableFuture<Object> reqeustIntercept(Context context, Object request) {
                System.out.println("first request intercept");
                return context.nextRequest(request);
            }

        });

        is.add(new Interceptor() {

            @Override
            public CompletableFuture<Object> reqeustIntercept(Context context, Object request) {
                System.out.println("second request intercept");
                return context.nextRequest(request);
            }

        });

        ContextHandler ch = new ContextHandler(bh, is.listIterator());
        CompletableFuture<Object> rf = ch.reqeust("call request").thenApply(u -> {
            System.out.println("convert " + u);
            return "reply";
        });
        String r = rf.get().toString();
        System.out.println("main get " + r);
        assertEquals("reply", r);
    }

    @Test
    public void test03() throws InterruptedException, ExecutionException {
        Handler bh = new Handler() {

            @Override
            public CompletableFuture<Object> reqeust(Object request) {
                return async().thenApply(response -> {
                    System.out.println(Thread.currentThread().getName() + ": convert response to reply");
                    return "" + response;
                });
            }

            private CompletableFuture<String> async() {
                CompletableFuture<String> rf = new CompletableFuture<String>();
                new Thread(new Runnable() {

                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                        }
                        rf.complete("response");
                        // if (new Random().nextBoolean()) {
                        // rf.complete("response");
                        // } else {
                        // rf.completeExceptionally(new RuntimeException("error"));
                        // }
                    }
                }).start();
                return rf;
            }
        };

        List<Interceptor> is = new ArrayList<>();
        is.add(new Interceptor() {

            @Override
            public CompletableFuture<Object> reqeustIntercept(Context context, Object request) {
                System.out.println(Thread.currentThread().getName() + ": first request intercept");
                return context.nextRequest(request).whenComplete((r, e) -> {
                    System.out.println(Thread.currentThread().getName() + ": first response complete");
                });
            }

        });

        is.add(new Interceptor() {

            @Override
            public CompletableFuture<Object> reqeustIntercept(Context context, Object request) {
                System.out.println(Thread.currentThread().getName() + ": second request intercept");
                return context.nextRequest(request).whenComplete((r, e) -> {
                    System.out.println(Thread.currentThread().getName() + ": second response complete");
                });
            }

        });

        ContextHandler ch = new ContextHandler(bh, is.listIterator());
        CompletableFuture<Object> rf = ch.reqeust("call request").thenApply(u -> {
            System.out.println(Thread.currentThread().getName() + ": parse reply " + u);
            return "reply";
        });
        try {
            Object r = rf.get();
            System.out.println(Thread.currentThread().getName() + ": get reply " + r);
            assertEquals("reply", r);
        } catch (Exception e) {
            assertEquals("error", e.getCause().getMessage());
        }
    }

    @Test
    public void test04() throws InterruptedException, ExecutionException {
        Handler bh = new Handler() {

            @Override
            public CompletableFuture<Object> reqeust(Object request) {
                return async().thenApply(response -> {
                    System.out.println(Thread.currentThread().getName() + ": convert response to reply");
                    return "" + response;
                });
            }

            private CompletableFuture<String> async() {
                CompletableFuture<String> rf = new CompletableFuture<String>();
                new Thread(new Runnable() {

                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                        }
                        rf.completeExceptionally(new RuntimeException("error"));
                    }
                }).start();
                return rf;
            }
        };

        List<Interceptor> is = new ArrayList<>();
        is.add(new Interceptor() {

            @Override
            public CompletableFuture<Object> reqeustIntercept(Context context, Object request) {
                System.out.println(Thread.currentThread().getName() + ": first request intercept");
                return context.nextRequest(request).whenComplete((r, e) -> {
                    System.out.println(Thread.currentThread().getName() + ": first response complete");
                });
            }

        });

        is.add(new Interceptor() {

            @Override
            public CompletableFuture<Object> reqeustIntercept(Context context, Object request) {
                System.out.println(Thread.currentThread().getName() + ": second request intercept");
                return context.nextRequest(request).whenComplete((r, e) -> {
                    System.out.println(Thread.currentThread().getName() + ": second response complete");
                });
            }

        });

        ContextHandler ch = new ContextHandler(bh, is.listIterator());
        CompletableFuture<Object> rf = ch.reqeust("call request").thenApply(u -> {
            System.out.println(Thread.currentThread().getName() + ": parse reply " + u);
            return "reply";
        });
        try {
            Object r = rf.get();
            System.out.println(Thread.currentThread().getName() + ": get reply " + r);
            assertEquals("reply", r);
        } catch (Exception e) {
            assertEquals("error", e.getCause().getMessage());
        }
    }

    @Test
    public void test05() throws InterruptedException, ExecutionException {
        Handler bh = new Handler() {

            @Override
            public CompletableFuture<Object> reqeust(Object request) {
                return async().thenApply(response -> {
                    System.out.println(Thread.currentThread().getName() + ": convert response to reply");
                    return "reply";
                });
            }

            private CompletableFuture<String> async() {
                return CompletableFuture.completedFuture("response");
            }
        };

        List<Interceptor> is = new ArrayList<>();
        is.add(new Interceptor() {

            @Override
            public CompletableFuture<Object> reqeustIntercept(Context context, Object request) {
                System.out.println(Thread.currentThread().getName() + ": first request intercept");
                return context.nextRequest(request).whenComplete((r, e) -> {
                    System.out.println(Thread.currentThread().getName() + ": first response complete");
                });
            }
        });

        is.add(new Interceptor() {

            @Override
            public CompletableFuture<Object> reqeustIntercept(Context context, Object request) {
                System.out.println(Thread.currentThread().getName() + ": second request intercept");
                return context.nextRequest(request).whenComplete((r, e) -> {
                    System.out.println(Thread.currentThread().getName() + ": second response complete");
                });
            }
        });

        ContextHandler ch = new ContextHandler(bh, is.listIterator());
        CompletableFuture<Object> rf = ch.reqeust("call request").thenApply(u -> {
            System.out.println(Thread.currentThread().getName() + ": parse reply " + u);
            return "reply";
        });
        try {
            Object r = rf.get();
            System.out.println(Thread.currentThread().getName() + ": get reply " + r);
            assertEquals("reply", r);
        } catch (Exception e) {
            assertEquals("error", e.getCause().getMessage());
        }
    }
}
