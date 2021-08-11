package com.dinstone.chain;

public interface FilterChain {
    FilterChainHandler addLast(Filter filter);
}
