package hd.controller.logic;

import hd.common.serach.JqGridHandler;
import hd.common.serach.SearchConditions;
import hd.common.serach.SearchResult;
import hd.common.tools.BodyMsg;
import hd.entity.Goods;
import hd.service.GoodsService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;

/**
 * 商品控制层
 * 
 * @author mao
 *
 */
@Controller
@RequestMapping("/logic/goods")
public class GoodsController extends BaseController {

	@Reference
	private GoodsService goodsService;

	/** 列表页面 */
	private static final String MANAGER = "logic/goods/manager";

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
	public SearchResult<Goods> list(HttpServletRequest request, HttpServletResponse response) {
		SearchConditions scs = new SearchConditions();
		new JqGridHandler(request).getWheres(scs);
		if (StringUtils.isBlank(scs.getOrderBy())) {
			scs.setOrderBy(" create_time desc ");
		}
		return goodsService.getSearchResult(scs);
	}

	/**
	 * 审核
	 * 
	 * @param request
	 * @param id
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/check.do", method = RequestMethod.POST)
	@ResponseBody
	public BodyMsg check(HttpServletRequest request, @RequestParam(value = "id", required = true) String id, @RequestParam(value = "status", required = true) Byte status) {
		BodyMsg bodyMsg = new BodyMsg();
		Goods goods = new Goods();
		goods.setId(id);
		goods.setStatus(status);
		goodsService.updateByPrimaryKeySelective(goods);
		bodyMsg.isSuccess();
		return bodyMsg;
	}
}