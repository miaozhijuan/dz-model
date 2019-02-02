package hd.common.tools;

import java.security.MessageDigest;

import org.apache.commons.lang3.StringUtils;

/**
 * MD5加密
 * 
 * @author maomao
 *
 */
public class Md5Util {

	/**
	 * 返回加密后的字符串
	 * 
	 * @param pwd
	 * @param code
	 * @return
	 */
	public static String stringMD5(String pwd, String code) {
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			byte[] inputByteArray = null;
			if (StringUtils.isNotBlank(code)) {
				inputByteArray = pwd.getBytes(code);
			} else {
				inputByteArray = pwd.getBytes();
			}
			messageDigest.update(inputByteArray);
			byte[] resultByteArray = messageDigest.digest();
			return byteArrayToHex(resultByteArray);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 返回加密数据
	 * 
	 * @param byteArray
	 * @return
	 */
	public static String byteArrayToHex(byte[] byteArray) {
		char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
		char[] resultCharArray = new char[byteArray.length * 2];
		int index = 0;
		for (byte b : byteArray) {
			resultCharArray[index++] = hexDigits[b >>> 4 & 0xf];
			resultCharArray[index++] = hexDigits[b & 0xf];
		}
		return new String(resultCharArray);
	}
}