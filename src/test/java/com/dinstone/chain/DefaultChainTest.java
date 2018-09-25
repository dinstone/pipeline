
package com.dinstone.chain;

import org.junit.Assert;
import org.junit.Test;

public class DefaultChainTest {

    @Test
    public void testIntercept() {
        DefaultChain chain = new DefaultChain(new Handler() {

            public Object handle(Object request) {
                System.out.println("handler");
                return "response";
            }
        });

        chain.addLast(new Interceptor() {

            public Object intercept(Object request, Chain chain) {
                System.out.println("first");
                return chain.intercept(request);
            }
        });
        chain.addLast(new Interceptor() {

            public Object intercept(Object request, Chain chain) {
                System.out.println("second");
                return chain.intercept(request);
            }
        });

        Object response = chain.intercept("request");
        Assert.assertEquals("response", response);
        
        response = chain.intercept("request");
        Assert.assertEquals("response", response);
    }

}
