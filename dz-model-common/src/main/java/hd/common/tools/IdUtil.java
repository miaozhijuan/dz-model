package hd.common.tools;

import java.util.UUID;

/**
 * 生成唯一标示
 * 
 * @author maomao
 *
 */
public class IdUtil {
	public static String getUUID() {
		return UUID.randomUUID().toString();
	}
}