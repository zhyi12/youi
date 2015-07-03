package org.youi.framework.util;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.youi.framework.core.dataobj.Domain;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 * Sends broadcast event that something happens with given {@link Domain}. For
 * example we can send events that some object was added/removed from its
 * parent. We send this event as broadcast because there are cases when we need
 * to know globally that some new object was added (for example in Forms API for
 * adapting each Composite).
 * 
 * @author scheglov_ke
 * @coverage core.model
 */
public final class BroadcastSupport {
	/**
	 * {@link Map} for "listener class" -> "listener implementations".
	 */
	private final Map<Class<?>, List<Object>> m_classToListeners = Collections
			.synchronizedMap(new HashMap<Class<?>, List<Object>>());
	/**
	 * {@link Map} for "listener target" -> "listener implementations".
	 */
	private final Map<Domain, List<Object>> m_targetToListeners = Collections
			.synchronizedMap(new HashMap<Domain, List<Object>>());

	// //////////////////////////////////////////////////////////////////////////
	//
	// Access
	//
	// //////////////////////////////////////////////////////////////////////////
	/**
	 * Adds new listener with superclass.
	 */
	public void addListener(Domain target, Object listenerImpl) {
		Class<?> listenerClass = getListenerClass(listenerImpl);
		addListener(getClassListeners(listenerClass), listenerImpl);
		if (target != null) {
			addListener(getTargetListeners(target), listenerImpl);
		}
	}

	/**
	 * Removes listener with superclass.
	 */
	public void removeListener(Domain target, Object listenerImpl) {
		Class<?> listenerClass = getListenerClass(listenerImpl);
		getClassListeners(listenerClass).remove(listenerImpl);
		if (target != null) {
			getTargetListeners(target).remove(listenerImpl);
		}
	}

	/**
	 * Moves listeners from one target to another.
	 * 
	 * @noreference
	 */
	public void targetListener(Domain oldTarget, Domain newTarget) {
		List<?> listeners = m_targetToListeners.remove(oldTarget);
		if (listeners != null) {
			getTargetListeners(newTarget).addAll(listeners);
		}
	}

	/**
	 * When we remove {@link Domain}'s from their parent, so exclude them from
	 * hierarchy, or these {@link Domain}'s are just not included into hierarchy
	 * during parsing; we should remove any broadcast listeners, registered by
	 * these {@link Domain}'s.
	 * 
	 * @noreference
	 */
	// public void cleanUpTargets(Domain root) {
	// List<Domain> targets = new
	// ArrayList<Domain>(m_targetToListeners.keySet());
	// for (Domain target : targets) {
	// if (!root.isItOrParentOf(target)) {
	// cleanUpTarget(target);
	// }
	// }
	// }

	// //////////////////////////////////////////////////////////////////////////
	//
	// Utils
	//
	// //////////////////////////////////////////////////////////////////////////
	/**
	 * @return the {@link Class} of broadcast listener extended/implemented by
	 *         given {@link Object}.
	 */
	private static Class<?> getListenerClass(Object listenerImpl) {
		Class<?> implClass = listenerImpl.getClass();
		// old case - extent listener class with many methods
		{
			Class<?> listenerClass = implClass.getSuperclass();
			if (listenerClass != Object.class) {
				while (listenerClass.getSuperclass() != Object.class) {
					listenerClass = listenerClass.getSuperclass();
				}
				return listenerClass;
			}
		}
		// new case - implement single listener interface
		{
			Class<?>[] interfaces = implClass.getInterfaces();
			// if (EnvironmentUtils.DEVELOPER_HOST) {
			// Assert.isTrue(
			// interfaces.length == 1,
			// "Only one listener interface expected, but %s found.",
			// interfaces.length);
			// }
			return interfaces[0];
		}
	}

	/**
	 * @return the {@link List} of listeners of given class. May return empty
	 *         {@link List}, but not <code>null</code>.
	 */
	private List<Object> getClassListeners(Class<?> listenerClass) {
		List<Object> listeners = m_classToListeners.get(listenerClass);
		if (listeners == null) {
			listeners = new ArrayList<Object>();// Lists.newArrayList();
			m_classToListeners.put(listenerClass, listeners);
		}
		return listeners;
	}

	/**
	 * @return the {@link List} of listeners of given class. May return empty
	 *         {@link List}, but not <code>null</code>.
	 */
	private List<Object> getTargetListeners(Domain target) {
		List<Object> listeners = m_targetToListeners.get(target);
		if (listeners == null) {
			listeners = new ArrayList<Object>();
			m_targetToListeners.put(target, listeners);
		}
		return listeners;
	}

	/**
	 * Adds new listener into {@link List} only if there are no same listener
	 * yet.
	 */
	private static void addListener(List<Object> listeners, Object listener) {
		if (!listeners.contains(listener)) {
			listeners.add(listener);
		}
	}

	// //////////////////////////////////////////////////////////////////////////
	//
	// Sending
	//
	// //////////////////////////////////////////////////////////////////////////
	private final Map<Class<?>, Object> m_listenerToMulticast = Collections
			.synchronizedMap(new HashMap<Class<?>, Object>());

	/**
	 * @return the implementation of given listener class (so it can be casted
	 *         to it) that can be used for sending events to all subscribers.
	 */
	public <T> T getListener(final Class<T> listenerClass) {
		Object listenerMulticast = m_listenerToMulticast.get(listenerClass);
		if (listenerMulticast == null) {
			Enhancer enhancer = new Enhancer();
			enhancer.setSuperclass(listenerClass);
			enhancer.setCallback(new MethodInterceptor() {
				public Object intercept(Object obj,
						java.lang.reflect.Method method, Object[] args,
						MethodProxy proxy) throws Throwable {
					List<Object> listeners = getClassListeners(listenerClass);
					for (Object listener : listeners.toArray()) {
						try {
							method.invoke(listener, args);
						} catch (InvocationTargetException e) {
							throw e.getCause();
						}
					}
					// no result
					return null;
				}
			});
			// remember multi-cast
			listenerMulticast = enhancer.create();
			m_listenerToMulticast.put(listenerClass, listenerMulticast);
		}
		//
		@SuppressWarnings("unchecked")
		T casted_listenerMulticast = (T) listenerMulticast;
		return casted_listenerMulticast;
	}
}
