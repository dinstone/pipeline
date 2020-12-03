package com.dinstone.chain;

public interface Filter {

    Object filter(Object request, FilterContext chain);
}
