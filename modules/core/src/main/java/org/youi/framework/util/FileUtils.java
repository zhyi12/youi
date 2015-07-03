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

import java.io.File;

/**
 * @功能描述
 * @作者 zhyi_12
 * @版本 1.0.0
 * @创建时间 Dec 6, 2010
 */
public class FileUtils {
	public static String formatFileName(String fileName) {
		char[] content = new char[fileName.length()];
		char[] result = new char[fileName.length()];
		fileName.getChars(0, fileName.length(), content, 0);

		int len = 0;
		for (int i = 0; i < content.length; ++i) {
			if ((content[i] == '%') && (content[(i + 1)] == '2')
					&& (content[(i + 2)] == '0')) {
				result[(len++)] = ' ';
				i += 2;
			} else {
				result[(len++)] = content[i];
			}
		}
		return String.valueOf(result, 0, len);
	}
	
	/**
	 * 
	 * @param dirPath
	 * @return
	 */
	public static File checkAndCreateDir(String dirPath){
		File dir = new File(dirPath);
		if(!dir.exists()){
			dir.mkdir();
		}
		return dir;
	}
	
	public static void checkAndCreateSubDir(String dirPath,String subPath){
		//
		subPath = subPath.replace("\\", "/");
		String[] subDirs = subPath.split("/");
		//for()
		String checkPath = dirPath;
		for(String subDir:subDirs){
			checkPath = checkPath+"/"+subDir;
			checkAndCreateDir(checkPath);
		}
	}
}
