package com.dinstone.pipeline;

public interface HandlerContext {

    void fireRequest(Object message);

    void fireResponse(Object message);

    Pipeline pipeline();

    Handler handler();

}
