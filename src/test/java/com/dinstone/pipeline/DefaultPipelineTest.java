package com.dinstone.pipeline;

import org.junit.Test;

public class DefaultPipelineTest {

    @Test
    public void testAddLast() {
        DefaultPipeline pipeline = new DefaultPipeline();
        pipeline.addLast("first", new Handler() {

            public void reqeust(HandlerContext context, Object reqeust) {
                System.out.println("first reqeust");
                context.fireRequest(reqeust);
                System.out.println("first reqeust");
            }

            public void response(HandlerContext context, Object response) {
                System.out.println("first response");
                context.fireResponse(response);
                System.out.println("first response");
            }

        });

        pipeline.addLast("second", new Handler() {

            public void reqeust(HandlerContext context, Object reqeust) {
                System.out.println("second reqeust");
                context.fireRequest(reqeust);
            }

            public void response(HandlerContext context, Object response) {
                System.out.println("second response");
                context.fireResponse(response);
            }

        });

        pipeline.fireRequest("messge");

        pipeline.fireResponse("messge");
    }

    @Test
    public void testAddFirst() {
        DefaultPipeline pipeline = new DefaultPipeline();

        pipeline.addFirst("second", new Handler() {

            public void reqeust(HandlerContext context, Object reqeust) {
                System.out.println("second reqeust");
                context.fireRequest(reqeust);
            }

            public void response(HandlerContext context, Object response) {
                System.out.println("second response");
                context.fireResponse(response);
            }

        });

        pipeline.addFirst("first", new Handler() {

            public void reqeust(HandlerContext context, Object reqeust) {
                System.out.println("first reqeust");
                context.fireRequest(reqeust);
            }

            public void response(HandlerContext context, Object response) {
                System.out.println("first response");
                context.fireResponse(response);
            }

        });

        pipeline.fireRequest("messge");

        pipeline.fireResponse("messge");

        pipeline.fireRequest("messge");
    }

    @Test
    public void testHandler() {
        DefaultPipeline pipeline = new DefaultPipeline();
        pipeline.addLast("first", new Handler() {

            public void reqeust(HandlerContext context, Object reqeust) {
                System.out.println("first reqeust");

                if ("1".equals(reqeust)) {
                    context.pipeline().remove("second");
                }

                context.fireRequest(reqeust);
            }

            public void response(HandlerContext context, Object response) {
                System.out.println("first response");
                context.fireResponse(response);
            }

        });

        pipeline.addLast("second", new Handler() {

            public void reqeust(HandlerContext context, Object reqeust) {
                System.out.println("second reqeust");
                context.fireRequest(reqeust);
            }

            public void response(HandlerContext context, Object response) {
                System.out.println("second response");
                context.fireResponse(response);
            }

        });

        pipeline.addLast("last", new Handler() {

            public void reqeust(HandlerContext context, Object reqeust) {
                System.out.println("last reqeust");
                context.fireResponse(reqeust + " : response");
            }

            public void response(HandlerContext context, Object response) {
                System.out.println("last response");
                context.fireResponse(response);
            }

        });

        pipeline.fireRequest("1");
    }

    @Test(expected = RuntimeException.class)
    public void testHandler01() {
        DefaultPipeline pipeline = new DefaultPipeline();
        pipeline.addLast("first", new Handler() {

            public void reqeust(HandlerContext context, Object reqeust) {
                System.out.println("first reqeust");
                context.fireRequest(reqeust);
            }

            public void response(HandlerContext context, Object response) {
                System.out.println("first response");
                context.fireResponse(response);
            }

        });

        pipeline.addLast("second", new Handler() {

            public void reqeust(HandlerContext context, Object reqeust) {
                System.out.println("second reqeust");
                context.fireRequest(reqeust);
            }

            public void response(HandlerContext context, Object response) {
                System.out.println("second response");
                context.fireResponse(response);
            }

        });

        pipeline.addLast("last", new Handler() {

            public void reqeust(HandlerContext context, Object reqeust) {
                throw new RuntimeException("test");
            }

            public void response(HandlerContext context, Object response) {
                System.out.println("last response");
                context.fireResponse(response);
            }

        });

        pipeline.fireRequest("1");
    }

    @Test
    public void testHandler02() {
        DefaultPipeline pipeline = new DefaultPipeline();
        pipeline.addLast("first", new Handler() {

            public void reqeust(HandlerContext context, Object reqeust) {
                System.out.println("first reqeust");
                context.fireRequest(reqeust);
            }

            public void response(HandlerContext context, Object response) {
                System.out.println("first response");
                context.fireResponse(response);
            }

        });

        pipeline.addLast("second", new Handler() {

            public void reqeust(HandlerContext context, Object reqeust) {
                System.out.println("second reqeust");
                context.fireRequest(reqeust);
            }

            public void response(HandlerContext context, Object response) {
                System.out.println("second response");
                context.fireResponse(response);
            }

        });

        pipeline.addLast("last", new Handler() {

            public void reqeust(HandlerContext context, Object reqeust) {
                System.out.println("last reqeust");

                context.pipeline().remove(this);

                context.fireResponse(reqeust + " : response");
            }

            public void response(HandlerContext context, Object response) {
                System.out.println("last response");
                context.fireResponse(response);
            }

        });

        pipeline.fireRequest("request");

        pipeline.fireResponse("response");
    }

}
