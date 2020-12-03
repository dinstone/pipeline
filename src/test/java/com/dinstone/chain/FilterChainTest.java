package com.dinstone.chain;

import org.junit.Assert;
import org.junit.Test;

public class FilterChainTest {

    @Test
    public void testIntercept() {
        FilterChain chain = new FilterChain(new Handler() {
            public Object handle(Object request) {
                System.out.println("handler");
                return "response";
            }
        });

        chain.addLast(new Filter() {
            public Object filter(Object request, FilterContext chain) {
                System.out.println("first");
                return chain.handle(request);
            }
        });
        chain.addLast(new Filter() {
            public Object filter(Object request, FilterContext chain) {
                System.out.println("second");
                return chain.handle(request);
            }
        });

        Object response = chain.handle("request");
        Assert.assertEquals("response", response);

        response = chain.handle("request");
        Assert.assertEquals("response", response);
    }

}
