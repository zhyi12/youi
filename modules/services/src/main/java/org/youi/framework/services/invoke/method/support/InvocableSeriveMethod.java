/**
 * 
 */
package org.youi.framework.services.invoke.method.support;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.core.GenericTypeResolver;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.MethodParameter;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;

/**
 * @author zhyi_12
 *
 */
public class InvocableSeriveMethod extends ServiceMethod {

	private ServiceMethodArgumentResolverComposite argumentResolvers = new ServiceMethodArgumentResolverComposite();


	private ParameterNameDiscoverer parameterNameDiscoverer = new LocalVariableTableParameterNameDiscoverer();


	/**
	 * Creates an instance from the given handler and method.
	 */
	public InvocableSeriveMethod(Object bean, Method method) {
		super(bean, method);
		argumentResolvers.addResolver(new ParamServiceMethodArgumentResolver());
		argumentResolvers.addResolver(new DomainServiceMethodArgumentResolver());
		argumentResolvers.addResolver(new DomainCollectionServiceMethodArgumentResolver());
		argumentResolvers.addResolver(new PagerServiceMethodArgumentResolver());
		
		argumentResolvers.addResolver(new OrdersServiceMethodArgumentResolver());
		argumentResolvers.addResolver(new ConditionsServiceMethodArgumentResolver());
		
		argumentResolvers.addResolver(new PubContextServiceMethodArgumentResolver());
		
		argumentResolvers.addResolver(new ReqContextServiceMethodArgumentResolver());
		argumentResolvers.addResolver(new MapServiceMethodArgumentResolver());
	}

	/**
	 * Create an instance from a {@code HandlerMethod}.
	 */
	public InvocableSeriveMethod(ServiceMethod handlerMethod) {
		super(handlerMethod);
	}

	/**
	 * Constructs a new handler method with the given bean instance, method name and parameters.
	 * @param bean the object bean
	 * @param methodName the method name
	 * @param parameterTypes the method parameter types
	 * @throws NoSuchMethodException when the method cannot be found
	 */
	public InvocableSeriveMethod(
			Object bean, String methodName, Class<?>... parameterTypes) throws NoSuchMethodException {
		super(bean, methodName, parameterTypes);
	}


	/**
	 * Set {@link HandlerMethodArgumentResolver}s to use to use for resolving method argument values.
	 */
	public void setHandlerMethodArgumentResolvers(ServiceMethodArgumentResolverComposite argumentResolvers) {
		this.argumentResolvers = argumentResolvers;
	}

	/**
	 * Set the ParameterNameDiscoverer for resolving parameter names when needed (e.g. default request attribute name).
	 * <p>Default is an {@link org.springframework.core.LocalVariableTableParameterNameDiscoverer} instance.
	 */
	public void setParameterNameDiscoverer(ParameterNameDiscoverer parameterNameDiscoverer) {
		this.parameterNameDiscoverer = parameterNameDiscoverer;
	}

	/**
	 * Invoke the method after resolving its argument values in the context of the given request. <p>Argument
	 * values are commonly resolved through {@link HandlerMethodArgumentResolver}s. The {@code provideArgs}
	 * parameter however may supply argument values to be used directly, i.e. without argument resolution.
	 * Examples of provided argument values include a {@link WebDataBinder}, a {@link SessionStatus}, or
	 * a thrown exception instance. Provided argument values are checked before argument resolvers.
	 *
	 * @param request the current request
	 * @param mavContainer the ModelAndViewContainer for this request
	 * @param providedArgs "given" arguments matched by type, not resolved
	 * @return the raw value returned by the invoked method
	 * @exception Exception raised if no suitable argument resolver can be found, or the method raised an exception
	 */
	public final Object invokeForRequest(Map<String,List<Object>> params,Object... providedArgs) throws Exception {
		Object[] args = getMethodArgumentValues(params,providedArgs);

		if (logger.isTraceEnabled()) {
			StringBuilder builder = new StringBuilder("Invoking [");
			builder.append(this.getMethod().getName()).append("] method with arguments ");
			builder.append(Arrays.asList(args));
			logger.trace(builder.toString());
		}

		Object returnValue = invoke(args);

		if (logger.isTraceEnabled()) {
			logger.trace("Method [" + this.getMethod().getName() + "] returned [" + returnValue + "]");
		}

		return returnValue;
	}

	/**
	 * Get the method argument values for the current request.
	 */
	private Object[] getMethodArgumentValues(
			Map<String,List<Object>> params,
			Object... providedArgs) throws Exception {

		MethodParameter[] parameters = getMethodParameters();
		Object[] args = new Object[parameters.length];
		for (int i = 0; i < parameters.length; i++) {
			MethodParameter parameter = parameters[i];
			parameter.initParameterNameDiscovery(parameterNameDiscoverer);
			GenericTypeResolver.resolveParameterType(parameter, getBean().getClass());

			args[i] = resolveProvidedArgument(parameter, providedArgs);
			if (args[i] != null) {
				continue;
			}

			if (argumentResolvers.supportsParameter(parameter)) {
				try {
					args[i] = argumentResolvers.resolveArgument(parameter,params,providedArgs);
					continue;
				} catch (Exception ex) {
					if (logger.isTraceEnabled()) {
						logger.trace(getArgumentResolutionErrorMessage("Error resolving argument", i), ex);
					}
					throw ex;
				}
			}

			if (args[i] == null) {
				String msg = getArgumentResolutionErrorMessage("No suitable resolver for argument", i);
				throw new IllegalStateException(msg);
			}
		}
		return args;
	}

	private String getArgumentResolutionErrorMessage(String message, int index) {
		MethodParameter param = getMethodParameters()[index];
		message += " [" + index + "] [type=" + param.getParameterType().getName() + "]";
		return getDetailedErrorMessage(message);
	}

	/**
	 * Adds HandlerMethod details such as the controller type and method signature to the given error message.
	 * @param message error message to append the HandlerMethod details to
	 */
	protected String getDetailedErrorMessage(String message) {
		StringBuilder sb = new StringBuilder(message).append("\n");
		sb.append("HandlerMethod details: \n");
		sb.append("Controller [").append(getBeanType().getName()).append("]\n");
		sb.append("Method [").append(getBridgedMethod().toGenericString()).append("]\n");
		return sb.toString();
	}

	/**
	 * Attempt to resolve a method parameter from the list of provided argument values.
	 */
	private Object resolveProvidedArgument(MethodParameter parameter, Object... providedArgs) {
		if (providedArgs == null) {
			return null;
		}
		for (Object providedArg : providedArgs) {
			if (parameter.getParameterType().isInstance(providedArg)) {
				return providedArg;
			}
		}
		return null;
	}

	/**
	 * Invoke the handler method with the given argument values.
	 */
	private Object invoke(Object... args) throws Exception {
		ReflectionUtils.makeAccessible(this.getBridgedMethod());
		try {
			return getBridgedMethod().invoke(getBean(), args);
		}
		catch (IllegalArgumentException e) {
			String msg = getInvocationErrorMessage(e.getMessage(), args);
			throw new IllegalArgumentException(msg, e);
		}
		catch (InvocationTargetException e) {
			// Unwrap for HandlerExceptionResolvers ...
			Throwable targetException = e.getTargetException();
			if (targetException instanceof RuntimeException) {
				throw (RuntimeException) targetException;
			}
			else if (targetException instanceof Error) {
				throw (Error) targetException;
			}
			else if (targetException instanceof Exception) {
				throw (Exception) targetException;
			}
			else {
				String msg = getInvocationErrorMessage("Failed to invoke controller method", args);
				throw new IllegalStateException(msg, targetException);
			}
		}catch(Exception e){
			logger.error(e.getMessage());
			throw e;
		}
	}

	private String getInvocationErrorMessage(String message, Object[] resolvedArgs) {
		StringBuilder sb = new StringBuilder(getDetailedErrorMessage(message));
		sb.append("Resolved arguments: \n");
		for (int i=0; i < resolvedArgs.length; i++) {
			sb.append("[").append(i).append("] ");
			if (resolvedArgs[i] == null) {
				sb.append("[null] \n");
			}
			else {
				sb.append("[type=").append(resolvedArgs[i].getClass().getName()).append("] ");
				sb.append("[value=").append(resolvedArgs[i]).append("]\n");
			}
		}
		return sb.toString();
	}

}
