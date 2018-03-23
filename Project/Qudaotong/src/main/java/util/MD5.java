package util;

import java.security.MessageDigest;

/**
 * 概要：MD5算法
 * 类名称：MD5
 * @author 
 */
public class MD5 {

	/**
	 * md5加密算法
	 * @param str
	 * @return
	 */
	public static String md5(String str) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(str.getBytes());
			byte b[] = md.digest();
			int i;
			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
			str = buf.toString();
		} catch (Exception e) {
			e.printStackTrace();

		}
		return str;
	}

	public static String md516(String str) {
		return MD5.md5(str).toString().substring(8, 24);
	}
}
