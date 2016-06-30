/**
 * 
 */
package org.youi.framework.services.converter;

import java.io.IOException;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.youi.framework.services.data.ResContext;
import org.youi.framework.util.StringUtils;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;

/**
 * @author zhyi_12 HttpRequestHandlingMessagingGateway
 */
@Component("webResConverter")
public class WebResConverter extends MappingJackson2HttpMessageConverter {

	// private FormHttpMessageConverter formHttpMessageConverter = new
	// FormHttpMessageConverter();

	public WebResConverter() {
		super();
		getObjectMapper().registerModule(new Hibernate5Module());
	}

	@SuppressWarnings("rawtypes")
	@Override
	protected void writeInternal(Object object, HttpOutputMessage outputMessage)
			throws IOException, HttpMessageNotWritableException {
		// 处理返回结果
		Object result = null;
		
		if(object instanceof ResContext){
			result = object;
		}else{
			result = new ResContext();
		}
		
		JsonEncoding encoding = getJsonEncoding(outputMessage.getHeaders().getContentType());
		JsonGenerator jsonGenerator = getObjectMapper().getFactory().createGenerator(outputMessage.getBody(), encoding);

		// A workaround for JsonGenerators not applying serialization features
		// https://github.com/FasterXML/jackson-databind/issues/12
		if (getObjectMapper().isEnabled(SerializationFeature.INDENT_OUTPUT)) {
			jsonGenerator.useDefaultPrettyPrinter();
		}

		try {
			String jsonp = outputMessage.getHeaders().getFirst("X-jsonp");
			if (StringUtils.isNotEmpty(jsonp)) {
				jsonGenerator.writeRaw(jsonp + "(");
			}

			if (object instanceof String) {
				// 字符串直接写入,避免转换
				jsonGenerator.writeRaw(object.toString());
				jsonGenerator.flush();
			} else {
				getObjectMapper().writeValue(jsonGenerator, result);
			}

			if (StringUtils.isNotEmpty(jsonp)) {
				jsonGenerator.writeRaw(");");
				jsonGenerator.flush();
			}

		} catch (JsonProcessingException ex) {
			throw new HttpMessageNotWritableException("Could not write JSON: " + ex.getMessage(), ex);
		}
	}

	@Override
	public boolean canWrite(Class<?> clazz, MediaType mediaType) {
		if (org.youi.framework.services.data.ResContext.class.isAssignableFrom(clazz)) {
			return true;
		}
		return super.canWrite(clazz, mediaType);
	}

	@Override
	public boolean canRead(Class<?> clazz, MediaType mediaType) {
		if (mediaType != null && MediaType.APPLICATION_FORM_URLENCODED.getType().endsWith(mediaType.getType())) {
			return true;
		}
		
		// form文件上传
		if (MediaType.MULTIPART_FORM_DATA.getType().equals(mediaType.getType())) {
			return true;
		}

		return super.canRead(clazz, mediaType);
	}

	@Override
	protected Object readInternal(Class<?> clazz, HttpInputMessage inputMessage)
			throws IOException, HttpMessageNotReadableException {
		// 写入请求数据
		return null;
	}

}
