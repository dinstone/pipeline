package com.dinstone.chain;

public class FilterContext {

    private FilterChain chain;
    private Filter filter;

    FilterContext prev;
    FilterContext next;

    public FilterContext(FilterChain chain, Filter filter) {
        this.chain = chain;
        this.filter = filter;
    }

    public Object handle(Object request) {
        if (filter != null) {
            return filter.filter(request, next);
        } else {
            return next.handle(request);
        }
    }

    public FilterChain getChain() {
        return chain;
    }
}
