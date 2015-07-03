/*
 * YOUI框架
 * Copyright 2010 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.youi.framework.util;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

/**
 * @功能描述
 * @作者  zhyi_12
 * @版本 1.0.0
 * @创建时间 Jan 9, 2010
 */
public class Dom4jUtils {
	
	private final static Log log = LogFactory.getLog(Dom4jUtils.class);//日记
	
	/**
	 * SAX 方式解析xml文件
	 * @param filePath xml文件路径
	 * @return 返回dom4j的Document对象
	 */
	public static Document saxParse(String filePath){
		Document doc = null;
		SAXReader saxReader = new SAXReader();
		try {
			doc = saxReader.read(filePath);
		} catch (DocumentException e) {
			log.error("SAXReader解析xml文件【"+filePath+"】异常:"+e.getMessage());
		}
		return doc;
	}
	
	public static Document saxParse(InputStream input){
		Document doc = null;
		SAXReader saxReader = new SAXReader();
		try {
			doc = saxReader.read(input);
		} catch (DocumentException e) {
			log.error("SAXReader解析xml文件异常:"+e.getMessage());
		}
		return doc;
	}
	/**
	 * 输出标准格式的xml文件
	 * @param filename
	 * @param doc
	 * @param encoding
	 * @throws GenerateException
	 */
	public static void writeFormatDocToFile(String filePath, Document doc,
			String encoding){
		FileOutputStream os  = null;
		try {
			os = new FileOutputStream(filePath);
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
		Dom4jUtils.formatXml(doc, os, encoding);
	}
	
	/**
	 * 格式化xml到输出流
	 * @param doc
	 * @param os
	 * @param encoding
	 */
	public static void formatXml(Document doc,OutputStream os,String encoding){
		if(os==null)return;
		OutputFormat format = null;
		XMLWriter output = null;
		try {
			format = OutputFormat.createPrettyPrint();
			format.setEncoding(encoding);
			format.setOmitEncoding(false);
			output = new XMLWriter(new BufferedWriter(new OutputStreamWriter(os, "UTF-8")),format);
			output.write(doc);
		} catch (IOException e) {
			log.error("xml文件IO写入错误:"+e.getMessage());
		}finally{
			try {
				output.close();
			} catch (IOException e) {
				log.info("文件关闭失败："+e.getMessage());
			}
		}
	}
	/**
	 * @param modelElement
	 * @param attrName
	 * @param attrValue
	 */
	public static void addAttribute(Element modelElement, String attrName,
			String attrValue) {
		if(attrValue!=null){
			modelElement.addAttribute(attrName, attrValue);
		}
	}
	
	/**
	 * @param xmlText
	 * @return
	 */
	public static Document parseText(String xmlText) {
		Document doc;
		try {
			doc = DocumentHelper.parseText(xmlText);
		} catch (DocumentException e) {
			doc = null;
			e.printStackTrace();
		}
		return doc;
	}
}
