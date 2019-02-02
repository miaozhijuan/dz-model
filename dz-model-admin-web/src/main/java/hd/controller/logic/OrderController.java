package hd.controller.logic;

import hd.common.serach.JqGridHandler;
import hd.common.serach.SearchConditions;
import hd.common.serach.SearchResult;
import hd.entity.Order;
import hd.service.OrderService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;

/**
 * 订单控制层
 * 
 * @author mao
 *
 */
@Controller
@RequestMapping("/logic/order")
public class OrderController extends BaseController {

	@Reference
	private OrderService orderService;

	/** 列表页面 */
	private static final String MANAGER = "logic/order/manager";

	/**
	 * 首页跳转
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/manager.do", method = RequestMethod.GET)
	public String manager(HttpServletRequest request, Model model) {
		return MANAGER;
	}

	/**
	 * 列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/list.do", method = RequestMethod.GET)
	@ResponseBody
	public SearchResult<Order> list(HttpServletRequest request, HttpServletResponse response) {
		SearchConditions scs = new SearchConditions();
		new JqGridHandler(request).getWheres(scs);
		if (StringUtils.isBlank(scs.getOrderBy())) {
			scs.setOrderBy(" create_time desc ");
		}
		return orderService.getSearchResult(scs);
	}
}