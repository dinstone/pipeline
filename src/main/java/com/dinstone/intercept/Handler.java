
package com.dinstone.intercept;

import java.util.concurrent.CompletableFuture;

public interface Handler {

    public CompletableFuture<Object> reqeust(Object request);

}
