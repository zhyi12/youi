/**
 * 
 */
package org.youi.framework.services.invoke.method.support;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.core.MethodParameter;
import org.youi.framework.core.orm.Order;
import org.youi.framework.esb.annotation.OrderCollection;
import org.youi.framework.util.ConditionUtils;

/**
 * @author zhyi_12
 *
 */
public class OrdersServiceMethodArgumentResolver implements
		ServiceMethodArgumentResolver {
	public static final String PREFIX_ORDERBY_DESC="desc:";//排序
	
	public static final String PREFIX_ORDERBY_ASC="asc:";//排序
	/* (non-Javadoc)
	 * @see com.gsoft.framework.esb.method.support.ServiceMethodArgumentResolver#supportsParameter(org.springframework.core.MethodParameter)
	 */
	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return Collection.class.isAssignableFrom(parameter.getParameterType())&&
				parameter.hasParameterAnnotation(OrderCollection.class);
	}

	/* (non-Javadoc)
	 * @see com.gsoft.framework.esb.method.support.ServiceMethodArgumentResolver#resolveArgument(org.springframework.core.MethodParameter, java.util.Map)
	 */
	@Override
	public Object resolveArgument(MethodParameter parameter,
			Map<String, List<Object>> params,Object... providedArgs) throws Exception {
		Collection<Order> orders = new ArrayList<Order>();
		List<Object> orderBys  = params.get("orderBy");
		
		if(orderBys!=null){
			for(Object orderBy:orderBys){
				String propertyName = orderBy.toString();
				if(propertyName.startsWith(PREFIX_ORDERBY_ASC)){//正序
					orders.add(ConditionUtils.getOrder(propertyName.substring(PREFIX_ORDERBY_ASC.length()), true));
				}else if(propertyName.startsWith(PREFIX_ORDERBY_DESC)){//逆序
					orders.add(ConditionUtils.getOrder(propertyName.substring(PREFIX_ORDERBY_DESC.length()), false));
				}
			}
		}
		return orders;
	}

}
