package org.youi.framework.core.web.view;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.SmartView;
import org.springframework.web.servlet.View;
import org.youi.framework.core.web.annotation.DataInfo;
import org.youi.framework.core.web.controller.TimeWebRequestInterceptor;



/**
 * @author zhyi_12
 *
 */
@Component("dataModelAndViewReturnValueHandler")
public class DataModelAndViewReturnValueHandler extends AbstractTransLogger  implements HandlerMethodReturnValueHandler{

	@Override
	public boolean supportsReturnType(MethodParameter returnType) {
		return DataModelAndView.class.equals(returnType.getParameterType());
	}

	@Override
	public void handleReturnValue(Object returnValue,
			MethodParameter returnType, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest) throws Exception {
		
		if (returnValue == null) {
			mavContainer.setRequestHandled(true);
			return;
		}
		
		long endTime = System.currentTimeMillis();
		long useTime = 0l;//耗时
		Object startTime = webRequest.getAttribute(TimeWebRequestInterceptor.TIME_ACCESS, WebRequest.SCOPE_REQUEST);
		if(startTime!=null){
			useTime = endTime-(Long)startTime;
		}
		
		DataModelAndView mav = (DataModelAndView) returnValue;
		
		if (mav.isReference()) {
			String viewName = mav.getViewName();
			mavContainer.setViewName(viewName);
			if (viewName != null && viewName.startsWith("redirect:")) {
				mavContainer.setRedirectModelScenario(true);
			}
		}
		else {
			View view = mav.getView();
			mavContainer.setView(view);
			if (view instanceof SmartView) {
				if (((SmartView) view).isRedirectView()) {
					mavContainer.setRedirectModelScenario(true);
				}
			}
		}
		mavContainer.addAllAttributes(mav.getModel());
		
		DataInfo dataInfo = returnType.getMethodAnnotation(DataInfo.class);
		if(dataInfo!=null){//设置页面信息
			mav.setCaption(dataInfo.text());//交易描述
			mav.setId(dataInfo.functionId());//交易ID
			
			String details = mav.getLogContent();//日记详细内容
			if(dataInfo.log()){
				HttpServletRequest request = 
					webRequest.getNativeRequest(HttpServletRequest.class);
				String userIP = request.getRemoteAddr();//记录访问用户IP
				//记录日记
				writeLog(dataInfo.functionId(),dataInfo.text(), details, useTime, userIP);
			}
		}
//			if(logger.isDebugEnabled()){
//				logger.debug("执行交易耗时"+useTime+"毫秒.");
//			}
	}

}
