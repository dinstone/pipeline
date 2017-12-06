
package com.dinstone.chain;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class DefaultChain implements Chain {

    private Queue<Interceptor> interceptors = new ConcurrentLinkedQueue<Interceptor>();

    private Handler handler;

    public DefaultChain(Handler handler) {
        if (handler == null) {
            throw new IllegalArgumentException("handler is null");
        }
        this.handler = handler;
    }

    public Object intercept(Object request) {
        Interceptor current = interceptors.poll();
        if (current == null) {
            return handler.handle(request);
        }
        return current.intercept(request, this);
    }

    public Chain addLast(Interceptor interceptor) {
        interceptors.add(interceptor);
        return this;
    }

}
