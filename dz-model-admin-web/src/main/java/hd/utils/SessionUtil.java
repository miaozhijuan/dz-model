package hd.utils;

import hd.common.finals.UserFinalUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * session 工具类
 * @author maomao
 *
 */
public class SessionUtil {
	/**
	 * 获取PC端用户信息
	 * @param request
	 * @return
	 */
	public static User getUser(HttpServletRequest request){		
		User user =(User) request.getSession().getAttribute(UserFinalUtil.CURRENT_USER);
		return user;
	}
}