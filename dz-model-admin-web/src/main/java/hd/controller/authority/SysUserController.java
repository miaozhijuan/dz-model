package hd.controller.authority;

import hd.common.finals.FinalUtil;
import hd.common.serach.JqGridHandler;
import hd.common.serach.SearchCondition;
import hd.common.serach.SearchConditions;
import hd.common.serach.SearchResult;
import hd.common.tools.BodyMsg;
import hd.common.tools.IdUtil;
import hd.common.tools.Md5Util;
import hd.entity.SysUser;
import hd.service.SysUserService;
import hd.utils.SessionUtil;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONArray;

/**
 * 系统用户控制层
 * 
 * @author maomao
 *
 */
@Controller
@RequestMapping("/authority/sysUser")
public class SysUserController extends BaseController<SysUser> {

	@Reference
	private SysUserService sysUserService;

	/** 列表页面 */
	private static final String MANAGER = "authority/sysUser/manager";
	/** 添加页面 */
	private static final String TOADD = "authority/sysUser/toAdd";
	/** 修改页面 */
	private static final String TOUPDATE = "authority/sysUser/toUpdate";

	@Override
	public String manager(HttpServletRequest request, Model model) {
		return MANAGER;
	}

	@Override
	public SearchResult<SysUser> list(HttpServletRequest request, HttpServletResponse response) {
		SearchConditions scs = new SearchConditions();
		new JqGridHandler(request).getWheres(scs);
		if (StringUtils.isBlank(scs.getOrderBy())) {
			scs.setOrderBy(" create_time desc ");
		}
		scs.addCondition("removed", 0, SearchCondition.OP_EQUAL);
		return sysUserService.getSearchResult(scs);
	}

	@Override
	public String toAdd(HttpServletRequest request, Model model) {
		return TOADD;
	}

	@Override
	public BodyMsg add(HttpServletRequest request, SysUser record) {
		BodyMsg bodyMsg = new BodyMsg();
		SearchConditions scs = new SearchConditions();
		scs.addCondition("name", record.getName(), SearchCondition.OP_EQUAL);
		scs.addCondition("removed", 0, SearchCondition.OP_EQUAL);
		List<SysUser> list = sysUserService.list(scs);
		if (null != list && !list.isEmpty()) {
			bodyMsg.isFail("登陆名[" + record.getName() + "]已存在，请核实");
			return bodyMsg;
		}
		record.setId(IdUtil.getUUID());
		record.setCreateTime(new Date());
		record.setCreateUser(SessionUtil.getUser(request).getId());
		record.setPwd(Md5Util.stringMD5(record.getPwd() + record.getId() + FinalUtil.MD5_PWD_SALT, FinalUtil.CHARACTER_UFT_8));
		sysUserService.insertSelective(record);
		bodyMsg.isSuccess();
		return bodyMsg;
	}

	@Override
	public String toUpdate(HttpServletRequest request, Model model, @RequestParam(value = "id", required = true) String id) {
		model.addAttribute("sysUser", sysUserService.selectByPrimaryKey(id));
		return TOUPDATE;
	}

	@Override
	public BodyMsg update(HttpServletRequest request, SysUser record) {
		BodyMsg bodyMsg = new BodyMsg();
		SearchConditions scs = new SearchConditions();
		scs.addCondition("name", record.getName(), SearchCondition.OP_EQUAL);
		scs.addCondition("removed", 0, SearchCondition.OP_EQUAL);
		List<SysUser> list = sysUserService.list(scs);
		SysUser sysUser = sysUserService.selectByPrimaryKey(record.getId());
		if (!sysUser.getName().equals(record.getName()) && null != list && !list.isEmpty()) {
			bodyMsg.isFail("登陆名[" + record.getName() + "]已存在，请核实");
			return bodyMsg;
		}
		// 当密码发生改变时修改密码
		if (!sysUser.getPwd().equals(record.getPwd())) {
			record.setPwd(Md5Util.stringMD5(record.getPwd() + record.getId() + FinalUtil.MD5_PWD_SALT, FinalUtil.CHARACTER_UFT_8));
		} else {
			record.setPwd(null);
		}
		record.setUpdateTime(new Date());
		record.setUpdateUser(SessionUtil.getUser(request).getId());
		sysUserService.updateByPrimaryKeySelective(record);
		bodyMsg.isSuccess();
		return bodyMsg;
	}

	@Override
	public BodyMsg delete(HttpServletRequest request, @RequestParam(value = "ids", required = true) String ids) {
		BodyMsg bodyMsg = new BodyMsg();
		JSONArray jsonArray = JSONArray.parseArray(ids);
		SysUser sysUser = null;
		for (Object id : jsonArray) {
			sysUser = new SysUser();
			sysUser.setId(id.toString());
			sysUser.setRemoved(FinalUtil.BYTE_1);
			sysUserService.updateByPrimaryKeySelective(sysUser);
		}
		bodyMsg.isSuccess();
		return bodyMsg;
	}
}