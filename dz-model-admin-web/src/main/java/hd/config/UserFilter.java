package hd.config;

import hd.common.finals.UserFinalUtil;
import hd.common.tools.BodyMsg;
import hd.utils.SessionUtil;
import hd.utils.User;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;

/**
 * 用户PC端访问过滤
 * 
 * @author maomao
 *
 */
@WebFilter(urlPatterns = "*.do")
public class UserFilter implements Filter {

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		String url = request.getServletPath();
		// 登陆失效或操作超时
		User user = SessionUtil.getUser(request);
		if (null == user || (System.currentTimeMillis() - user.getLoginTime() > UserFinalUtil.INVALID_TIME)) {
			response.sendRedirect(request.getContextPath() + "/authority/main/login.casual");
			return;
		}
		// 没此权限
		List<String> uList = user.getuList();
		if (!uList.contains(url)) {
			// 传统同步请求
			String header = request.getHeader("x-requested-with");
			if (null == header) {
				response.sendRedirect(request.getContextPath() + "/authority/main/error404.casual");
				return;
			}
			// Ajax亲求
			BodyMsg bodyMsg = new BodyMsg();
			bodyMsg.setCode(404);
			PrintWriter out = response.getWriter();
			out.write(JSONObject.toJSONString(bodyMsg));
			return;
		}
		user.setLoginTime(System.currentTimeMillis());
		request.getSession().setAttribute(UserFinalUtil.CURRENT_USER, user);
		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig config) throws ServletException {

	}
}