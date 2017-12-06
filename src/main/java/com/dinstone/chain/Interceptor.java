
package com.dinstone.chain;

public interface Interceptor {

    Object intercept(Object request, Chain chain);
}
