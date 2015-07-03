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
package org.youi.framework.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.code.kaptcha.servlet.KaptchaServlet;

/**
 * @功能描述
 * @作者  zhyi_12
 * @版本 1.0.0
 * @创建时间 Dec 26, 2010
 */
public class DefaultKaptchaServlet extends KaptchaServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7252404239588870349L;

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		//String referer = req.getHeader("Referer");
		//防止盗链: 根据http头Referer的比较防止验证码图片的盗链。
		//if(referer!=null&&referer.startsWith(ScoreConfig.COMMON_WEB_REFFERPREFIX)){
		super.doGet(req, resp);
		//}
	}
}
