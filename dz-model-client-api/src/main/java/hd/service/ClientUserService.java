package hd.service;

import hd.common.base.BaseService;
import hd.entity.ClientUser;

/**
 * 客户端用户接口
 * 
 * @author mao
 *
 */
public interface ClientUserService extends BaseService<ClientUser> {

	/**
	 * 根据手机号返回对象
	 * 
	 * @param phone
	 * @return
	 */
	ClientUser selectByPhone(String phone);
}
