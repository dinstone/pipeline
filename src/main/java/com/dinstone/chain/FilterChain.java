package com.dinstone.chain;

/**
 * @author dinstone
 */
public class FilterChain implements Handler {

    private FilterContext headContext;

    private FilterContext tailContext;

    public FilterChain(Handler handler) {
        if (handler == null) {
            throw new IllegalArgumentException("handler is null");
        }
        this.tailContext = new FilterContext(new Filter() {
            @Override
            public Object filter(Object request, FilterContext chain) {
                return handler.handle(request);
            }
        });
        this.headContext = new FilterContext(null);

        this.headContext.next = tailContext;
        this.tailContext.prev = headContext;
    }

    public FilterChain addLast(Filter filter) {
        FilterContext now = new FilterContext(filter);

        FilterContext last = tailContext.prev;
        last.next = now;

        now.prev = last;
        now.next = tailContext;

        tailContext.prev = now;

        return this;
    }

    public Object handle(Object request) {
        return headContext.handle(request);
    }
}
