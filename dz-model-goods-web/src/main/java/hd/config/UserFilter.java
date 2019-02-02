package hd.config;

import hd.common.base.PcUserVo;
import hd.common.finals.FinalUtil;
import hd.common.tools.BodyMsg;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONObject;

/**
 * 用户PC端访问过滤
 * 
 * @author maomao
 *
 */
@WebFilter(urlPatterns = "*.do")
public class UserFilter extends BaseController implements Filter {

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
		response.setHeader("Access-Control-Max-Age", "3600");
		response.setHeader("Access-Control-Allow-Headers", "Authentication,X-Requested-With,Origin,Content-Type, Accept ,token");
		response.setHeader("Access-Control-Allow-Credentials", "true");
		// 当亲求类型为OPTIONS时不过滤
		if (FinalUtil.HTTP_OPTIONS.equals(request.getMethod())) {
			chain.doFilter(request, response);
			return;
		}
		String token = request.getHeader("token");
		if (StringUtils.isBlank(token)) {
			BodyMsg bodyMsg = new BodyMsg();
			bodyMsg.setCode(1000);
			PrintWriter out = response.getWriter();
			out.write(JSONObject.toJSONString(bodyMsg));
			return;
		}
		PcUserVo pcUserVo = getPcUserByToken(token);
		if (null == pcUserVo) {
			BodyMsg bodyMsg = new BodyMsg();
			bodyMsg.setCode(1000);
			PrintWriter out = response.getWriter();
			out.write(JSONObject.toJSONString(bodyMsg));
			return;
		}
		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig config) throws ServletException {

	}
}