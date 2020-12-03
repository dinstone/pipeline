package com.dinstone.chain;

class FilterContext {

    private Filter filter;

    FilterContext prev;
    FilterContext next;

    FilterContext(Filter filter) {
        this.filter = filter;
    }

    public Object handle(Object request) {
        if (filter != null) {
            return filter.filter(request, next);
        } else {
            return next.handle(request);
        }
    }

}
