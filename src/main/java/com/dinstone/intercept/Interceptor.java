
package com.dinstone.intercept;

import java.util.concurrent.CompletableFuture;

public interface Interceptor {

    public CompletableFuture<Object> reqeustIntercept(Context context, Object request);

}
