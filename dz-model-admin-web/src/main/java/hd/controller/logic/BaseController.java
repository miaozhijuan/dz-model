package hd.controller.logic;

import hd.common.base.PcUserVo;
import hd.common.finals.UserFinalUtil;
import hd.entity.ClientUser;
import hd.service.ClientUserService;
import hd.service.StringRedisTemplateService;

import org.springframework.beans.BeanUtils;

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
	
	@Reference
	private ClientUserService clientUserService;

	/**
	 * 根据用户ID获取用户信息
	 * 
	 * @param token
	 * @return
	 */
	public PcUserVo getPcUserById(String id) {
		PcUserVo pcUserVo = null;
		if (!stringRedisTemplateService.hasKey(UserFinalUtil.PCUSER + id)) {
			ClientUser clientUser = clientUserService.selectByPrimaryKey(id);
			if(null != clientUser){
				BeanUtils.copyProperties(clientUser, pcUserVo);
			}
			return pcUserVo;
		}
		String str = stringRedisTemplateService.get(UserFinalUtil.PCUSER + id);
		pcUserVo = JSONObject.parseObject(str, PcUserVo.class);
		return pcUserVo;
	}
}