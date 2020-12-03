package com.dinstone.pipeline;

public interface Pipeline {

    Pipeline fireRequest(Object message);

    Pipeline fireResponse(Object message);

    Pipeline addLast(String name, Handler handler);

    Pipeline addFirst(String name, Handler handler);

    Pipeline remove(String name);

    Pipeline remove(Handler handler);

}
