package hd.controller.authority;

import hd.common.finals.FinalUtil;
import hd.common.serach.JqGridHandler;
import hd.common.serach.SearchCondition;
import hd.common.serach.SearchConditions;
import hd.common.serach.SearchResult;
import hd.common.tools.BodyMsg;
import hd.common.tools.IdUtil;
import hd.entity.SysRole;
import hd.service.SysRoleService;
import hd.utils.SessionUtil;

import java.util.Date;
import java.util.List;

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
import com.alibaba.fastjson.JSONArray;

/**
 * 系统角色控制层
 * 
 * @author mao
 *
 */
@Controller
@RequestMapping("/authority/sysRole")
public class SysRoleController extends BaseController<SysRole> {

	@Reference
	private SysRoleService sysRoleService;

	/** 列表页面 */
	private static final String MANAGER = "authority/sysRole/manager";
	/** 添加页面 */
	private static final String TOADD = "authority/sysRole/toAdd";
	/** 修改页面 */
	private static final String TOUPDATE = "authority/sysRole/toUpdate";
	/** 角色分配页 */
	private static final String ALLOTROLE = "authority/sysRole/allotRole";

	@Override
	public String manager(HttpServletRequest request, Model model) {
		return MANAGER;
	}

	@Override
	public SearchResult<SysRole> list(HttpServletRequest request, HttpServletResponse response) {
		SearchConditions scs = new SearchConditions();
		new JqGridHandler(request).getWheres(scs);
		if (StringUtils.isBlank(scs.getOrderBy())) {
			scs.setOrderBy(" create_time desc ");
		}
		scs.addCondition("removed", 0, SearchCondition.OP_EQUAL);
		return sysRoleService.getSearchResult(scs);
	}

	@Override
	public String toAdd(HttpServletRequest request, Model model) {
		return TOADD;
	}

	@Override
	public BodyMsg add(HttpServletRequest request, SysRole record) {
		BodyMsg bodyMsg = new BodyMsg();
		SearchConditions scs = new SearchConditions();
		scs.addCondition("name", record.getName(), SearchCondition.OP_EQUAL);
		scs.addCondition("removed", 0, SearchCondition.OP_EQUAL);
		List<SysRole> list = sysRoleService.list(scs);
		if (null != list && !list.isEmpty()) {
			bodyMsg.isFail("角色名[" + record.getName() + "]已存在，请核实");
			return bodyMsg;
		}
		record.setId(IdUtil.getUUID());
		record.setCreateTime(new Date());
		record.setCreateUser(SessionUtil.getUser(request).getId());
		sysRoleService.insertSelective(record);
		bodyMsg.isSuccess();
		return bodyMsg;
	}

	@Override
	public String toUpdate(HttpServletRequest request, Model model, @RequestParam(value = "id", required = true) String id) {
		model.addAttribute("sysRole", sysRoleService.selectByPrimaryKey(id));
		return TOUPDATE;
	}

	@Override
	public BodyMsg update(HttpServletRequest request, SysRole record) {
		BodyMsg bodyMsg = new BodyMsg();
		SearchConditions scs = new SearchConditions();
		scs.addCondition("name", record.getName(), SearchCondition.OP_EQUAL);
		scs.addCondition("removed", 0, SearchCondition.OP_EQUAL);
		List<SysRole> list = sysRoleService.list(scs);
		SysRole sysRole = sysRoleService.selectByPrimaryKey(record.getId());
		if (!sysRole.getName().equals(record.getName()) && null != list && !list.isEmpty()) {
			bodyMsg.isFail("角色名[" + record.getName() + "]已存在，请核实");
			return bodyMsg;
		}
		record.setUpdateTime(new Date());
		record.setUpdateUser(SessionUtil.getUser(request).getId());
		sysRoleService.updateByPrimaryKeySelective(record);
		bodyMsg.isSuccess();
		return bodyMsg;
	}

	@Override
	public BodyMsg delete(HttpServletRequest request, @RequestParam(value = "ids", required = true) String ids) {
		BodyMsg bodyMsg = new BodyMsg();
		JSONArray jsonArray = JSONArray.parseArray(ids);
		SysRole sysRole = null;
		for (Object id : jsonArray) {
			sysRole = new SysRole();
			sysRole.setId(id.toString());
			sysRole.setRemoved(FinalUtil.BYTE_1);
			sysRoleService.updateByPrimaryKeySelective(sysRole);
		}
		bodyMsg.isSuccess();
		return bodyMsg;
	}

	/**
	 * 用户分配角色页
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/allotRole.do")
	public String allotRole(HttpServletRequest request, Model model, @RequestParam(value = "userId", required = true) String userId) {
		model.addAttribute("userId", userId);
		return ALLOTROLE;
	}

	/**
	 * 获取用户角色集合
	 * 
	 * @param request
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/allotRoleTree.do", method = RequestMethod.GET)
	@ResponseBody
	public BodyMsg allotRoleTree(HttpServletRequest request, @RequestParam(value = "userId", required = true) String userId) {
		BodyMsg bodyMsg = new BodyMsg();
		bodyMsg.putData(sysRoleService.allotRole(userId));
		bodyMsg.isSuccess();
		return bodyMsg;
	}

	/**
	 * 保存分配角色
	 * 
	 * @param request
	 * @param userId
	 * @param roleIds
	 * @return
	 */
	@RequestMapping(value = "/saveRole.do", method = RequestMethod.GET)
	@ResponseBody
	public BodyMsg saveRole(HttpServletRequest request, @RequestParam(value = "userId", required = true) String userId, @RequestParam(value = "roleIds", required = true) String roleIds) {
		BodyMsg bodyMsg = new BodyMsg();
		sysRoleService.saveRole(userId, roleIds);
		bodyMsg.isSuccess();
		return bodyMsg;
	}
}