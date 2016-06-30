/**
 * 
 */
package org.youi.framework.services.invoke.method.support;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.MethodParameter;
import org.springframework.util.Assert;

/**
 * @author zhyi_12
 *
 */
public class ServiceMethodArgumentResolverComposite implements ServiceMethodArgumentResolver {

	protected final Log logger = LogFactory.getLog(getClass());

	private final List<ServiceMethodArgumentResolver> argumentResolvers =
			new LinkedList<ServiceMethodArgumentResolver>();
	
	private final Map<MethodParameter, ServiceMethodArgumentResolver> argumentResolverCache =
		new ConcurrentHashMap<MethodParameter, ServiceMethodArgumentResolver>(256);
	/**
	 * Return a read-only list with the contained resolvers, or an empty list.
	 */
	public List<ServiceMethodArgumentResolver> getResolvers() {
		return Collections.unmodifiableList(this.argumentResolvers);
	}

	/**
	 * Whether the given {@linkplain MethodParameter method parameter} is supported by any registered
	 * {@link ServiceMethodArgumentResolver}.
	 */
	public boolean supportsParameter(MethodParameter parameter) {
		return getArgumentResolver(parameter) != null;
	}

	/**
	 * Iterate over registered {@link ServiceMethodArgumentResolver}s and invoke the one that supports it.
	 * @exception IllegalStateException if no suitable {@link ServiceMethodArgumentResolver} is found.
	 */
	public Object resolveArgument(
			MethodParameter parameter,Map<String,List<Object>> params,Object... providedArgs)
			throws Exception {

		ServiceMethodArgumentResolver resolver = getArgumentResolver(parameter);
		Assert.notNull(resolver, "Unknown parameter type [" + parameter.getParameterType().getName() + "]");
		return resolver.resolveArgument(parameter,params,providedArgs);
	}

	/**
	 * Find a registered {@link ServiceMethodArgumentResolver} that supports the given method parameter.
	 */
	private ServiceMethodArgumentResolver getArgumentResolver(MethodParameter parameter) {
		ServiceMethodArgumentResolver result = this.argumentResolverCache.get(parameter);
		if (result == null) {
			for (ServiceMethodArgumentResolver methodArgumentResolver : this.argumentResolvers) {
				if (logger.isTraceEnabled()) {
					logger.trace("Testing if argument resolver [" + methodArgumentResolver + "] supports [" +
							parameter.getGenericParameterType() + "]");
				}
				if (methodArgumentResolver.supportsParameter(parameter)) {
					result = methodArgumentResolver;
					this.argumentResolverCache.put(parameter, result);
					break;
				}
			}
		}
		return result;
	}

	/**
	 * Add the given {@link ServiceMethodArgumentResolver}.
	 */
	public ServiceMethodArgumentResolverComposite addResolver(ServiceMethodArgumentResolver argumentResolver) {
		this.argumentResolvers.add(argumentResolver);
		return this;
	}

	/**
	 * Add the given {@link ServiceMethodArgumentResolver}s.
	 */
	public ServiceMethodArgumentResolverComposite addResolvers(
			List<? extends ServiceMethodArgumentResolver> argumentResolvers) {
		if (argumentResolvers != null) {
			for (ServiceMethodArgumentResolver resolver : argumentResolvers) {
				this.argumentResolvers.add(resolver);
			}
		}
		return this;
	}

}
