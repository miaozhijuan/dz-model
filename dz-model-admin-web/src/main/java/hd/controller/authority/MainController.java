package hd.controller.authority;

import hd.common.finals.UserFinalUtil;
import hd.common.finals.FinalUtil;
import hd.common.serach.SearchCondition;
import hd.common.serach.SearchConditions;
import hd.common.tools.BodyMsg;
import hd.common.tools.Md5Util;
import hd.entity.SysPermission;
import hd.entity.SysUser;
import hd.service.SysPermissionService;
import hd.service.SysUserService;
import hd.utils.MenuUtil;
import hd.utils.SessionUtil;
import hd.utils.User;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;

/**
 * 系统入口控制层
 * 
 * @author mao
 *
 */
@Controller
@RequestMapping("/authority/main")
public class MainController {

	@Reference
	private SysUserService sysUserService;

	@Reference
	private SysPermissionService sysPermissionService;

	/** 登陆页面 */
	private static final String LOGIN = "login";

	/** 404页面 */
	private static final String ERROR_404 = "error_404";

	/** 首页页面 */
	private static final String INDEX = "index";

	/** 修改密码 */
	private static final String UPDATEPWD = "updatePwd";

	/**
	 * 跳转登陆页
	 */
	@RequestMapping("/login.casual")
	public String login(HttpServletRequest request) {
		request.getSession().invalidate();
		return LOGIN;
	}

	/**
	 * 用户登陆
	 */
	@RequestMapping(value = "/loginIn.casual", method = RequestMethod.POST)
	@ResponseBody
	public BodyMsg loginIn(HttpServletRequest request, @RequestParam(value = "name", required = true) String name, @RequestParam(value = "pwd", required = true) String pwd,
			@RequestParam(value = "code", required = true) String code) {
		BodyMsg bodyMsg = new BodyMsg();
		if (!code.equals(request.getSession().getAttribute(UserFinalUtil.VERIFICATION_CODE))) {
			bodyMsg.isFail("验证码错误!");
			return bodyMsg;
		}
		SearchConditions scs = new SearchConditions();
		scs.addCondition("name", name, SearchCondition.OP_EQUAL);
		scs.addCondition("status", 1, SearchCondition.OP_EQUAL);
		scs.addCondition("removed", 0, SearchCondition.OP_EQUAL);
		List<SysUser> userList = sysUserService.list(scs);
		if (null == userList || userList.isEmpty()) {
			bodyMsg.isFail("用户名密码错误!");
			return bodyMsg;
		}
		SysUser sysuser = userList.get(0);
		if (!Md5Util.stringMD5(pwd + sysuser.getId() + FinalUtil.MD5_PWD_SALT, FinalUtil.CHARACTER_UFT_8).equals(sysuser.getPwd())) {
			bodyMsg.isFail("用户名密码错误!");
			return bodyMsg;
		}
		// 获取权限集合
		List<SysPermission> list = null;
		scs.clearCondition();
		// 超级管理员
		if (sysuser.getId().equals(UserFinalUtil.ADMIN_ID)) {
			scs.addCondition("removed", 0, SearchCondition.OP_EQUAL);
			scs.addCondition("status", 1, SearchCondition.OP_EQUAL);
			scs.setOrderBy("sort");
			scs.setPage(null);
			list = sysPermissionService.list(scs);
		} else {// 普通用户
			scs.addCondition("m1.id", sysuser.getId(), SearchCondition.OP_EQUAL);
			scs.addCondition("m1.removed", 0, SearchCondition.OP_EQUAL);
			scs.addCondition("m3.removed", 0, SearchCondition.OP_EQUAL);
			scs.addCondition("m5.removed", 0, SearchCondition.OP_EQUAL);
			scs.addCondition("m5.status", 1, SearchCondition.OP_EQUAL);
			scs.setOrderBy("sort");
			list = sysPermissionService.menuList(scs);
		}
		List<String> iList = new ArrayList<String>();
		List<String> uList = new ArrayList<String>();
		if (null != list && !list.isEmpty()) {
			for (SysPermission sysPermission : list) {
				iList.add(sysPermission.getId());
				uList.add(sysPermission.getUrl());
			}
		}
		User user = new User();
		user.setId(sysuser.getId());
		user.setRealName(sysuser.getRealName());
		user.setLoginTime(System.currentTimeMillis());
		user.setuList(uList);
		request.getSession().setAttribute(UserFinalUtil.CURRENT_USER, user);
		bodyMsg.putData(iList);
		bodyMsg.isSuccess();
		return bodyMsg;
	}

	/**
	 * 获取验证码
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/loginCode.casual", method = RequestMethod.GET)
	public void loginCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 验证码图片的宽度。
		int width = 100;
		// 验证码图片的高度。
		int height = 30;
		// 验证码字符个数
		int codeCount = 4;
		int x = 0;
		// 字体高度
		int fontHeight;
		int codeY;
		char[] codeSequence = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6',
				'7', '8', '9' };
		x = width / (codeCount + 1);
		fontHeight = height - 2;
		codeY = height - 4;
		BufferedImage buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = buffImg.createGraphics();
		// 创建一个随机数生成器类
		Random random = new Random();
		// 将图像填充为白色
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, width, height);
		// 创建字体，字体的大小应该根据图片的高度来定。
		Font font = new Font("Fixedsys", Font.PLAIN, fontHeight);
		// 设置字体。
		g.setFont(font);
		// 画边框。
		// g.setColor(Color.BLACK);
		// g.drawRect(0, 0, width - 1, height - 1);
		// 随机产生160条干扰线，使图象中的认证码不易被其它程序探测到。
		g.setColor(Color.BLACK);
		int num = 10;
		for (int i = 0; i < num; i++) {
			int x2 = random.nextInt(width);
			int y2 = random.nextInt(height);
			int xl = random.nextInt(12);
			int yl = random.nextInt(12);
			g.drawLine(x2, y2, x + xl, y2 + yl);
		}
		// randomCode用于保存随机产生的验证码，以便用户登录后进行验证。
		StringBuffer randomCode = new StringBuffer();
		int red = 0, green = 0, blue = 0;
		// 随机产生codeCount数字的验证码。
		for (int i = 0; i < codeCount; i++) {
			// 得到随机产生的验证码数字。
			String strRand = String.valueOf(codeSequence[random.nextInt(36)]);
			// 产生随机的颜色分量来构造颜色值，这样输出的每位数字的颜色值都将不同。
			red = random.nextInt(255);
			green = random.nextInt(255);
			blue = random.nextInt(255);
			// 用随机产生的颜色将验证码绘制到图像中。
			g.setColor(new Color(red, green, blue));
			g.drawString(strRand, (i + 1) * x, codeY);
			// 将产生的四个随机数组合在一起。
			randomCode.append(strRand);
		}
		// 将四位数字的验证码保存到Session中。
		request.getSession().setAttribute(UserFinalUtil.VERIFICATION_CODE, randomCode.toString());
		ServletOutputStream sos = response.getOutputStream();
		ImageIO.write(buffImg, "jpeg", sos);
		sos.close();
	}

	/**
	 * 注销
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/loginOut.casual")
	public String loginOut(HttpServletRequest request) {
		request.getSession().invalidate();
		return "redirect:/authority/main/login.casual";
	}

	/**
	 * 跳转404
	 */
	@RequestMapping("/error404.casual")
	public String error404() {
		return ERROR_404;
	}

	/**
	 * 跳转首页
	 */
	@RequestMapping("/index.do")
	public String index() {
		return INDEX;
	}

	/**
	 * 初始化主页菜单
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/tree.do", method = RequestMethod.GET)
	@ResponseBody
	public BodyMsg tree(HttpServletRequest request, Model model) {
		BodyMsg bodyMsg = new BodyMsg();
		User user = SessionUtil.getUser(request);
		List<SysPermission> list = null;
		// 超级管理员
		SearchConditions scs = new SearchConditions();
		if (user.getId().equals(UserFinalUtil.ADMIN_ID)) {
			scs.addCondition("removed", 0, SearchCondition.OP_EQUAL);
			scs.addCondition("status", 1, SearchCondition.OP_EQUAL);
			scs.addCondition("level", 0, SearchCondition.OP_EQUAL);
			scs.setOrderBy("sort");
			scs.setPage(null);
			list = sysPermissionService.list(scs);
		} else {// 普通用户
			scs.addCondition("m1.id", user.getId(), SearchCondition.OP_EQUAL);
			scs.addCondition("m1.removed", 0, SearchCondition.OP_EQUAL);
			scs.addCondition("m3.removed", 0, SearchCondition.OP_EQUAL);
			scs.addCondition("m5.removed", 0, SearchCondition.OP_EQUAL);
			scs.addCondition("m5.status", 1, SearchCondition.OP_EQUAL);
			scs.addCondition("m5.level", 0, SearchCondition.OP_EQUAL);
			scs.setOrderBy("sort");
			list = sysPermissionService.menuList(scs);
		}
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		if (null != list && !list.isEmpty()) {
			// 获取根目录
			List<SysPermission> rooList = MenuUtil.getSysMenuRooList(list);
			Map<String, Object> detilsMap = null;
			for (SysPermission sysPermission : rooList) {
				detilsMap = new HashMap<String, Object>(4);
				detilsMap.put("name", sysPermission.getName());
				detilsMap.put("type", MenuUtil.isParent(list, sysPermission));
				detilsMap.put("url", sysPermission.getUrl());
				detilsMap.put("children", setTree(list, sysPermission.getId()));
				dataList.add(detilsMap);
			}
		}
		bodyMsg.putData(dataList);
		bodyMsg.isSuccess();
		return bodyMsg;
	}

	/**
	 * 生成树形
	 * 
	 * @param list
	 * @param parentId
	 * @return
	 */
	public List<Map<String, Object>> setTree(List<SysPermission> list, String parentId) {
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		if (null != list && !list.isEmpty()) {
			Map<String, Object> detilsMap = null;
			for (SysPermission sysPermission : list) {
				if (parentId.equals(sysPermission.getParentId())) {
					detilsMap = new HashMap<String, Object>(4);
					detilsMap.put("name", sysPermission.getName());
					detilsMap.put("type", MenuUtil.isParent(list, sysPermission));
					detilsMap.put("url", sysPermission.getUrl());
					detilsMap.put("children", setTree(list, sysPermission.getId()));
					dataList.add(detilsMap);
				}
			}
		}
		return dataList;
	}

	/**
	 * 修改密码页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/updatePwd.do")
	public String updatePwd(HttpServletRequest request) {
		return UPDATEPWD;
	}

	/**
	 * 修改密码
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/updatePwdSave.do", method = RequestMethod.POST)
	@ResponseBody
	public BodyMsg updatePwdSave(HttpServletRequest request, @RequestParam(value = "oldPwd", required = true) String oldPwd, @RequestParam(value = "newPwd", required = true) String newPwd) {
		BodyMsg bodyMsg = new BodyMsg();
		User user = SessionUtil.getUser(request);
		SysUser sysUser = sysUserService.selectByPrimaryKey(user.getId());
		if (!sysUser.getPwd().equals(Md5Util.stringMD5(oldPwd + sysUser.getId() + FinalUtil.MD5_PWD_SALT, FinalUtil.CHARACTER_UFT_8))) {
			bodyMsg.isFail("旧密码[" + oldPwd + "]错误，请核实");
			return bodyMsg;
		}
		sysUser.setPwd(Md5Util.stringMD5(newPwd + sysUser.getId() + FinalUtil.MD5_PWD_SALT, FinalUtil.CHARACTER_UFT_8));
		sysUserService.updateByPrimaryKeySelective(sysUser);
		bodyMsg.isSuccess();
		request.getSession().invalidate();
		return bodyMsg;
	}
}