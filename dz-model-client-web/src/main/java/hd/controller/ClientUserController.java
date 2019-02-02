package hd.controller;

import hd.common.base.PcUserVo;
import hd.common.finals.FinalUtil;
import hd.common.finals.UserFinalUtil;
import hd.common.tools.BodyMsg;
import hd.common.tools.IdUtil;
import hd.common.tools.Md5Util;
import hd.entity.ClientUser;
import hd.service.ClientUserService;
import hd.service.StringRedisTemplateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;

/**
 * 用户接口
 * 
 * @author mao
 *
 */
@Api(value = "客户端用户", tags = { "客户端用户接口" })
@RestController
@RequestMapping("/clientUser")
public class ClientUserController {

	@Reference
	private ClientUserService clientUserService;

	@Reference
	private StringRedisTemplateService stringRedisTemplateService;

	/**
	 * 客户端注册
	 * 
	 * @param phone
	 * @param pwd
	 * @return
	 */
	@ApiOperation(value = "注册", notes = "注册", httpMethod = FinalUtil.HTTP_POST, response = BodyMsg.class)
	@RequestMapping(value = "/register.casual", method = RequestMethod.POST)
	public BodyMsg register(@ApiParam(required = true, name = "phone", value = "手机号") @RequestParam(required = true, value = "phone") String phone,
			@ApiParam(required = true, name = "pwd", value = "密码") @RequestParam(required = true, value = "pwd") String pwd) {
		BodyMsg bodyMsg = new BodyMsg();
		ClientUser clientUser = clientUserService.selectByPhone(phone);
		if (null != clientUser) {
			bodyMsg.isFail("账户[" + phone + "]在系统已存在，请核实");
			return bodyMsg;
		}
		clientUser = new ClientUser();
		clientUser.setId(IdUtil.getUUID());
		clientUser.setPhone(phone);
		clientUser.setPwd(Md5Util.stringMD5(pwd + clientUser.getId() + FinalUtil.MD5_PWD_SALT, FinalUtil.CHARACTER_UFT_8));
		clientUser.setStatus(FinalUtil.BYTE_1);
		clientUser.setCreateTime(new Date());
		clientUserService.insertSelective(clientUser);
		// 缓存用户信息
		PcUserVo pcUserVo = new PcUserVo();
		BeanUtils.copyProperties(clientUser, pcUserVo);
		stringRedisTemplateService.set(UserFinalUtil.PCUSER + pcUserVo.getId(), JSONObject.toJSONString(pcUserVo));
		bodyMsg.isSuccess();
		return bodyMsg;
	}

	/**
	 * 客户端登陆
	 * 
	 * @param phone
	 * @param pwd
	 * @return
	 */
	@ApiOperation(value = "登陆", notes = "登陆", httpMethod = FinalUtil.HTTP_POST, response = BodyMsg.class)
	@RequestMapping(value = "/logIn.casual", method = RequestMethod.POST)
	public BodyMsg logIn(@ApiParam(required = true, name = "phone", value = "手机号") @RequestParam(required = true, value = "phone") String phone,
			@ApiParam(required = true, name = "pwd", value = "密码") @RequestParam(required = true, value = "pwd") String pwd) {
		BodyMsg bodyMsg = new BodyMsg();
		ClientUser clientUser = clientUserService.selectByPhone(phone);
		if (null == clientUser) {
			bodyMsg.isFail("用户名密码错误");
			return bodyMsg;
		}
		if (!clientUser.getPwd().equals(Md5Util.stringMD5(pwd + clientUser.getId() + FinalUtil.MD5_PWD_SALT, FinalUtil.CHARACTER_UFT_8))) {
			bodyMsg.isFail("用户名密码错误");
			return bodyMsg;
		}
		String token = IdUtil.getUUID();
		// 缓存用户信息
		PcUserVo pcUserVo = new PcUserVo();
		BeanUtils.copyProperties(clientUser, pcUserVo);
		stringRedisTemplateService.set(UserFinalUtil.TOKEN + token, pcUserVo.getId(), UserFinalUtil.TOKEN_TIME, TimeUnit.MILLISECONDS);
		if (!stringRedisTemplateService.hasKey(UserFinalUtil.PCUSER + pcUserVo.getId())) {
			stringRedisTemplateService.set(UserFinalUtil.PCUSER + pcUserVo.getId(), JSONObject.toJSONString(pcUserVo));
		}
		bodyMsg.putData(token);
		bodyMsg.isSuccess();
		return bodyMsg;
	}
}
