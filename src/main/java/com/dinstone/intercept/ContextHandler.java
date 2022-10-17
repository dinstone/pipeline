
package com.dinstone.intercept;

import java.util.ListIterator;
import java.util.concurrent.CompletableFuture;

public class ContextHandler implements Context, Handler {

    Handler nextHandler;

    ListIterator<Interceptor> listIterator;

    public ContextHandler(Handler nextHandler, ListIterator<Interceptor> listIterator) {
        super();
        this.nextHandler = nextHandler;
        this.listIterator = listIterator;
    }

    @Override
    public CompletableFuture<Object> reqeust(Object request) {
        return nextRequest(request);
    }

    @Override
    public CompletableFuture<Object> nextRequest(Object request) {
        if (listIterator != null && listIterator.hasNext()) {
            return listIterator.next().reqeustIntercept(this, request);
        } else {
            return nextHandler.reqeust(request);
        }
    }

}
