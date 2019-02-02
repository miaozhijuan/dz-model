package hd.controller.authority;

import hd.common.serach.SearchResult;
import hd.common.tools.BodyMsg;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 控制层基类
 * 
 * @author maomao
 *
 */
public abstract class BaseController<T> {

	/**
	 * 首页跳转
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/manager.do", method = RequestMethod.GET)
	public abstract String manager(HttpServletRequest request, Model model);

	/**
	 * 查询集合
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/list.do", method = RequestMethod.GET)
	@ResponseBody
	public abstract SearchResult<T> list(HttpServletRequest request, HttpServletResponse response);

	/**
	 * 跳转新增页面
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/toAdd.do", method = RequestMethod.GET)
	public abstract String toAdd(HttpServletRequest request, Model model);

	/**
	 * 新增保存
	 * 
	 * @param request
	 * @param record
	 * @return
	 */
	@RequestMapping(value = "/add.do", method = RequestMethod.POST)
	@ResponseBody
	public abstract BodyMsg add(HttpServletRequest request, T record);

	/**
	 * 跳转修改页面
	 * 
	 * @param request
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/toUpdate.do", method = RequestMethod.GET)
	public abstract String toUpdate(HttpServletRequest request, Model model, String id);

	/**
	 * 修改保存
	 * 
	 * @param request
	 * @param record
	 * @return
	 */
	@RequestMapping(value = "/update.do", method = RequestMethod.POST)
	@ResponseBody
	public abstract BodyMsg update(HttpServletRequest request, T record);

	/**
	 * 删除
	 * 
	 * @param request
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/delete.do", method = RequestMethod.GET)
	@ResponseBody
	public abstract BodyMsg delete(HttpServletRequest request, String ids);

}