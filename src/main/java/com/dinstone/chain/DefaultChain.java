
package com.dinstone.chain;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 
 * @author dinstone
 *
 */
public class DefaultChain implements Chain {

	private List<Interceptor> interceptors = new CopyOnWriteArrayList<Interceptor>();

	private Handler handler;

	private int index;

	public DefaultChain(Handler handler) {
		if (handler == null) {
			throw new IllegalArgumentException("handler is null");
		}
		this.handler = handler;
	}

	public Object intercept(Object request) {
		if (index < interceptors.size()) {
			return interceptors.get(index++).intercept(request, this);
		} else {
			index = 0;
			return handler.handle(request);
		}
	}

	public Chain addLast(Interceptor interceptor) {
		interceptors.add(interceptor);
		return this;
	}

}
