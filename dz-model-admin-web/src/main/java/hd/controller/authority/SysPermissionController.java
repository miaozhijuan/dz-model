package hd.controller.authority;

import hd.common.finals.FinalUtil;
import hd.common.finals.UserFinalUtil;
import hd.common.serach.JqGridHandler;
import hd.common.serach.SearchCondition;
import hd.common.serach.SearchConditions;
import hd.common.serach.SearchResult;
import hd.common.tools.BodyMsg;
import hd.common.tools.IdUtil;
import hd.entity.SysPermission;
import hd.service.SysPermissionService;
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
 * 系统权限控制层
 * 
 * @author mao
 *
 */
@Controller
@RequestMapping("/authority/sysPermission")
public class SysPermissionController extends BaseController<SysPermission> {

	@Reference
	private SysPermissionService sysPermissionService;

	/** 列表页面 */
	private static final String MANAGER = "authority/sysPermission/manager";
	/** 添加页面 */
	private static final String TOADD = "authority/sysPermission/toAdd";
	/** 修改页面 */
	private static final String TOUPDATE = "authority/sysPermission/toUpdate";
	/** 角色分配页 */
	private static final String ALLOTPERMISSION = "authority/sysPermission/allotPermission";

	@Override
	public String manager(HttpServletRequest request, Model model) {
		return MANAGER;
	}

	@Override
	public SearchResult<SysPermission> list(HttpServletRequest request, HttpServletResponse response) {
		String parentId = request.getParameter("parentId");
		if (StringUtils.isBlank(parentId)) {
			parentId = UserFinalUtil.PERMISSION_ID;
		}
		SearchConditions scs = new SearchConditions();
		new JqGridHandler(request).getWheres(scs);
		scs.addCondition("parent_id", parentId, SearchCondition.OP_EQUAL);
		scs.addCondition("removed", 0, SearchCondition.OP_EQUAL);
		if (StringUtils.isBlank(scs.getOrderBy())) {
			scs.setOrderBy(" create_time desc ");
		}
		return sysPermissionService.getSearchResult(scs);
	}

	@Override
	public String toAdd(HttpServletRequest request, Model model) {
		String parentId = request.getParameter("parentId").trim();
		// 根目录
		if (UserFinalUtil.PERMISSION_ID.equals(parentId)) {
			model.addAttribute("parentId", parentId);
			model.addAttribute("parentName", "根目录");
		} else {
			SysPermission parentSysPermission = sysPermissionService.selectByPrimaryKey(parentId);
			model.addAttribute("parentId", parentId);
			model.addAttribute("parentName", parentSysPermission.getName());
		}
		return TOADD;
	}

	@Override
	public BodyMsg add(HttpServletRequest request, SysPermission record) {
		BodyMsg bodyMsg = new BodyMsg();
		record.setId(IdUtil.getUUID());
		record.setCreateTime(new Date());
		record.setCreateUser(SessionUtil.getUser(request).getId());
		sysPermissionService.insertSelective(record);
		bodyMsg.isSuccess();
		return bodyMsg;
	}

	@Override
	public String toUpdate(HttpServletRequest request, Model model, @RequestParam(value = "id", required = true) String id) {
		SysPermission sysPermission = sysPermissionService.selectByPrimaryKey(id);
		model.addAttribute("sysPermission", sysPermission);
		// 根目录
		if (UserFinalUtil.PERMISSION_ID.equals(sysPermission.getParentId())) {
			model.addAttribute("parentId", sysPermission.getParentId());
			model.addAttribute("parentName", "根目录");
		} else {
			SysPermission parentSysPermission = sysPermissionService.selectByPrimaryKey(sysPermission.getParentId());
			model.addAttribute("parentId", sysPermission.getParentId());
			model.addAttribute("parentName", parentSysPermission.getName());
		}
		return TOUPDATE;
	}

	@Override
	public BodyMsg update(HttpServletRequest request, SysPermission record) {
		BodyMsg bodyMsg = new BodyMsg();
		record.setUpdateTime(new Date());
		record.setUpdateUser(SessionUtil.getUser(request).getId());
		sysPermissionService.updateByPrimaryKeySelective(record);
		bodyMsg.isSuccess();
		return bodyMsg;
	}

	@Override
	public BodyMsg delete(HttpServletRequest request, @RequestParam(value = "ids", required = true) String ids) {
		BodyMsg bodyMsg = new BodyMsg();
		JSONArray jsonArray = JSONArray.parseArray(ids);
		SysPermission sysPermission = null;
		List<SysPermission> list = null;
		SearchConditions scs = new SearchConditions();
		for (Object id : jsonArray) {
			scs.clearCondition();
			scs.addCondition("parent_id", id, SearchCondition.OP_EQUAL);
			list = sysPermissionService.list(scs);
			if (list != null) {
				bodyMsg.isFail("有子集菜单，请核实");
				return bodyMsg;
			}
			sysPermission = new SysPermission();
			sysPermission.setId(id.toString());
			sysPermission.setRemoved(FinalUtil.BYTE_1);
			sysPermissionService.updateByPrimaryKeySelective(sysPermission);
		}
		bodyMsg.isSuccess();
		return bodyMsg;
	}

	/**
	 * 初始化权限菜单
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/sysPermissionTree.do", method = RequestMethod.GET)
	@ResponseBody
	public BodyMsg sysPermissionTree() {
		BodyMsg bodyMsg = new BodyMsg();
		bodyMsg.putData(sysPermissionService.zTreeNodeList());
		bodyMsg.isSuccess();
		return bodyMsg;
	}

	/**
	 * 角色分配菜单页
	 * 
	 * @param request
	 * @param model
	 * @param roleId
	 * @return
	 */
	@RequestMapping("/allotPermission.do")
	public String allotPermission(HttpServletRequest request, Model model, @RequestParam(value = "roleId", required = true) String roleId) {
		model.addAttribute("roleId", roleId);
		return ALLOTPERMISSION;
	}

	/**
	 * 获取权限树形
	 * 
	 * @param request
	 * @param roleId
	 * @return
	 */
	@RequestMapping(value = "/allotPermissionTree.do", method = RequestMethod.GET)
	@ResponseBody
	public BodyMsg allotRoleTree(HttpServletRequest request, @RequestParam(value = "roleId", required = true) String roleId) {
		BodyMsg bodyMsg = new BodyMsg();
		bodyMsg.putData(sysPermissionService.allotPermission(roleId));
		bodyMsg.isSuccess();
		return bodyMsg;
	}

	/**
	 * 保存分配权限
	 * 
	 * @param request
	 * @param roleId
	 * @param permissionIds
	 * @return
	 */
	@RequestMapping(value = "/savePermission.do", method = RequestMethod.GET)
	@ResponseBody
	public BodyMsg saveRole(HttpServletRequest request, @RequestParam(value = "roleId", required = true) String roleId, @RequestParam(value = "permissionIds", required = true) String permissionIds) {
		BodyMsg bodyMsg = new BodyMsg();
		sysPermissionService.savePermission(roleId, permissionIds);
		bodyMsg.isSuccess();
		return bodyMsg;
	}
}