package com.dinstone.pipeline;

public class DefaultPipeline implements Pipeline {

    private class HeadHandler implements Handler {

        public void reqeust(HandlerContext context, Object message) {
            System.out.println("head request");
        }

        public void response(HandlerContext handlerContext, Object response) {
            System.out.println("head response");
        }

    }

    private class TailHandler implements Handler {

        public void reqeust(HandlerContext context, Object message) {
            System.out.println("tail request");
        }

        public void response(HandlerContext handlerContext, Object response) {
            System.out.println("tail response");
        }

    }

    final DefaultHandlerContext headHandlerContext;

    final DefaultHandlerContext tailHandlerContext;

    public DefaultPipeline() {
        TailHandler tailHandler = new TailHandler();
        tailHandlerContext = new DefaultHandlerContext(this, generateName(tailHandler), tailHandler);

        HeadHandler headHandler = new HeadHandler();
        headHandlerContext = new DefaultHandlerContext(this, generateName(headHandler), headHandler);

        headHandlerContext.nextHandlerContext = tailHandlerContext;
        tailHandlerContext.prevHandlerContext = headHandlerContext;
    }

    private String generateName(Handler handler) {
        return handler.getClass().getSimpleName();
    }

    private String filterName(String name, Handler handler) {
        if (name == null) {
            return generateName(handler);
        }
        checkDuplicateName(name);
        return name;
    }

    private void checkDuplicateName(String name) {
        if (context(name) != null) {
            throw new IllegalArgumentException("Duplicate handler name: " + name);
        }
    }

    private DefaultHandlerContext context(String name) {
        DefaultHandlerContext context = headHandlerContext.nextHandlerContext;
        while (context != tailHandlerContext) {
            if (context.name().equals(name)) {
                return context;
            }
            context = context.nextHandlerContext;
        }
        return null;
    }

    private final DefaultHandlerContext context(Handler handler) {
        DefaultHandlerContext context = headHandlerContext.nextHandlerContext;
        while (context != tailHandlerContext) {
            if (context.handler() == handler) {
                return context;
            }
            context = context.nextHandlerContext;
        }
        return null;
    }

    private void addLast0(final String name, DefaultHandlerContext newCtx) {
        DefaultHandlerContext prev = tailHandlerContext.prevHandlerContext;
        newCtx.prevHandlerContext = prev;
        newCtx.nextHandlerContext = tailHandlerContext;
        prev.nextHandlerContext = newCtx;
        tailHandlerContext.prevHandlerContext = newCtx;
    }

    public Pipeline addFirst(String name, Handler handler) {
        synchronized (this) {
            name = filterName(name, handler);
            DefaultHandlerContext newCtx = new DefaultHandlerContext(this, name, handler);
            addFirst0(name, newCtx);
        }

        return this;
    }

    private void addFirst0(String name, DefaultHandlerContext newCtx) {
        DefaultHandlerContext next = headHandlerContext.nextHandlerContext;
        next.prevHandlerContext = newCtx;
        headHandlerContext.nextHandlerContext = newCtx;
        newCtx.prevHandlerContext = headHandlerContext;
        newCtx.nextHandlerContext = next;
    }

    public Pipeline addLast(String name, Handler handler) {
        synchronized (this) {
            name = filterName(name, handler);
            DefaultHandlerContext newCtx = new DefaultHandlerContext(this, name, handler);
            addLast0(name, newCtx);
        }

        return this;
    }

    public Pipeline fireRequest(Object message) {
        headHandlerContext.fireRequest(message);
        return this;
    }

    public Pipeline fireResponse(Object message) {
        tailHandlerContext.fireResponse(message);
        return this;
    }

    public Pipeline remove(String name) {
        synchronized (this) {
            DefaultHandlerContext context = context(name);
            if (context != null) {
                DefaultHandlerContext prev = context.prevHandlerContext;
                DefaultHandlerContext next = context.nextHandlerContext;
                prev.nextHandlerContext = next;
                next.prevHandlerContext = prev;
            }
        }

        return this;
    }

    public Pipeline remove(Handler handler) {
        synchronized (this) {
            DefaultHandlerContext context = context(handler);
            if (context != null) {
                DefaultHandlerContext prev = context.prevHandlerContext;
                DefaultHandlerContext next = context.nextHandlerContext;
                prev.nextHandlerContext = next;
                next.prevHandlerContext = prev;
            }
        }

        return this;
    }

}
