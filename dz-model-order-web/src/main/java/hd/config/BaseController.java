package hd.config;

import hd.common.base.PcUserVo;
import hd.common.finals.UserFinalUtil;
import hd.service.StringRedisTemplateService;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;

/**
 * 
 * @author maomao
 *
 */
public class BaseController {

	@Reference
	private StringRedisTemplateService stringRedisTemplateService;

	/**
	 * 根据token获取用户信息
	 * 
	 * @param token
	 * @return
	 */
	public PcUserVo getPcUserByToken(String token) {
		PcUserVo pcUserVo = null;
		if (!stringRedisTemplateService.hasKey(UserFinalUtil.TOKEN + token)) {
			return pcUserVo;
		}
		String id = stringRedisTemplateService.get(UserFinalUtil.TOKEN + token);
		pcUserVo = getPcUserById(id);
		return pcUserVo;
	}

	/**
	 * 根据用户ID获取用户信息
	 * 
	 * @param token
	 * @return
	 */
	public PcUserVo getPcUserById(String id) {
		PcUserVo pcUserVo = null;
		if (!stringRedisTemplateService.hasKey(UserFinalUtil.PCUSER + id)) {
			return pcUserVo;
		}
		String str = stringRedisTemplateService.get(UserFinalUtil.PCUSER + id);
		pcUserVo = JSONObject.parseObject(str, PcUserVo.class);
		return pcUserVo;
	}
}