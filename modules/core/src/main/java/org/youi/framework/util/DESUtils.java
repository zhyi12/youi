/**
 * 
 */
package org.youi.framework.util;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author zhyi_12
 * 
 */
public class DESUtils {

	private static final String ALGORITHM_DESEDE = "DESede";

	private static final String DECRYPT_ENCODING = "UTF-8";

	/**
	 * 加密信息
	 * 
	 * @param key
	 * @param info
	 * @return
	 */
	public static String encrypt(String key, String info) {
		byte[] desKey;
		try {
			desKey = build3DesKey(key);
		} catch (Exception e) {
			throw new RuntimeException("秘钥构建失败：" + e.getMessage());
		}

		SecretKey secretKey = new SecretKeySpec(desKey, ALGORITHM_DESEDE);
		Cipher cipher = null;
		byte[] byteResult = null;
		try {
			cipher = Cipher.getInstance(ALGORITHM_DESEDE);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (cipher != null) {
			try {
				cipher.init(Cipher.ENCRYPT_MODE, secretKey);
			} catch (InvalidKeyException e) {
				e.printStackTrace();
			}

			try {
				byteResult = cipher.doFinal(info.getBytes(DECRYPT_ENCODING));
			} catch (IllegalBlockSizeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (BadPaddingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if (byteResult == null) {
			return "";
		}
		return byte2HexStr(byteResult);
	}

	/**
	 * 解密信息
	 * 
	 * @param dest
	 * @param key
	 * @return
	 * @throws BadPaddingException 
	 * @throws IllegalBlockSizeException 
	 * @throws Exception
	 */
	public static String decrypt(String dest, String key) throws IllegalBlockSizeException, BadPaddingException {
		SecretKey secretKey = null;
		try {
			secretKey = new SecretKeySpec(build3DesKey(key),
					ALGORITHM_DESEDE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(secretKey==null){
			return "";
		}
		Cipher cipher = null;
		try {
			cipher = Cipher.getInstance(ALGORITHM_DESEDE);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		}
		
		byte[] byteResult = null; 
		if(cipher!=null){
			try {
				cipher.init(Cipher.DECRYPT_MODE, secretKey);
			} catch (InvalidKeyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			byteResult = cipher.doFinal(str2ByteArray(dest));
			
		}
		
		try {
			return new String(byteResult, DECRYPT_ENCODING);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return "";
	}

	/**
	 * 字节数组转化为大写16进制字符串
	 * 
	 * @param b
	 * @return
	 */
	private static String byte2HexStr(byte[] b) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < b.length; i++) {
			String s = Integer.toHexString(b[i] & 0xFF);
			if (s.length() == 1) {
				sb.append("0");
			}

			sb.append(s.toUpperCase());
		}

		return sb.toString();
	}

	/**
	 * 字符串转字节数组
	 * 
	 * @param s
	 * @return
	 */
	private static byte[] str2ByteArray(String s) {
		int byteArrayLength = s.length() / 2;
		byte[] b = new byte[byteArrayLength];
		for (int i = 0; i < byteArrayLength; i++) {
			byte b0 = (byte) Integer.valueOf(s.substring(i * 2, i * 2 + 2), 16)
					.intValue();
			b[i] = b0;
		}
		return b;
	}

	/**
	 * 构造3DES加解密方法key
	 * 
	 * @param keyStr
	 * @return
	 * @throws Exception
	 */
	private static byte[] build3DesKey(String keyStr) throws Exception {
		byte[] key = new byte[24];
		byte[] temp = keyStr.getBytes("UTF-8");
		if (key.length > temp.length) {
			System.arraycopy(temp, 0, key, 0, temp.length);
		} else {
			System.arraycopy(temp, 0, key, 0, key.length);
		}
		return key;
	}

	
	public static void main(String[] args) throws IllegalBlockSizeException, BadPaddingException{
		
//		String key = "app11111111111111111111111111111111111111";
//		Map<String,String> info = new HashMap<String,String>();
//		info.put("app", "app");
//		info.put("username", "zhangsan");
//		info.put("date", DateUtils.getToday("yyyyMMdd"));
//		
//		String encryptInfo = DESUtils.encrypt(key, PojoMapper.toJson(info, false));
//		System.out.println(encryptInfo);
//		
//		System.out.println(DESUtils.decrypt(encryptInfo, key));
//		
		
		System.out.println(DESUtils.decrypt("5BA18D58A2E49722509977A43E6E33200D5E9E3A7950DF60BB854B263E91D0D061D80D1DD3E2EDCEDBF055737E586C3A8D07056FC51EA9576508BEA493F4BEB9",
				"sysChannel"));
		
	}
}
