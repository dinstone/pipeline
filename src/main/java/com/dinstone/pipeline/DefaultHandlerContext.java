
package com.dinstone.pipeline;

public class DefaultHandlerContext implements HandlerContext {

    volatile DefaultHandlerContext nextHandlerContext;

    volatile DefaultHandlerContext prevHandlerContext;

    private final DefaultPipeline pipeline;

    private final String name;

    private final Handler handler;

    public DefaultHandlerContext(DefaultPipeline pipeline, String name, Handler handler) {
        super();
        this.pipeline = pipeline;
        this.name = name;
        this.handler = handler;
    }

    public void fireRequest(Object message) {
        this.nextHandlerContext.invokeRequest(message);
    }

    private void invokeRequest(Object message) {
        handler.reqeust(this, message);
    }

    public String name() {
        return name;
    }

    public Pipeline pipeline() {
        return pipeline;
    }

    public void fireResponse(Object message) {
        this.prevHandlerContext.invokeResponse(message);
    }

    private void invokeResponse(Object message) {
        handler.response(this, message);
    }

    public Handler handler() {
        return handler;
    }

}
