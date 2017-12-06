
package com.dinstone.chain;

public interface Chain {

    Object intercept(Object request);

    Chain addLast(Interceptor interceptor);
}
