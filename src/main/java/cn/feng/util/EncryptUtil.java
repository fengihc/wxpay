package cn.feng.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * 加密工具类
 * 	本项目暂未用到
 *
 */
public class EncryptUtil {
	public static final String KEY_SHA = "SHA";

	public static final String KEY_MD5 = "MD5";

	public static final String KEY_SHA1 = "SHA-1";

	private static final String KEY_SHA256 = "SHA-256";
	
	private static final String KEY_SHA512 = "SHA-512";

	/**
	 *
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] decryptBASE64(String key) throws Exception {
		return (new BASE64Decoder()).decodeBuffer(key);
	}

	/**
	 *
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String encryptBASE64(byte[] key) throws Exception {
		return (new BASE64Encoder()).encodeBuffer(key);
	}

	/**
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptMD5(byte[] data) throws Exception {

		MessageDigest md5 = MessageDigest.getInstance(KEY_MD5);
		md5.update(data);

		return md5.digest();

	}

	/**
	 *
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptSHA(String shaType,byte[] data) throws Exception {

		MessageDigest sha = MessageDigest.getInstance(shaType);
		sha.update(data);

		return sha.digest();

	}

	/**
	 *
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptSHA1(byte[] data) throws Exception {

		return encryptSHA(KEY_SHA1, data);

	}
	
	/**
	 *
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptSHA256(byte[] data) throws Exception {

		return encryptSHA(KEY_SHA256, data);

	}
	
	/**
	 *
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptSHA512(byte[] data) throws Exception {

		return encryptSHA(KEY_SHA512, data);

	}

	/**
	 * 
	 * @param content
	 * @param charset
	 */
	public static String encryptMD5(String content, String charset) {
		try {
			byte[] b = encryptMD5(content.getBytes(charset));
			StringBuffer buffer = new StringBuffer();

			for (int i = 0; i < b.length; i++) {
				String shaHex = Integer.toHexString(b[i] & 0xFF);
				if (shaHex.length() < 2) {
					buffer.append(0);
				}
				buffer.append(shaHex);
			}
			return buffer.toString();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";

	}

	/**
	 */
	public static String encryptMD5(String content) {
		return encryptMD5(content, "UTF-8");
	}

	/**
	 * @return
	 */
	public static String encryptSHA(String shaType,String content, String charset) {
		try {
			byte[] b = encryptSHA(shaType,content.getBytes(charset));
			StringBuffer buffer = new StringBuffer();
			// 鐎涙濡弫鎵矋鏉烆剚宕叉稉锟介崡浣稿彋鏉╂稑鍩�閺侊拷
			for (int i = 0; i < b.length; i++) {
				String shaHex = Integer.toHexString(b[i] & 0xFF);
				if (shaHex.length() < 2) {
					buffer.append(0);
				}
				buffer.append(shaHex);
			}
			return buffer.toString();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	/**
	 * @return
	 */
	public static String encryptSHA(String shaType,String content){
		return encryptSHA(shaType,content,"UTF-8");
	}
	
	/**
	 * @return
	 */
	public static String encryptSHA1(String content,String charset){
		
		return encryptSHA(KEY_SHA1, content,charset);
	}
	
	
	/**
	 * @return
	 */
	public static String encryptSHA1(String content){
		return encryptSHA1(content,"UTF-8");
	}
	
	/**
	 * @return
	 */
	public static String encryptSHA256(String content,String charset){
		
		return encryptSHA(KEY_SHA256, content,charset);
	}
	
	
	/**
	 * @return
	 */
	public static String encryptSHA256(String content){
		return encryptSHA256(content,"UTF-8");
	}
	
	/**
	 * @return
	 */
	public static String encryptSHA512(String content,String charset){
		
		return encryptSHA(KEY_SHA512, content,charset);
	}
	
	
	public static String encryptSHA512(String content){
		return encryptSHA512(content,"UTF-8");
	}

}