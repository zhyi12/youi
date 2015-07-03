/*

 * @(#)PojoMapper.java  1.0.0 下午07:51:00

 * Copyright 2013 gicom, Inc. All rights reserved.

 */
package org.youi.framework.util;

/**
 * <p></p>
 * @author 
 * @version 1.0.0
 * @see    
 * @since 
 */
import java.io.IOException;
import java.io.StringWriter;

import org.youi.framework.core.dataobj.Domain;
import org.youi.framework.core.json.DomainJsonModule;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate3.Hibernate3Module;

public class PojoMapper {

	private static ObjectMapper objectMapper = new ObjectMapper();
	
	private static ObjectMapper domainMapper = new ObjectMapper();
	private static JsonFactory jf = new JsonFactory();
	
	static {
		domainMapper.registerModule(new DomainJsonModule());
		objectMapper.registerModule(new Hibernate3Module());
	}

//	public static <T> Object fromJson(String jsonAsString, Class<T> pojoClass)
//			throws JsonMappingException, JsonParseException, IOException {
//		return m.readValue(jsonAsString, pojoClass);
//	}
//
//	public static <T> Object fromJson(FileReader fr, Class<T> pojoClass)
//			throws JsonParseException, IOException {
//		return m.readValue(fr, pojoClass);
//	}

	public static String domainToJson(Domain domain){
		return toJson(domainMapper,domain,false);
	}
	
	public static String toJson(Object pojo, boolean prettyPrint) {
		return toJson(objectMapper,pojo,false);
	}
	
	public static String toJson(ObjectMapper objectMapper,Object pojo, boolean prettyPrint) {
		StringWriter sw = new StringWriter();

		try {
			JsonGenerator jg = jf.createJsonGenerator(sw);
			
			if (prettyPrint) {
				jg.useDefaultPrettyPrinter();
			}
			
			objectMapper.writeValue(jg, pojo);
		} catch (JsonMappingException e) {
			throw new RuntimeException(e);
		} catch (JsonGenerationException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return sw.toString();
	}

//	public static void toJson(Object pojo, FileWriter fw, boolean prettyPrint)
//			throws JsonMappingException, JsonGenerationException, IOException {
//		JsonGenerator jg = jf.createJsonGenerator(fw);
//		if (prettyPrint) {
//			jg.useDefaultPrettyPrinter();
//		}
//		objectMapper.writeValue(jg, pojo);
//	}
}