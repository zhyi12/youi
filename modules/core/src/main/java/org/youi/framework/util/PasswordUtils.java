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

import java.util.Random;

import org.apache.shiro.crypto.hash.Md5Hash;


/**
 * @功能描述
 * @作者  zhyi_12
 * @版本 1.0.0
 * @创建时间 Aug 11, 2010
 */
public class PasswordUtils {
	
	/**
	 * md5加密
	 * @param password
	 * @return
	 */
	public static String md5Password(String password){
//		return md5PasswordEncoder.encodePassword(password, null);
		return 	new Md5Hash(password).toHex();
		//return null;
	}
	public static String md5Password(String password,String salt){
		return 	new Md5Hash(password,salt).toHex();
	}

	/**
	 * 生成随机密码
	 * @param 
	 * @return password
	 */
	public static String randomPassword(){
		char[] pwdchs = {'a' , 'b' , 'c' , 'd' , 'e' , 'f' , 'g' , 'h' , 'i' , 'j' , 'k' , 'l' , 'm' , 'n' ,
		         'o' , 'p' , 'q' , 'r' , 's' , 'd' , 'u' , 'v' , 'w' , 'x' , 'y' , 'z' ,
		         'A' , 'B' , 'C' , 'D' , 'E' , 'F' , 'G' , 'H' , 'I' , 'J' , 'K' , 'L' , 'M' , 'N' ,
		         'O' , 'P' , 'Q' , 'R' , 'S' , 'T' , 'U' , 'V' , 'W' , 'X' , 'Y' , 'Z' ,
		         '0' , '1' , '2' , '3' , '4' , '5' , '6' , '7' , '8' , '9'};
		Random rand = new Random();
		String pwd = "";
		//随机生成六位数密码
		for(int j = 0 ; j < 6; j++){
			pwd+= pwdchs[rand.nextInt(62)];
		}	
		return pwd;
	}
}
