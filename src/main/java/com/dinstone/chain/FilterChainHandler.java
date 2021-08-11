package com.dinstone.chain;

/**
 * @author dinstone
 */
public class FilterChainHandler implements FilterChain, Handler {

    private FilterContext headContext;

    private FilterContext tailContext;

    public FilterChainHandler(Handler handler) {
        if (handler == null) {
            throw new IllegalArgumentException("handler is null");
        }
        this.tailContext = new FilterContext(this, new Filter() {
            @Override
            public Object filter(Object request, FilterContext chain) {
                return handler.handle(request);
            }
        });
        this.headContext = new FilterContext(this, null);

        this.headContext.next = tailContext;
        this.tailContext.prev = headContext;
    }

    @Override
    public FilterChainHandler addLast(Filter filter) {
        FilterContext now = new FilterContext(this, filter);

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
