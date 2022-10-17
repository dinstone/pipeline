
package com.dinstone.intercept;

import java.util.concurrent.CompletableFuture;

public interface Context {

    CompletableFuture<Object> nextRequest(Object request);
}
