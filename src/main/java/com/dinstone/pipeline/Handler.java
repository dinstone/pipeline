package com.dinstone.pipeline;

public interface Handler {

    public void reqeust(HandlerContext context, Object request);

    public void response(HandlerContext context, Object response);
}
